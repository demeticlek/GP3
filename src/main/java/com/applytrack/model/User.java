package com.applytrack.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a user in the ApplyTrack system.
 * Maps to PRD requirements PR0001 (authentication), PR0002 (role-based access),
 * PR0004 (student profile).
 * Traces to Context: C0003 (primary users), C0006 (secure role-based tracker).
 * Traces to Class Diagram: M0002 (User entity).
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String passwordHash;
    private String fullName;
    private String role;       // STUDENT, ADVISOR, ADMIN
    private String program;    // e.g. "Computer Programming"
    private LocalDateTime createdAt;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String email, String passwordHash, String fullName,
                String role, String program) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.program = program;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{id=" + id + ", email='" + email + "', role='" + role + "'}";
    }
}