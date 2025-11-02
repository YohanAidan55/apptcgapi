package com.aidan.apptcg.security.config;

import com.aidan.apptcg.user.controller.user.UserControllerApi;
import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements UserDetailsService {
private final UserControllerApi userControllerApi;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO userEntity = userControllerApi.getByEmail(email);


        // Convertit les rôles en authorities Spring
        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority(userEntity.getRole().name())
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .disabled(!userEntity.isEnabled())
                .build();
    }
}
