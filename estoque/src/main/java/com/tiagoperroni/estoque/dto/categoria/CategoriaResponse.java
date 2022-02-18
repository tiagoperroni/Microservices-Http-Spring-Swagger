package com.tiagoperroni.estoque.dto.categoria;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiagoperroni.estoque.model.Categoria;
import com.tiagoperroni.estoque.model.Produto;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class CategoriaResponse {

    private Integer id;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;
   
    @JsonIgnore(value = true)
    private List<Produto> produtos;

    public static CategoriaResponse convert(Categoria categoria) {
        var catResp = new CategoriaResponse();
        BeanUtils.copyProperties(categoria, catResp);
        return catResp;
    }
    
}
