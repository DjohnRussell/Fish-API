package no.hiof.danieljr.FishApi.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String displayName;
}
