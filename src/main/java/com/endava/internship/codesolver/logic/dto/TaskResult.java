package com.endava.internship.codesolver.logic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskResult {

    private String results;

    private boolean compiled;

    private boolean successful;

}
