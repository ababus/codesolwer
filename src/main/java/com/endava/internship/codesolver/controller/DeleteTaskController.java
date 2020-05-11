package com.endava.internship.codesolver.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.endava.internship.codesolver.logic.service.TaskServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/deleteTask")
@RequiredArgsConstructor
public class DeleteTaskController {

    private final TaskServiceImpl taskService;

    @GetMapping
    public String deleteTaskByID(HttpServletRequest request) {
        final Cookie taskId = WebUtils.getCookie(request, "taskId");
        if (taskId != null) {
            taskService.deleteTaskById(taskId.getValue());
        }
        return "redirect:/index";
    }
}
