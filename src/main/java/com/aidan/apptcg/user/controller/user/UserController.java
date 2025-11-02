package com.aidan.apptcg.user.controller.user;

import com.aidan.apptcg.user.domain.dto.UserDTO;
import com.aidan.apptcg.user.repository.entity.UserEntity;
import com.aidan.apptcg.user.repository.UserRepository;
import com.aidan.apptcg.user.repository.mapper.UserMapper;
import com.aidan.apptcg.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApi {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public UserDTO getCurrentUser(UserDetails userDetails) {
       return userRepository.findByEmail(userDetails.getUsername())
               .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + userDetails.getUsername()));
    }

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    @GetMapping("/getByEmail")
    public UserDTO getByEmail(String email) {
        return userMapper.toDto(userService.getByEmail(email));
    }
}

