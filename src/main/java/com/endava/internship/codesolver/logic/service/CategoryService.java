package com.endava.internship.codesolver.logic.service;

import java.util.List;
import java.util.Map;

import com.endava.internship.codesolver.model.entities.Category;
import com.endava.internship.codesolver.model.entities.Task;

public interface CategoryService {

    Map<String, String> getCategoryMap();

    Map<Category, List<Task>> getCategoryTaskMap();

    boolean addCategory(String name);

    void updateCategory(String oldName, String newName);
}
