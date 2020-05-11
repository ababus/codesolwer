package com.endava.internship.codesolver.logic.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.endava.internship.codesolver.model.dao.TaskDao;
import com.endava.internship.codesolver.model.dao.UserStatisticsDao;
import com.endava.internship.codesolver.model.entities.Category;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.User;
import com.endava.internship.codesolver.model.entities.UserStatistics;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskRepository;

    private final UserStatisticsDao userStatisticsRepository;

    private final UserService userService;

    private final StatisticService statisticService;

    private final CategoryService categoryService;

    private static final Random random = new Random();

    @Value("${tasksPerUser}")
    private int tasksPerUserProperty;

    public Map<String, String> getTasksForCurrentUser() {
        return getTasksForUser(userService.getCurrentUser()).stream()
                .collect(Collectors.toMap(Task::getTaskId, Task::getTaskTitle));
    }

    public Task findTaskById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deleteTaskById(String id) {
        userStatisticsRepository.deleteByTask(findTaskById(id));
        taskRepository.deleteTaskByTaskId(id);
    }

    public List<Task> getTasksForUser(User user) {
        if (userService.userIsAdministrator(user)) {
            return taskRepository.findAll();

        } else if (userIsNew(user)) {
            final List<Task> taskList = getRandomTasks();
            statisticService.saveTasksForUser(taskList, user);
            return taskList;

        } else {
            return userStatisticsRepository.findByUser(user)
                    .stream()
                    .map(UserStatistics::getTask)
                    .collect(Collectors.toList());
        }
    }

    private boolean userIsNew(User user) {
        return (userStatisticsRepository.countByUser(user) == 0);
    }

    private List<Task> getRandomTasks() {

        final Map<Category, List<Task>> categoryMap = categoryService.getCategoryTaskMap();
        List<Task> selectedTasks = new LinkedList<>();

        for (List<Task> tasks : categoryMap.values()) {
            selectedTasks.add(tasks.remove(random.nextInt(tasks.size())));
        }

        final List<Task> remainingTasks = categoryMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));

        int extraTasksCount = Math.min(tasksPerUserProperty - selectedTasks.size(), remainingTasks.size());

        while (extraTasksCount > 0) {
            selectedTasks.add(remainingTasks.remove(random.nextInt(remainingTasks.size())));
            extraTasksCount--;
        }

        return selectedTasks;
    }
}