package com.tiagoperroni.pedidos.repository;

import com.tiagoperroni.pedidos.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
