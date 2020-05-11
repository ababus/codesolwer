package com.endava.internship.codesolver.logic.converter.testentities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private List<Privilege> privileges;


    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User that = (User) obj;
            return new EqualsBuilder()
                    .append(this.id, that.getId())
                    .append(this.age, that.getAge())
                    .append(this.firstName, that.getFirstName())
                    .append(this.lastName, that.getLastName())
                    .append(this.privileges, that.getPrivileges())
                    .build();
        } else {
            return false;
        }
    }


    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.age)
                .append(this.firstName)
                .append(this.lastName)
                .append(this.privileges)
                .build();
    }

}
