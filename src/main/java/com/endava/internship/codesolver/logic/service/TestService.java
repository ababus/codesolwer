package com.endava.internship.codesolver.logic.service;

import com.endava.internship.codesolver.logic.dto.TaskResult;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.TestForTask;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TestService {

    TaskResult executeTestsForTask(String classCode, String taskId);

    Map<String, String> getMapOfTestNames(String taskId);

    TestForTask findTestById(String id);

    void modifyTask(Task task, TestForTask test);

    String addTestForTask(Task theTask, String newTestBody);

    boolean checkTestExistsForTask(Task theTask, TestForTask test);

}
