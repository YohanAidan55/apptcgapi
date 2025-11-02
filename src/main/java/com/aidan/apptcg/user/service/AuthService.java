package com.aidan.apptcg.user.service;

import com.aidan.apptcg.exception.NotFoundException;
import com.aidan.apptcg.notification.controller.NotificationControllerApi;
import com.aidan.apptcg.security.jwt.JwtService;
import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.domain.enums.RoleEnum;
import com.aidan.apptcg.user.repository.UserRepository;
import com.aidan.apptcg.user.repository.entity.UserEntity;
import com.aidan.apptcg.user.repository.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final NotificationControllerApi notificationControllerApi;
    private final JwtService jwtService;

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(_ -> {
                    throw new IllegalStateException("User already exists with email: " + userDTO.getEmail());
                });
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setEnabled(false);
        userEntity.setRole(RoleEnum.ROLE_USER);
        String confirmedToken = jwtService.generateTokenEmail(userDTO.getEmail(), 5);
        notificationControllerApi.sendRegistrationEmail(userDTO.getEmail(), confirmedToken);
        return userMapper.toDto(userRepository.save(userEntity));
    }

    @Transactional
    public UserDTO confirmToken(String token) {
        String email = jwtService.extractUsername(token);

        if (email == null || jwtService.isTokenExpired(token)) {
            throw new IllegalArgumentException("Token invalide ou expiré");
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));
        userEntity.setEnabled(true);
        return userMapper.toDto(userRepository.save(userEntity));
    }


    @Transactional
    public void requestPasswordReset(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));

        String resetToken = jwtService.generateToken(email);

        notificationControllerApi.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        String email = jwtService.extractUsername(token);

        if (!jwtService.isTokenValid(token, email)) {
            throw new RuntimeException("Token invalide ou expiré");
        }

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
