package com.endava.internship.codesolver.model.dao;

import com.endava.internship.codesolver.model.entities.TestForTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends CrudRepository<TestForTask, String> {

}
