package com.endava.internship.codesolver.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endava.internship.codesolver.model.entities.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByNameContainingIgnoreCase(String categoryName);

    boolean existsByNameIgnoreCase(String name);

    List<Category> findAll();
}
