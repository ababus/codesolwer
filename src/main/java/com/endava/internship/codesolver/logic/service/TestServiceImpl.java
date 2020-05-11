package com.endava.internship.codesolver.logic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.endava.internship.codesolver.logic.dto.TaskResult;
import com.endava.internship.codesolver.logic.generators.CodeExecutorImpl;
import com.endava.internship.codesolver.model.dao.TaskDao;
import com.endava.internship.codesolver.model.dao.TestDao;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.TestForTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {

    private static final String NO_TASK_FOUND = "No task found!";

    private final TaskDao taskRepository;

    private final TestDao testRepository;

    private final CodeExecutorImpl codeExecutor;

    public TaskResult executeTestsForTask(String classCode, String taskId) {
        return codeExecutor.execute(classCode, getTestsForCurrentTask(taskId));
    }

    public void modifyTask(Task task, TestForTask test) {

        String taskTitle = task.getTaskTitle();
        String taskBody = task.getTaskBody();
        String testBody = test.getTestBody();

        log.info("Modifying task " + taskTitle);
        TaskResult taskResults = codeExecutor.execute(taskBody, testBody);

        if (!taskResults.isCompiled()) {
            log.info("\nCouldn't compile the provided code. \n" +
                    "Task code: \n" + task.getTaskBody() +
                    "Test code: \n " + test.getTestBody());

        } else {
            taskRepository.save(task);
            test.setTask(task);
        }

        testRepository.save(test);

        log.info("Successfully updated the task :" + task.getTaskId() + "\t" + taskTitle + "\n" +
                "Updated test : " + test.getTestId() + "\t" + test.getTestBody());
    }

    public String addTestForTask(Task theTask, String newTestBody) {

        TaskResult taskResults = codeExecutor.execute(theTask.getTaskBody(), newTestBody);

        if (!taskResults.isCompiled()) {
            return "-1";
        } else {
            TestForTask newTest = new TestForTask();
            newTest.setTask(theTask);
            newTest.setTestBody(newTestBody);
            testRepository.save(newTest);
            return newTest.getTestId();
        }
    }

    public boolean checkTestExistsForTask(Task theTask, TestForTask test) {

        return theTask.getTests()
                .stream()
                .anyMatch(testForTask -> getTestName(test).equalsIgnoreCase(getTestName(testForTask)));
    }

    public Map<String, String> getMapOfTestNames(String taskId) {
        final Map<String, String> testNames = new HashMap<>();
        List<TestForTask> listOfAllTests = getTests(taskId);
        listOfAllTests.forEach(test -> testNames.put(test.getTestId(), getTestName(test)));
        return testNames;
    }

    public TestForTask findTestById(String testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException(NO_TASK_FOUND));
    }

    private List<TestForTask> getTests(String taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException(NO_TASK_FOUND))
                .getTests();
    }

    private String getTestsForCurrentTask(String taskId) {
        List<TestForTask> testList = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException(NO_TASK_FOUND))
                .getTests();

        StringBuilder testsForCurrentTask = new StringBuilder();
        testList.forEach(test -> testsForCurrentTask.append(test.getTestBody()));
        return testsForCurrentTask.toString();
    }

    private String getTestName(TestForTask test) {
        String testName = test.getTestBody();
        final String pattern = "(boolean)(.+?)(\\{)";
        final Pattern p = Pattern.compile(pattern);
        final Matcher testMatcher = p.matcher(testName);

        while (testMatcher.find()) {
            testName = testMatcher.group(2);
        }
        return testName;
    }

}
