-- RoadSathi SQL Seed Data Script
-- Paste and run this in your database SQL console to seed test hazards across major demo routes in India.

-- Clean existing seed entries to avoid primary key collisions
DELETE FROM reports;
DELETE FROM alerts;
DELETE FROM potholes;
DELETE FROM roads;

-- =========================================================
-- 1. MATHURA LOCAL REGION SEED DATA (DENSE PRESENTATION SET)
-- =========================================================

-- Level Crossing Railway Gates in Mathura
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES 
(
  'b28c8eae-1ce4-47c4-936d-4e0508505001',
  'RAILWAY_GATE',
  'CLOSED', -- Triggers re-routing calculations
  ST_GeomFromText('POINT(77.6828 27.5187)', 4326),
  'Masani Bypass Railway Crossing Gate (Mathura)',
  TRUE,
  NOW(),
  NOW()
),
(
  'b28c8eae-1ce4-47c4-936d-4e0508505002',
  'RAILWAY_GATE',
  'OPEN',
  ST_GeomFromText('POINT(77.6812 27.5015)', 4326),
  'Bhuteshwar Level Crossing Gate',
  TRUE,
  NOW(),
  NOW()
),
(
  'b28c8eae-1ce4-47c4-936d-4e0508505003',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(77.6974 27.4852)', 4326),
  'Aurangabad Railway Crossing Gate',
  TRUE,
  NOW(),
  NOW()
);

-- Waterlogging & Accident Alerts in Mathura
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES 
(
  'b28c8eae-1ce4-47c4-936d-4e0508505004',
  'WATER_LOGGING',
  'CLOSED', -- Severe flooding, path blocked
  ST_GeomFromText('POINT(77.6740 27.4980)', 4326),
  'Water Logging under NH-19 Bypass Bridge (Mathura)',
  TRUE,
  NOW(),
  NOW()
),
(
  'b28c8eae-1ce4-47c4-936d-4e0508505005',
  'ACCIDENT',
  'CLOSED',
  ST_GeomFromText('POINT(77.6685 27.5255)', 4326),
  'Multi-vehicle Collision near Krishna Nagar NH-19 exit',
  TRUE,
  NOW(),
  NOW()
),
(
  'b28c8eae-1ce4-47c4-936d-4e0508505006',
  'WATER_LOGGING',
  'OPEN',
  ST_GeomFromText('POINT(77.6888 27.5095)', 4326),
  'Minor flooding near Deeg Gate Crossing',
  TRUE,
  NOW(),
  NOW()
),
(
  'b28c8eae-1ce4-47c4-936d-4e0508505007',
  'ACCIDENT',
  'OPEN',
  ST_GeomFromText('POINT(77.6980 27.4930)', 4326),
  'Accident hazard near Mathura Junction Main Entrance',
  TRUE,
  NOW(),
  NOW()
);

-- Potholes scattered across Mathura local roads
INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES 
(
  'c38d8eae-2ce4-47c4-936d-4e0508505001',
  ST_GeomFromText('POINT(77.6650 27.5450)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505002',
  ST_GeomFromText('POINT(77.6593 27.5650)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505003',
  ST_GeomFromText('POINT(77.6810 27.5110)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505004',
  ST_GeomFromText('POINT(77.6865 27.5020)', 4326),
  NULL,
  'LOW',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505005',
  ST_GeomFromText('POINT(77.6924 27.4985)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505006',
  ST_GeomFromText('POINT(77.7020 27.4890)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505007',
  ST_GeomFromText('POINT(77.6745 27.5312)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505008',
  ST_GeomFromText('POINT(77.6698 27.5410)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505009',
  ST_GeomFromText('POINT(77.6890 27.5050)', 4326),
  NULL,
  'LOW',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505010',
  ST_GeomFromText('POINT(77.7125 27.4720)', 4326),
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 2. BAREILLY TO MATHURA ROUTE SEED DATA
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505032',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(78.6473 27.8105)', 4326),
  'Kasganj Level Crossing Gate (Closed for train crossing)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES 
(
  'c38d8eae-2ce4-47c4-936d-4e0508505033',
  ST_GeomFromText('POINT(79.1264 28.0514)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505034',
  ST_GeomFromText('POINT(78.0512 27.5968)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 3. DELHI TO NOIDA ROUTE SEED DATA (DND Flyway corridor)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505035',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(77.2830 28.5835)', 4326),
  'DND Approach railway gate (Maintenance Closure)',
  TRUE,
  NOW(),
  NOW()
);


-- =========================================================
-- 4. DELHI TO AGRA ROUTE SEED DATA (Yamuna Expressway)
-- =========================================================
INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES 
(
  'c38d8eae-2ce4-47c4-936d-4e0508505036',
  ST_GeomFromText('POINT(77.6500 27.8900)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 5. MUMBAI TO PUNE EXPRESSWAY CORRIDOR
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505037',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(73.6800 18.7300)', 4326),
  'Talegaon Railway Crossing Gate (Closed for Express Train pass)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505038',
  ST_GeomFromText('POINT(73.4000 18.7500)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 6. BANGALORE TRANSIT CORRIDORS (Airport & Tech Hubs)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505039',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(77.5900 13.0400)', 4326),
  'Hebbal level crossing gate (Maintenance Closures)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505040',
  ST_GeomFromText('POINT(77.7000 12.9900)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 7. HYDERABAD LOCAL PATHWAYS (Gachibowli to Secunderabad)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505041',
  'WATER_LOGGING',
  'CLOSED',
  ST_GeomFromText('POINT(78.4600 17.4400)', 4326),
  'Begumpet Underpass Waterlogging (Heavy Rain accumulation)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505042',
  ST_GeomFromText('POINT(78.3800 17.4400)', 4326),
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 8. LUCKNOW CITY TRANSIT (Charbagh to Airport)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505043',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(80.9600 26.8700)', 4326),
  'Nishatganj Level Crossing Gate (Closed)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505044',
  ST_GeomFromText('POINT(80.9200 26.8300)', 4326),
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 9. JAIPUR HERITAGE ROADWAYS (Amer Fort approach)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505045',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(75.8600 26.9700)', 4326),
  'Kanak Vrindavan Crossing Gate (Railway Shunting Delay)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505046',
  ST_GeomFromText('POINT(75.8500 26.9600)', 4326),
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 10. KOLKATA HUB CORRIDORS (Howrah to Salt Lake)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505047',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(88.4000 22.6200)', 4326),
  'Dum Dum Level Crossing Gate (Closed for suburban train passage)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505048',
  ST_GeomFromText('POINT(88.4300 22.5700)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- =========================================================
-- 11. CHENNAI CITY ROUTES (OMR Tech Corridor)
-- =========================================================
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505049',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(80.2300 13.0600)', 4326),
  'Nungambakkam Railway Gate Crossing (Closed for MRTS train)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505050',
  ST_GeomFromText('POINT(80.2200 13.0100)', 4326),
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);
