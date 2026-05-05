package com.applytrack.model;

import java.time.LocalDate;

/**
 * Builder pattern for Application objects.
 * It makes servlet code cleaner by moving object construction into a single fluent class.
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
