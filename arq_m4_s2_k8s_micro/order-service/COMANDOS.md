--
Ejecutar clean y package (MVN)

--
docker build -t order-service:1.0 .

--verificar
docker images order-service

--powershell ejecutar:

docker run -p 8081:8081 \
-e SPRING_PROFILES_ACTIVE=kubernetes \
-e DB_URL=jdbc:postgresql://host.docker.internal:5435/orderdb \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=postgres \
order-service:1.0


--postman
http://localhost:8081/actuator/health

--
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml


kubectl get namespaces  
kubectl get configmap -n order-service
kubectl get secret -n order-service
kubectl describe secret order-service-secret -n order-service
kubectl get pods -n order-service

-- ver log:
kubectl logs -f order-service-77fc68d574-s9vd7 -n order-service
kubectl get service -n order-service