package com.sih.roadassistant.controller;

import com.sih.roadassistant.model.User;
import com.sih.roadassistant.model.Voucher;
import com.sih.roadassistant.repository.UserRepository;
import com.sih.roadassistant.repository.VoucherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rewards")
@CrossOrigin(origins = "*")
@PreAuthorize("isAuthenticated()")
public class RewardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        return ResponseEntity.ok(Map.of("points", user.getRewardPoints()));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam("userId") UUID userId) {
        List<Voucher> vouchers = voucherRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<Map<String, Object>> response = vouchers.stream().map(v -> Map.<String, Object>of(
                "id", v.getId(),
                "code", v.getCode(),
                "amount", v.getAmount(),
                "status", v.getStatus(),
                "createdAt", v.getCreatedAt().toString()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/redeem")
    public ResponseEntity<?> redeemVoucher(@RequestParam("userId") UUID userId, @RequestParam("voucherType") String voucherType) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }

        int pointsRequired = 500;
        int voucherAmount = 50;

        if ("FASTAG_100".equalsIgnoreCase(voucherType)) {
            pointsRequired = 1000;
            voucherAmount = 100;
        }

        if (user.getRewardPoints() < pointsRequired) {
            return ResponseEntity.badRequest().body(Map.of("error", "Insufficient points balance. You need " + pointsRequired + " points."));
        }
        user.setRewardPoints(user.getRewardPoints() - pointsRequired);
        userRepository.save(user);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        String voucherCode = "FASTAG" + voucherAmount + "-" + sb.toString();

        Voucher voucher = Voucher.builder()
                .code(voucherCode)
                .amount(voucherAmount)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        voucherRepository.save(voucher);

        return ResponseEntity.ok(Map.of(
                "message", "Successfully redeemed Rs. " + voucherAmount + " FASTag voucher!",
                "code", voucherCode,
                "remainingPoints", user.getRewardPoints()
        ));
    }
}