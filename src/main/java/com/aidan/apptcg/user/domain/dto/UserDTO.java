package com.aidan.apptcg.user.domain.dto;

import com.aidan.apptcg.user.domain.contract.UserContract;
import com.aidan.apptcg.user.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO implements UserContract {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private RoleEnum role;
}
