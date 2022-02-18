package com.tiagoperroni.estoque.dto.produto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiagoperroni.estoque.model.Categoria;
import com.tiagoperroni.estoque.model.Produto;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class ProdutoResponse {

    private Integer id;
    private String nome;
    private Double preco;
    private Integer quantidade;

    @JsonIgnoreProperties(value = "produtos")
    private Categoria categoria;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;

    public static ProdutoResponse convert(Produto produto) {
        var produtoResponse = new ProdutoResponse();
        BeanUtils.copyProperties(produto, produtoResponse);
        return produtoResponse;
    }

}
