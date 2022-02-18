package com.tiagoperroni.clientes.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class ClienteRequest {

    private String nome;
    @Email
    private String email;
    @CPF
    private String cpf;
    private String dataNascimento;
    private LocalDateTime dataCadastro;
    
}
