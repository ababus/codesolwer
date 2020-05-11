package com.endava.internship.codesolver.logic.dto;

import com.endava.internship.codesolver.controller.security.constraint.FieldMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@FieldMatch.List({
        @FieldMatch(first = "username", second = "confirmUsername", message = "The login fields must match"),
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
@Getter
@Setter
public class UserRegistrationDto {

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    private String username;

    @NotNull
    private String confirmUsername;

    @AssertTrue
    private Boolean terms;

}
