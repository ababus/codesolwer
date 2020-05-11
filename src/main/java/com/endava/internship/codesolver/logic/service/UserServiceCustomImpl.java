package com.endava.internship.codesolver.logic.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.endava.internship.codesolver.model.dao.UserDao;
import com.endava.internship.codesolver.model.entities.User;

import static com.endava.internship.codesolver.logic.service.UserServiceImpl.mapRolesToAuthorities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceCustomImpl implements UserDetailsService {

    private final UserDao userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String login) {
        final User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        if (!user.isActive()) {
            throw new UsernameNotFoundException("User is inactive");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
}
