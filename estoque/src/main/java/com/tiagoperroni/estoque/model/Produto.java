package com.tiagoperroni.estoque.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tiagoperroni.estoque.dto.produto.ProdutoRequest;
import com.tiagoperroni.estoque.dto.produto.ProdutoResponse;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Double preco;

    private Integer quantidade;

    @ManyToOne       
    private Categoria categoria;
    

    private LocalDateTime dataCriacao;

    public static Produto convert(ProdutoRequest request) {
        var produto = new Produto();
        BeanUtils.copyProperties(request, produto, "dataCriacao");
        return produto;
    }

    public static Produto convertResponse(ProdutoResponse response) {
        var produto = new Produto();
        BeanUtils.copyProperties(response, produto);
        return produto;
    }

}
