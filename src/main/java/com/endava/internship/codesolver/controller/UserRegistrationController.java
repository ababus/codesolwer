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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @GetMapping("/adminRegistration")
    public String findAllUsersRedirect(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/users";
    }

    @PostMapping("/adminRegistration")
    public String registerUserAccountFromAdmin(@ModelAttribute("user") @Valid UserRegistrationDto newUser, BindingResult result, Model model) {
        User existing = userService.findByLogin(newUser.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
            FieldError error = new FieldError("registrationNewUser", "username", "");
            result.addError(error);

            model.addAttribute("userError", "There is already an account registered with that username");
            return "redirect:/users";
        }

        userService.save(newUser);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/users";
    }

}
