# 🐟 Fish API

A REST API for fish data built with Spring Boot and Firebase Firestore. The API provides two main functionalities:

1. **Fish Species Database** (`/species`) — Public reference database with ~59 real fish species, including common names, scientific information, habitats, and conservation status.
2. **Personal Catches** (`/fishes`) — Authenticated API where users can store, update, and filter their own fishing experiences.

---

## Technologies

- **Spring Boot** — Java framework for REST API
- **Firebase Firestore** — NoSQL database for data storage
- **Firebase Authentication** — User authentication via ID tokens
- **Lombok** — Reduces boilerplate code in DTOs

---

## Setup and Running

### Prerequisites

- Java 17 or newer
- Gradle (included in the project via wrapper)
- Firebase project with Firestore enabled
- Firebase Admin SDK key (JSON file)

### Configuration

1. Place the Firebase Admin SDK JSON key in the project.
2. Update `src/main/resources/application.properties` with correct Firebase settings.
3. For login functionality, set your Firebase Web API Key in `UserController.java`.

### Running

```bash
./gradlew bootRun
```

The API starts at `http://localhost:8080`.

---

## Authentication

Most endpoints require authentication via Firebase ID token. Send the token in the `Authorization` header:

```
Authorization: Bearer <your_firebase_id_token>
```

### How to get a token

1. **Register a user:**
   ```bash
   curl -X POST http://localhost:8080/users/register \
     -H "Content-Type: application/json" \
     -d '{"email":"user@example.com","password":"password123"}'
   ```

2. **Login and get token:**
   ```bash
   curl -X POST http://localhost:8080/users/login \
     -H "Content-Type: application/json" \
     -d '{"email":"user@example.com","password":"password123"}'
   ```

   The response contains an `idToken` used in the `Authorization` header.

3. **Fish Species Database** (`/species`) is **public** and requires no authentication for GET requests.

---

## Endpoints

### 🐟 Fish Species Database (`/species`)

Public reference database with real fish species. GET requests do **not** require authentication.

#### Get all species (with optional filters)

```
GET /species
```

| Parameter             | Type   | Description                                              |
|-----------------------|--------|----------------------------------------------------------|
| `waterType`           | String | Water type: `freshwater`, `saltwater`, `brackish`        |
| `family`              | String | Family, e.g. `Salmonidae`, `Centrarchidae`               |
| `habitat`             | String | Habitat: `river`, `lake`, `ocean`, `estuary`             |
| `diet`                | String | Diet type: `carnivore`, `herbivore`, `omnivore`          |
| `conservationStatus`  | String | IUCN status: `LC`, `NT`, `VU`, `EN`, `CR`               |
| `continent`           | String | Continent: `Europe`, `North America`, `Asia`             |
| `size`                | String | Size: `small`, `medium`, `large`, `extra-large`          |

**Examples:**

```bash
# Get all species
curl http://localhost:8080/species

# Filter by freshwater
curl "http://localhost:8080/species?waterType=freshwater"

# Filter by family (case-insensitive)
curl "http://localhost:8080/species?family=Salmonidae"

# Combine multiple filters
curl "http://localhost:8080/species?diet=carnivore&continent=Europe"
```

#### Get species by ID

```
GET /species/{speciesId}
```

```bash
curl http://localhost:8080/species/oncorhynchusmykiss
```

#### Search by scientific name

```
GET /species/search/scientificName/{scientificName}
```

```bash
curl http://localhost:8080/species/search/scientificName/Oncorhynchus%20mykiss
```

#### Add new species (requires authentication)

```
POST /species
Authorization: Bearer <token>
Content-Type: application/json
```

```bash
curl -X POST http://localhost:8080/species \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "commonName": "Atlantic Salmon",
    "norwegianName": "Atlanterhavslaks",
    "scientificName": "Salmo salar",
    "family": "Salmonidae",
    "waterType": "anadromous",
    "diet": "carnivore",
    "continent": "Europe"
  }'
```

#### Update species (requires authentication)

```
PUT /species/{speciesId}
Authorization: Bearer <token>
Content-Type: application/json
```

#### Delete species (requires authentication)

```
DELETE /species/{speciesId}
Authorization: Bearer <token>
```

---

### 🎣 Personal Catches (`/fishes`)

All endpoints require **authentication**. Data is stored per user in Firestore under `users/{userId}/fishes/`.

#### Add a catch

```
POST /fishes
Authorization: Bearer <token>
Content-Type: application/json
```

