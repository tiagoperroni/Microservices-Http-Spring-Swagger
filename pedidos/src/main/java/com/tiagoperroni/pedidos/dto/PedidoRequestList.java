package com.tiagoperroni.pedidos.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PedidoRequestList {

    private Integer produtoId;
    @NotNull
    private Integer quantidade;
    
}
