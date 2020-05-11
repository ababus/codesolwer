package com.endava.internship.codesolver.controller;

import com.endava.internship.codesolver.logic.dto.TaskResult;
import com.endava.internship.codesolver.logic.service.StatisticService;
import com.endava.internship.codesolver.logic.service.TaskService;
import com.endava.internship.codesolver.logic.service.TestService;
import com.endava.internship.codesolver.model.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainPageController {

    private final TaskService taskService;

    private final TestService testService;

    private final StatisticService statisticService;

    @GetMapping
    public String getTaskById(@RequestParam("taskId") String taskId, Model model, HttpServletRequest request) {

        Task currentTask = taskService.findTaskById(taskId);
        String solution = statisticService.getSolutionOrTaskBody(taskId);

        if (currentTask == null)
            return "redirect:/index";

        currentTask.setTaskBody(solution);
        request.getSession().setAttribute("currentTask", currentTask);

        model.addAttribute("task", currentTask);
        model.addAttribute("testres", "");
        model.addAttribute("remained_attempts", statisticService.getRemainedAttempts(taskId));
        return "main";
    }

    @PostMapping
    public String submit(@ModelAttribute Task task, Model model, HttpServletRequest request) {
        final Task currentTask = (Task) request.getSession().getAttribute("currentTask");
        final String solution = task.getTaskBody();

        TaskResult taskResult = testService.executeTestsForTask(solution, currentTask.getTaskId());

        model.addAttribute("task.taskBody", solution);
        model.addAttribute("testres", taskResult.getResults());
        model.addAttribute("remained_attempts", statisticService.getOrUpdateRemainedAttempts(taskResult, solution, currentTask.getTaskId()));
        return "main";
    }

}
