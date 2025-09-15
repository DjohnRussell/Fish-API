package no.hiof.danieljr.FishApi.service;


import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import no.hiof.danieljr.FishApi.dto.FishDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FishService {

    public String addFish(String userId, FishDto fishDto) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users")
                .document(userId)
                .collection("fishes")
                .document(fishDto.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("name", fishDto.getName());
        data.put("addedAt", fishDto.getAddedAt());

        docRef.set(data).get();
        return docRef.getId();
    }

    public List<FishDto> getFishes(String userId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        List<FishDto> fishes = new ArrayList<>();

        for (QueryDocumentSnapshot doc : db.collection("users")
                .document(userId)
                .collection("fishes")
                .get()
                .get()
                .getDocuments()) {
            fishes.add(new FishDto(
                    doc.getId(),
                    doc.getString("name"),
                    doc.getDate("addedAt")
            ));
        }
        return fishes;
    }
}
