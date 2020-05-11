package com.endava.internship.codesolver.model.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Mapping between User and UserStatistics There is a bidirectional relation between User and UserStatistics But!
 *
 * @OneToMany FetchType is LAZY by default, so be careful trying to access UserStatistics from User! If the User is not in persistence context (the transaction
 * is closed) you will get an empty list!
 */

@Entity
@Table(name = "users_tasks")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "userStatisticsId")
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStatistics {

    @EmbeddedId
    private UserStatisticsId userStatisticsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

    @Column(name = "attempts")
    private int attempts = 3;

    @Column(name = "passed")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean passed;

    @Column(name = "solution")
    private String solution;
}
