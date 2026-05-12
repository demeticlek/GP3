package com.applytrack.observer;

/**
 * Concrete observer that logs application-change events.
 *
 * This class is part of the Observer pattern implementation.
 * It reacts when an application is created, updated, or deleted.
 *
 * For this iteration, the observer writes audit-style messages to the server
 * console. In a future version, this same pattern could be extended to store
 * audit records in the database, send notifications, or update dashboard data.
 */
public class ApplicationAuditObserver implements ApplicationObserver {

    /**
     * Handles an application-change event by writing a structured message
     * to the server console.
     *
     * @param event details about the application event
     */
    @Override
    public void onApplicationChanged(ApplicationEvent event) {
        System.out.println("[ApplyTrack Observer] Application "
                + event.getType()
                + " | userId=" + event.getUserId()
                + " | applicationId=" + event.getApplicationId()
                + " | company=" + event.getCompanyName()
                + " | status=" + event.getStatus()
                + " | time=" + event.getOccurredAt());
    }
}