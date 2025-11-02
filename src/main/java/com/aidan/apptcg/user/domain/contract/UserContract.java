package com.aidan.apptcg.user.domain.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public interface UserContract {

    @NotNull
    @Length(min = 1, max = 20)
    String getFirstName();

    @NotNull
    @Length(min = 1, max = 20)
    String getLastName();

    @NotNull
    @Length(min = 1, max = 20)
    String getUserName();

    @NotNull
    @Email
    String getEmail();

    @NotNull
    @Length(min = 6, max = 50)
    String getPassword();
}
