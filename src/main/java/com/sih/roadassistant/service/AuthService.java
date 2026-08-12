package com.sih.roadassistant.service;

import com.sih.roadassistant.dto.AuthRequest;
import com.sih.roadassistant.model.User;
import com.sih.roadassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final Set<String> DISPOSABLE_EMAIL_DOMAINS = Set.of(
            "yopmail.com", "tempmail.com", "10minutemail.com", "temp-mail.org",
            "sharklasers.com", "guerrillamail.com", "dispostable.com", "mailinator.com"
    );
    private final SecureRandom secureRandom = new SecureRandom();

    public User registerUser(AuthRequest request) {
        String normalizedEmail = request.getEmail().toLowerCase().trim();
        if (isDisposableEmail(normalizedEmail)) {
            throw new RuntimeException("Temporary/Disposable email addresses are not allowed.");
        }
        if (!hasValidMxRecord(normalizedEmail)) {
            throw new RuntimeException("Registration blocked: The email domain does not have a valid mail server. Please check for typos.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new RuntimeException("Email is already registered");
        }

        String verificationCode = String.format("%06d", secureRandom.nextInt(999999));
        User user = User.builder()
                .username(request.getUsername())
                .email(normalizedEmail)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .isVerified(false)
                .verificationCode(verificationCode)
                .verificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15))
                .build();

        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser.getEmail(), verificationCode);
        return savedUser;
    }

    public boolean verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getIsVerified()){
            throw new RuntimeException("Email is already verified");
        }
        if (user.getVerificationCodeExpiresAt() == null || LocalDateTime.now().isAfter(user.getVerificationCodeExpiresAt())) {
            throw new RuntimeException("Verification code has expired. Please request a new code.");
        }
        if (user.getVerificationCode() != null && user.getVerificationCode().equals(code)) {
            user.setIsVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        throw new RuntimeException("Invalid verification code.");
    }

    public User loginUser(AuthRequest request) {
        User user = null;
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            user = userRepository.findByEmail(request.getEmail()).orElse(null);
        }
        if (user == null && request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            user = userRepository.findByUsername(request.getUsername()).orElse(null);
        }

        if (user == null) {
            throw new RuntimeException("Invalid email/username or password");
        }

        if (!user.getIsVerified()) {
            throw new RuntimeException("Please verify your email address first.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email/username or password");
        }

        return user;
    }
    private boolean isDisposableEmail(String email) {
        if (email == null || !email.contains("@")) return false;
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase().trim();
        return DISPOSABLE_EMAIL_DOMAINS.contains(domain);
    }
    private boolean hasValidMxRecord(String email) {
        if (email == null || !email.contains("@")) return false;
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase().trim();
        try {
            java.util.Hashtable<String, String> env = new java.util.Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            javax.naming.directory.DirContext ictx = new javax.naming.directory.InitialDirContext(env);
            javax.naming.directory.Attributes attrs = ictx.getAttributes(domain, new String[] { "MX" });
            javax.naming.directory.Attribute attr = attrs.get("MX");
            return (attr != null && attr.size() > 0);
        } catch (Exception e) {
            return false;
        }
    }
    public void resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getIsVerified()) {
            throw new RuntimeException("Account is already verified");
        }

        // Rate Limit Check: Enforce a 60-second cooldown
        if (user.getVerificationCodeExpiresAt() != null) {
            LocalDateTime cooldownLimit = user.getVerificationCodeExpiresAt().minusMinutes(14); // 60 seconds after last request
            if (LocalDateTime.now().isBefore(cooldownLimit)) {
                long secondsRemaining = java.time.Duration.between(LocalDateTime.now(), cooldownLimit).getSeconds();
                throw new RuntimeException("Please wait " + secondsRemaining + " seconds before requesting another code.");
            }
        }
        String verificationCode = String.format("%06d", secureRandom.nextInt(999999));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);
    }

    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getResetCodeExpiresAt() != null) {
            LocalDateTime cooldownLimit = user.getResetCodeExpiresAt().minusMinutes(14); // 60 seconds cooldown
            if (LocalDateTime.now().isBefore(cooldownLimit)) {
                long secondsRemaining = java.time.Duration.between(LocalDateTime.now(), cooldownLimit).getSeconds();
                throw new RuntimeException("Please wait " + secondsRemaining + " seconds before requesting another reset code.");
            }
        }

        String resetCode = String.format("%06d", secureRandom.nextInt(999999));
        user.setResetCode(resetCode);
        user.setResetCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);
        emailService.sendResetPasswordEmail(user.getEmail(), resetCode);
    }
    public void resetPassword(String email, String resetCode, String newPassword) {
        User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getResetCodeExpiresAt() == null || LocalDateTime.now().isAfter(user.getResetCodeExpiresAt())) {
            throw new RuntimeException("Reset code has expired. Please request a new one.");
        }

        if (user.getResetCode() == null || !user.getResetCode().equals(resetCode)) {
            throw new RuntimeException("Invalid reset code");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setResetCode(null);
        user.setResetCodeExpiresAt(null);
        userRepository.save(user);
    }
}