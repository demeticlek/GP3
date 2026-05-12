package com.applytrack.observer;

import java.time.LocalDateTime;

/**
 * Represents an event that occurs when a job application changes.
 *
 * This class is part of the Observer pattern implementation for Group Project 3.
 * Instead of tightly coupling follow-up actions directly inside the servlets,
 * the servlet creates an ApplicationEvent and sends it to the event publisher.
 *
 * This improves maintainability because future features, such as notifications,
 * activity logs, or dashboard refresh logic, can respond to application changes
 * without changing the servlet workflow.
 */
public class ApplicationEvent {

    private final ApplicationEventType type;
    private final int userId;
    private final int applicationId;
    private final String companyName;
    private final String status;
    private final LocalDateTime occurredAt;

    /**
     * Creates a new application event.
     *
     * @param type          the type of event, such as CREATED, UPDATED, or DELETED
     * @param userId        the ID of the user who owns the application
     * @param applicationId the ID of the affected application
     * @param companyName   the company name associated with the application
     * @param status        the current application status
     */
    public ApplicationEvent(ApplicationEventType type, int userId, int applicationId,
                            String companyName, String status) {
        this.type = type;
        this.userId = userId;
        this.applicationId = applicationId;
        this.companyName = companyName;
        this.status = status;
        this.occurredAt = LocalDateTime.now();
    }

    public ApplicationEventType getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}