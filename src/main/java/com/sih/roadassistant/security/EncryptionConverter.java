package com.sih.roadassistant.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {
    private static final String ALGORITHM = "AES";
    private static byte[] getSecretKey() {
        String secret = System.getenv("DB_ENCRYPTION_KEY");
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("DB_ENCRYPTION_KEY environment variable is missing!");
        }
        byte[] keyBytes = secret.getBytes();
        if (keyBytes.length != 16) {
            byte[] padded = new byte[16];
            System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 16));
            return padded;
        }
        return keyBytes;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            SecretKeySpec keySpec = new SecretKeySpec(getSecretKey(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec); // Initialize cipher for encryption
            byte[] encryptedBytes = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption Failed", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            SecretKeySpec keySpec = new SecretKeySpec(getSecretKey(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec); // Initialize cipher for decryption
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption Failed", e);
        }
    }
}
