package com.endava.internship.codesolver.logic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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

    @Value("${tasksPerUser}")
    private int totalTasks;

    public Map<String, String> getStatisticForUser(String username) {
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findByUserLogin(username);
        return getUserStatistics(userStatisticsList);
    }

    public Map<String, String> getStatisticForCurrentUser() {
        final User currentUser = userService.getCurrentUser();
        List<UserStatistics> userStatisticsList = userStatisticsRepository.findByUser(currentUser);
        return getUserStatistics(userStatisticsList);
    }

    private Map<String, String> getDefaultStat() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("statistic", "No tasks found");
        resultMap.put("success", "0");
        resultMap.put("progress", "0");
        resultMap.put("fail", "0");
        resultMap.put("notAttempted", "0");
        return resultMap;
    }

    private Map<String, String> getUserStatistics(final List<UserStatistics> userStatisticsList) {
        if (totalTasks == 0) {
            return getDefaultStat();
        }

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
        long unAttemptedTasks = totalTasks - userStatisticsList.size();

        summaryStatistics.append(String.format("\nTOTAL: \n\t Passed: %2d\n\t Currently attempting:%2d\n\t Failed: %2d\n\t Not attempted: %2d",
                successfulTasks, inProgressTasks, failedTasks, unAttemptedTasks));

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("statistic", summaryStatistics.toString());
        resultMap.put("success", String.valueOf(Math.ceil(successfulTasks * 100 / ((double) totalTasks))));
        resultMap.put("progress", String.valueOf(Math.ceil(inProgressTasks * 100 / (double) totalTasks)));
        resultMap.put("fail", String.valueOf(Math.ceil(failedTasks * 100 / (double) totalTasks)));
        resultMap.put("notAttempted", String.valueOf(Math.ceil(unAttemptedTasks * 100 / (double) totalTasks)));

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

    public Map<String, Integer> getStatusForCurrentTasks(Set<String> tasksIds) {
        Map<String, Integer> status = new HashMap<>();
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

    /**
     * 1- true 2- undefined 3- false 4-failed
     */
    private int getStatus(String taskId) {
        final Optional<UserStatistics> userStatistics = userStatisticsRepository.findByUserAndTask(userService.getCurrentUser(), getCurrentTask(taskId));
        if (userStatistics.isPresent()) {
            final UserStatistics statistics = userStatistics.get();
            if (!statistics.isPassed() && statistics.getAttempts() != 0 && statistics.getAttempts() != maxAllowedAttempts) {
                return 2;
            }
            if (!statistics.isPassed() && statistics.getAttempts() == maxAllowedAttempts) {
                return 4;
            }
            return statistics.isPassed() ? 1 : 3;
        }
        return 3;
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
