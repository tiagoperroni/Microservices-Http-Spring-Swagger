package com.tiagoperroni.pedidos.utils;

import com.tiagoperroni.pedidos.exceptions.ModelNotFoundException;
import com.tiagoperroni.pedidos.model.Produto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RequisicaoEstoque {

    @Value("${ESTOQUE_API_URL:http://localhost:8080/api/produtos/}")
    private String estoqueUrl;

    public Produto getProdutoById(Integer id) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = estoqueUrl + id;
            ResponseEntity<Produto> response = restTemplate.getForEntity(url, Produto.class);
            System.out.println(response.getBody());
            var produto = new Produto();
            BeanUtils.copyProperties(response.getBody(), produto);
            return produto;
        } catch (Exception e) {
            throw new ModelNotFoundException(String.format("Não foram encontrado produtos com id '%s'.", id));
        }        
    }

    public void updateEstoque(Integer id, Integer quantidade) {
      
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = estoqueUrl + "atualizaEstoque/" + id + "/" + quantidade;
            restTemplate.put(url, null);    
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
