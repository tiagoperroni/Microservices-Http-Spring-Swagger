package com.tiagoperroni.pedidos.dto;

import java.util.List;
import lombok.Data;

@Data
public class PedidoRequest {

    private String clienteCpf;
    
    private List<PedidoRequestList> produtos;
    
}
