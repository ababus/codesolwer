package com.endava.internship.codesolver.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
@ToString(of = "name")
public class Role {

    @Id
    @Column(name = "role_id")
    private String id;

    @Column(name = "role_body")
    private String name;

}