```bash
curl -X POST http://localhost:8080/fishes \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rainbow Trout",
    "scientificName": "Oncorhynchus mykiss",
    "country": "Norway",
    "region": "Viken",
    "city": "Drammen",
    "locationName": "Drammenselva",
    "waterType": "freshwater",
    "habitat": "river",
    "weight": 2.5,
    "length": 45,
    "fishingMethod": "rod and reel",
    "baitUsed": "artificial fly"
  }'
```

#### Get all catches (with optional filters)

```
GET /fishes
Authorization: Bearer <token>
```

| Parameter             | Type   | Description                                              |
|-----------------------|--------|----------------------------------------------------------|
| `country`             | String | Country, e.g. `Norway`, `USA`                            |
| `region`              | String | Region or county                                         |
| `city`                | String | City or town                                             |
| `waterType`           | String | Water type: `freshwater`, `saltwater`, `brackish`        |
| `habitat`             | String | Habitat: `river`, `lake`, `ocean`                        |
| `species`             | String | Species name                                             |
| `family`              | String | Family, e.g. `Salmonidae`                                |
| `waterBodyType`       | String | Water body: `river`, `lake`, `stream`, `pond`            |
| `size`                | String | Size: `small`, `medium`, `large`, `extra-large`          |
| `diet`                | String | Diet type: `carnivore`, `herbivore`, `omnivore`          |
| `conservationStatus`  | String | IUCN status: `LC`, `NT`, `VU`, `EN`, `CR`               |

```bash
# Get all catches
curl -H "Authorization: Bearer <token>" http://localhost:8080/fishes

# Filter by country
curl -H "Authorization: Bearer <token>" "http://localhost:8080/fishes?country=Norway"
```

#### Get catch by ID

```
GET /fishes/{fishId}
Authorization: Bearer <token>
```

#### Update catch (partial update)

```
PUT /fishes/{fishId}
Authorization: Bearer <token>
Content-Type: application/json
```

#### Delete catch

```
DELETE /fishes/{fishId}
Authorization: Bearer <token>
```

---

### 👤 User Management (`/users`)

#### Register new user

```
POST /users/register
Content-Type: application/json
```

```bash
curl -X POST http://localhost:8080/users/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

#### Login

```
POST /users/login
Content-Type: application/json
```

```bash
curl -X POST http://localhost:8080/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

Returns a Firebase ID token used in the `Authorization` header.

---

## Species Database Data

The API comes with **~59 real fish species** that are automatically seeded on first startup. The species database contains:

### Identification
- Common names (English and Norwegian)
- Scientific names (Latin)
- Species, genus, and family classification

### Distribution
- Native continent and countries
- Natural regions

### Water and Environment
- Water type: freshwater, saltwater, brackish, anadromous, catadromous
- Habitat: river, lake, ocean, estuary, coral reef, stream, pond
- Depth and temperature ranges

### Physical Characteristics
- Average and maximum weight (kg)
- Average and maximum length (cm)
- Color description and size category

### Biology and Behavior
- Diet type: carnivore, herbivore, omnivore, insectivore
- Prey, behavior, migration patterns
- Spawning season and habitat
- Lifespan

### Conservation Status
- IUCN status (LC, NT, VU, EN, CR)
- Protected species (yes/no)
- Notes and descriptions

### Example Species in the Database

| Common Name       | Scientific Name            | Family       | Water Type  |
|-------------------|----------------------------|--------------|-------------|
| Rainbow Trout     | Oncorhynchus mykiss        | Salmonidae   | Freshwater  |
| Atlantic Cod      | Gadus morhua               | Gadidae      | Saltwater   |
| Northern Pike     | Esox lucius                | Esocidae     | Freshwater  |
| European Perch    | Perca fluviatilis          | Percidae     | Freshwater  |
| Atlantic Herring  | Clupea harengus            | Clupeidae    | Saltwater   |
| Atlantic Salmon   | Salmo salar                | Salmonidae   | Anadromous  |
| Common Carp       | Cyprinus carpio            | Cyprinidae   | Freshwater  |
| Atlantic Mackerel | Scomber scombrus           | Scombridae   | Saltwater   |
| Common Sole       | Solea solea                | Soleidae     | Saltwater   |
| Brown Trout       | Salmo trutta               | Salmonidae   | Anadromous  |

---

## Firestore Database Structure

```
fishSpecies/
  └── {scientificName}/    # Document ID based on scientific name
       ├── commonName
       ├── norwegianName
       ├── scientificName
       ├── family
       ├── waterType
       ├── habitats
       ├── diet
       ├── conservationStatus
       └── ...

users/
  └── {userId}/
       └── fishes/
            └── {fishId}/  # UUID
                 ├── name
                 ├── country
                 ├── weight
                 └── ...
```

