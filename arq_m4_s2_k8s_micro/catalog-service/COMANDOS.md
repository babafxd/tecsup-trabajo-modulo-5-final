--
Ejecutar clean y package (MVN)

-- 
docker build -t product-service:1.0 .

--verificar
docker images product-service

--powershell ejecutar:

docker run -p 8082:8082 \
-e SPRING_PROFILES_ACTIVE=kubernetes \
-e DB_URL=jdbc:postgresql://host.docker.internal:5433/productdb \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=postgres \
product-service:1.0


--postman
http://localhost:8082/actuator/health


--
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml


kubectl get namespaces  
kubectl get configmap -n product-service
kubectl get secret -n product-service
kubectl describe secret product-service-secret -n product-service
kubectl get pods -n product-service 

-- ver log:
kubectl logs -f product-service-7fcb6b4847-dl8p4 -n product-service
kubectl get service -n product-service