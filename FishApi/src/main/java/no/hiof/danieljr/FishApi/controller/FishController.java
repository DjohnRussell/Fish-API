package no.hiof.danieljr.FishApi.controller;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import no.hiof.danieljr.FishApi.dto.FishDto;
import no.hiof.danieljr.FishApi.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fishes")
public class FishController {

    @Autowired
    private FishService fishService;

    @PostMapping
    public ResponseEntity<String> addFish(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FishDto fishDto) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        String fishId = fishService.addFish(uid, fishDto);

        return ResponseEntity.ok("Fish added with ID: " + fishId);
    }


    @GetMapping
    public ResponseEntity<List<FishDto>> getFishes(
            @RequestHeader("Authorization") String authHeader) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        List<FishDto> fishes = fishService.getFishes(uid);
        return ResponseEntity.ok(fishes);
    }
}
