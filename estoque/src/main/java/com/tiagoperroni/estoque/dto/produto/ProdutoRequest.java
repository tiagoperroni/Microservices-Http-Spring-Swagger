package com.tiagoperroni.estoque.dto.produto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProdutoRequest {   

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer quantidade;

    @NotNull
    private Integer categoriaId;   

}
