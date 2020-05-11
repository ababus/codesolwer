package com.endava.internship.codesolver.controller;

import com.endava.internship.codesolver.logic.service.StatisticService;
import com.endava.internship.codesolver.logic.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final TaskService taskService;

    private final StatisticService statisticService;

    @GetMapping("/index")
    public String getCode(Model model) {
        Map<String, String> tasks = taskService.getTasksForCurrentUser();
        model.addAttribute("tasks", tasks);
        model.addAttribute("status", statisticService.getStatusForCurrentTasks(tasks.keySet()));
        return "/index";
    }

}
