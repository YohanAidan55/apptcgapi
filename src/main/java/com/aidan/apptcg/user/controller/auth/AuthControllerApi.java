package com.aidan.apptcg.user.controller.auth;

import com.aidan.apptcg.user.domain.dto.RegisterResponseDTO;
import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.repository.entity.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/auth")
public interface AuthControllerApi {
    @PostMapping("/register")
    UserDTO register(@RequestBody @Validated UserDTO userDTO);

    @PostMapping("/login")
    RegisterResponseDTO login(@RequestBody @Validated LoginRequest request);

    @PostMapping("/confirm")
    UserDTO confirmAccount(@RequestBody @Validated ConfirmRegisterRequest confirmRegisterRequest);

    @PostMapping("/forgot-password")
    void forgotPassword(@RequestBody @Validated ForgotPasswordRequest request);

    @PostMapping("/reset-password")
    void resetPassword(@RequestBody @Validated ResetPasswordRequest request);

    record LoginRequest(@NotNull @Email String email, @NotNull @Length(min = 6, max = 50) String password) {}
    record ForgotPasswordRequest(@NotNull @Email String email) {}
    record ConfirmRegisterRequest(@NotNull String token) {}
    record ResetPasswordRequest(@NotNull String token, @NotNull @Length(min = 6, max = 50) String newPassword) {}

}
