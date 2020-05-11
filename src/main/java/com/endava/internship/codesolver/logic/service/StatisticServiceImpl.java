package com.endava.internship.codesolver.logic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.endava.internship.codesolver.logic.dto.TaskResult;
import com.endava.internship.codesolver.model.dao.TaskDao;
import com.endava.internship.codesolver.model.dao.UserStatisticsDao;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.User;
import com.endava.internship.codesolver.model.entities.UserStatistics;
import com.endava.internship.codesolver.model.entities.UserStatisticsId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final TaskDao taskRepository;

    private final UserStatisticsDao userStatisticsRepository;

    private final UserService userService;

    @Value("${numberOfAttempts}")
    private int maxAllowedAttempts;

    public Map<String, String> getStatisticForCurrentUser() {
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findByUser(userService.getCurrentUser());

        StringBuilder summaryStatistics = new StringBuilder();
        long successfulTasks = 0;
        long inProgressTasks = 0;

        for (UserStatistics userStatistic : userStatisticsList) {
            summaryStatistics.append(String.format("%s:\n\tAttempts: %2d\n\tPassed: %s\n",
                    userStatistic.getTask().getTaskTitle(),
                    userStatistic.getAttempts(),
                    userStatistic.isPassed()));

            if (userStatistic.isPassed()) {
                successfulTasks++;
            } else {
                if (userStatistic.getAttempts() < maxAllowedAttempts) {
                    inProgressTasks++;
                }
            }
        }

        long failedTasks = userStatisticsList.size() - successfulTasks - inProgressTasks;
        long totalTasks = taskRepository.count();
        long unAttemptedTasks = totalTasks - userStatisticsList.size();

        summaryStatistics.append(String.format("\nTOTAL: \n\t Passed: %2d\n\t Currently attempting:%2d\n\t Failed: %2d\n\t Not attempted: %2d",
                successfulTasks, inProgressTasks, failedTasks, unAttemptedTasks));

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("statistic", summaryStatistics.toString());
        resultMap.put("success", String.valueOf(successfulTasks * 100 / totalTasks));
        resultMap.put("progress", String.valueOf(inProgressTasks * 100 / totalTasks));
        resultMap.put("fail", String.valueOf(failedTasks * 100 / totalTasks));
        resultMap.put("notAttempted", String.valueOf(unAttemptedTasks * 100 / totalTasks));

        return resultMap;
    }

    public int getOrUpdateRemainedAttempts(TaskResult taskResult, String taskBody, String taskId) {

        int remainedAttempts = getRemainedAttempts(taskId);
        if (remainedAttempts > 0) {
            updateStatistics(taskResult, taskBody, taskId);
            remainedAttempts -= 1;
        }
        return remainedAttempts;
    }

    public String getSolutionOrTaskBody(String taskId) {
        return findStatisticsForCurrentUserByTaskId(taskId)
                .map(UserStatistics::getSolution)
                .orElse(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new).getTaskBody());
    }

    public int getRemainedAttempts(String taskId) {
        return findStatisticsForCurrentUserByTaskId(taskId)
                .map(userStatistics1 -> maxAllowedAttempts - userStatistics1.getAttempts())
                .orElse(maxAllowedAttempts);
    }

    public Map<String, Boolean> getStatusForCurrentTasks(Set<String> tasksIds) {
        Map<String, Boolean> status = new HashMap<>();
        for (String taskId : tasksIds) {
            status.put(taskId, getStatus(taskId));
        }
        return status;
    }

    // @Transactional
    public void saveTasksForUser(List<Task> taskList, User user) {
        for (Task task : taskList) {
            UserStatisticsId usId = new UserStatisticsId(user.getUserId(), task.getTaskId());

            UserStatistics userStatistics = UserStatistics.builder()
                    .userStatisticsId(usId)
                    .user(user)
                    .task(task)
                    .passed(false)
                    .attempts(0)
                    .solution(task.getTaskBody())
                    .build();

            userStatisticsRepository.save(userStatistics);
        }
    }

    private boolean getStatus(String taskId) {
        return userStatisticsRepository.findByUserAndTask(userService.getCurrentUser(), getCurrentTask(taskId))
                .map(UserStatistics::isPassed)
                .orElse(false);
    }

    private void updateStatistics(TaskResult taskResult, String taskBody, String taskId) {

        UserStatisticsId userStatisticsId = new UserStatisticsId(userService.getCurrentUser().getUserId(), taskId);
        Optional<UserStatistics> foundUserStatistics = userStatisticsRepository.findById(userStatisticsId);

        foundUserStatistics.ifPresent(us1 -> {
            us1.setAttempts(us1.getAttempts() + 1);
            us1.setSolution(taskBody);
            us1.setPassed(taskResult.isSuccessful());
        });

        UserStatistics firstSubmitUserStatistics = UserStatistics.builder()
                .userStatisticsId(userStatisticsId)
                .attempts(1)
                .passed(taskResult.isSuccessful())
                .solution(taskBody)
                .build();

        userStatisticsRepository.save(foundUserStatistics.orElse(firstSubmitUserStatistics));
    }

    private Optional<UserStatistics> findStatisticsForCurrentUserByTaskId(String taskId) {
        return userStatisticsRepository.findById(new UserStatisticsId(userService.getCurrentUser().getUserId(), taskId));
    }

    private Task getCurrentTask(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("There is no such task"));
    }
}
