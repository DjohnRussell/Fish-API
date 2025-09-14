package no.hiof.danieljr.FishApi.controller;

import no.hiof.danieljr.FishApi.dto.LoginRequest;
import no.hiof.danieljr.FishApi.dto.RegisterRequest;
import no.hiof.danieljr.FishApi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        try {
            userService.registerFirebaseUser(request);
            return ResponseEntity.ok("Bruker registrert i Firebase: " + request.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Feil ved registrering: " + e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            // Firebase Admin SDK støtter ikke direkte password-login, så vi må bruke REST API
            String firebaseApiKey = "DIN_FIREBASE_WEB_API_KEY"; // fra Firebase Console -> Project Settings -> Web API Key
            String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + firebaseApiKey;

            // JSON body for Firebase REST API
            String json = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                    request.getEmail(),
                    request.getPassword()
            );

            // Send POST request til Firebase
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest httpRequest = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                    .build();

            java.net.http.HttpResponse<String> response = client.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return ResponseEntity.ok(response.body()); // returner ID token m.m.
            } else {
                return ResponseEntity.status(response.statusCode()).body(response.body());
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Innloggingsfeil: " + e.getMessage());
        }
    }

}


