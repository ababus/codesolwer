package com.endava.internship.codesolver.logic.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.endava.internship.codesolver.logic.dto.UserRegistrationDto;
import com.endava.internship.codesolver.logic.dto.UserRolesDTO;
import com.endava.internship.codesolver.model.entities.User;

@Service
public interface UserService extends UserDetailsService {

    User findByLogin(String login);

    void save(UserRegistrationDto registration);

    User getCurrentUser();

    boolean userIsAdministrator(User user);

    List<UserRolesDTO> getAllUsers();
}
