package com.applytrack.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a job application record in ApplyTrack.
 * Maps to PRD: PR0005 (CRUD operations), PR0006 (status tracking).
 * Traces to Context: C0004 (single source of truth), C0008 (see all applications).
 * Traces to Class Diagram: M0002 (Application entity).
 */
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Valid status values matching PRD PR0006 */
    public static final String STATUS_SAVED = "Saved";
    public static final String STATUS_APPLIED = "Applied";
    public static final String STATUS_INTERVIEWING = "Interviewing";
    public static final String STATUS_OFFER = "Offer";
    public static final String STATUS_REJECTED = "Rejected";
    public static final String STATUS_CLOSED = "Closed";

    private int id;
    private int userId;
    private String companyName;
    private String positionTitle;
    private String status;
    private LocalDate applicationDate;
    private String jobUrl;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Application() {
        this.status = STATUS_SAVED;
        this.applicationDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getPositionTitle() { return positionTitle; }
    public void setPositionTitle(String positionTitle) { this.positionTitle = positionTitle; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getJobUrl() { return jobUrl; }
    public void setJobUrl(String jobUrl) { this.jobUrl = jobUrl; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Application{id=" + id + ", company='" + companyName +
               "', position='" + positionTitle + "', status='" + status + "'}";
    }
}
