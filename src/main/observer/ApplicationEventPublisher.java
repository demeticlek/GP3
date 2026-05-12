package com.applytrack.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Publishes application-change events to registered observers.
 *
 * This class acts as the Subject/Publisher in the Observer pattern.
 * Servlets notify this publisher when an application is created, updated,
 * or deleted. The publisher then forwards the event to all registered observers.
 *
 * A CopyOnWriteArrayList is used because servlet applications may handle
 * multiple requests at the same time. This collection allows safe iteration
 * while observers are being notified.
 *
 * The class is also implemented as a simple Singleton so there is one shared
 * event publisher used across the application.
 */
public class ApplicationEventPublisher {

    private static final ApplicationEventPublisher INSTANCE = new ApplicationEventPublisher();

    private final List<ApplicationObserver> observers = new CopyOnWriteArrayList<>();

    /**
     * Private constructor prevents outside classes from creating separate
     * publisher instances.
     *
     * The audit observer is registered by default so application changes
     * are logged immediately without requiring extra setup in each servlet.
     */
    private ApplicationEventPublisher() {
        observers.add(new ApplicationAuditObserver());
    }

    /**
     * Returns the shared publisher instance.
     *
     * @return singleton ApplicationEventPublisher instance
     */
    public static ApplicationEventPublisher getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a new observer.
     *
     * This allows future functionality, such as email notifications or dashboard
     * cache updates, to be added without changing the servlet logic.
     *
     * @param observer observer to add
     */
    public void addObserver(ApplicationObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes a registered observer.
     *
     * @param observer observer to remove
     */
    public void removeObserver(ApplicationObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about an application event.
     *
     * @param event event containing details about the application change
     */
    public void notifyObservers(ApplicationEvent event) {
        for (ApplicationObserver observer : observers) {
            observer.onApplicationChanged(event);
        }
    }
}