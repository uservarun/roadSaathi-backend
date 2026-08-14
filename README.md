# RoadSaathi — Backend API Service

This is the core API backend service for the **RoadSaathi** safe-travel routing and road-safety assistant. Built with Java and Spring Boot, it handles spatial navigation algorithms, user verification, and telemetry data.

---

## 🛠️ Technology Stack
*   **Language & Core:** Java 21 & Spring Boot (4.1.0)
*   **Security:** Spring Security & JWT (JSON Web Tokens)
*   **Database:** PostgreSQL with **PostGIS** spatial extensions
*   **ORM / Spatial JPA:** Hibernate Spatial (JTS - JTS Topology Suite)
*   **AI Integration:** Google Gemini 1.5 Flash API (Asynchronous image audit)
*   **SMTP Service:** Brevo Mail Relay

---

## 🚀 Core Features
*   **Safety Routing Engine:** Calculates travel routes scored by known road hazards (potholes, waterlogging, closed railway gates) using PostGIS spatial algorithms.
*   **Incident Manager:** Handles geofenced pothole reporting, AI visual image verification, and level crossing gate status updates.
*   **Authentication & Accounts:** Secure user signups, email verification, login sessions, and password resets.
*   **Live Telemetry Tracker:** Processes driving speed telemetry from citizens to automatically update railway crossing statuses.
*   **Saved Commutes:** Manages user commutes profile operations.
