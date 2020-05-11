package com.endava.internship.codesolver.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tests")
@Getter
@Setter
@ToString(of = "testBody")
@EqualsAndHashCode(of = "testId")
public class TestForTask {

    @Id
    @Column(name = "test_id", updatable = false, nullable = false)
    private String testId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "test_file")
    private String testBody;

}
