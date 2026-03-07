package com.tecsup.app.micro.payment.domain.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del Repositorio de Usuario (Interface)
 * Define el contrato para la persistencia sin depender de la implementación
 * Esta interfaz pertenece al dominio y será implementada en la capa de infraestructura
 */
public interface UserRepository {
    
    /**
     * Obtiene todos los usuarios
     */
    List<Payment> findAll();
    
    /**
     * Busca un usuario por ID
     */
    Optional<Payment> findById(Long id);
    
    /**
     * Busca un usuario por email
     */
    Optional<Payment> findByEmail(String email);
    
    /**
     * Guarda un nuevo usuario o actualiza uno existente
     */
    Payment save(Payment payment);
    
    /**
     * Elimina un usuario por ID
     */
    void deleteById(Long id);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);
}
