package com.applytrack.model;

import java.util.List;
import java.util.Map;

/**
 * DTO pattern: groups dashboard counts and application rows for the servlet/view.
 */
public class DashboardData {
    private final Map<String, Integer> counts;
    private final List<Application> applications;

    public DashboardData(Map<String, Integer> counts, List<Application> applications) {
        this.counts = counts;
        this.applications = applications;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public List<Application> getApplications() {
        return applications;
    }
}
