package com.tiagoperroni.estoque.repository;

import java.util.List;
import java.util.Optional;

import com.tiagoperroni.estoque.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    Optional<List<Categoria>> findByNomeIgnoreCaseContaining(String nome);

}
