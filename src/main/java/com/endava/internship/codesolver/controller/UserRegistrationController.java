package com.endava.internship.codesolver.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.endava.internship.codesolver.logic.dto.UserRegistrationDto;
import com.endava.internship.codesolver.logic.service.UserService;
import com.endava.internship.codesolver.model.entities.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserRegistrationController {

    private static final String PAGE = "registration";

    private final UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return PAGE;
    }

    @GetMapping("/users")
    public String findAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "users";
        }

        User existing = userService.findByLogin(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
            FieldError error = new FieldError("registrationNewUser", "username",
                    "");
            result.addError(error);
            return PAGE;
        }

        userService.save(userDto);
        return "redirect:/" + PAGE + "?success";
    }

}
