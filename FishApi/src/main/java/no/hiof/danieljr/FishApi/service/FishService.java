package no.hiof.danieljr.FishApi.service;


import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
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
                .document(fishDto.getId() != null ? fishDto.getId() : UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("name", fishDto.getName());
        data.put("scientificName", fishDto.getScientificName());
        data.put("species", fishDto.getSpecies());
        data.put("family", fishDto.getFamily());
        data.put("genus", fishDto.getGenus());
        data.put("country", fishDto.getCountry());
        data.put("region", fishDto.getRegion());
        data.put("city", fishDto.getCity());
        data.put("locationName", fishDto.getLocationName());
        data.put("latitude", fishDto.getLatitude());
        data.put("longitude", fishDto.getLongitude());
        data.put("waterType", fishDto.getWaterType());
        data.put("habitat", fishDto.getHabitat());
        data.put("waterBodyType", fishDto.getWaterBodyType());
        data.put("waterDepth", fishDto.getWaterDepth());
        data.put("waterTemperature", fishDto.getWaterTemperature());
        data.put("waterClarity", fishDto.getWaterClarity());
        data.put("weight", fishDto.getWeight());
        data.put("length", fishDto.getLength());
        data.put("color", fishDto.getColor());
        data.put("size", fishDto.getSize());
        data.put("diet", fishDto.getDiet());
        data.put("behavior", fishDto.getBehavior());
        data.put("migrationPattern", fishDto.getMigrationPattern());
        data.put("spawningSeason", fishDto.getSpawningSeason());
        data.put("minDepth", fishDto.getMinDepth());
        data.put("maxDepth", fishDto.getMaxDepth());
        data.put("conservationStatus", fishDto.getConservationStatus());
        data.put("catchLimit", fishDto.getCatchLimit());
        data.put("minSizeLimit", fishDto.getMinSizeLimit());
        data.put("protectedSpecies", fishDto.getProtectedSpecies());
        data.put("baitUsed", fishDto.getBaitUsed());
        data.put("fishingMethod", fishDto.getFishingMethod());
        data.put("gearType", fishDto.getGearType());
        data.put("photoUrls", fishDto.getPhotoUrls());
        data.put("notes", fishDto.getNotes());
        data.put("description", fishDto.getDescription());
        data.put("addedAt", fishDto.getAddedAt() != null ? fishDto.getAddedAt() : new Date());
        data.put("caughtDate", fishDto.getCaughtDate());

        docRef.set(data).get();
        return docRef.getId();
    }

    public List<FishDto> getFishes(String userId) throws Exception {
        return getFishes(userId, null, null, null, null, null, null, null, null, null, null, null);
    }

    public List<FishDto> getFishes(String userId, String country, String region, String city,
                                    String waterType, String habitat, String species, String family,
                                    String waterBodyType, String size, String diet, String conservationStatus) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        List<FishDto> fishes = new ArrayList<>();

        // Build a Firestore query with equality filters for provided parameters
        Query query = db.collection("users")
                .document(userId)
                .collection("fishes");

        // Firestore supports compound queries with multiple equality filters
        // We need to track which fields are being queried to handle composite index requirements
        List<String> filterFields = new ArrayList<>();
        Map<String, String> filterValues = new LinkedHashMap<>();

        if (country != null && !country.isEmpty()) {
            filterFields.add("country");
            filterValues.put("country", country.toLowerCase());
        }
        if (region != null && !region.isEmpty()) {
            filterFields.add("region");
            filterValues.put("region", region.toLowerCase());
        }
        if (city != null && !city.isEmpty()) {
            filterFields.add("city");
            filterValues.put("city", city.toLowerCase());
        }
        if (waterType != null && !waterType.isEmpty()) {
            filterFields.add("waterType");
            filterValues.put("waterType", waterType.toLowerCase());
        }
        if (habitat != null && !habitat.isEmpty()) {
            filterFields.add("habitat");
            filterValues.put("habitat", habitat.toLowerCase());
        }
        if (species != null && !species.isEmpty()) {
            filterFields.add("species");
            filterValues.put("species", species.toLowerCase());
        }
        if (family != null && !family.isEmpty()) {
            filterFields.add("family");
            filterValues.put("family", family.toLowerCase());
        }
        if (waterBodyType != null && !waterBodyType.isEmpty()) {
            filterFields.add("waterBodyType");
            filterValues.put("waterBodyType", waterBodyType.toLowerCase());
        }
        if (size != null && !size.isEmpty()) {
            filterFields.add("size");
            filterValues.put("size", size.toLowerCase());
        }
        if (diet != null && !diet.isEmpty()) {
            filterFields.add("diet");
            filterValues.put("diet", diet.toLowerCase());
        }
        if (conservationStatus != null && !conservationStatus.isEmpty()) {
            filterFields.add("conservationStatus");
            filterValues.put("conservationStatus", conservationStatus.toLowerCase());
        }

        // If no filters, fetch all
        if (filterFields.isEmpty()) {
            for (QueryDocumentSnapshot doc : query.get().get().getDocuments()) {
                fishes.add(extractFishDto(doc));
            }
            return fishes;
        }

        // If only one filter, use a simple Firestore query
        if (filterFields.size() == 1) {
            String field = filterFields.get(0);
            String value = filterValues.get(field);
            Query filteredQuery = query.whereEqualTo(field, value);
            for (QueryDocumentSnapshot doc : filteredQuery.get().get().getDocuments()) {
                fishes.add(extractFishDto(doc));
            }
            return fishes;
        }

        // For multiple filters, Firestore requires composite indexes for certain field combinations
        // We use the first field for the query and apply remaining filters in memory
        // This is more efficient than fetching all documents when at least one indexable filter exists
        String firstField = filterFields.get(0);
        String firstValue = filterValues.get(firstField);
        filterFields.remove(0);
        filterValues.remove(firstField);

        Query filteredQuery = query.whereEqualTo(firstField, firstValue);
        List<FishDto> queryResults = new ArrayList<>();
        for (QueryDocumentSnapshot doc : filteredQuery.get().get().getDocuments()) {
            queryResults.add(extractFishDto(doc));
        }

        // Apply remaining filters in memory on the reduced result set
        for (FishDto fish : queryResults) {
            boolean match = true;
            for (String field : filterFields) {
                String expectedValue = filterValues.get(field);
                String actualValue = getFieldValue(fish, field);
                if (!expectedValue.equalsIgnoreCase(actualValue != null ? actualValue : "")) {
                    match = false;
                    break;
                }
            }
            if (match) {
                fishes.add(fish);
            }
        }

        return fishes;
    }

    // Helper to get field value from FishDto by field name
    private String getFieldValue(FishDto fish, String field) {
        switch (field) {
            case "country": return fish.getCountry();
            case "region": return fish.getRegion();
            case "city": return fish.getCity();
            case "waterType": return fish.getWaterType();
            case "habitat": return fish.getHabitat();
            case "species": return fish.getSpecies();
            case "family": return fish.getFamily();
            case "waterBodyType": return fish.getWaterBodyType();
            case "size": return fish.getSize();
            case "diet": return fish.getDiet();
            case "conservationStatus": return fish.getConservationStatus();
            default: return null;
        }
    }

    public FishDto getFishById(String userId, String fishId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot doc = db.collection("users")
                .document(userId)
                .collection("fishes")
                .document(fishId)
                .get()
                .get();

        if (doc.exists()) {
            return extractFishDto((QueryDocumentSnapshot) doc);
        }
        return null;
    }

    public void updateFish(String userId, String fishId, FishDto fishDto) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users")
                .document(userId)
                .collection("fishes")
                .document(fishId);

        Map<String, Object> data = new HashMap<>();
        if (fishDto.getName() != null) data.put("name", fishDto.getName());
        if (fishDto.getScientificName() != null) data.put("scientificName", fishDto.getScientificName());
        if (fishDto.getSpecies() != null) data.put("species", fishDto.getSpecies());
        if (fishDto.getFamily() != null) data.put("family", fishDto.getFamily());
        if (fishDto.getGenus() != null) data.put("genus", fishDto.getGenus());
        if (fishDto.getCountry() != null) data.put("country", fishDto.getCountry());
        if (fishDto.getRegion() != null) data.put("region", fishDto.getRegion());
        if (fishDto.getCity() != null) data.put("city", fishDto.getCity());
        if (fishDto.getLocationName() != null) data.put("locationName", fishDto.getLocationName());
        if (fishDto.getLatitude() != null) data.put("latitude", fishDto.getLatitude());
        if (fishDto.getLongitude() != null) data.put("longitude", fishDto.getLongitude());
        if (fishDto.getWaterType() != null) data.put("waterType", fishDto.getWaterType());
        if (fishDto.getHabitat() != null) data.put("habitat", fishDto.getHabitat());
        if (fishDto.getWaterBodyType() != null) data.put("waterBodyType", fishDto.getWaterBodyType());
        if (fishDto.getWaterDepth() != null) data.put("waterDepth", fishDto.getWaterDepth());
        if (fishDto.getWaterTemperature() != null) data.put("waterTemperature", fishDto.getWaterTemperature());
        if (fishDto.getWaterClarity() != null) data.put("waterClarity", fishDto.getWaterClarity());
        if (fishDto.getWeight() != null) data.put("weight", fishDto.getWeight());
        if (fishDto.getLength() != null) data.put("length", fishDto.getLength());
        if (fishDto.getColor() != null) data.put("color", fishDto.getColor());
        if (fishDto.getSize() != null) data.put("size", fishDto.getSize());
        if (fishDto.getDiet() != null) data.put("diet", fishDto.getDiet());
        if (fishDto.getBehavior() != null) data.put("behavior", fishDto.getBehavior());
        if (fishDto.getMigrationPattern() != null) data.put("migrationPattern", fishDto.getMigrationPattern());
        if (fishDto.getSpawningSeason() != null) data.put("spawningSeason", fishDto.getSpawningSeason());
        if (fishDto.getMinDepth() != null) data.put("minDepth", fishDto.getMinDepth());
        if (fishDto.getMaxDepth() != null) data.put("maxDepth", fishDto.getMaxDepth());
        if (fishDto.getConservationStatus() != null) data.put("conservationStatus", fishDto.getConservationStatus());
        if (fishDto.getCatchLimit() != null) data.put("catchLimit", fishDto.getCatchLimit());
        if (fishDto.getMinSizeLimit() != null) data.put("minSizeLimit", fishDto.getMinSizeLimit());
        if (fishDto.getProtectedSpecies() != null) data.put("protectedSpecies", fishDto.getProtectedSpecies());
        if (fishDto.getBaitUsed() != null) data.put("baitUsed", fishDto.getBaitUsed());
        if (fishDto.getFishingMethod() != null) data.put("fishingMethod", fishDto.getFishingMethod());
        if (fishDto.getGearType() != null) data.put("gearType", fishDto.getGearType());
        if (fishDto.getPhotoUrls() != null) data.put("photoUrls", fishDto.getPhotoUrls());
        if (fishDto.getNotes() != null) data.put("notes", fishDto.getNotes());
        if (fishDto.getDescription() != null) data.put("description", fishDto.getDescription());
        if (fishDto.getCaughtDate() != null) data.put("caughtDate", fishDto.getCaughtDate());

        docRef.update(data).get();
    }

    public void deleteFish(String userId, String fishId) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("users")
                .document(userId)
                .collection("fishes")
                .document(fishId)
                .delete()
                .get();
    }

    private FishDto extractFishDto(QueryDocumentSnapshot doc) {
        FishDto fishDto = new FishDto();
        fishDto.setId(doc.getId());
        fishDto.setName(doc.getString("name"));
        fishDto.setScientificName(doc.getString("scientificName"));
        fishDto.setSpecies(doc.getString("species"));
        fishDto.setFamily(doc.getString("family"));
        fishDto.setGenus(doc.getString("genus"));
        fishDto.setCountry(doc.getString("country"));
        fishDto.setRegion(doc.getString("region"));
        fishDto.setCity(doc.getString("city"));
        fishDto.setLocationName(doc.getString("locationName"));
        fishDto.setLatitude(doc.getDouble("latitude"));
        fishDto.setLongitude(doc.getDouble("longitude"));
        fishDto.setWaterType(doc.getString("waterType"));
        fishDto.setHabitat(doc.getString("habitat"));
        fishDto.setWaterBodyType(doc.getString("waterBodyType"));
        fishDto.setWaterDepth(doc.getDouble("waterDepth"));
        fishDto.setWaterTemperature(doc.getDouble("waterTemperature"));
        fishDto.setWaterClarity(doc.getString("waterClarity"));
        fishDto.setWeight(doc.getDouble("weight"));
        fishDto.setLength(doc.getDouble("length"));
        fishDto.setColor(doc.getString("color"));
        fishDto.setSize(doc.getString("size"));
        fishDto.setDiet(doc.getString("diet"));
        fishDto.setBehavior(doc.getString("behavior"));
        fishDto.setMigrationPattern(doc.getString("migrationPattern"));
        fishDto.setSpawningSeason(doc.getString("spawningSeason"));
        fishDto.setMinDepth(doc.contains("minDepth") ? ((Number) doc.getData().get("minDepth")).intValue() : null);
        fishDto.setMaxDepth(doc.contains("maxDepth") ? ((Number) doc.getData().get("maxDepth")).intValue() : null);
        fishDto.setConservationStatus(doc.getString("conservationStatus"));
        fishDto.setCatchLimit(doc.getString("catchLimit"));
        fishDto.setMinSizeLimit(doc.getString("minSizeLimit"));
        fishDto.setProtectedSpecies(doc.getBoolean("protectedSpecies"));
        fishDto.setBaitUsed(doc.getString("baitUsed"));
        fishDto.setFishingMethod(doc.getString("fishingMethod"));
        fishDto.setGearType(doc.getString("gearType"));
        fishDto.setPhotoUrls((List<String>) doc.get("photoUrls"));
        fishDto.setNotes(doc.getString("notes"));
        fishDto.setDescription(doc.getString("description"));
        fishDto.setAddedAt(doc.getDate("addedAt"));
        fishDto.setCaughtDate(doc.getDate("caughtDate"));
        return fishDto;
    }
}
