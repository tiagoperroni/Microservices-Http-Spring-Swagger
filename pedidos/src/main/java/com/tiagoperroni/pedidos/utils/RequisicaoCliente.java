package com.tiagoperroni.pedidos.utils;

import com.tiagoperroni.pedidos.exceptions.ModelNotFoundException;
import com.tiagoperroni.pedidos.model.Cliente;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RequisicaoCliente {

    @Value("${ESTOQUE_API_URL:http://localhost:8081/api/clientes/cpf/}")
    private String estoqueUrl;

    public Cliente getClienteByCpf(String cpf) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = estoqueUrl + cpf;
            
            ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);            
            var cliente = new Cliente();
            BeanUtils.copyProperties(response.getBody(), cliente);
            return cliente;
        } catch (Exception e) {
            throw new ModelNotFoundException("Não foi encontrado cliente com o CPF informado.");
        }
    }
}
