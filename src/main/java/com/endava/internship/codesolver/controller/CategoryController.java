package com.endava.internship.codesolver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.endava.internship.codesolver.logic.service.CategoryService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class CategoryController {

    private static final String REDIRECT_PAGE = "redirect:/category";

    @NonNull
    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getAllCategories(Model model, HttpServletRequest request) {

        model.addAttribute("errorStack", request.getSession().getAttribute("errorStack"));
        model.addAttribute("categories", categoryService.getCategoryMap());
        return "category";
    }

    @GetMapping("/addNewCategory")
    public String addNewCategory(@RequestParam("categoryName") String categoryName, Model model, HttpServletRequest request) {

        String errorMessage = "";

        if (!categoryService.addCategory(categoryName)) {
            errorMessage = "Category already exists";
        }

        model.addAttribute("errorStack", errorMessage);
        return REDIRECT_PAGE;
    }

    @GetMapping("/saveCurrentCategory")
    public String saveCurrentCategory(@RequestParam("oldName") String oldName,
            @RequestParam("newName") String newName,
            Model model,
            HttpServletRequest request) {

        request.getSession().removeAttribute("errorStack");
        try {
            categoryService.updateCategory(oldName, newName);
        } catch (Exception e) {
            request.getSession().setAttribute("errorStack", "This category already exists!");
            model.addAttribute("errorStack", request.getSession().getAttribute("errorStack"));
            log.error(e.getMessage());
        }
        return REDIRECT_PAGE;
    }
}
