package com.endava.internship.codesolver.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@EqualsAndHashCode(of = "taskId")
@ToString(of = {"taskId", "taskTitle", "taskBody", "description"})
public class Task {

    @Id
    @Column(name="task_id")
    private String taskId;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "task_body")
    private String taskBody;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "task",
            cascade = CascadeType.ALL)
    private List<TestForTask> tests;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
}
