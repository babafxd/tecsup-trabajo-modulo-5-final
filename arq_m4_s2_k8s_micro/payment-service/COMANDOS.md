--
Ejecutar clean y package (MVN)

--
docker build -t payment-service:1.0 .

--verificar
docker images payment-service

--powershell ejecutar:

docker run -p 8086:8086
-e SPRING_PROFILES_ACTIVE=kubernetes
-e DB_URL=jdbc:postgresql://host.docker.internal:5437/paymentdb
-e DB_USERNAME=postgres
-e DB_PASSWORD=postgres
payment-service:1.0


--postman
http://localhost:8085/actuator/health

--
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml


kubectl get namespaces  
kubectl get configmap -n payment-service
kubectl get secret -n payment-service
kubectl describe secret payment-service-secret -n payment-service
kubectl get pods -n payment-service

-- ver log:
kubectl logs -f payment-service-6f65854dff-m2jwq -n payment-service
kubectl get service -n payment-service

kubectl describe pod order-service-84dc67f765-7p49z -n order-service
kubectl exec -it order-service-84dc67f765-7p49z -n order-service -- printenv | findstr KAFKA