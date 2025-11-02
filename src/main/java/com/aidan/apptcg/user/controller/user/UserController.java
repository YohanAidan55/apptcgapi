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
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApi {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        Optional<UserEntity> userOpt = userRepository.findByEmail(userDetails.getUsername());
        return userOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body((UserEntity) Map.of("error", "User not found")));
    }

    @GetMapping("/all")
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/getByEmail")
    public UserDTO getByEmail(String email) {
        return userMapper.toDto(userService.getByEmail(email));
    }
}

