package com.endava.internship.codesolver.logic.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.endava.internship.codesolver.logic.dto.UserRegistrationDto;
import com.endava.internship.codesolver.logic.dto.UserRolesDTO;
import com.endava.internship.codesolver.model.dao.RoleDao;
import com.endava.internship.codesolver.model.dao.UserDao;
import com.endava.internship.codesolver.model.entities.Role;
import com.endava.internship.codesolver.model.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private static final String ROLE_USER = "RULE_USER";

    private final UserDao userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final HttpServletRequest authentication;

    private final RoleDao roleRepository;

    private final JdbcTemplate jdbcTemplate;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public void save(UserRegistrationDto registration) {
        final String userId = UUID.randomUUID().toString();
        userRepository.save(User.builder()
                .userId(userId)
                .login(registration.getUsername())
                .password(passwordEncoder.encode(registration.getPassword()))
                .roles(Collections.singletonList(roleRepository.findByName(ROLE_USER)))
                .build());
        jdbcTemplate.update("INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES (:userId, '1')", userId);
    }

    public User getCurrentUser() {
        String login = authentication.getUserPrincipal().getName();
        return userRepository.findByLogin(login).orElseThrow(() -> new NoSuchElementException("There is no such user in the system"));
    }

    public boolean userIsAdministrator(User user) {
        return user.getRoles().contains(roleRepository.findByName(ROLE_ADMIN));
    }

    public List<UserRolesDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserRolesDTO(user.getLogin(), user.getRoles(), user.isActive()))
                .collect(Collectors.toList());
    }

    public UserDetails loadUserByUsername(String login) {
        final User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
