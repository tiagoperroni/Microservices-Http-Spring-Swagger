package com.tiagoperroni.pedidos.repository;

import java.util.Optional;

import com.tiagoperroni.pedidos.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);
}
