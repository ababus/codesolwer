package com.endava.internship.codesolver.logic.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMessagesForTests {

    NOT_COMPILED("Something went Wrong. Couldn't compile your test. Please, retry"),
    TEST_EXISTS("The test with the same name for this task already exists.\nPlease, be aware, and provide a different test.");

    private final String message;

    public String getMessage() {
        return this.message;
    }

}
