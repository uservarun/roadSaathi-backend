package com.sih.roadassistant.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init(){
        try{
            jdbcTemplate.execute("ALTER TABLE users ADD COLUMN IF NOT EXISTS reward_points INT DEFAULT 0");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS vouchers (" +
                    "id UUID PRIMARY KEY, " +
                    "code VARCHAR(50) UNIQUE NOT NULL, " +
                    "amount INT NOT NULL, " +
                    "status VARCHAR(20) NOT NULL, " +
                    "created_at TIMESTAMP NOT NULL, " +
                    "user_id UUID REFERENCES users(id)" +
                    ")");
            System.out.println(">>> Database Schema Auto-Migration completed succesfully :)");
        } catch (Exception e) {
            System.err.println(">>> Database Auto-Migration warning: " +e.getMessage());
        }
    }
}
