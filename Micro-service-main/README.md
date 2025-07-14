# Système de Gestion des Médecins et des Visites

Ce projet est composé de deux microservices Spring Boot pour la gestion des médecins et des visites médicales.

## Prérequis

- Java 17
- Maven
- MongoDB
- MySQL
- Docker (optionnel)

## Structure du Projet

- `shared-library` : Bibliothèque partagée contenant les DTOs
- `medecin-service` : Service de gestion des médecins (MongoDB)
- `visite-service` : Service de gestion des visites (MySQL)

## Configuration

### Base de données

1. MongoDB (medecin-service) :
   - Base de données : healthcare_medecins
   - Port : 27017

2. MySQL (visite-service) :
   - Base de données : healthcare_visites
   - Port : 3306
   - Utilisateur : root
   - Mot de passe : root

## Installation

1. Compiler la bibliothèque partagée :
   ```bash
   cd shared-library
   mvn clean install
   ```

2. Compiler et démarrer le service médecin :
   ```bash
   cd medecin-service
   mvn spring-boot:run
   ```

3. Compiler et démarrer le service visite :
   ```bash
   cd visite-service
   mvn spring-boot:run
   ```

## API Endpoints

### Service Médecin (port 8081)

- GET `/api/medecins` : Liste tous les médecins
- GET `/api/medecins/{id}` : Récupère un médecin par ID
- POST `/api/medecins` : Crée un nouveau médecin
- PUT `/api/medecins/{id}` : Met à jour un médecin
- DELETE `/api/medecins/{id}` : Supprime un médecin

### Service Visite (port 8082)

- GET `/api/visites` : Liste toutes les visites
- GET `/api/visites/{id}` : Récupère une visite par ID
- GET `/api/visites/medecin/{medecinId}` : Liste les visites d'un médecin
- POST `/api/visites` : Crée une nouvelle visite
- PUT `/api/visites/{id}` : Met à jour une visite
- DELETE `/api/visites/{id}` : Supprime une visite

## Exemple d'utilisation

### Créer un médecin

```bash
curl -X POST http://localhost:8081/api/medecins \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "specialite": "Généraliste",
    "email": "jean.dupont@example.com",
    "telephone": "+33123456789",
    "adresse": "123 rue de la Santé, Paris"
  }'
```

### Créer une visite

```bash
curl -X POST http://localhost:8082/api/visites \
  -H "Content-Type: application/json" \
  -d '{
    "medecinId": "...",
    "nomPatient": "Martin Durant",
    "dateVisite": "2024-03-15T10:30:00",
    "motif": "Consultation de routine",
    "notes": "Patient en bonne santé générale",
    "statut": "PLANIFIE"
  }'
```

## Fonctionnalités à venir

- Service de configuration
- Dockerisation des services
- Communication asynchrone avec Kafka
- Intégration de Keycloak pour la sécurité 