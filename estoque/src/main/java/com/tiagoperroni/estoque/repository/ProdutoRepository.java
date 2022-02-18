package com.tiagoperroni.estoque.repository;

import java.util.List;
import java.util.Optional;

import com.tiagoperroni.estoque.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Optional<List<Produto>> findByNomeIgnoreCaseContaining(String nome);

    List<Produto> findByCategoriaId(Integer id);
    
}
