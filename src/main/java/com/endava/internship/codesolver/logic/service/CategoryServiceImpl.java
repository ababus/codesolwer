package com.endava.internship.codesolver.logic.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.endava.internship.codesolver.model.dao.CategoryDao;
import com.endava.internship.codesolver.model.dao.TaskDao;
import com.endava.internship.codesolver.model.entities.Category;
import com.endava.internship.codesolver.model.entities.Task;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryRepository;

    private final TaskDao taskRepository;

    public Map<String, String> getCategoryMap() {
        return categoryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Category::getCategoryId, Category::getName));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryByName(final String categoryName) {
        return categoryRepository.findAll()
                .stream()
                .filter(category -> category.getName().equalsIgnoreCase(categoryName))
                .findFirst();
    }

    public Map<Category, List<Task>> getCategoryTaskMap() {
        return taskRepository.findAll().stream()
                .collect(Collectors.groupingBy(Task::getCategory));
    }

    @Transactional
    public boolean addCategory(String categoryName) {
        categoryRepository.save(Category.builder().categoryId(UUID.randomUUID().toString()).name(categoryName).build());
        return true;
    }

    public void updateCategory(String oldName, String newName) {
        final Optional<Category> currentCategory = categoryRepository.findByNameContainingIgnoreCase(oldName);
        currentCategory.ifPresent(c -> c.setName(newName));
        categoryRepository.save(currentCategory.orElseThrow(() -> new NoSuchElementException("There is no such category")));
    }

}
