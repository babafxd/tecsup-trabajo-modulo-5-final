--
Ejecutar clean y package (MVN)

-- 
docker build -t catalog-service:1.0 .

--verificar
docker images catalog-service

--powershell ejecutar:

docker run -p 8084:8084
-e SPRING_PROFILES_ACTIVE=kubernetes
-e DB_URL=jdbc:postgresql://host.docker.internal:5435/catalogdb
-e DB_USERNAME=postgres
-e DB_PASSWORD=postgres
catalog-service:1.0


--postman
http://localhost:8084/actuator/health

--
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml


kubectl get namespaces  
kubectl get configmap -n catalog-service
kubectl get secret -n catalog-service
kubectl describe secret catalog-service-secret -n catalog-service
kubectl get pods -n catalog-service 

-- ver log:
kubectl logs -f catalog-service-6fbc5f46bf-gm2gj -n catalog-service
kubectl get service -n catalog-service