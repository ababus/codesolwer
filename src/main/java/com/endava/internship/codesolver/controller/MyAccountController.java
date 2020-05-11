package com.endava.internship.codesolver.controller;

import com.endava.internship.codesolver.logic.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/currentAccount")
@AllArgsConstructor
public class MyAccountController {

    private final StatisticService statisticService;

    @GetMapping
    public String getStatisticForUser(Model model) {
        Map<String, String> userSummary = statisticService.getStatisticForCurrentUser();
        model.mergeAttributes(userSummary);
        return "myAccount";
    }

}