package com.tiagoperroni.pedidos.repository;


import com.tiagoperroni.pedidos.model.Pedidos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long>{    
  
}
