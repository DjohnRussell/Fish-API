package no.hiof.danieljr.FishApi.controller;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import no.hiof.danieljr.FishApi.dto.FishSpeciesDto;
import no.hiof.danieljr.FishApi.service.FishSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/species")
public class FishSpeciesController {

    @Autowired
    private FishSpeciesService fishSpeciesService;

    @GetMapping
    public ResponseEntity<List<FishSpeciesDto>> getSpecies(
            @RequestParam(required = false) String waterType,
            @RequestParam(required = false) String family,
            @RequestParam(required = false) String habitat,
            @RequestParam(required = false) String diet,
            @RequestParam(required = false) String conservationStatus,
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String size) throws Exception {

        List<FishSpeciesDto> species = fishSpeciesService.getSpecies(waterType, family, habitat,
                diet, conservationStatus, continent, size);

        return ResponseEntity.ok(species);
    }

    @GetMapping("/{speciesId}")
    public ResponseEntity<FishSpeciesDto> getSpeciesById(@PathVariable String speciesId) throws Exception {
        FishSpeciesDto species = fishSpeciesService.getSpeciesById(speciesId);
        if (species != null) {
            return ResponseEntity.ok(species);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search/scientificName/{scientificName}")
    public ResponseEntity<FishSpeciesDto> getSpeciesByScientificName(@PathVariable String scientificName) throws Exception {
        FishSpeciesDto species = fishSpeciesService.getSpeciesByScientificName(scientificName);
        if (species != null) {
            return ResponseEntity.ok(species);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> addSpecies(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FishSpeciesDto speciesDto) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

        String speciesId = fishSpeciesService.addSpecies(speciesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Species added with ID: " + speciesId);
    }

    @PutMapping("/{speciesId}")
    public ResponseEntity<String> updateSpecies(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String speciesId,
            @RequestBody FishSpeciesDto speciesDto) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

        if (!fishSpeciesService.speciesExists(speciesId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Species not found with ID: " + speciesId);
        }

        fishSpeciesService.updateSpecies(speciesId, speciesDto);
        return ResponseEntity.ok("Species updated successfully with ID: " + speciesId);
    }

    @DeleteMapping("/{speciesId}")
    public ResponseEntity<String> deleteSpecies(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String speciesId) throws Exception {

        String idToken = authHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

        if (!fishSpeciesService.speciesExists(speciesId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Species not found with ID: " + speciesId);
        }

        fishSpeciesService.deleteSpecies(speciesId);
        return ResponseEntity.ok("Species deleted successfully with ID: " + speciesId);
    }
}
