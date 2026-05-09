package com.applytrack.model;

import java.time.LocalDate;

/**
 * Builder pattern implementation for constructing Application objects.
 *
 * This builder provides a readable and maintainable way to create
 * Application instances that contain many optional and required fields.
 *
 * Using the Builder pattern improves code clarity in servlets and avoids
 * telescoping constructors or excessive setter usage.
 */
public class ApplicationBuilder {
    private final Application application;

    public ApplicationBuilder() {
        this.application = new Application();
    }

    public ApplicationBuilder id(int id) {
        application.setId(id);
        return this;
    }

    public ApplicationBuilder userId(int userId) {
        application.setUserId(userId);
        return this;
    }

    public ApplicationBuilder companyName(String companyName) {
        application.setCompanyName(clean(companyName));
        return this;
    }

    public ApplicationBuilder positionTitle(String positionTitle) {
        application.setPositionTitle(clean(positionTitle));
        return this;
    }

    public ApplicationBuilder status(String status) {
        application.setStatus((status == null || status.trim().isEmpty()) ? Application.STATUS_SAVED : status.trim());
        return this;
    }

    public ApplicationBuilder applicationDate(String dateValue) {
        if (dateValue != null && !dateValue.trim().isEmpty()) {
            application.setApplicationDate(LocalDate.parse(dateValue.trim()));
        }
        return this;
    }

    public ApplicationBuilder jobUrl(String jobUrl) {
        application.setJobUrl(clean(jobUrl));
        return this;
    }

    public ApplicationBuilder notes(String notes) {
        application.setNotes(clean(notes));
        return this;
    }

    public Application build() {
        return application;
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
