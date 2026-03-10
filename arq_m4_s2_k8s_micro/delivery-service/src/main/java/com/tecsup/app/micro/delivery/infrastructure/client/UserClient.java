package com.tecsup.app.micro.delivery.infrastructure.client;


import com.tecsup.app.micro.delivery.domain.model.Token;
import com.tecsup.app.micro.delivery.domain.model.User;
import com.tecsup.app.micro.delivery.infrastructure.client.dto.TokenResponseDto;
import com.tecsup.app.micro.delivery.infrastructure.client.dto.UserDto;
import com.tecsup.app.micro.delivery.infrastructure.client.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;
    private final UserDtoMapper userDTOMapper;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${user.service.username}")
    private String userServiceUsername;

    @Value("${user.service.password}")
    private String userServicePassword;

    public Optional<User> getUserById(Long userId, String jwtToken) {
        log.info("Calling User Service (PostgreSQL userdb) to get user with id: {}", userId);

        String url = this.userServiceUrl + "/api/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (jwtToken != null && !jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        } else {
            log.warn("No JWT token provided for User Service call");
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserDto> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, UserDto.class
            );
            log.info("User retrieved successfully from user-service: {}", response.getBody());
            User domainUser = userDTOMapper.toDomain(response.getBody());
            return Optional.of(domainUser);

        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error calling User Service: {}", e.getMessage());
            throw new RuntimeException("Error calling User Service: " + e.getMessage());
        }
    }


    public Optional<Token> getToken() {
        log.info("Iniciando autenticación getToken en User Service");

        String url = this.userServiceUrl + "/api/auth/login";

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", userServiceUsername);
        loginRequest.put("password", userServicePassword);

        // 3. Configurar Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 4. Crear la entidad que une Body + Headers
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(loginRequest, headers);

        try {
            // 5. Ejecutar POST usando exchange
            ResponseEntity<TokenResponseDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    TokenResponseDto.class
            );

            if (response.getBody() != null) {
                log.info("Token generado exitosamente");

                // Mapeamos el DTO de respuesta al objeto de dominio Token
                Token domainToken = new Token(response.getBody().getToken());
                return Optional.of(domainToken);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.NotFound e) {
            log.warn("Credenciales inválidas o usuario no encontrado");
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error crítico al llamar a User Service (Auth): {}", e.getMessage());
            throw new RuntimeException("Error en la comunicación con el servicio de autenticación");
        }
    }



}