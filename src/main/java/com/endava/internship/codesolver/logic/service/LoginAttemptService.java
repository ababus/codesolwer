package com.endava.internship.codesolver.logic.service;

public interface LoginAttemptService {

    int getSuccessfulAttempts(final String username);

    void incrementUnsuccessfulAttempts(String username);

    void refreshAttempts(String username);

}
