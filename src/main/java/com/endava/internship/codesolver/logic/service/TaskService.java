package com.endava.internship.codesolver.logic.service;

import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskService {

    Task findTaskById(String id);

    void deleteTaskById(String id);

    Map<String, String> getTasksForCurrentUser();

    List<Task> getTasksForUser(User user);
}
