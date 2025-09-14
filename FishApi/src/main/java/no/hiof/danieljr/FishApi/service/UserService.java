package no.hiof.danieljr.FishApi.service;

import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import no.hiof.danieljr.FishApi.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final FirebaseAuth firebaseAuth;

    public UserService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void registerFirebaseUser(RegisterRequest request) throws FirebaseAuthException {
        CreateRequest firebaseRequest = new CreateRequest()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setDisplayName(request.getDisplayName());

        firebaseAuth.createUser(firebaseRequest);
    }
}
