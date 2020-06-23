package com.endava.internship.codesolver.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.endava.internship.codesolver.logic.service.StatisticService;
import com.endava.internship.codesolver.logic.service.TaskServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DeleteTaskController {

    private final TaskServiceImpl taskService;

    private final StatisticService statisticService;

    @GetMapping("/deleteTask")
    public String deleteTaskByID(@RequestParam("taskId") String taskId, Model model, HttpServletRequest request) {

        taskService.deleteTaskById(taskId);
        Map<String, String> tasks = taskService.getTasksForCurrentUser();
        model.addAttribute("tasks", tasks);
        model.addAttribute("status", statisticService.getStatusForCurrentTasks(tasks.keySet()));
        request.getSession().removeAttribute("errorStack");
        return "index";
    }
}
