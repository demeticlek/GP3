DROP DATABASE IF EXISTS applytrack;
CREATE DATABASE applytrack
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE applytrack;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(120) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'STUDENT',
    program VARCHAR(120),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT chk_users_email CHECK (email LIKE '%@%'),
    CONSTRAINT chk_users_role CHECK (role IN ('STUDENT', 'ADVISOR', 'ADMIN'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    company_name VARCHAR(120) NOT NULL,
    position_title VARCHAR(120) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'Saved',
    application_date DATE NOT NULL,
    job_url VARCHAR(500),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_applications_users
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT chk_applications_status
        CHECK (status IN ('Saved', 'Applied', 'Interviewing', 'Offer', 'Rejected', 'Closed')),

    INDEX idx_applications_user_id (user_id),
    INDEX idx_applications_status (status),
    INDEX idx_applications_updated_at (updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample login for local testing:
-- Email: davis.demo@algonquinlive.com
-- Password: Password123
INSERT INTO users (email, password_hash, full_name, role, program) VALUES
('davis.demo@algonquinlive.com', 'pbkdf2$65536$QXBwbHlUcmFja1NhbHQwMQ==$AS/AGyxK7W2mAnQB8nlb0s4BMFYldLwGus2fjUwY6DQ=', 'Davis Demo', 'STUDENT', 'Computer Programming');

INSERT INTO applications (user_id, company_name, position_title, status, application_date, job_url, notes) VALUES
(1, 'Shopify', 'Systems Administrator', 'Offer', '2026-04-22', 'https://example.com/shopify', 'Offer received; follow-up required.'),
(1, 'City of Ottawa', 'Customer Service Agent', 'Applied', '2026-04-21', 'https://example.com/ottawa', 'Application submitted.'),
(1, 'Meta', 'Front Stack Developer', 'Closed', '2026-04-09', 'https://example.com/meta', 'Closed after review.'),
(1, 'Microsoft', 'Developer', 'Rejected', '2026-04-15', 'https://example.com/microsoft', 'Rejected after screening.'),
(1, 'DND', 'Analyst', 'Saved', '2026-04-12', 'https://example.com/dnd', 'Saved for later review.');

SELECT 'ApplyTrack GP3 database created successfully.' AS message;
SELECT * FROM users;
SELECT * FROM applications;
