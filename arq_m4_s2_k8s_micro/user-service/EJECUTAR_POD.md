--
Ejecutar clean y package (MVN)

--
docker build -t user-service:1.0 .

--verificar
docker images user-service

--powershell ejecutar:

docker run -p 8083:8083
-e SPRING_PROFILES_ACTIVE=kubernetes
-e DB_URL=jdbc:postgresql://host.docker.internal:5434/userdb
-e DB_USERNAME=postgres
-e DB_PASSWORD=postgres
user-service:1.0


--postman
http://localhost:8083/actuator/health


--
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml


kubectl get namespaces  
kubectl get configmap -n user-service
kubectl get secret -n user-service
kubectl describe secret user-service-secret -n user-service
kubectl get pods -n user-service

-- ver log:
kubectl logs -f user-service-b66889dfb-bvnz5 -n user-service
kubectl get service -n user-service


