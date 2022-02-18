package com.tiagoperroni.clientes.repository;

import java.util.List;
import java.util.Optional;

import com.tiagoperroni.clientes.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeIgnoreCaseContaining(String nome);

    Optional<Cliente> findByCpf(String cpf);
}
