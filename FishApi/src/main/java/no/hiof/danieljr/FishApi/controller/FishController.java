package no.hiof.danieljr.FishApi.controller;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import no.hiof.danieljr.FishApi.dto.FishDto;
import no.hiof.danieljr.FishApi.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.status(HttpStatus.CREATED).body("Fish added with ID: " + fishId);
    }

    @GetMapping
    public ResponseEntity<List<FishDto>> getFishes(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String waterType,
            @RequestParam(required = false) String habitat,
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String family,
            @RequestParam(required = false) String waterBodyType,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String diet,
            @RequestParam(required = false) String conservationStatus) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        // Pass filters to service for Firestore-level querying
        List<FishDto> fishes = fishService.getFishes(uid, country, region, city, waterType, habitat,
                species, family, waterBodyType, size, diet, conservationStatus);

        return ResponseEntity.ok(fishes);
    }

    @GetMapping("/{fishId}")
    public ResponseEntity<FishDto> getFishById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String fishId) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        FishDto fish = fishService.getFishById(uid, fishId);
        if (fish != null) {
            return ResponseEntity.ok(fish);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{fishId}")
    public ResponseEntity<String> updateFish(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String fishId,
            @RequestBody FishDto fishDto) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        // Check if fish exists
        FishDto existingFish = fishService.getFishById(uid, fishId);
        if (existingFish == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fish not found with ID: " + fishId);
        }

        fishService.updateFish(uid, fishId, fishDto);
        return ResponseEntity.ok("Fish updated successfully with ID: " + fishId);
    }

    @DeleteMapping("/{fishId}")
    public ResponseEntity<String> deleteFish(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String fishId) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        // Check if fish exists
        FishDto existingFish = fishService.getFishById(uid, fishId);
        if (existingFish == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fish not found with ID: " + fishId);
        }

        fishService.deleteFish(uid, fishId);
        return ResponseEntity.ok("Fish deleted successfully with ID: " + fishId);
    }
}
