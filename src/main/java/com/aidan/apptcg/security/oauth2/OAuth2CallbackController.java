package com.aidan.apptcg.security.oauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2CallbackController {

    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam String token) {
        return "Connexion réussie ✅<br/>Token JWT :<br/><code>" + token + "</code>";
    }
}