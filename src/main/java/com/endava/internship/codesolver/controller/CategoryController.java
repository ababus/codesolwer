package com.endava.internship.codesolver.controller;

import com.endava.internship.codesolver.logic.service.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
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
    public String saveCurrentCategory(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName, Model model, HttpServletRequest request) {

        //FIXME: verify: currently the UI says that there is an error even when it is not.
        request.getSession().removeAttribute("errorStack");
        categoryService.updateCategory(oldName, newName);
        request.getSession().setAttribute("errorStack", "This category already exists!");
        model.addAttribute("errorStack", request.getSession().getAttribute("errorStack"));
        return REDIRECT_PAGE;
    }
}
