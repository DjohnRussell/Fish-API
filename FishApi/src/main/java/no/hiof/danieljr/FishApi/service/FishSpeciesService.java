package no.hiof.danieljr.FishApi.service;


import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;
import no.hiof.danieljr.FishApi.dto.FishSpeciesDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FishSpeciesService {

    private static final String COLLECTION = "fishSpecies";

    public String addSpecies(FishSpeciesDto speciesDto) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        String docId = speciesDto.getId() != null ? speciesDto.getId() : generateDocId(speciesDto.getScientificName());
        DocumentReference docRef = db.collection(COLLECTION).document(docId);

        Map<String, Object> data = new HashMap<>();
        data.put("commonName", speciesDto.getCommonName());
        data.put("scientificName", speciesDto.getScientificName());
        data.put("species", speciesDto.getSpecies());
        data.put("family", speciesDto.getFamily());
        data.put("genus", speciesDto.getGenus());
        data.put("nativeRegions", speciesDto.getNativeRegions());
        data.put("nativeCountries", speciesDto.getNativeCountries());
        data.put("continent", speciesDto.getContinent());
        data.put("waterType", speciesDto.getWaterType());
        data.put("habitats", speciesDto.getHabitats());
        data.put("waterBodyTypes", speciesDto.getWaterBodyTypes());
        data.put("minDepth", speciesDto.getMinDepth());
        data.put("maxDepth", speciesDto.getMaxDepth());
        data.put("minTemperature", speciesDto.getMinTemperature());
        data.put("maxTemperature", speciesDto.getMaxTemperature());
        data.put("avgWeight", speciesDto.getAvgWeight());
        data.put("maxWeight", speciesDto.getMaxWeight());
        data.put("avgLength", speciesDto.getAvgLength());
        data.put("maxLength", speciesDto.getMaxLength());
        data.put("color", speciesDto.getColor());
        data.put("size", speciesDto.getSize());
        data.put("diet", speciesDto.getDiet());
        data.put("prey", speciesDto.getPrey());
        data.put("behavior", speciesDto.getBehavior());
        data.put("migrationPattern", speciesDto.getMigrationPattern());
        data.put("spawningSeason", speciesDto.getSpawningSeason());
        data.put("spawningHabitat", speciesDto.getSpawningHabitat());
        data.put("lifespan", speciesDto.getLifespan());
        data.put("conservationStatus", speciesDto.getConservationStatus());
        data.put("protectedSpecies", speciesDto.getProtectedSpecies());
        data.put("notes", speciesDto.getNotes());
        data.put("description", speciesDto.getDescription());

        docRef.set(data).get();
        return docId;
    }

    public void addSpeciesBatch(List<FishSpeciesDto> speciesList) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        WriteBatch batch = db.batch();

        for (FishSpeciesDto speciesDto : speciesList) {
            String docId = speciesDto.getId() != null ? speciesDto.getId() : generateDocId(speciesDto.getScientificName());
            DocumentReference docRef = db.collection(COLLECTION).document(docId);

            Map<String, Object> data = new HashMap<>();
            data.put("commonName", speciesDto.getCommonName());
            data.put("scientificName", speciesDto.getScientificName());
            data.put("species", speciesDto.getSpecies());
            data.put("family", speciesDto.getFamily());
            data.put("genus", speciesDto.getGenus());
            data.put("nativeRegions", speciesDto.getNativeRegions());
            data.put("nativeCountries", speciesDto.getNativeCountries());
            data.put("continent", speciesDto.getContinent());
            data.put("waterType", speciesDto.getWaterType());
            data.put("habitats", speciesDto.getHabitats());
            data.put("waterBodyTypes", speciesDto.getWaterBodyTypes());
            data.put("minDepth", speciesDto.getMinDepth());
            data.put("maxDepth", speciesDto.getMaxDepth());
            data.put("minTemperature", speciesDto.getMinTemperature());
            data.put("maxTemperature", speciesDto.getMaxTemperature());
            data.put("avgWeight", speciesDto.getAvgWeight());
            data.put("maxWeight", speciesDto.getMaxWeight());
            data.put("avgLength", speciesDto.getAvgLength());
            data.put("maxLength", speciesDto.getMaxLength());
            data.put("color", speciesDto.getColor());
            data.put("size", speciesDto.getSize());
            data.put("diet", speciesDto.getDiet());
            data.put("prey", speciesDto.getPrey());
            data.put("behavior", speciesDto.getBehavior());
            data.put("migrationPattern", speciesDto.getMigrationPattern());
            data.put("spawningSeason", speciesDto.getSpawningSeason());
            data.put("spawningHabitat", speciesDto.getSpawningHabitat());
            data.put("lifespan", speciesDto.getLifespan());
            data.put("conservationStatus", speciesDto.getConservationStatus());
            data.put("protectedSpecies", speciesDto.getProtectedSpecies());
            data.put("notes", speciesDto.getNotes());
            data.put("description", speciesDto.getDescription());

            batch.set(docRef, data);
        }

        batch.commit().get();
    }

    public List<FishSpeciesDto> getAllSpecies() throws Exception {
        return getSpecies(null, null, null, null, null, null, null);
    }

    public List<FishSpeciesDto> getSpecies(String waterType, String family, String habitat,
                                            String diet, String conservationStatus, String continent, String size) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        List<FishSpeciesDto> speciesList = new ArrayList<>();

        Query query = db.collection(COLLECTION);

        List<String> filterFields = new ArrayList<>();
        Map<String, String> filterValues = new LinkedHashMap<>();

        if (waterType != null && !waterType.isEmpty()) {
            filterFields.add("waterType");
            filterValues.put("waterType", waterType.toLowerCase());
        }
        if (family != null && !family.isEmpty()) {
            filterFields.add("family");
            filterValues.put("family", family.toLowerCase());
        }
        if (habitat != null && !habitat.isEmpty()) {
            filterFields.add("habitat");
            filterValues.put("habitat", habitat.toLowerCase());
        }
        if (diet != null && !diet.isEmpty()) {
            filterFields.add("diet");
            filterValues.put("diet", diet.toLowerCase());
        }
        if (conservationStatus != null && !conservationStatus.isEmpty()) {
            filterFields.add("conservationStatus");
            filterValues.put("conservationStatus", conservationStatus.toLowerCase());
        }
        if (continent != null && !continent.isEmpty()) {
            filterFields.add("continent");
            filterValues.put("continent", continent.toLowerCase());
        }
        if (size != null && !size.isEmpty()) {
            filterFields.add("size");
            filterValues.put("size", size.toLowerCase());
        }

        if (filterFields.isEmpty()) {
            for (QueryDocumentSnapshot doc : query.get().get().getDocuments()) {
                speciesList.add(extractSpeciesDto(doc));
            }
            return speciesList;
        }

        if (filterFields.size() == 1) {
            String field = filterFields.get(0);
            String value = filterValues.get(field);
            Query filteredQuery = query.whereEqualTo(field, value);
            for (QueryDocumentSnapshot doc : filteredQuery.get().get().getDocuments()) {
                speciesList.add(extractSpeciesDto(doc));
            }
            return speciesList;
        }

        String firstField = filterFields.get(0);
        String firstValue = filterValues.get(firstField);
        filterFields.remove(0);
        filterValues.remove(firstField);

        Query filteredQuery = query.whereEqualTo(firstField, firstValue);
        List<FishSpeciesDto> queryResults = new ArrayList<>();
        for (QueryDocumentSnapshot doc : filteredQuery.get().get().getDocuments()) {
            queryResults.add(extractSpeciesDto(doc));
        }

        for (FishSpeciesDto species : queryResults) {
            boolean match = true;
            for (String field : filterFields) {
                String expectedValue = filterValues.get(field);
                String actualValue = getFieldValue(species, field);
                if (!expectedValue.equalsIgnoreCase(actualValue != null ? actualValue : "")) {
                    match = false;
                    break;
                }
            }
            if (match) {
                speciesList.add(species);
            }
        }

        return speciesList;
    }

    private String getFieldValue(FishSpeciesDto species, String field) {
        switch (field) {
            case "waterType": return species.getWaterType();
            case "family": return species.getFamily();
            case "habitat": return species.getHabitats() != null ? String.join(",", species.getHabitats()) : "";
            case "diet": return species.getDiet();
            case "conservationStatus": return species.getConservationStatus();
            case "continent": return species.getContinent();
            case "size": return species.getSize();
            default: return null;
        }
    }

    public FishSpeciesDto getSpeciesById(String speciesId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot doc = db.collection(COLLECTION).document(speciesId).get().get();

        if (doc.exists()) {
            return extractSpeciesDto((QueryDocumentSnapshot) doc);
        }
        return null;
    }

    public FishSpeciesDto getSpeciesByScientificName(String scientificName) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COLLECTION).whereEqualTo("scientificName", scientificName);
        List<QueryDocumentSnapshot> docs = query.get().get().getDocuments();

        if (!docs.isEmpty()) {
            return extractSpeciesDto(docs.get(0));
        }
        return null;
    }

    public void updateSpecies(String speciesId, FishSpeciesDto speciesDto) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION).document(speciesId);

        Map<String, Object> data = new HashMap<>();
        if (speciesDto.getCommonName() != null) data.put("commonName", speciesDto.getCommonName());
        if (speciesDto.getScientificName() != null) data.put("scientificName", speciesDto.getScientificName());
        if (speciesDto.getSpecies() != null) data.put("species", speciesDto.getSpecies());
        if (speciesDto.getFamily() != null) data.put("family", speciesDto.getFamily());
        if (speciesDto.getGenus() != null) data.put("genus", speciesDto.getGenus());
        if (speciesDto.getNativeRegions() != null) data.put("nativeRegions", speciesDto.getNativeRegions());
        if (speciesDto.getNativeCountries() != null) data.put("nativeCountries", speciesDto.getNativeCountries());
        if (speciesDto.getContinent() != null) data.put("continent", speciesDto.getContinent());
        if (speciesDto.getWaterType() != null) data.put("waterType", speciesDto.getWaterType());
        if (speciesDto.getHabitats() != null) data.put("habitats", speciesDto.getHabitats());
        if (speciesDto.getWaterBodyTypes() != null) data.put("waterBodyTypes", speciesDto.getWaterBodyTypes());
        if (speciesDto.getMinDepth() != null) data.put("minDepth", speciesDto.getMinDepth());
        if (speciesDto.getMaxDepth() != null) data.put("maxDepth", speciesDto.getMaxDepth());
        if (speciesDto.getMinTemperature() != null) data.put("minTemperature", speciesDto.getMinTemperature());
        if (speciesDto.getMaxTemperature() != null) data.put("maxTemperature", speciesDto.getMaxTemperature());
        if (speciesDto.getAvgWeight() != null) data.put("avgWeight", speciesDto.getAvgWeight());
        if (speciesDto.getMaxWeight() != null) data.put("maxWeight", speciesDto.getMaxWeight());
        if (speciesDto.getAvgLength() != null) data.put("avgLength", speciesDto.getAvgLength());
        if (speciesDto.getMaxLength() != null) data.put("maxLength", speciesDto.getMaxLength());
        if (speciesDto.getColor() != null) data.put("color", speciesDto.getColor());
        if (speciesDto.getSize() != null) data.put("size", speciesDto.getSize());
        if (speciesDto.getDiet() != null) data.put("diet", speciesDto.getDiet());
        if (speciesDto.getPrey() != null) data.put("prey", speciesDto.getPrey());
        if (speciesDto.getBehavior() != null) data.put("behavior", speciesDto.getBehavior());
        if (speciesDto.getMigrationPattern() != null) data.put("migrationPattern", speciesDto.getMigrationPattern());
        if (speciesDto.getSpawningSeason() != null) data.put("spawningSeason", speciesDto.getSpawningSeason());
        if (speciesDto.getSpawningHabitat() != null) data.put("spawningHabitat", speciesDto.getSpawningHabitat());
        if (speciesDto.getLifespan() != null) data.put("lifespan", speciesDto.getLifespan());
        if (speciesDto.getConservationStatus() != null) data.put("conservationStatus", speciesDto.getConservationStatus());
        if (speciesDto.getProtectedSpecies() != null) data.put("protectedSpecies", speciesDto.getProtectedSpecies());
        if (speciesDto.getNotes() != null) data.put("notes", speciesDto.getNotes());
        if (speciesDto.getDescription() != null) data.put("description", speciesDto.getDescription());

        docRef.update(data).get();
    }

    public void deleteSpecies(String speciesId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION).document(speciesId).delete().get();
    }

    public boolean speciesExists(String speciesId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot doc = db.collection(COLLECTION).document(speciesId).get().get();
        return doc.exists();
    }

    private String generateDocId(String scientificName) {
        if (scientificName == null) return UUID.randomUUID().toString();
        return scientificName.replaceAll("[\\s()]", "").toLowerCase();
    }

    private FishSpeciesDto extractSpeciesDto(QueryDocumentSnapshot doc) {
        FishSpeciesDto dto = new FishSpeciesDto();
        dto.setId(doc.getId());
        dto.setCommonName(doc.getString("commonName"));
        dto.setScientificName(doc.getString("scientificName"));
        dto.setSpecies(doc.getString("species"));
        dto.setFamily(doc.getString("family"));
        dto.setGenus(doc.getString("genus"));
        dto.setNativeRegions((List<String>) doc.get("nativeRegions"));
        dto.setNativeCountries((List<String>) doc.get("nativeCountries"));
        dto.setContinent(doc.getString("continent"));
        dto.setWaterType(doc.getString("waterType"));
        dto.setHabitats((List<String>) doc.get("habitats"));
        dto.setWaterBodyTypes((List<String>) doc.get("waterBodyTypes"));
        dto.setMinDepth(doc.contains("minDepth") ? ((Number) doc.getData().get("minDepth")).intValue() : null);
        dto.setMaxDepth(doc.contains("maxDepth") ? ((Number) doc.getData().get("maxDepth")).intValue() : null);
        dto.setMinTemperature(doc.getDouble("minTemperature"));
        dto.setMaxTemperature(doc.getDouble("maxTemperature"));
        dto.setAvgWeight(doc.getDouble("avgWeight"));
        dto.setMaxWeight(doc.getDouble("maxWeight"));
        dto.setAvgLength(doc.getDouble("avgLength"));
        dto.setMaxLength(doc.getDouble("maxLength"));
        dto.setColor(doc.getString("color"));
        dto.setSize(doc.getString("size"));
        dto.setDiet(doc.getString("diet"));
        dto.setPrey((List<String>) doc.get("prey"));
        dto.setBehavior(doc.getString("behavior"));
        dto.setMigrationPattern(doc.getString("migrationPattern"));
        dto.setSpawningSeason(doc.getString("spawningSeason"));
        dto.setSpawningHabitat(doc.getString("spawningHabitat"));
        dto.setLifespan(doc.contains("lifespan") ? ((Number) doc.getData().get("lifespan")).intValue() : null);
        dto.setConservationStatus(doc.getString("conservationStatus"));
        dto.setProtectedSpecies(doc.getBoolean("protectedSpecies"));
        dto.setNotes(doc.getString("notes"));
        dto.setDescription(doc.getString("description"));
        return dto;
    }
}
