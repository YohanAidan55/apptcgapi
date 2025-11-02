package com.aidan.apptcg.security.oauth2;


import com.aidan.apptcg.user.repository.entity.UserEntity;
import com.aidan.apptcg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String userName = (String) attributes.get("userName");

        // Vérifie si l’utilisateur existe déjà
        Optional<UserEntity> existingUser = userRepository.findByEmail(email);

        UserEntity userEntity = existingUser.orElseGet(() -> {
            UserEntity newUserEntity = new UserEntity();
            newUserEntity.setEmail(email);
            newUserEntity.setUserName(userName);
            return userRepository.save(newUserEntity);
        });

        // Retourne un OAuth2User Spring Security standard
        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email"
        );
    }
}
