package no.hiof.danieljr.FishApi.dto;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FishDto {
    private String id;

    // Basic identification
    private String name;              // Common name of the fish (e.g. "Rainbow Trout")
    private String scientificName;    // Scientific/Latin name (e.g. "Oncorhynchus mykiss")
    private String species;           // Species classification
    private String family;            // Family classification (e.g. "Salmonidae")
    private String genus;             // Genus classification

    // Location & geography
    private String country;           // Country where the fish was caught/found
    private String region;            // Region or state/province
    private String city;              // City or town near the location
    private String locationName;      // Specific location name (e.g. "Lake Tahoe", "River Thames")
    private Double latitude;          // GPS latitude of the location
    private Double longitude;         // GPS longitude of the location

    // Water & environment
    private String waterType;         // Type of water: "freshwater", "saltwater", "brackish"
    private String habitat;           // Habitat description (e.g. "river", "lake", "ocean", "estuary", "coral reef")
    private String waterBodyType;     // Specific water body: "river", "lake", "stream", "pond", "ocean", "sea", "reservoir"
    private Double waterDepth;        // Depth in meters where fish was found
    private Double waterTemperature;  // Water temperature in Celsius
    private String waterClarity;      // Water clarity: "clear", "murky", "turbid"

    // Physical characteristics
    private Double weight;            // Weight in kilograms
    private Double length;            // Length in centimeters
    private String color;             // Dominant color or color pattern description
    private String size;              // Size category: "small", "medium", "large", "extra-large"

    // Behavior & biology
    private String diet;              // Diet type: "carnivore", "herbivore", "omnivore", "insectivore", "planktivore"
    private String behavior;          // Behavior notes (e.g. "schooling", "solitary", "migratory")
    private String migrationPattern;  // Migration pattern if applicable
    private String spawningSeason;    // Spawning/breeding season
    private Integer minDepth;         // Minimum depth in meters
    private Integer maxDepth;         // Maximum depth in meters

    // Conservation & regulations
    private String conservationStatus; // Conservation status: "LC", "NT", "VU", "EN", "CR", "EW", "EX"
    private String catchLimit;        // Catch limit regulations
    private String minSizeLimit;      // Minimum size limit for catching
    private Boolean protectedSpecies; // Whether the species is protected

    // Fishing details
    private String baitUsed;          // Bait or lure used to catch the fish
    private String fishingMethod;     // Method used: "rod and reel", "handline", "net", "spear", "trap"
    private String gearType;          // Specific gear type used

    // Media & notes
    private List<String> photoUrls;   // URLs to photos of the fish
    private String notes;             // Additional notes or observations
    private String description;       // General description of the fish

    // Timestamps
    private Date addedAt;             // When the record was created
    private Date caughtDate;          // Date the fish was caught or observed
}

