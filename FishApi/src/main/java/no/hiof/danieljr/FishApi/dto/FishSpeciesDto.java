package no.hiof.danieljr.FishApi.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FishSpeciesDto {
    private String id;

    // Identification
    private String commonName;           // Common name (e.g. "Rainbow Trout")
    private String norwegianName;        // Norwegian name (e.g. "Regnbueørret")
    private String scientificName;       // Scientific/Latin name (e.g. "Oncorhynchus mykiss")
    private String species;              // Species epithet
    private String family;               // Family classification (e.g. "Salmonidae")
    private String genus;                // Genus classification

    // Native range & distribution
    private List<String> nativeRegions;  // Native geographic regions
    private List<String> nativeCountries; // Native countries
    private String continent;            // Primary continent

    // Water & environment
    private String waterType;            // "freshwater", "saltwater", "brackish", "anadromous", "catadromous"
    private List<String> habitats;       // Habitat types: "river", "lake", "ocean", "estuary", "coral reef", "stream", "pond", "reservoir"
    private List<String> waterBodyTypes; // "river", "lake", "stream", "pond", "ocean", "sea", "reservoir", "estuary"
    private Integer minDepth;            // Minimum depth in meters
    private Integer maxDepth;            // Maximum depth in meters
    private Double minTemperature;       // Minimum water temperature in Celsius
    private Double maxTemperature;       // Maximum water temperature in Celsius

    // Physical characteristics
    private Double avgWeight;            // Average weight in kilograms
    private Double maxWeight;            // Maximum recorded weight in kilograms
    private Double avgLength;            // Average length in centimeters
    private Double maxLength;            // Maximum recorded length in centimeters
    private String color;                // Dominant color or color pattern description
    private String size;                 // Size category: "small", "medium", "large", "extra-large"

    // Behavior & biology
    private String diet;                 // "carnivore", "herbivore", "omnivore", "insectivore", "planktivore"
    private List<String> prey;           // Common prey items
    private String behavior;             // Behavior notes: "schooling", "solitary", "migratory", "territorial"
    private String migrationPattern;     // Migration pattern if applicable
    private String spawningSeason;       // Spawning/breeding season
    private String spawningHabitat;      // Where spawning occurs
    private Integer lifespan;            // Lifespan in years

    // Conservation & regulations
    private String conservationStatus;   // IUCN status: "LC", "NT", "VU", "EN", "CR", "EW", "EX"
    private Boolean protectedSpecies;    // Whether the species is protected
    private String notes;                // Additional notes or observations
    private String description;          // General description of the species
}
