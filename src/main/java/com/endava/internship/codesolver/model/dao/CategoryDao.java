package com.endava.internship.codesolver.model.dao;

import com.endava.internship.codesolver.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByNameContainingIgnoreCase (String categoryName);

    boolean existsByNameContainingIgnoreCase (String name);

    List<Category> findAll();
 }
