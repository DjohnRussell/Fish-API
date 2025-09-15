package no.hiof.danieljr.FishApi.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FishDto {
    private String id;
    private String name;
    private Date addedAt;
}

