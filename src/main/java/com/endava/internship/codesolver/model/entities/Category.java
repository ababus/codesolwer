package com.endava.internship.codesolver.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(of = "categoryId")
@ToString(of = "name")
@Getter
@Setter
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Task> tasks;
}