---

## Field Reference

### FishSpeciesDto (Species Database)

| Field                 | Type            | Description                                          |
|-----------------------|-----------------|------------------------------------------------------|
| `id`                  | String          | Document ID (based on scientific name)               |
| `commonName`          | String          | Common English name                                  |
| `norwegianName`       | String          | Norwegian name                                       |
| `scientificName`      | String          | Scientific Latin name                                |
| `species`             | String          | Specific epithet                                     |
| `family`              | String          | Family                                               |
| `genus`               | String          | Genus name                                           |
| `nativeRegions`       | List\<String\>  | Natural regions                                      |
| `nativeCountries`     | List\<String\>  | Native countries                                     |
| `continent`           | String          | Native continent                                     |
| `waterType`           | String          | Water type                                           |
| `habitats`            | List\<String\>  | Habitat types                                        |
| `minDepth` / `maxDepth` | Integer        | Depth in meters                                      |
| `minTemperature` / `maxTemperature` | Double | Temperature in Celsius                      |
| `avgWeight` / `maxWeight` | Double      | Weight in kg                                         |
| `avgLength` / `maxLength` | Double      | Length in cm                                         |
| `color`               | String          | Color description                                    |
| `size`                | String          | Size category                                        |
| `diet`                | String          | Diet type                                            |
| `prey`                | List\<String\>  | Prey species                                         |
| `behavior`            | String          | Behavior notes                                       |
| `migrationPattern`    | String          | Migration pattern                                    |
| `spawningSeason`      | String          | Spawning season                                      |
| `lifespan`            | Integer         | Lifespan in years                                    |
| `conservationStatus`  | String          | IUCN status                                          |
| `protectedSpecies`    | Boolean         | Protected species                                    |
| `notes`               | String          | Notes                                                |
| `description`         | String          | Description                                          |

### FishDto (Personal Catches)

| Field                 | Type            | Description                                          |
|-----------------------|-----------------|------------------------------------------------------|
| `id`                  | String          | UUID                                                 |
| `name`                | String          | Common fish name                                     |
| `scientificName`      | String          | Scientific name                                      |
| `country`             | String          | Country                                              |
| `region`              | String          | Region or county                                     |
| `city`                | String          | City                                                 |
| `locationName`        | String          | Specific location                                    |
| `latitude` / `longitude` | Double       | GPS coordinates                                      |
| `waterType`           | String          | Water type                                           |
| `habitat`             | String          | Habitat                                              |
| `waterBodyType`       | String          | Water body type                                      |
| `weight`              | Double          | Weight in kg                                         |
| `length`              | Double          | Length in cm                                         |
| `fishingMethod`       | String          | Fishing method                                       |
| `baitUsed`            | String          | Bait or lure                                         |
| `caughtDate`          | Date            | Catch date                                           |
| `addedAt`             | Date            | Creation date                                        |
| ...                   | ...             | See `FishDto.java` for full list                     |

---

## IUCN Conservation Status

| Code | Status                        |
|------|-------------------------------|
| LC   | Least Concern                 |
| NT   | Near Threatened               |
| VU   | Vulnerable                    |
| EN   | Endangered                    |
| CR   | Critically Endangered         |
| EW   | Extinct in the Wild           |
| EX   | Extinct                       |

---

## Project Structure

```
FishApi/
├── src/main/java/no/hiof/danieljr/FishApi/
│   ├── FishApiApplication.java      # Main application
│   ├── config/
│   │   ├── FirebaseConfig.java      # Firebase configuration
│   │   ├── SecurityConfig.java      # Security configuration
│   │   └── FishSpeciesSeeder.java   # Auto-seeding of species data
│   ├── controller/
│   │   ├── FishController.java      # Personal catches
│   │   ├── FishSpeciesController.java # Species database
│   │   └── UserController.java      # User registration and login
│   ├── dto/
│   │   ├── FishDto.java             # DTO for catches
│   │   ├── FishSpeciesDto.java      # DTO for species
│   │   ├── LoginRequest.java        # Login request
│   │   └── RegisterRequest.java     # Registration request
│   └── service/
│       ├── FishService.java         # Catch logic
│       ├── FishSpeciesService.java  # Species logic
│       └── UserService.java         # User logic
└── src/main/resources/
    └── application.properties       # Configuration
```

---

## License

This project is licensed under the [MIT License](../LICENSE).
