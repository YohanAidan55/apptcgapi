package com.aidan.apptcg.user.controller.auth;

import com.aidan.apptcg.security.jwt.JwtService;
import com.aidan.apptcg.user.domain.dto.RegisterResponseDTO;
import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    public UserDTO register(UserDTO userDTO) {
        return authService.register(userDTO);
    }

    public RegisterResponseDTO login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateAccessToken(user.getUsername());

        return new RegisterResponseDTO(token);
    }

    @Override
    public void setPassword(SetPasswordRequest request) {
        authService.setPassword(request.email(), request.newPassword());
    }

    public UserDTO confirmAccount(ConfirmRegisterRequest confirmRegisterRequest) {
         return authService.confirmToken(confirmRegisterRequest.token());
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        authService.requestPasswordReset(request.email());
    }

    public void resetPassword(ResetPasswordRequest request) {
        authService.resetPassword(request.token(), request.newPassword());
    }

}
