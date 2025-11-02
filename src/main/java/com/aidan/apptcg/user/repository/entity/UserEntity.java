package com.aidan.apptcg.user.repository.entity;

import com.aidan.apptcg.user.domain.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String userName;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
