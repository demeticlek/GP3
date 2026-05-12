package com.applytrack.observer;

/**
 * Defines the types of application-related events that can occur in the system.
 *
 * This enum supports the Observer pattern by giving observers a clear,
 * consistent way to identify what kind of change happened to an application.
 *
 * Current supported events:
 * - CREATED: a new job application was added
 * - UPDATED: an existing job application was modified
 * - DELETED: an application was removed
 */
public enum ApplicationEventType {
    CREATED,
    UPDATED,
    DELETED
}