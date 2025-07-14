#!/bin/bash

echo "Testing microservices health..."

# Test Eureka Server
echo "Testing Eureka Server..."
curl -s http://localhost:8761/actuator/health | grep -q "UP" && echo "Eureka Server is UP" || echo "Eureka Server is DOWN"

# Test Config Server
echo "Testing Config Server..."
curl -s http://localhost:8888/actuator/health | grep -q "UP" && echo "Config Server is UP" || echo "Config Server is DOWN"

# Test API Gateway
echo "Testing API Gateway..."
curl -s http://localhost:8080/actuator/health | grep -q "UP" && echo "API Gateway is UP" || echo "API Gateway is DOWN"

# Test Visite Service
echo "Testing Visite Service..."
curl -s http://localhost:8082/actuator/health | grep -q "UP" && echo "Visite Service is UP" || echo "Visite Service is DOWN"

# Test Medecin Service
echo "Testing Medecin Service..."
curl -s http://localhost:8081/actuator/health | grep -q "UP" && echo "Medecin Service is UP" || echo "Medecin Service is DOWN"

# Test Keycloak
echo "Testing Keycloak..."
curl -s http://localhost:8090/health/ready | grep -q "UP" && echo "Keycloak is UP" || echo "Keycloak is DOWN"

echo "Testing completed!" 