package com.applytrack.dao;

/**
 * Singleton + Factory Method pattern used to centralize DAO creation.
 * This reduces direct dependencies on concrete DAO classes in servlets/services.
 */
public final class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    private DaoFactory() {
        this.applicationRepository = new ApplicationDAO();
        this.userRepository = new UserDAO();
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
