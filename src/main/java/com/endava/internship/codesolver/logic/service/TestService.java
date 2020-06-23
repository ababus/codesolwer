package com.endava.internship.codesolver.logic.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.endava.internship.codesolver.logic.dto.TaskResult;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.TestForTask;

@Service
public interface TestService {

    TaskResult executeTestsForTask(String classCode, String taskId);

    Map<String, String> getMapOfTestNames(String taskId);

    Optional<TestForTask> findTestById(String id);

    String modifyTask(Task task, TestForTask test);

    String addTestForTask(Task theTask, String newTestBody);

    boolean checkTestExistsForTask(Task theTask, TestForTask test);

}
