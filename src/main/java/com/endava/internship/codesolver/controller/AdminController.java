package com.endava.internship.codesolver.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.endava.internship.codesolver.logic.service.TaskService;
import com.endava.internship.codesolver.logic.service.TestService;
import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.TestForTask;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mainAdmin")
@RequiredArgsConstructor
public class AdminController {

    private final TaskService taskService;

    private final TestService testService;

    @GetMapping
    public String getTaskById(@RequestParam("taskId") String taskId, @RequestParam("testId") String testId,
            Model model, HttpServletRequest request) {

        Task currentTask = taskService.findTaskById(taskId);
        Map<String, String> testName;

        if (testId.equals("0")) {
            testId = currentTask.getTests().get(0).getTestId();
            return "redirect:/mainAdmin?taskId=" + taskId + "&testId=" + testId;
        }

        if (currentTask == null) {
            return "redirect:/index";
        }

        TestForTask currentTest = testService.findTestById(testId);
        testName = testService.getMapOfTestNames(taskId);

        request.getSession().setAttribute("currentTask", currentTask);
        request.getSession().setAttribute("testId", testId);
        request.getSession().removeAttribute("message");

        model.addAttribute("task", currentTask);
        model.addAttribute("tests_names", testName);
        model.addAttribute("currentTest", currentTest);
        return "mainAdmin";
    }

    @PostMapping
    public String submit(@ModelAttribute Task task, @ModelAttribute TestForTask test, HttpServletRequest request) {

        final Task currentTask = (Task) request.getSession().getAttribute("currentTask");
        final String taskId = currentTask.getTaskId();
        final String testId = request.getSession().getAttribute("testId").toString();

        task.setTaskId(taskId);
        test.setTestId(testId);

        testService.modifyTask(task, test);

        return "redirect:/mainAdmin?taskId=" + taskId + "&testId=" + testId;
    }

}