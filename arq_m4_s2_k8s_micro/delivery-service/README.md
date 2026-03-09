# KUBERNETES CON SPRING BOOT Y DOCKER


### 1.- Actualizar profile de Kubernetes `application-kubernetes.yaml`


``` yaml 
# ============================================
# APPLICATION CONFIGURATION FOR KUBERNETES
# ============================================
# Este archivo se activa con: -Dspring.profiles.active=kubernetes
# Las variables ${} se reemplazan con variables de entorno

# ============================================
# SERVER CONFIGURATION
# ============================================
server:
  port: 8081

spring:
  application:
    name: delivery-service

  # ============================================
  # DATASOURCE CONFIGURATION
  # ============================================
  # Estas variables vienen de ConfigMap y Secrets de Kubernetes
  datasource:
    url: ${DB_URL:jdbc:postgresql://host.docker.internal:5434/userdb}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver

    # ============================================
    # CONNECTION POOL
    # ============================================
    hikari:
      maximum-pool-size: ${POOL_SIZE:10}
      minimum-idle: 5
      connection-timeout: 20000

  # ============================================
  # JPA CONFIGURATION
  # ============================================
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        jdbc:
          lob:
            non_contextual_creation: true
    hibernate:
      ddl-auto: ${DDL_AUTO:validate}
    show-sql: ${SHOW_SQL:false}

# ============================================
# ACTUATOR (Para health checks de Kubernetes)
# ============================================
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      probes:
        enabled: true
      show-details: when-authorized
  health:
    livenessState:
      enabled: true
    readinessState:
      enabled: true

# ============================================
# LOGGING
# ============================================
logging:
  level:
    com.tecsup.app.micro.delivery: ${LOG_LEVEL:INFO}
    org.hibernate.SQL: ${SQL_LOG_LEVEL:WARN}
```

### 2.- Agregar dependencia de Actuator en `pom.xml`

```xml

<!-- Spring Boot Actuator (para health checks de Kubernetes) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

```
- Compilar para verificar
```
cd delivery-service
mvn clean compile
```


### 3.- Iniciar PostgreSQL para userdb
```
# Desde el directorio raíz del proyecto
docker-compose -f ../docker-compose.yml up -d

# Ver logs
docker logs postgres-delivery

```

#### Crear las tablas
```
```




### 4.- Dockerizar delivery-service

#### Compilar con perfil Kubernetes

```
mvn clean package -DskipTests
```

#### Dockerfile

```
# ============================================
# ETAPA 2: RUNTIME
# ============================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar JAR desde etapa de build
COPY target/*.jar /app/delivery-service.jar

# Puerto
EXPOSE 8081

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/delivery-service.jar"]

```

#### Constuir imagen

```
# Construir imagen
docker build -t delivery-service:1.0 .

# Este proceso toma 2-3 minutos la primera vez
# Ver progreso: [1/2] STEP X/Y...

# Verificar imagen creada
docker images | grep delivery-service

# Deberías ver:
# delivery-service   1.0   abc123def456   1 minute ago   230MB

```

#### Probar la imagen Docker

```
# Ejecutar contenedor de la app
docker run -p 8081:8081 \
-e SPRING_PROFILES_ACTIVE=kubernetes \
-e DB_URL=jdbc:postgresql://host.docker.internal:5434/userdb \
-e DB_USERNAME=postgres \
-e DB_PASSWORD=postgres \
delivery-service:1.0

# Deberías ver:
# Started UserServiceApplication in X seconds


# En otra terminal, probar

# Health check
curl http://localhost:8081/actuator/health

# Respuesta esperada:
# {"status":"UP","groups":["liveness","readiness"]}

# Listar usuarios
curl http://localhost:8081/api/deliveries

```


### 5.- Desplegar en Kubernetes

#### Crear Namespace en Kubernetes

- Aplicar namespace
```
kubectl apply -f k8s/00-namespace.yaml

# Output:
# namespace/delivery-service created
```
- Verificar namespace
```
kubectl get namespaces  

# Deberías ver:
# delivery-service       Active   X minutes
```

#### Crear ConfigMap

- Aplicar ConfigMap
```
kubectl apply -f k8s/01-configmap.yaml

# Output:
# configmap/delivery-service-config created
```
- Verificar ConfigMap
```
kubectl get configmap -n delivery-service

# Ver contenido
kubectl describe configmap delivery-service-config -n delivery-service
```



#### Crear Secret
```
# Aplicar
kubectl apply -f k8s/02-secret.yaml

# Output:
# secret/delivery-service-secret created

# Verificar
kubectl get secret -n delivery-service

# Ver detalle 
kubectl describe secret delivery-service-secret -n delivery-service

```

#### Desplegar User-Service

- Aplicar Deployment
```
kubectl apply -f k8s/03-deployment.yaml

# Output:
# deployment.apps/delivery-service created
```


- En caso necesites redesplegar (por ejemplo, después de corregir un error en el Deployment):
```
 kubectl rollout restart deployment delivery-service -n delivery-service
```


- Verificar pods
```
kubectl get pods -n delivery-service 
```

- Ver logs
```
# Ver logs
kubectl logs -f <POD_NAME> -n delivery-service

# Ver descripción completa del pod
kubectl describe pod <POD_NAME> -n delivery-service

```

- Verificar variables de entorno

```
# Entrar al pod
kubectl exec -it <POD_NAME> -n delivery-service -- /bin/sh

# Ver variables
env | grep DB_


# Salir
exit

```

#### Exponer con Service

- Aplicar Service

```
kubectl apply -f k8s/04-service.yaml

# Output:
# service/delivery-service created
```

- Verificar Service
```

kubectl get service -n delivery-service

# Output:
# NAME              TYPE       CLUSTER-IP      PORT(S)        AGE
# delivery-service      NodePort   10.96.xxx.xxx   80:30082/TCP   5s

```

- Probar delivery-service
```
# Health check
curl http://localhost:30081/actuator/health

# Output esperado:
# {"status":"UP"}
```
# Listar deliveries
```
curl http://localhost:30081/api/deliveries
```

#### Ver logs 
```
# Ver logs de delivery-service
kubectl logs -f <POD_NAME> -n delivery-service


```

