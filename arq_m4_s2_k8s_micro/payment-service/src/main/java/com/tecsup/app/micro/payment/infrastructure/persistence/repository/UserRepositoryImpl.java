package com.tecsup.app.micro.payment.infrastructure.persistence.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.UserRepository;
import com.tecsup.app.micro.payment.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de Usuario (Adaptador)
 * Conecta el dominio con la infraestructura de persistencia
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    
    @Override
    public List<Payment> findAll() {
        log.debug("Finding all users");
        return jpaUserRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Payment> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        return jpaUserRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public Optional<Payment> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return jpaUserRepository.findByEmail(email)
                .map(this::toDomain);
    }
    
    @Override
    public Payment save(Payment payment) {
        log.debug("Saving user: {}", payment.getEmail());
        UserEntity entity = toEntity(payment);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public void deleteById(Long id) {
        log.debug("Deleting user by id: {}", id);
        jpaUserRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        log.debug("Checking if email exists: {}", email);
        return jpaUserRepository.existsByEmail(email);
    }
    
    // Mappers
    
    private Payment toDomain(UserEntity entity) {
        return Payment.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    private UserEntity toEntity(Payment payment) {
        return UserEntity.builder()
                .id(payment.getId())
                .name(payment.getName())
                .email(payment.getEmail())
                .phone(payment.getPhone())
                .address(payment.getAddress())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
