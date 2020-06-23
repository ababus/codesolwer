package com.endava.internship.codesolver.logic.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.endava.internship.codesolver.model.entities.Category;
import com.endava.internship.codesolver.model.entities.Task;

public interface CategoryService {

    Map<String, String> getCategoryMap();

    List<Category> getCategories();

    Optional<Category> getCategoryByName(String categoryName);

    Map<Category, List<Task>> getCategoryTaskMap();

    boolean addCategory(String name);

    void updateCategory(String oldName, String newName);
}
