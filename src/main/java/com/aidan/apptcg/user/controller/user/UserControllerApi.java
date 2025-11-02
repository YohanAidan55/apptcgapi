package com.aidan.apptcg.user.controller.user;

import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.repository.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequestMapping("/api/users")
public interface UserControllerApi {

    @GetMapping("/me")
    ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails);

    @GetMapping("/all")
    List<UserEntity> getAll();

    @GetMapping("/get-by-email")
    UserDTO getByEmail(@RequestParam String email);
}

