package com.endava.internship.codesolver.controller;

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

import static com.endava.internship.codesolver.logic.service.ErrorMessagesForTests.NOT_COMPILED;
import static com.endava.internship.codesolver.logic.service.ErrorMessagesForTests.TEST_EXISTS;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/addTest")
@RequiredArgsConstructor
public class AddTestController {

    private final TaskService taskService;

    private final TestService testService;

    @GetMapping
    public String getTaskById(@RequestParam("taskId") String taskId, @ModelAttribute String results, Model model, HttpServletRequest request) {

        TestForTask newTest = new TestForTask();

        request.getSession().setAttribute("taskId", taskId);
        model.addAttribute("newTest", newTest);
        model.addAttribute("addingTestRes", request.getSession().getAttribute("message"));

        return "addTest";
    }

    @PostMapping
    public String submit(@ModelAttribute TestForTask newTest, Model model, HttpServletRequest request) {

        String taskId = request.getSession().getAttribute("taskId").toString();
        Task currentTask = taskService.findTaskById(taskId
        );
        String newTestBody = newTest.getTestBody();

        if (testService.checkTestExistsForTask(currentTask, newTest)) {
            request.getSession().setAttribute("message", TEST_EXISTS.getMessage());
            return "redirect:/addTest";
        }

        String newTestId = testService.addTestForTask(currentTask, newTestBody);

        if (newTestId.equals("-1")) {
            request.getSession().setAttribute("message", NOT_COMPILED.getMessage());
            return "redirect:/addTest";
        }

        model.addAttribute("newTestBody", newTestBody);

        return "redirect:/mainAdmin?taskId=" + taskId + "&testId=" + newTestId;
    }

}