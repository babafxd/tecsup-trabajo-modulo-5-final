--
Ejecutar clean y package (MVN)

--
docker build -t delivery-service:1.0 .

--verificar
docker images delivery-service


--
kubectl apply -f k8s/00-namespace.yaml
kubectl apply -f k8s/01-configmap.yaml
kubectl apply -f k8s/02-secret.yaml
kubectl apply -f k8s/03-deployment.yaml
kubectl apply -f k8s/04-service.yaml


kubectl get namespaces  
kubectl get configmap -n delivery-service
kubectl get secret -n delivery-service
kubectl describe secret delivery-service-secret -n delivery-service
kubectl get pods -n delivery-service

-- ver log:
kubectl logs -f delivery-service-7dbf758dc6-ch69f -n delivery-service
kubectl get service -n delivery-service
