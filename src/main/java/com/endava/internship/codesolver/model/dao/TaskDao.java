package com.endava.internship.codesolver.model.dao;

import com.endava.internship.codesolver.model.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao extends CrudRepository<Task, String> {

    void deleteTaskByTaskId(@Param("taskId") String taskId);

    List<Task> findAll();
}
