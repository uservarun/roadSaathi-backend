-- RoadSathi SQL Seed Data Script
-- Paste and run this in your database SQL console to seed test hazards across major demo routes in India.

-- Clean existing seed entries to avoid primary key collisions
DELETE FROM reports;
DELETE FROM alerts;
DELETE FROM potholes;
DELETE FROM roads;

-- 1. MATHURA LOCAL REGION SEED DATA (Mathura Junction to Vrindavan)
-- Alerts & Potholes situated along the local transit corridor
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES 
(
  'b28c8eae-1ce4-47c4-936d-4e0508505999',
  'RAILWAY_GATE',
  'CLOSED', -- Set to CLOSED to trigger re-routing calculations
  ST_GeomFromText('POINT(77.6830 27.5187)', 4326),
  'Masani Bypass Railway Crossing Gate (Mathura)',
  TRUE,
  NOW(),
  NOW()
),
(
  'b28c8eae-1ce4-47c4-936d-4e0508505001',
  'WATER_LOGGING',
  'OPEN',
  ST_GeomFromText('POINT(77.6740 27.4980)', 4326),
  'Water logging under bypass bridge',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES 
(
  'c38d8eae-2ce4-47c4-936d-4e0508505888',
  ST_GeomFromText('POINT(77.6650 27.5450)', 4326),
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'd48e8eae-3ce4-47c4-936d-4e0508505777',
  ST_GeomFromText('POINT(77.6593 27.5650)', 4326),
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- 2. BAREILLY TO MATHURA ROUTE SEED DATA
-- Hazards placed strategically along the State Highway 33 / NH 530B corridor between Bareilly and Mathura
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505002',
  'RAILWAY_GATE',
  'CLOSED', -- Active closed gate blocking the main highway path near Kasganj
  ST_GeomFromText('POINT(78.6473 27.8105)', 4326),
  'Kasganj Level Crossing Gate (Closed for train crossing)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES 
(
  'c38d8eae-2ce4-47c4-936d-4e0508505003',
  ST_GeomFromText('POINT(79.1264 28.0514)', 4326), -- Budaun highway pothole
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
),
(
  'c38d8eae-2ce4-47c4-936d-4e0508505004',
  ST_GeomFromText('POINT(78.0512 27.5968)', 4326), -- Hathras bypass pothole
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- 3. DELHI TO NOIDA ROUTE SEED DATA (DND Flyway corridor)
-- Hazards placed to test short commuter route deviations
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505005',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(77.2830 28.5835)', 4326), -- DND Flyway approach junction
  'DND Approach railway gate (Maintenance Closure)',
  TRUE,
  NOW(),
  NOW()
);


-- 4. DELHI TO AGRA ROUTE SEED DATA (Yamuna Expressway highway corridor)
-- Test hazards along the high speed expressway
INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES 
(
  'c38d8eae-2ce4-47c4-936d-4e0508505006',
  ST_GeomFromText('POINT(77.6500 27.8900)', 4326), -- Jewar toll approach segment
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
);


-- 5. MUMBAI TO PUNE EXPRESSWAY CORRIDOR
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505007',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(73.6800 18.7300)', 4326), -- Near Talegaon bypass
  'Talegaon Railway Crossing Gate (Closed for Express Train pass)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505008',
  ST_GeomFromText('POINT(73.4000 18.7500)', 4326), -- Pothole near Lonavala ghat section
  'https://images.unsplash.com/photo-1515162305285-0293e4767cc2',
  'HIGH',
  'VERIFIED',
  NOW()
);


-- 6. BANGALORE TRANSIT CORRIDORS (Airport & Tech Hubs)
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505009',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(77.5900 13.0400)', 4326), -- Hebbal crossing approach
  'Hebbal level crossing gate (Maintenance Closures)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505010',
  ST_GeomFromText('POINT(77.7000 12.9900)', 4326), -- KR Puram ORR junction pothole
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- 7. HYDERABAD LOCAL PATHWAYS (Gachibowli to Secunderabad)
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505011',
  'WATER_LOGGING',
  'CLOSED',
  ST_GeomFromText('POINT(78.4600 17.4400)', 4326), -- Begumpet underpass
  'Begumpet Underpass Waterlogging (Heavy Rain accumulation)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505012',
  ST_GeomFromText('POINT(78.3800 17.4400)', 4326), -- Madhapur Image Towers road
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- 8. LUCKNOW CITY TRANSIT (Charbagh to Airport)
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505013',
  'RAILWAY_GATE',
  'CLOSED',
  ST_GeomFromText('POINT(80.9600 26.8700)', 4326), -- Nishatganj approach
  'Nishatganj Level Crossing Gate (Closed)',
  TRUE,
  NOW(),
  NOW()
);

INSERT INTO potholes (id, coordinate, image_url, severity, ai_status, detected_at)
VALUES (
  'c38d8eae-2ce4-47c4-936d-4e0508505014',
  ST_GeomFromText('POINT(80.9200 26.8300)', 4326), -- Charbagh bypass junction pothole
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- 9. JAIPUR HERITAGE ROADWAYS (Amer Fort approach)
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505015',
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
  'c38d8eae-2ce4-47c4-936d-4e0508505016',
  ST_GeomFromText('POINT(75.8500 26.9600)', 4326), -- Jal Mahal approach pothole
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);


-- 10. KOLKATA HUB CORRIDORS (Howrah to Salt Lake)
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505017',
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
  'c38d8eae-2ce4-47c4-936d-4e0508505018',
  ST_GeomFromText('POINT(88.4300 22.5700)', 4326), -- Salt Lake Sec-V tech hub pothole
  NULL,
  'MEDIUM',
  'VERIFIED',
  NOW()
);


-- 11. CHENNAI CITY ROUTES (OMR Tech Corridor)
INSERT INTO alerts (id, alert_type, status, coordinate, description, is_active, created_at, updated_at)
VALUES (
  'b28c8eae-1ce4-47c4-936d-4e0508505019',
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
  'c38d8eae-2ce4-47c4-936d-4e0508505020',
  ST_GeomFromText('POINT(80.2200 13.0100)', 4326), -- Guindy Industrial Estate road pothole
  NULL,
  'HIGH',
  'VERIFIED',
  NOW()
);

