package com.tiagoperroni.pedidos.repository;

import com.tiagoperroni.pedidos.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
}
