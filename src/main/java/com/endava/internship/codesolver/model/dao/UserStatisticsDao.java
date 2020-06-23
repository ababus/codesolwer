package com.endava.internship.codesolver.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.endava.internship.codesolver.model.entities.Task;
import com.endava.internship.codesolver.model.entities.User;
import com.endava.internship.codesolver.model.entities.UserStatistics;
import com.endava.internship.codesolver.model.entities.UserStatisticsId;

@Repository
public interface UserStatisticsDao extends CrudRepository<UserStatistics, UserStatisticsId> {

    List<UserStatistics> findByUser(User user);

    List<UserStatistics> findByUserLogin(String login);

    long countByUser(User user);

    long countByUserLogin(String login);

    Optional<UserStatistics> findByUserAndTask(User user, Task task);

    void deleteByTask(Task task);
}