package com.endava.internship.codesolver.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.endava.internship.codesolver.logic.service.StatisticService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping
@AllArgsConstructor
public class MyAccountController {

    private final StatisticService statisticService;

    @GetMapping("/currentAccount")
    public String getStatisticForCurrentUser(Model model) {
        Map<String, String> userSummary = statisticService.getStatisticForCurrentUser();
        model.mergeAttributes(userSummary);
        return "myAccount";
    }

    @GetMapping("/userAccount")
    public String getStatisticForUser(@RequestParam("username") String username, Model model) {
        Map<String, String> userSummary = statisticService.getStatisticForUser(username);
        model.mergeAttributes(userSummary);
        model.addAttribute("userStat", username);
        return "myAccount";
    }

}