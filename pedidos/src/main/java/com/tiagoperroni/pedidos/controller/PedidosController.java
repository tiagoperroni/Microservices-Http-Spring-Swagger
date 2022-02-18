package com.tiagoperroni.pedidos.controller;

import java.util.List;

import com.tiagoperroni.pedidos.dto.PedidoRequest;
import com.tiagoperroni.pedidos.model.Cliente;
import com.tiagoperroni.pedidos.model.Pedidos;
import com.tiagoperroni.pedidos.service.PedidosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {
    
    @Autowired
    private PedidosService pedidosService;

    @ApiOperation(value = "Listar Todos Pedidos")
    @GetMapping
    public ResponseEntity<List<Pedidos>> getAll() {
        return new ResponseEntity<>(this.pedidosService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Listar Todos Clientes")
    @GetMapping("/cliente")
    public ResponseEntity<List<Cliente>> getAllCliente() {
        return new ResponseEntity<>(this.pedidosService.getAllCliente(), HttpStatus.OK);
    }

    @ApiOperation(value = "Listar Pedidos por CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<Pedidos>> getByCpf(@PathVariable("cpf") String cpf ) {
        return new ResponseEntity<>(this.pedidosService.findByCpf(cpf), HttpStatus.OK);
    }

    @ApiOperation(value = "Salvar Pedido")
    @PostMapping
    public ResponseEntity<Pedidos> create(@RequestBody PedidoRequest request) {
        return new ResponseEntity<>(pedidosService.create(request), HttpStatus.CREATED);
    }
}
