package com.endava.internship.codesolver.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.endava.internship.codesolver.logic.service.CategoryService;
import com.endava.internship.codesolver.logic.service.TaskService;
import com.endava.internship.codesolver.logic.service.TestService;
import com.endava.internship.codesolver.model.entities.Category;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.TestForTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/mainAdmin")
@RequiredArgsConstructor
public class AdminController {

    private final TaskService taskService;

    private final TestService testService;

    private final CategoryService categoryService;

    @GetMapping
    public String getTaskById(@RequestParam("taskId") String taskId, @RequestParam("testId") String testId,
            Model model, HttpServletRequest request) {

        if ("0".equals(taskId)) {
            return createNewTask(model, request);
        }
        try {
            Task currentTask = taskService.findTaskById(taskId);
            Map<String, String> testName;

            if (testId.equals("0")) {
                testId = currentTask.getTests().get(0).getTestId();
                return "redirect:/mainAdmin?taskId=" + taskId + "&testId=" + testId;
            }

            TestForTask currentTest = testService.findTestById(testId)
                    .orElse(new TestForTask());
            testName = testService.getMapOfTestNames(taskId);

            request.getSession().setAttribute("currentTask", currentTask);
            request.getSession().setAttribute("testId", testId);
            request.getSession().removeAttribute("message");

            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("task", currentTask);
            model.addAttribute("tests_names", testName);
            model.addAttribute("currentTest", currentTest);
            return "mainAdmin";
        } catch (Exception e) {
            log.warn(e.getMessage());
            return "redirect:/index";
        }
    }

    public String createNewTask(Model model, HttpServletRequest request) {
        final String taskId = UUID.randomUUID().toString();
        Task currentTask = new Task() {{
            setTaskId(taskId);
        }};
        final TestForTask test = new TestForTask() {{
            setTask(currentTask);
            setTestId(UUID.randomUUID().toString());
        }};
        currentTask.setTests(Collections.singletonList(test));
        request.getSession().setAttribute("currentTask", currentTask);
        request.getSession().setAttribute("testId", test.getTestId());
        request.getSession().removeAttribute("message");

        Map<String, String> testName = new HashMap() {{
            put("Sample test", "");
        }};
        model.addAttribute("tests_names", testName);
        model.addAttribute("task", currentTask);
        model.addAttribute("currentTest", test);
        model.addAttribute("categories", categoryService.getCategories());
        return "mainAdmin";
    }

    @PostMapping
    public String submit(@ModelAttribute Task task,
            @ModelAttribute TestForTask test,
            @ModelAttribute String category,
            HttpServletRequest request) {
        request.getSession().removeAttribute("result");
        final Task currentTask = (Task) request.getSession().getAttribute("currentTask");
        final String taskId = currentTask.getTaskId();
        final String testId = request.getSession().getAttribute("testId").toString();

        if (task.getCategory() == null) {
            if (currentTask.getCategory() != null) {
                task.setCategory(currentTask.getCategory());
            } else {
                task.setCategory(categoryService.getCategoryByName(task.getCategoryName())
                        .orElse(Category.builder().categoryId(UUID.randomUUID().toString()).name(task.getTaskTitle()).build()));
            }
        }

        task.setTaskId(taskId);
        test.setTestId(testId);
        try {
            request.getSession().setAttribute("errorStack", testService.modifyTask(task, test));
        } catch (Exception e) {
            request.getSession().setAttribute("errorStack", "Something went wrong, please try again.");
        }
        return "redirect:/mainAdmin?taskId=" + taskId + "&testId=" + testId;
    }

}