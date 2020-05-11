package com.endava.internship.codesolver.model.dao;

import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.User;
import com.endava.internship.codesolver.model.entities.UserStatistics;
import com.endava.internship.codesolver.model.entities.UserStatisticsId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticsDao extends CrudRepository<UserStatistics, UserStatisticsId> {

    List<UserStatistics> findByUser(User user);

    long countByUser (User user);

    Optional<UserStatistics> findByUserAndTask(User user, Task task);

    void deleteByTask(Task task);
}