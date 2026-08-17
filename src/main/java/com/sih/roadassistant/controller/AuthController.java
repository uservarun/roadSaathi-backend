package com.sih.roadassistant.controller;

import com.sih.roadassistant.dto.AuthRequest;
import com.sih.roadassistant.model.User;
import com.sih.roadassistant.service.AuthService;
import com.sih.roadassistant.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private jakarta.servlet.http.HttpServletRequest httpServletRequest;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthRequest request) {
        try {
            String clientIp = httpServletRequest.getRemoteAddr();
            String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                clientIp = xForwardedFor.split(",")[0].trim();
            }
            
            User user = authService.registerUser(request, clientIp);
            String token = jwtUtils.generateToken(user.getUsername(), user.getId(), user.getRole());
            return new ResponseEntity<>(Map.of("user", user, "token", token), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            User user = authService.loginUser(request);
            String token = jwtUtils.generateToken(user.getUsername(), user.getId(), user.getRole());
            return ResponseEntity.ok(Map.of("user", user, "token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("email") String email, @RequestParam("code") String code) {
        try {
            boolean success = authService.verifyEmail(email, code);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "Email verified successfully! You can now log in."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid verification code."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestParam("email") String email) {
        try {
            authService.resendVerificationCode(email);
            return ResponseEntity.ok(Map.of("message", "Verification code resent successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        try {
            authService.requestPasswordReset(email);
            return ResponseEntity.ok(Map.of("message", "Password reset code sent. Please check your email."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("code") String code,
            @RequestParam("newPassword") String newPassword) {
        try {
            authService.resetPassword(email, code, newPassword);
            return ResponseEntity.ok(Map.of("message", "Password has been reset successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}