package com.applytrack.observer;

/**
 * Observer interface for application-change events.
 *
 * This interface defines the contract that all observers must follow.
 * Any class that wants to respond when an application is created, updated,
 * or deleted can implement this interface.
 *
 * Design Pattern:
 * This is the Observer interface in the Observer pattern.
 * It allows the application to add new response behaviors without modifying
 * the servlets that perform CRUD operations.
 */
public interface ApplicationObserver {

    /**
     * Called when an application-related event occurs.
     *
     * @param event details about the application event
     */
    void onApplicationChanged(ApplicationEvent event);
}