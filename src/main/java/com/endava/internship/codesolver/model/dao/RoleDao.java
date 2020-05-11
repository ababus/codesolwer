package com.endava.internship.codesolver.model.dao;

import com.endava.internship.codesolver.model.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {

    Role findByName(String name);

}
