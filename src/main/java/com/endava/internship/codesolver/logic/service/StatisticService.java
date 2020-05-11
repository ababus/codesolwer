package com.endava.internship.codesolver.logic.service;

import com.endava.internship.codesolver.logic.dto.TaskResult;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StatisticService {

    Map<String, String> getStatisticForCurrentUser();

    int getOrUpdateRemainedAttempts(TaskResult taskResult, String taskBody, String taskId);

    int getRemainedAttempts(String taskId);

    String getSolutionOrTaskBody(String taskId);

    Map<String, Boolean> getStatusForCurrentTasks(Set<String> tasks);

    void saveTasksForUser (List<Task> taskList, User user);
}
