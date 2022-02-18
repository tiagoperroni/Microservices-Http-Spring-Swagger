package com.tiagoperroni.clientes.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tiagoperroni.clientes.model.Cliente;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class ClienteResponse {
    
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    @Column(name = "data_nascimento")
    private String dataNascimento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCadastro;

    public static ClienteResponse convertCliente(Cliente cliente) {
        var clienteResponse = new ClienteResponse();
        BeanUtils.copyProperties(cliente, clienteResponse);
        return clienteResponse;
    }

}
