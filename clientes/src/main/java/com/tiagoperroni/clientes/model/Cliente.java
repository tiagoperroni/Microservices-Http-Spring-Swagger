package com.tiagoperroni.clientes.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import com.tiagoperroni.clientes.dto.ClienteRequest;
import com.tiagoperroni.clientes.dto.ClienteResponse;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(unique = true)
    @Email
    private String email;

    @Column(unique = true)
    @CPF
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    private LocalDateTime dataCadastro;

    public static Cliente convertRequest(ClienteRequest request) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(request, cliente, "id");
        return cliente;
    }

    public static Cliente convertResponse(ClienteResponse response) {
        var cliente = new Cliente();
        BeanUtils.copyProperties(response, cliente);
        return cliente;
    }
    
}
