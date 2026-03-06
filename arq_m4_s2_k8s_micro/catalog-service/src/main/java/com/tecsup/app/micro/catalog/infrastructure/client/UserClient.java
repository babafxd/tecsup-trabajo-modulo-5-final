package com.tecsup.app.micro.catalog.infrastructure.client;

import com.tecsup.app.micro.catalog.domain.model.User;
import com.tecsup.app.micro.catalog.infrastructure.client.dto.UserDto;
import com.tecsup.app.micro.catalog.infrastructure.client.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;
    private final UserDtoMapper userDTOMapper;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public User getUserById(Long userId, String jwtToken) {
        log.info("Calling User Service (PostgreSQL userdb) to get user with id: {}", userId);

        String url = this.userServiceUrl + "/api/users/" + userId;

        // =============================================
        // Sesión 2: Propagar JWT en el header
        // =============================================
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
            return userDTOMapper.toDomain(response.getBody());
        } catch (Exception e) {
            log.error("Error calling User Service: {}", e.getMessage());
            throw new RuntimeException("Error calling User Service: " + e.getMessage());
        }
    }

    /**
     * Metodo de versión anterior (sin JWT) - mantener para compatibilidad
     * Se puede eliminar una vez que JWT esté completamente implementado
     */
    public User getUserById(Long userId) {
        return getUserById(userId, null);
    }

}