package com.endava.internship.codesolver.logic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.endava.internship.codesolver.model.dao.UserDao;
import com.endava.internship.codesolver.model.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final UserDao userDao;

    @Value("${numberOfAllowedAttempts}")
    private int numberOfAllowedAttempts;

    public int getSuccessfulAttempts(final String username) {
        return userDao.findByLogin(username).map(User::getNumberOfAttempts).orElse(0);
    }

    @Transactional
    public void incrementUnsuccessfulAttempts(final String username) {
        final Optional<User> userOptional = userDao.findByLogin(username);

        if (userOptional.isPresent()) {
            final User foundUser = userOptional.get();
            int numberOfAttempts = foundUser.getNumberOfAttempts();
            if (numberOfAttempts > numberOfAllowedAttempts) {
                foundUser.setActive(false);
            } else {
                foundUser.setNumberOfAttempts(numberOfAttempts + 1);
            }
        }
    }

    @Transactional
    public void refreshAttempts(final String username) {
        userDao.findByLogin(username).ifPresent(user -> user.setNumberOfAttempts(0));
    }
}
