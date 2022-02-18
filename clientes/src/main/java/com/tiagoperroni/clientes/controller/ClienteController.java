package com.tiagoperroni.clientes.controller;

import java.util.HashMap;
import java.util.List;

import com.tiagoperroni.clientes.dto.ClienteRequest;
import com.tiagoperroni.clientes.dto.ClienteResponse;
import com.tiagoperroni.clientes.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value = "Versão da API")
    @GetMapping("/version")
    public ResponseEntity<HashMap<String, String>> getVersionStatus() {
        var resposta = new HashMap<String, String>();
        resposta.put("Version", "1.0.0");
        resposta.put("Status", "ON");
        resposta.put("Microservice", "Api-Clientes");
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @ApiOperation(value = "Listar Todos")
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll() {
        return new ResponseEntity<>(this.clienteService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Listar por Id")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.clienteService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Listar por Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ClienteResponse>> getById(@PathVariable("nome") String nome) {
        return new ResponseEntity<>(this.clienteService.findByNome(nome), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar por CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponse> getByCPF(@PathVariable("cpf") String cpf) {
        return new ResponseEntity<>(this.clienteService.findByCPF(cpf), HttpStatus.OK);
    }

    @ApiOperation(value = "Salvar Cliente")
    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest request) {
        return new ResponseEntity<>(this.clienteService.create(request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> save(@PathVariable Long id, @RequestBody ClienteRequest request) {
        return new ResponseEntity<>(this.clienteService.update(id, request), HttpStatus.ACCEPTED);
    }    

    @ApiOperation(value = "Deletar Cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, String>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(this.clienteService.delete(id), HttpStatus.ACCEPTED);
    }

}
