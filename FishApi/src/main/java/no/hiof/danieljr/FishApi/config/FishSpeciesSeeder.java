package no.hiof.danieljr.FishApi.config;


import no.hiof.danieljr.FishApi.dto.FishSpeciesDto;
import no.hiof.danieljr.FishApi.service.FishSpeciesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FishSpeciesSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(FishSpeciesSeeder.class);

    private final FishSpeciesService fishSpeciesService;

    public FishSpeciesSeeder(FishSpeciesService fishSpeciesService) {
        this.fishSpeciesService = fishSpeciesService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Checking if fish species database needs seeding...");
        List<FishSpeciesDto> existing = fishSpeciesService.getAllSpecies();

        if (!existing.isEmpty()) {
            // Check if any species is missing norwegianName and update them
            boolean needsUpdate = existing.stream().anyMatch(s -> s.getNorwegianName() == null || s.getNorwegianName().isEmpty());
            if (needsUpdate) {
                log.info("Updating existing species with Norwegian names...");
                List<FishSpeciesDto> speciesList = buildSpeciesData();
                for (FishSpeciesDto species : speciesList) {
                    String docId = species.getScientificName().replaceAll("[\\s()]", "").toLowerCase();
                    // Only update the norwegianName field
                    FishSpeciesDto updateDto = new FishSpeciesDto();
                    updateDto.setNorwegianName(species.getNorwegianName());
                    fishSpeciesService.updateSpecies(docId, updateDto);
                }
                log.info("Successfully updated {} species with Norwegian names.", speciesList.size());
            } else {
                log.info("Fish species database already contains {} species with Norwegian names. Skipping.", existing.size());
            }
            return;
        }

        log.info("Seeding fish species database with real species data...");
        List<FishSpeciesDto> speciesList = buildSpeciesData();
        fishSpeciesService.addSpeciesBatch(speciesList);
        log.info("Successfully seeded {} fish species into the database.", speciesList.size());
    }

    private List<FishSpeciesDto> buildSpeciesData() {
        return Arrays.asList(
                // ===== SALMONIDAE (Salmon & Trout Family) =====

                new FishSpeciesDto(null, "Atlantic Salmon", "Atlanterhavssørl", "Salmo salar", "salar", "Salmonidae", "Salmo",
                        Arrays.asList("North Atlantic", "Eastern Canada", "Norway", "Iceland", "Scotland"),
                        Arrays.asList("Norway", "Iceland", "Canada", "United States", "United Kingdom", "Ireland", "Faroe Islands", "Greenland"),
                        "Europe", "anadromous",
                        Arrays.asList("river", "ocean", "stream", "lake"),
                        Arrays.asList("river", "ocean", "stream", "lake"),
                        0, 200, 2.0, 14.0,
                        4.6, 12.0, 58.0, 100.0, "Silver with pink lateral line; dark back", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "insects", "squid"),
                        "migratory", "Anadromous: spawns in freshwater rivers, feeds in ocean",
                        "Spring to early summer", "Gravel river beds", 13,
                        "LC", false, null,
                        "The Atlantic salmon is a highly prized game fish known for its remarkable migratory journey from freshwater spawning grounds to the open ocean and back."),

                new FishSpeciesDto(null, "Rainbow Trout", "Regnbueørret", "Oncorhynchus mykiss", "mykiss", "Salmonidae", "Oncorhynchus",
                        Arrays.asList("Pacific Northwest", "North America"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "freshwater",
                        Arrays.asList("river", "stream", "lake", "reservoir"),
                        Arrays.asList("river", "stream", "lake", "reservoir"),
                        0, 30, 4.0, 20.0,
                        2.3, 11.3, 60.0, 120.0, "Silver with pink-red lateral stripe; dark green back", "large",
                        "carnivore", Arrays.asList("insects", "crustaceans", "small fish", "leeches"),
                        "solitary", "Some populations are anadromous (steelhead)",
                        "Spring", "Gravel-bottomed streams", 8,
                        "LC", false, null,
                        "One of the most popular game fish worldwide, widely stocked in freshwater systems. The anadromous form is known as steelhead."),

                new FishSpeciesDto(null, "Brown Trout", "Brunørret", "Salmo trutta", "trutta", "Salmonidae", "Salmo",
                        Arrays.asList("Europe", "North Africa", "Western Asia"),
                        Arrays.asList("United Kingdom", "Germany", "France", "Norway", "Spain", "Italy", "Poland"),
                        "Europe", "anadromous",
                        Arrays.asList("river", "lake", "stream", "ocean"),
                        Arrays.asList("river", "lake", "stream", "ocean"),
                        0, 50, 4.0, 20.0,
                        3.6, 17.0, 70.0, 130.0, "Brown with black and red spots; lighter belly", "large",
                        "carnivore", Arrays.asList("insects", "crustaceans", "small fish", "mollusks"),
                        "territorial", "Sea trout migrate to ocean; resident forms stay in freshwater",
                        "Autumn to winter", "Gravel river beds", 10,
                        "LC", false, null,
                        "A native European trout that is both freshwater resident and anadromous (sea trout). Known for being a cautious and challenging catch."),

                new FishSpeciesDto(null, "Brook Trout", "Bekkeørret", "Salvelinus fontinalis", "fontinalis", "Salmonidae", "Salvelinus",
                        Arrays.asList("Eastern North America"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "freshwater",
                        Arrays.asList("stream", "river", "lake", "pond"),
                        Arrays.asList("stream", "river", "lake", "pond"),
                        0, 20, 2.0, 16.0,
                        0.9, 5.4, 40.0, 80.0, "Olive-green with red spots surrounded by blue halos; marbled belly", "medium",
                        "carnivore", Arrays.asList("insects", "crustaceans", "small fish"),
                        "territorial", "Prefers cold, well-oxygenated water",
                        "Late summer to autumn", "Shallow gravel areas in streams", 7,
                        "LC", false, null,
                        "Also called speckled trout, it is the state fish of New York and Maryland. Prefers cold, clear, well-oxygenated streams."),

                new FishSpeciesDto(null, "Lake Trout", "Innsjøørret", "Salvelinus namaycush", "namaycush", "Salmonidae", "Salvelinus",
                        Arrays.asList("Northern North America"),
                        Arrays.asList("Canada", "United States"),
                        "North America", "freshwater",
                        Arrays.asList("lake", "reservoir"),
                        Arrays.asList("lake", "reservoir"),
                        10, 200, 0.5, 12.0,
                        5.4, 28.0, 80.0, 130.0, "Dark brown to olive with light star-shaped spots", "extra-large",
                        "carnivore", Arrays.asList("small fish", "insects", "crustaceans"),
                        "solitary", "Deep-water dweller; migrates to shallower waters to spawn",
                        "Autumn", "Shallow gravel areas in deep lakes", 16,
                        "LC", false, null,
                        "The largest member of the char family found in North America, inhabiting deep, cold lakes."),

                new FishSpeciesDto(null, "Arctic Char", "Røye", "Salvelinus alpinus", "alpinus", "Salmonidae", "Salvelinus",
                        Arrays.asList("Arctic regions", "Northern Europe", "Northern Asia", "Northern North America"),
                        Arrays.asList("Norway", "Iceland", "Canada", "Greenland", "Russia", "Finland", "Sweden"),
                        "Europe", "anadromous",
                        Arrays.asList("lake", "river", "ocean", "stream"),
                        Arrays.asList("lake", "river", "ocean", "stream"),
                        0, 100, 0.0, 12.0,
                        2.7, 18.0, 70.0, 110.0, "Dark with light spots and pink markings", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "insects"),
                        "migratory", "Some populations are anadromous",
                        "Autumn", "Gravel beds in rivers and lake shallows", 15,
                        "LC", false, null,
                        "A cold-water fish related to trout and salmon, found in Arctic and sub-Arctic waters across the Northern Hemisphere."),

                // ===== CICHLIDAE (Cichlid Family) =====

                new FishSpeciesDto(null, "Nile Tilapia", "Niltilapia", "Oreochromis niloticus", "niloticus", "Cichlidae", "Oreochromis",
                        Arrays.asList("East Africa", "Nile Basin"),
                        Arrays.asList("Egypt", "Ethiopia", "Kenya", "Sudan", "Tanzania", "Uganda"),
                        "Africa", "freshwater",
                        Arrays.asList("river", "lake", "pond", "stream"),
                        Arrays.asList("river", "lake", "pond", "stream"),
                        0, 10, 20.0, 32.0,
                        1.8, 4.6, 30.0, 60.0, "Olive-brown with darker vertical bars", "medium",
                        "omnivore", Arrays.asList("algae", "plants", "insects", "small crustaceans"),
                        "schooling", "Mouthbrooder; both parents guard young",
                        "Rainy season", "Shallow waters with vegetation", 9,
                        "LC", false, null,
                        "One of the most important aquaculture species worldwide, native to the Nile River basin."),

                // ===== CENTRARCHIDAE (Sunfish Family) =====

                new FishSpeciesDto(null, "Largemouth Bass", "Svartmunnbass", "Micropterus salmoides", "salmoides", "Centrarchidae", "Micropterus",
                        Arrays.asList("Eastern and Central North America"),
                        Arrays.asList("United States", "Canada", "Mexico"),
                        "North America", "freshwater",
                        Arrays.asList("lake", "pond", "river", "stream", "reservoir"),
                        Arrays.asList("lake", "pond", "river", "stream", "reservoir"),
                        0, 10, 10.0, 32.0,
                        1.8, 10.0, 30.0, 70.0, "Dark green to olive with dark lateral band", "large",
                        "carnivore", Arrays.asList("small fish", "crayfish", "frogs", "insects"),
                        "territorial", "Ambush predator near cover",
                        "Spring", "Shallow gravel nests", 16,
                        "LC", false, null,
                        "The most popular game fish in North America, known for its aggressive strikes and fighting ability."),

                new FishSpeciesDto(null, "Smallmouth Bass", "Rødmunnbass", "Micropterus dolomieu", "dolomieu", "Centrarchidae", "Micropterus",
                        Arrays.asList("Eastern North America", "Great Lakes"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "freshwater",
                        Arrays.asList("river", "stream", "lake", "reservoir"),
                        Arrays.asList("river", "stream", "lake", "reservoir"),
                        0, 30, 5.0, 28.0,
                        1.1, 5.4, 30.0, 55.0, "Bronze to olive with vertical bars", "medium",
                        "carnivore", Arrays.asList("crayfish", "insects", "small fish"),
                        "territorial", "Prefers rocky bottoms and swift currents",
                        "Spring", "Shallow gravel areas", 10,
                        "LC", false, null,
                        "Known as the 'bronze zebra,' it is highly prized for its fighting spirit and preference for rocky river habitats."),

                new FishSpeciesDto(null, "Bluegill", "Blågill", "Lepomis macrochirus", "macrochirus", "Centrarchidae", "Lepomis",
                        Arrays.asList("Eastern and Central North America"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "freshwater",
                        Arrays.asList("lake", "pond", "stream", "reservoir"),
                        Arrays.asList("lake", "pond", "stream", "reservoir"),
                        0, 5, 10.0, 32.0,
                        0.3, 1.8, 20.0, 40.0, "Blue-green with dark ear flap; reddish opercular flap", "small",
                        "omnivore", Arrays.asList("insects", "crustaceans", "small fish", "plant matter"),
                        "schooling", "Forms schools; males guard nests",
                        "Late spring to summer", "Shallow vegetated areas", 10,
                        "LC", false, null,
                        "A popular panfish found throughout North America, often the first fish caught by young anglers."),

                // ===== ESCOCEPIDAE (Pikeperch Family) =====

                new FishSpeciesDto(null, "Zander", "Gjeddeabbor", "Sander lucioperca", "lucioperca", "Esocidae", "Sander",
                        Arrays.asList("Eastern Europe", "Western Asia"),
                        Arrays.asList("Russia", "Germany", "Poland", "Romania", "Ukraine", "Hungary"),
                        "Europe", "freshwater",
                        Arrays.asList("river", "lake", "reservoir"),
                        Arrays.asList("river", "lake", "reservoir"),
                        0, 50, 5.0, 24.0,
                        3.6, 12.0, 50.0, 100.0, "Silver with dark back; two dorsal fins", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans"),
                        "solitary", "Nocturnal predator",
                        "Spring", "Gravel river beds", 12,
                        "LC", false, null,
                        "A large predatory fish popular in European angling, also known as pikeperch or sandfish."),

                // ===== ESCOCEPIDAE (Pike Family) =====

                new FishSpeciesDto(null, "Northern Pike", "Gjedde", "Esox lucius", "lucius", "Esocidae", "Esox",
                        Arrays.asList("Northern Hemisphere", "Europe", "Asia", "North America"),
                        Arrays.asList("Russia", "Canada", "United States", "Norway", "Sweden", "Finland", "Germany", "Poland"),
                        "Europe", "freshwater",
                        Arrays.asList("lake", "pond", "river", "stream"),
                        Arrays.asList("lake", "pond", "river", "stream"),
                        0, 10, 2.0, 24.0,
                        4.5, 25.0, 70.0, 150.0, "Green with yellow spots; flat head with sharp teeth", "extra-large",
                        "carnivore", Arrays.asList("small fish", "frogs", "waterfowl", "small mammals"),
                        "solitary", "Ambush predator near vegetation",
                        "Late winter to early spring", "Shallow vegetated areas", 15,
                        "LC", false, null,
                        "A large, predatory freshwater fish found across the Northern Hemisphere, known for its aggressive feeding and sharp teeth."),

                // ===== PERCACEAE (Perch Family) =====

                new FishSpeciesDto(null, "European Perch", "Abbor", "Perca fluviatilis", "fluviatilis", "Percidae", "Perca",
                        Arrays.asList("Europe", "Northern Asia"),
                        Arrays.asList("United Kingdom", "Germany", "France", "Norway", "Sweden", "Russia", "Poland"),
                        "Europe", "freshwater",
                        Arrays.asList("lake", "river", "pond", "reservoir"),
                        Arrays.asList("lake", "river", "pond", "reservoir"),
                        0, 20, 4.0, 24.0,
                        0.5, 2.5, 25.0, 50.0, "Golden-green with dark vertical bars; orange-red fins", "medium",
                        "carnivore", Arrays.asList("small fish", "insects", "crustaceans"),
                        "schooling", "Forms large schools",
                        "Spring", "Vegetated shallow areas", 10,
                        "LC", false, null,
                        "A popular game and food fish throughout Europe, recognized by its distinctive vertical bars."),

                new FishSpeciesDto(null, "Yellow Perch", "Gul abbor", "Perca flavescens", "flavescens", "Percidae", "Perca",
                        Arrays.asList("Central and Eastern North America"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "freshwater",
                        Arrays.asList("lake", "pond", "river", "reservoir"),
                        Arrays.asList("lake", "pond", "river", "reservoir"),
                        0, 15, 4.0, 26.0,
                        0.3, 1.8, 20.0, 40.0, "Golden-yellow with dark vertical bars", "small",
                        "carnivore", Arrays.asList("insects", "crustaceans", "small fish"),
                        "schooling", "Forms large schools, especially in winter",
                        "Spring", "Vegetated areas", 10,
                        "LC", false, null,
                        "A popular panfish in North America, known for its golden color and good eating quality."),

                // ===== GADIDAE (Cod Family) =====

                new FishSpeciesDto(null, "Atlantic Cod", "Torske", "Gadus morhua", "morhua", "Gadidae", "Gadus",
                        Arrays.asList("North Atlantic"),
                        Arrays.asList("Norway", "Iceland", "Canada", "United States", "United Kingdom", "Faroe Islands", "Greenland", "Denmark"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 600, 0.0, 10.0,
                        13.6, 96.0, 80.0, 200.0, "Greenish-brown with lighter belly; three barbel on chin", "extra-large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "mollusks", "worms"),
                        "schooling", "Migrates between feeding and spawning grounds",
                        "Winter to spring", "Shallow coastal waters", 25,
                        "VU", false, null,
                        "One of the most important commercial fish in the world, historically central to the fishing industry of Northern Europe and North America."),

                new FishSpeciesDto(null, "Haddock", "Høydire", "Melanogrammus aeglefinus", "aeglefinus", "Gadidae", "Melanogrammus",
                        Arrays.asList("North Atlantic"),
                        Arrays.asList("Norway", "Iceland", "Canada", "United Kingdom", "Faroe Islands", "Greenland"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        20, 600, 0.0, 10.0,
                        3.6, 14.0, 60.0, 100.0, "Brownish with dark thumbprint mark; silvery sides", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "worms"),
                        "schooling", "Migrates seasonally",
                        "Spring", "Shallow coastal waters", 19,
                        "LC", false, null,
                        "A close relative of cod, recognized by a dark thumbprint-like spot behind the gill cover."),

                new FishSpeciesDto(null, "Pollock", "Sei", "Pollachius virens", "virens", "Gadidae", "Pollachius",
                        Arrays.asList("North Atlantic"),
                        Arrays.asList("Norway", "Iceland", "United Kingdom", "Ireland", "Canada"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        20, 300, 2.0, 14.0,
                        5.4, 20.0, 70.0, 120.0, "Greenish-brown with dark spots; forked tail", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "squid"),
                        "schooling", "Pelagic migrant",
                        "Winter to spring", "Coastal waters", 15,
                        "LC", false, null,
                        "Also called pollock or saithe, it is an important commercial and sport fish in the North Atlantic."),

                // ===== SCIAENIDAE (Drum Family) =====

                new FishSpeciesDto(null, "Atlantic Croaker", "Atlanterhavskroaker", "Micropogonias undulatus", "undulatus", "Sciaenidae", "Micropogonias",
                        Arrays.asList("Western Atlantic"),
                        Arrays.asList("United States", "Mexico", "Brazil"),
                        "North America", "brackish",
                        Arrays.asList("estuary", "river", "ocean", "sea"),
                        Arrays.asList("estuary", "river", "ocean", "sea"),
                        0, 50, 15.0, 30.0,
                        1.4, 5.4, 35.0, 65.0, "Bronze to olive with silvery belly", "medium",
                        "carnivore", Arrays.asList("crustaceans", "small fish", "worms"),
                        "schooling", "Migrates between estuaries and ocean",
                        "Spring to summer", "Shallow estuarine waters", 12,
                        "LC", false, null,
                        "A popular saltwater game fish in the southeastern United States, known for its drumming sound."),

                // ===== MORONIDAE (Weakfish Family) =====

                new FishSpeciesDto(null, "Striped Bass", "Striped bass", "Morone saxatilis", "saxatilis", "Moronidae", "Morone",
                        Arrays.asList("Eastern North America"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "river", "estuary", "reservoir"),
                        Arrays.asList("ocean", "river", "estuary", "reservoir"),
                        0, 100, 10.0, 28.0,
                        9.1, 57.0, 100.0, 200.0, "Silver with seven dark horizontal stripes", "extra-large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "squid"),
                        "schooling", "Anadromous; some landlocked populations",
                        "Spring and autumn", "Shallow coastal and river waters", 30,
                        "LC", false, null,
                        "The premier saltwater game fish of the Atlantic coast, known for its powerful runs and impressive size."),

                // ===== CENTROPOMIDAE (Snook Family) =====

                new FishSpeciesDto(null, "Red Drum", "Rød tromme", "Sciaenops ocellatus", "ocellatus", "Sciaenidae", "Sciaenops",
                        Arrays.asList("Western Atlantic", "Gulf of Mexico"),
                        Arrays.asList("United States", "Mexico", "Brazil"),
                        "North America", "brackish",
                        Arrays.asList("estuary", "ocean", "sea", "river"),
                        Arrays.asList("estuary", "ocean", "sea", "river"),
                        0, 30, 18.0, 32.0,
                        4.5, 33.0, 60.0, 120.0, "Bronze to silver with red tail; spot near tail", "extra-large",
                        "carnivore", Arrays.asList("crabs", "shrimp", "small fish", "mollusks"),
                        "solitary", "Coastal and estuarine dweller",
                        "Spring to summer", "Shallow coastal waters", 60,
                        "LC", false, null,
                        "Also called redfish, it is one of the most popular saltwater game fish in the southeastern United States."),

                // ===== CARANGIDAE (Jacks and Pompano) =====

                new FishSpeciesDto(null, "Atlantic Pompano", "Atlanterhavspompano", "Trachinotus carolinus", "carolinus", "Carangidae", "Trachinotus",
                        Arrays.asList("Western Atlantic"),
                        Arrays.asList("United States", "Mexico", "Brazil"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea", "estuary"),
                        Arrays.asList("ocean", "sea", "estuary"),
                        0, 30, 20.0, 30.0,
                        1.4, 6.8, 35.0, 70.0, "Golden-bronze with silvery belly; compressed body", "medium",
                        "carnivore", Arrays.asList("crustaceans", "small fish", "mollusks"),
                        "schooling", "Coastal pelagic",
                        "Spring to summer", "Shallow sandy bottoms", 8,
                        "LC", false, null,
                        "A highly prized saltwater game fish known for its acrobatic fights and excellent table quality."),

                // ===== SERRANIDAE (Groupers and Sea Bass) =====

                new FishSpeciesDto(null, "European Sea Bass", "Sibbe", "Dicentrarchus labrax", "labrax", "Moronidae", "Dicentrarchus",
                        Arrays.asList("Eastern Atlantic", "Mediterranean", "Black Sea"),
                        Arrays.asList("Spain", "France", "Italy", "Greece", "Portugal", "Morocco"),
                        "Europe", "brackish",
                        Arrays.asList("ocean", "sea", "estuary"),
                        Arrays.asList("ocean", "sea", "estuary"),
                        0, 50, 12.0, 26.0,
                        2.7, 12.0, 50.0, 100.0, "Silver with dark back; two dorsal fins", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans"),
                        "schooling", "Enters estuaries to feed",
                        "Winter", "Coastal waters", 10,
                        "LC", false, null,
                        "Also called sea bass or loup de mer, it is a popular game and food fish in Mediterranean and Atlantic waters."),

                // ===== LABRIDAE (Wrasse Family) =====

                new FishSpeciesDto(null, "Common Wrasse", "Kobbe", "Labrus bergylta", "bergylta", "Labridae", "Labrus",
                        Arrays.asList("North Atlantic", "Norway", "Scotland"),
                        Arrays.asList("Norway", "United Kingdom", "Iceland", "Faroe Islands", "Canada"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 100, 2.0, 14.0,
                        1.4, 5.4, 35.0, 65.0, "Greenish-brown with mottled pattern", "medium",
                        "carnivore", Arrays.asList("crustaceans", "mollusks", "small fish"),
                        "solitary", "Rocky reef dweller",
                        "Spring", "Rocky coastal areas", 10,
                        "LC", false, null,
                        "A common reef fish in the North Atlantic, known for its colorful appearance and territorial behavior."),

                // ===== CYPINIDAE (Cyprinid Family) =====

                new FishSpeciesDto(null, "Common Carp", "Karp", "Cyprinus carpio", "carpio", "Cyprinidae", "Cyprinus",
                        Arrays.asList("Europe", "Asia"),
                        Arrays.asList("Germany", "France", "Russia", "China", "Poland", "Hungary"),
                        "Europe", "freshwater",
                        Arrays.asList("lake", "pond", "river", "reservoir"),
                        Arrays.asList("lake", "pond", "river", "reservoir"),
                        0, 10, 2.0, 28.0,
                        4.5, 45.0, 50.0, 120.0, "Golden-brown with large scales; two barbels", "extra-large",
                        "omnivore", Arrays.asList("plants", "insects", "crustaceans", "mollusks", "worms"),
                        "schooling", "Bottom feeder",
                        "Spring", "Shallow vegetated areas", 40,
                        "LC", false, null,
                        "One of the most widely distributed fish species in the world, important for both aquaculture and carp fishing."),

                new FishSpeciesDto(null, "Silver Carp", "Sølvkarp", "Hypophthalmichthys molitrix", "molitrix", "Cyprinidae", "Hypophthalmichthys",
                        Arrays.asList("Asia", "Siberia"),
                        Arrays.asList("China", "Russia", "Kazakhstan"),
                        "Asia", "freshwater",
                        Arrays.asList("river", "lake", "reservoir"),
                        Arrays.asList("river", "lake", "reservoir"),
                        0, 20, 10.0, 30.0,
                        18.0, 45.0, 100.0, 150.0, "Silver-gray with large scales", "extra-large",
                        "planktivore", Arrays.asList("phytoplankton", "zooplankton"),
                        "schooling", "Surface feeder",
                        "Spring to summer", "Shallow waters", 30,
                        "LC", false, null,
                        "A large filter-feeding carp native to Asia, widely farmed and introduced to many regions."),

                new FishSpeciesDto(null, "Common Roach", "Røye", "Rutilus rutilus", "rutilus", "Cyprinidae", "Rutilus",
                        Arrays.asList("Europe", "Northern Asia"),
                        Arrays.asList("United Kingdom", "Germany", "France", "Russia", "Poland", "Sweden"),
                        "Europe", "freshwater",
                        Arrays.asList("lake", "pond", "river", "stream"),
                        Arrays.asList("lake", "pond", "river", "stream"),
                        0, 10, 4.0, 24.0,
                        0.3, 2.0, 20.0, 45.0, "Golden-bronze with silvery belly", "small",
                        "omnivore", Arrays.asList("insects", "plant matter", "small crustaceans"),
                        "schooling", "Forms large schools",
                        "Spring", "Vegetated shallow areas", 12,
                        "LC", false, null,
                        "A common European freshwater fish, popular among coarse fish anglers."),

                new FishSpeciesDto(null, "European Chub", "Braxen", "Squalius cephalus", "cephalus", "Cyprinidae", "Squalius",
                        Arrays.asList("Western and Central Europe"),
                        Arrays.asList("United Kingdom", "France", "Germany", "Spain", "Italy"),
                        "Europe", "freshwater",
                        Arrays.asList("river", "stream"),
                        Arrays.asList("river", "stream"),
                        0, 10, 8.0, 24.0,
                        0.7, 3.6, 25.0, 55.0, "Olive-brown with silvery sides; deep body", "medium",
                        "omnivore", Arrays.asList("insects", "plant matter", "small crustaceans"),
                        "schooling", "Prefers slow-moving rivers",
                        "Spring", "Gravel areas in rivers", 10,
                        "LC", false, null,
                        "A sturdy river fish popular in European coarse fishing, known for its acrobatic fights."),

                // ===== SILURIDAE (Catfish Family) =====

                new FishSpeciesDto(null, "Wels Catfish", "Ålreke", "Silurus glanis", "glanis", "Siluridae", "Silurus",
                        Arrays.asList("Central and Eastern Europe"),
                        Arrays.asList("Germany", "Poland", "Romania", "Hungary", "Russia", "Ukraine"),
                        "Europe", "freshwater",
                        Arrays.asList("river", "lake", "pond"),
                        Arrays.asList("river", "lake", "pond"),
                        0, 20, 5.0, 24.0,
                        30.0, 130.0, 120.0, 300.0, "Dark olive to black; whisker-like barbels", "extra-large",
                        "carnivore", Arrays.asList("fish", "frogs", "waterfowl", "small mammals"),
                        "solitary", "Nocturnal predator",
                        "Spring", "Shallow vegetated areas", 45,
                        "NT", false, null,
                        "The largest freshwater fish in Europe, capable of reaching over 3 meters in length."),

                // ===== ICTALURIDAE (North American Catfish) =====

                new FishSpeciesDto(null, "Channel Catfish", "Kanaalkattfisk", "Ictalurus punctatus", "punctatus", "Ictaluridae", "Ictalurus",
                        Arrays.asList("Central North America"),
                        Arrays.asList("United States", "Canada", "Mexico"),
                        "North America", "freshwater",
                        Arrays.asList("river", "lake", "pond", "reservoir"),
                        Arrays.asList("river", "lake", "pond", "reservoir"),
                        0, 15, 10.0, 32.0,
                        3.6, 31.0, 50.0, 110.0, "Gray to olive with dark spots; long barbels", "extra-large",
                        "omnivore", Arrays.asList("insects", "crustaceans", "small fish", "worms", "plant matter"),
                        "solitary", "Nocturnal bottom feeder",
                        "Spring to summer", "Shallow waters", 12,
                        "LC", false, null,
                        "The most widely farmed catfish in the United States and a popular sport fish."),

                new FishSpeciesDto(null, "Blue Catfish", "Blå kattfisk", "Ictalurus furcatus", "furcatus", "Ictaluridae", "Ictalurus",
                        Arrays.asList("Central North America"),
                        Arrays.asList("United States", "Mexico"),
                        "North America", "freshwater",
                        Arrays.asList("river", "reservoir"),
                        Arrays.asList("river", "reservoir"),
                        0, 30, 10.0, 32.0,
                        14.0, 68.0, 80.0, 150.0, "Blue-gray to black; forked tail", "extra-large",
                        "carnivore", Arrays.asList("fish", "crayfish", "crustaceans"),
                        "solitary", "Large river dweller",
                        "Spring", "Deep river channels", 20,
                        "LC", false, null,
                        "The largest North American catfish, found in major river systems."),

                // ===== ONCORYNCHUS (Pacific Salmon) =====

                new FishSpeciesDto(null, "Chinook Salmon", "Kongesørl", "Oncorhynchus tshawytscha", "tshawytscha", "Salmonidae", "Oncorhynchus",
                        Arrays.asList("Pacific Northwest", "Alaska", "Russia"),
                        Arrays.asList("United States", "Canada", "Russia", "Japan"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "river", "stream"),
                        Arrays.asList("ocean", "river", "stream"),
                        0, 200, 2.0, 14.0,
                        13.6, 61.0, 100.0, 150.0, "Silver in ocean; red body, green head at spawning", "extra-large",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "migratory", "Anadromous; returns to natal stream to spawn",
                        "Autumn", "Gravel river beds", 8,
                        "LC", false, null,
                        "The largest Pacific salmon species, also called king salmon, highly valued for sport and commercial fishing."),

                new FishSpeciesDto(null, "Sockeye Salmon", "Rødøye", "Oncorhynchus nerka", "nerka", "Salmonidae", "Oncorhynchus",
                        Arrays.asList("North Pacific", "Alaska", "British Columbia"),
                        Arrays.asList("United States", "Canada", "Russia", "Japan"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "lake", "river"),
                        Arrays.asList("ocean", "lake", "river"),
                        0, 200, 2.0, 12.0,
                        2.3, 6.8, 45.0, 75.0, "Silver in ocean; bright red at spawning", "medium",
                        "carnivore", Arrays.asList("zooplankton", "small fish", "krill"),
                        "migratory", "Anadromous; some landlocked populations (redfish)",
                        "Summer to autumn", "Lake and river shallows", 6,
                        "LC", false, null,
                        "Known for its bright red spawning color and excellent eating quality, it is a key commercial species."),

                new FishSpeciesDto(null, "Coho Salmon", "Sølvøye", "Oncorhynchus kisutch", "kisutch", "Salmonidae", "Oncorhynchus",
                        Arrays.asList("North Pacific"),
                        Arrays.asList("United States", "Canada", "Russia", "Japan"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "river", "stream"),
                        Arrays.asList("ocean", "river", "stream"),
                        0, 200, 2.0, 14.0,
                        3.6, 9.1, 55.0, 90.0, "Silver with black spots; bright red at spawning", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "squid"),
                        "migratory", "Anadromous",
                        "Autumn", "Gravel river beds", 5,
                        "LC", false, null,
                        "Also called silver salmon, it is a popular sport fish known for its fighting ability."),

                new FishSpeciesDto(null, "Pink Salmon", "Pinklaks", "Oncorhynchus gorbuscha", "gorbuscha", "Salmonidae", "Oncorhynchus",
                        Arrays.asList("North Pacific"),
                        Arrays.asList("United States", "Canada", "Russia", "Japan", "Alaska"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "river", "stream"),
                        Arrays.asList("ocean", "river", "stream"),
                        0, 100, 2.0, 12.0,
                        1.4, 3.6, 35.0, 55.0, "Silver with dark spots; deep red at spawning", "small",
                        "carnivore", Arrays.asList("zooplankton", "small fish", "krill"),
                        "migratory", "Anadromous; two-year life cycle",
                        "Summer", "Gravel river beds", 3,
                        "LC", false, null,
                        "The smallest and most abundant Pacific salmon, with a strict two-year life cycle."),

                new FishSpeciesDto(null, "Chum Salmon", "Chum laks", "Oncorhynchus keta", "keta", "Salmonidae", "Oncorhynchus",
                        Arrays.asList("North Pacific"),
                        Arrays.asList("United States", "Canada", "Russia", "Japan", "Alaska"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "river", "stream"),
                        Arrays.asList("ocean", "river", "stream"),
                        0, 200, 2.0, 12.0,
                        3.6, 9.1, 55.0, 85.0, "Olive to bronze; dark green at spawning", "medium",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "insects"),
                        "migratory", "Anadromous; enters rivers earliest",
                        "Autumn", "Gravel river beds", 5,
                        "LC", false, null,
                        "Also called dog salmon, it is the first Pacific salmon to enter rivers to spawn."),

                // ===== CORYPHAENIDAE (Dolphinfish) =====

                new FishSpeciesDto(null, "Mahi-Mahi", "Mahi-Mahi", "Coryphaena hippurus", "hippurus", "Coryphaenidae", "Coryphaena",
                        Arrays.asList("Tropical and subtropical oceans worldwide"),
                        Arrays.asList("United States", "Mexico", "Brazil", "Japan", "Australia", "Hawaii"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 200, 20.0, 30.0,
                        8.2, 20.0, 80.0, 130.0, "Golden-green with blue sides; elongated head", "large",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "schooling", "Pelagic; follows floating debris",
                        "Year-round in tropics", "Open ocean", 5,
                        "LC", false, null,
                        "Also called dolphinfish, it is a popular game fish known for its vibrant colors and fast swimming."),

                // ===== ISTIOPHORIDAE (Billfish) =====

                new FishSpeciesDto(null, "Blue Marlin", "Blå marlin", "Makaira nigricans", "nigricans", "Istiophoridae", "Makaira",
                        Arrays.asList("Tropical and subtropical oceans"),
                        Arrays.asList("United States", "Mexico", "Japan", "Hawaii", "Fiji"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 300, 18.0, 28.0,
                        180.0, 800.0, 200.0, 450.0, "Deep blue back; silver belly; long bill", "extra-large",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "solitary", "Pelagic apex predator",
                        "Year-round in tropics", "Open ocean", 9,
                        "VU", true, null,
                        "One of the largest and most sought-after billfish, a trophy of big game fishing."),

                new FishSpeciesDto(null, "Sailfish", "Seilfisk", "Istiophorus platypterus", "platypterus", "Istiophoridae", "Istiophorus",
                        Arrays.asList("Tropical and subtropical oceans"),
                        Arrays.asList("United States", "Mexico", "Brazil", "Fiji", "Hawaii"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 200, 20.0, 30.0,
                        36.0, 150.0, 150.0, 300.0, "Blue-green back; silver belly; large dorsal fin", "extra-large",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "solitary", "Fastest fish in the ocean",
                        "Year-round in tropics", "Open ocean", 6,
                        "LC", false, null,
                        "The fastest fish in the ocean, capable of speeds over 110 km/h, known for its sail-like dorsal fin."),

                // ===== CARANX (Jacks) =====

                new FishSpeciesDto(null, "Bluefin Tuna", "Blåfinne tunfisk", "Thunnus thynnus", "thynnus", "Scombridae", "Thunnus",
                        Arrays.asList("North Atlantic", "Mediterranean"),
                        Arrays.asList("United States", "Spain", "Italy", "Japan", "Iceland", "Norway"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 500, 5.0, 24.0,
                        136.0, 680.0, 200.0, 450.0, "Deep blue back; silver belly; yellow finlets", "extra-large",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "migratory", "Trans-oceanic migrant",
                        "Spring", "Coastal waters", 26,
                        "EN", true, null,
                        "The largest tuna species and one of the most valuable fish in the world, heavily fished for sushi."),

                new FishSpeciesDto(null, "Yellowfin Tuna", "Gulfinne tunfisk", "Thunnus albacares", "albacares", "Scombridae", "Thunnus",
                        Arrays.asList("Tropical and subtropical oceans"),
                        Arrays.asList("United States", "Japan", "Fiji", "Hawaii", "Mexico"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 250, 18.0, 28.0,
                        27.0, 180.0, 100.0, 200.0, "Blue back; bright yellow fins", "extra-large",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "schooling", "Pelagic migrant",
                        "Year-round in tropics", "Open ocean", 6,
                        "LC", false, null,
                        "A popular sport and commercial tuna species, known for its speed and bright yellow fins."),

                // ===== LUTJANIDAE (Snappers) =====

                new FishSpeciesDto(null, "Red Snapper", "Rød snapper", "Lutjanus campechanus", "campechanus", "Lutjanidae", "Lutjanus",
                        Arrays.asList("Western Atlantic", "Gulf of Mexico"),
                        Arrays.asList("United States", "Mexico"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        10, 100, 18.0, 28.0,
                        2.7, 14.0, 50.0, 90.0, "Red to pink; five bony ridges on gill cover", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "squid"),
                        "solitary", "Reef and structure dweller",
                        "Spring to summer", "Reef areas", 30,
                        "VU", false, null,
                        "A highly prized game and food fish of the Gulf of Mexico and Atlantic coast."),

                // ===== HAEMULIDAE (Grunt Family) =====

                new FishSpeciesDto(null, "Mangrove Jack", "Mangrovejack", "Lutjanus argentimaculatus", "argentimaculatus", "Lutjanidae", "Lutjanus",
                        Arrays.asList("Indo-Pacific"),
                        Arrays.asList("Australia", "Thailand", "Indonesia", "Philippines", "Fiji"),
                        "Asia", "brackish",
                        Arrays.asList("ocean", "estuary", "river", "coral reef"),
                        Arrays.asList("ocean", "estuary", "river", "coral reef"),
                        0, 50, 22.0, 30.0,
                        9.0, 36.0, 70.0, 120.0, "Bronze to silver with silvery spots", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans"),
                        "solitary", "Reef and mangrove dweller",
                        "Summer", "Reef areas", 20,
                        "LC", false, null,
                        "A powerful game fish found throughout the Indo-Pacific, known for its strength and fighting ability."),

                // ===== CARCHARHINIDAE (Reef Sharks) =====

                new FishSpeciesDto(null, "European Eel", "Europeisk ål", "Anguilla anguilla", "anguilla", "Anguillidae", "Anguilla",
                        Arrays.asList("Europe", "North Africa"),
                        Arrays.asList("United Kingdom", "France", "Germany", "Spain", "Italy", "Norway"),
                        "Europe", "catadromous",
                        Arrays.asList("river", "lake", "ocean", "estuary"),
                        Arrays.asList("river", "lake", "ocean", "estuary"),
                        0, 200, 4.0, 20.0,
                        1.4, 9.0, 60.0, 130.0, "Dark brown back; silver belly; elongated body", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "insects", "worms"),
                        "solitary", "Catadromous: spawns in Sargasso Sea, grows in freshwater",
                        "Autumn", "Sargasso Sea", 85,
                        "CR", true, null,
                        "A mysterious catadromous fish that migrates thousands of kilometers to spawn in the Sargasso Sea. Critically endangered."),

                // ===== ALBUcidAE (Tarpon) =====

                new FishSpeciesDto(null, "Tarpon", "Tarpon", "Megalops atlanticus", "atlanticus", "Megalopidae", "Megalops",
                        Arrays.asList("Western Atlantic", "Gulf of Mexico", "Caribbean"),
                        Arrays.asList("United States", "Mexico", "Brazil", "Cuba"),
                        "North America", "brackish",
                        Arrays.asList("ocean", "estuary", "river", "sea"),
                        Arrays.asList("ocean", "estuary", "river", "sea"),
                        0, 30, 22.0, 32.0,
                        36.0, 180.0, 150.0, 250.0, "Silver with large scales; upturned mouth", "extra-large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "squid"),
                        "solitary", "Coastal and estuarine; takes air at surface",
                        "Spring to summer", "Shallow coastal waters", 50,
                        "VU", true, null,
                        "Known as the silver king, it is one of the most sought-after game fish, famous for its acrobatic jumps."),

                // ===== CLUPEIDAE (Herring) =====

                new FishSpeciesDto(null, "Herring", "Sild", "Clupea harengus", "harengus", "Clupeidae", "Clupea",
                        Arrays.asList("North Atlantic"),
                        Arrays.asList("Norway", "United Kingdom", "Iceland", "Canada", "Denmark"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 200, 4.0, 14.0,
                        0.3, 0.5, 25.0, 40.0, "Blue-green back; silver sides and belly", "small",
                        "planktivore", Arrays.asList("phytoplankton", "zooplankton"),
                        "schooling", "Massive schools; migrates to spawn",
                        "Winter to spring", "Shallow coastal waters", 20,
                        "LC", false, null,
                        "One of the most abundant and economically important fish species in the North Atlantic."),

                // ===== CLUPEIDAE =====

                new FishSpeciesDto(null, "Mackerel", "Makrell", "Scomber scombrus", "scombrus", "Scombridae", "Scomber",
                        Arrays.asList("North Atlantic"),
                        Arrays.asList("Norway", "United Kingdom", "Iceland", "Canada", "United States"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 200, 6.0, 16.0,
                        0.7, 2.7, 30.0, 50.0, "Dark green back with wavy lines; silver belly", "medium",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "zooplankton"),
                        "schooling", "Pelagic migrant",
                        "Spring to summer", "Coastal waters", 12,
                        "LC", false, null,
                        "A fast-swimming pelagic fish, important for both commercial fishing and angling."),

                // ===== SCOMBRIDAE =====

                new FishSpeciesDto(null, "Atlantic Bonito", "Atlanterhavsbonito", "Sarda sarda", "sarda", "Scombridae", "Sarda",
                        Arrays.asList("North Atlantic", "Mediterranean"),
                        Arrays.asList("Spain", "Portugal", "Italy", "Greece", "Morocco"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        0, 200, 14.0, 24.0,
                        2.7, 7.3, 45.0, 75.0, "Blue back; silver with dark vertical bars", "medium",
                        "carnivore", Arrays.asList("small fish", "squid", "crustaceans"),
                        "schooling", "Pelagic",
                        "Spring to autumn", "Open ocean", 5,
                        "LC", false, null,
                        "A fast pelagic fish popular in Mediterranean and Atlantic sport fishing."),

                // ===== TOBUCIDAE (Bream) =====

                new FishSpeciesDto(null, "Common Bream", "Braxen", "Abramis brama", "brama", "Cyprinidae", "Abramis",
                        Arrays.asList("Europe", "Northern Asia"),
                        Arrays.asList("United Kingdom", "Germany", "France", "Russia", "Poland"),
                        "Europe", "freshwater",
                        Arrays.asList("lake", "pond", "river", "reservoir"),
                        Arrays.asList("lake", "pond", "river", "reservoir"),
                        0, 10, 4.0, 24.0,
                        2.0, 12.0, 40.0, 80.0, "Golden-bronze with large scales; deep body", "large",
                        "omnivore", Arrays.asList("insects", "plant matter", "small crustaceans", "worms"),
                        "schooling", "Bottom feeder in weedy areas",
                        "Spring", "Vegetated shallow areas", 15,
                        "LC", false, null,
                        "A popular coarse fish in Europe, known for its cautious nature and fighting ability."),

                // ===== ACIPENSERIDAE (Sturgeon) =====

                new FishSpeciesDto(null, "Atlantic Sturgeon", "Atlanterhavssjøørre", "Acipenser oxyrinchus", "oxyrinchus", "Acipenseridae", "Acipenser",
                        Arrays.asList("Eastern North America"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "anadromous",
                        Arrays.asList("ocean", "river", "estuary"),
                        Arrays.asList("ocean", "river", "estuary"),
                        0, 100, 4.0, 20.0,
                        45.0, 180.0, 150.0, 300.0, "Gray to olive; five rows of bony plates", "extra-large",
                        "carnivore", Arrays.asList("crustaceans", "mollusks", "worms", "small fish"),
                        "solitary", "Ancient species; catadromous migration",
                        "Spring", "Gravel river beds", 55,
                        "VU", true, null,
                        "An ancient fish species that has existed for over 150 million years, now vulnerable due to habitat loss."),

                // ===== SYNGNATHIDAE (Seahorse) =====

                new FishSpeciesDto(null, "Flatfish - Sole", "Sole", "Solea solea", "solea", "Soleidae", "Solea",
                        Arrays.asList("North Atlantic", "Mediterranean"),
                        Arrays.asList("United Kingdom", "France", "Spain", "Portugal", "Netherlands"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        10, 100, 8.0, 18.0,
                        0.5, 2.0, 30.0, 50.0, "Brown with mottled pattern; both eyes on right side", "medium",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "worms"),
                        "solitary", "Bottom dweller; buries in sand",
                        "Autumn to winter", "Sandy bottoms", 10,
                        "LC", false, null,
                        "A popular flatfish and food fish, characterized by both eyes on one side of its flattened body."),

                // ===== SCIAENIDAE =====

                new FishSpeciesDto(null, "Weakfish", "Svakfisk", "Cynoscion regalis", "regalis", "Sciaenidae", "Cynoscion",
                        Arrays.asList("Western Atlantic"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "brackish",
                        Arrays.asList("ocean", "estuary", "river"),
                        Arrays.asList("ocean", "estuary", "river"),
                        0, 50, 15.0, 30.0,
                        2.7, 11.3, 50.0, 100.0, "Silver with dark lateral bar", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans"),
                        "solitary", "Coastal and estuarine",
                        "Spring to summer", "Shallow coastal waters", 8,
                        "LC", false, null,
                        "A popular game fish on the Atlantic coast, known for its delicate mouth and fighting ability."),

                // ===== CENTROPOMIDAE =====

                new FishSpeciesDto(null, "Snook", "Snook", "Centropomus undecimalis", "undecimalis", "Centropomidae", "Centropomus",
                        Arrays.asList("Western Atlantic", "Caribbean"),
                        Arrays.asList("United States", "Mexico", "Brazil", "Cuba"),
                        "North America", "brackish",
                        Arrays.asList("estuary", "river", "ocean", "mangrove"),
                        Arrays.asList("estuary", "river", "ocean"),
                        0, 30, 22.0, 32.0,
                        4.5, 27.0, 60.0, 120.0, "Silver with dark lateral line; yellow fins", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "squid"),
                        "solitary", "Mangrove and structure dweller",
                        "Spring to summer", "Mangrove areas", 12,
                        "LC", false, null,
                        "A premier game fish of the southeastern United States and Caribbean, known for its power and beauty."),

                // ===== LOBOSTICHTHYIDAE (Pompano) =====

                new FishSpeciesDto(null, "Flounder", "Sjøabbor", "Paralichthys dentatus", "dentatus", "Paralichthyidae", "Paralichthys",
                        Arrays.asList("Western Atlantic"),
                        Arrays.asList("United States", "Canada"),
                        "North America", "brackish",
                        Arrays.asList("ocean", "estuary", "sea"),
                        Arrays.asList("ocean", "estuary", "sea"),
                        0, 60, 10.0, 24.0,
                        1.4, 7.3, 40.0, 75.0, "Brown with mottled pattern; both eyes on right side", "medium",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "worms"),
                        "solitary", "Bottom dweller; buries in sand",
                        "Autumn to winter", "Sandy bottoms", 8,
                        "LC", false, null,
                        "A popular flatfish of the Atlantic coast, also called summer flounder or fluke."),

                // ===== GADIDAE =====

                new FishSpeciesDto(null, "Hake", "Hyse", "Merluccius merluccius", "merluccius", "Gadidae", "Merluccius",
                        Arrays.asList("North Atlantic", "Mediterranean"),
                        Arrays.asList("Spain", "Portugal", "France", "United Kingdom", "Morocco"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        50, 1000, 4.0, 16.0,
                        1.8, 7.3, 45.0, 80.0, "Silver-gray; pointed snout; two barbels", "medium",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "worms"),
                        "schooling", "Demersal; deep-water dweller",
                        "Autumn to winter", "Sandy bottoms", 12,
                        "LC", false, null,
                        "An important commercial fish of the North Atlantic and Mediterranean, found on sandy bottoms."),

                // ===== MUGILIDAE (Mullet) =====

                new FishSpeciesDto(null, "Grey Mullet", "Grå mulle", "Mugil cephalus", "cephalus", "Mugilidae", "Mugil",
                        Arrays.asList("Worldwide", "tropical and temperate waters"),
                        Arrays.asList("Australia", "Japan", "Spain", "United States", "South Africa"),
                        "Europe", "brackish",
                        Arrays.asList("estuary", "river", "ocean", "sea"),
                        Arrays.asList("estuary", "river", "ocean", "sea"),
                        0, 30, 14.0, 30.0,
                        1.4, 5.4, 35.0, 65.0, "Silver-gray; two rows of teeth on tongue", "medium",
                        "omnivore", Arrays.asList("algae", "plant matter", "small invertebrates"),
                        "schooling", "Coastal and estuarine",
                        "Spring to summer", "Shallow coastal waters", 10,
                        "LC", false, null,
                        "A widespread coastal fish found in tropical and temperate waters worldwide."),

                // ===== CALLONIMATIDAE (Sea Perch) =====

                new FishSpeciesDto(null, "Turbot", "Torskefjell", "Scophthalmus maximus", "maximus", "Scophthalmidae", "Scophthalmus",
                        Arrays.asList("North Atlantic", "Mediterranean", "Black Sea"),
                        Arrays.asList("United Kingdom", "France", "Spain", "Netherlands", "Denmark"),
                        "Europe", "saltwater",
                        Arrays.asList("ocean", "sea"),
                        Arrays.asList("ocean", "sea"),
                        10, 100, 6.0, 18.0,
                        4.5, 20.0, 60.0, 110.0, "Brown with mottled pattern; both eyes on right side", "large",
                        "carnivore", Arrays.asList("small fish", "crustaceans", "mollusks"),
                        "solitary", "Bottom dweller on sandy/muddy bottoms",
                        "Autumn to winter", "Sandy bottoms", 20,
                        "LC", false, null,
                        "The king of flatfish, highly prized as a food fish and popular in European aquaculture."),

                // ===== SCIAENIDAE =====

                new FishSpeciesDto(null, "Spot", "Plettromme", "Leiostomus xanthurus", "xanthurus", "Sciaenidae", "Leiostomus",
                        Arrays.asList("Western Atlantic"),
                        Arrays.asList("United States"),
                        "North America", "brackish",
                        Arrays.asList("ocean", "estuary", "sea"),
                        Arrays.asList("ocean", "estuary", "sea"),
                        0, 50, 15.0, 30.0,
                        0.3, 1.4, 20.0, 35.0, "Silver with a single black spot behind gill", "small",
                        "carnivore", Arrays.asList("crustaceans", "small fish", "worms"),
                        "schooling", "Coastal and estuarine",
                        "Spring to summer", "Sandy bottoms", 5,
                        "LC", false, null,
                        "A small drum fish found along the Atlantic coast, named for the distinctive black spot behind its gill."),

                // ===== CARANGIDAE =====

                new FishSpeciesDto(null, "Permit", "Permit", "Trachinotus falcatus", "falcatus", "Carangidae", "Trachinotus",
                        Arrays.asList("Tropical Atlantic", "Caribbean"),
                        Arrays.asList("United States", "Bahamas", "Cuba", "Mexico"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea", "reef"),
                        Arrays.asList("ocean", "sea"),
                        0, 50, 24.0, 30.0,
                        9.0, 27.0, 70.0, 110.0, "Silver with golden tint; compressed body", "large",
                        "carnivore", Arrays.asList("crabs", "mollusks", "small fish"),
                        "solitary", "Reef and sandbar dweller",
                        "Year-round in tropics", "Reef areas", 10,
                        "LC", false, null,
                        "One of the most challenging and sought-after game fish in the Caribbean, known for its wariness and power."),

                // ===== HAEMULIDAE =====

                new FishSpeciesDto(null, "Grunts", "Brumpefisk", "Haemulon plumierii", "plumierii", "Haemulidae", "Haemulon",
                        Arrays.asList("Western Atlantic", "Caribbean"),
                        Arrays.asList("United States", "Mexico", "Brazil", "Cuba"),
                        "North America", "saltwater",
                        Arrays.asList("ocean", "sea", "coral reef"),
                        Arrays.asList("ocean", "sea", "coral reef"),
                        0, 30, 22.0, 30.0,
                        0.5, 1.4, 25.0, 40.0, "Silver with blue-green tint; yellow fins", "small",
                        "carnivore", Arrays.asList("small fish", "crustaceans"),
                        "schooling", "Reef dweller",
                        "Year-round in tropics", "Reef areas", 5,
                        "LC", false, null,
                        "A common reef fish in the Caribbean, known for the grunting sound it makes when removed from water.")
        );
    }
}
