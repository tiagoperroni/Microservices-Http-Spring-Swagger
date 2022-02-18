package com.tiagoperroni.pedidos.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;

    private Double valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataPedido;

    @ManyToOne
    private Cliente cliente;

    @OneToMany
    private List<Produto> produtos;

    public void calculaTotal(List<Produto> produtos) {
        Double valor = 0.00;
        for (Produto prods : produtos) {
            valor += prods.calculaTotal();
        }
        this.valorTotal = valor;
    }

}
