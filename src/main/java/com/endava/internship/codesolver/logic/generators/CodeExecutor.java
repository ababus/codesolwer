package com.endava.internship.codesolver.logic.generators;

import com.endava.internship.codesolver.logic.dto.TaskResult;

public interface CodeExecutor {

    TaskResult execute(final String solution, final String test);

}
