package com.aidan.apptcg.security.oauth2;

import com.aidan.apptcg.security.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${app.oauth2.redirect-url}")
    private String frontendRedirectBase;
    private final JwtService jwtService;

    // URL de redirection front-end (tu peux la rendre configurable dans application.yaml)
    private static final String FRONT_REDIRECT_URL = "http://localhost:5173/oauth2/callback?token=";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        // Génère un JWT pour cet utilisateur
        String token = jwtService.generateAccessToken(email);

        log.info("OAuth2 login success for user: {}", email);

        // 🔁 Redirige vers ton front (Angular/Ionic/React) avec le token dans l’URL
        String redirectUrl = frontendRedirectBase + URLEncoder.encode(token, StandardCharsets.UTF_8);
        response.sendRedirect(redirectUrl);
    }
}
