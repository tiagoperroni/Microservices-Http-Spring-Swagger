package com.tiagoperroni.estoque.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.tiagoperroni.estoque.dto.produto.ProdutoRequest;
import com.tiagoperroni.estoque.dto.produto.ProdutoResponse;
import com.tiagoperroni.estoque.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @ApiOperation(value = "Listar Todos")
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> findAll() {
        return new ResponseEntity<>(this.produtoService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar por Id")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.produtoService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar por Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoResponse>> getByNome(@PathVariable("nome") String nome) {
        return new ResponseEntity<>(this.produtoService.findByName(nome), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar por Categoria")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponse>> getByNome(@PathVariable("categoriaId") Integer categoriaId) {
        return new ResponseEntity<>(this.produtoService.findByCategoriaId(categoriaId), HttpStatus.OK);
    }

    @ApiOperation(value = "Salvar Produto")
    @PostMapping
    public ResponseEntity<ProdutoResponse> create(@Valid @RequestBody ProdutoRequest request) {
        return new ResponseEntity<>(this.produtoService.create(request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable Integer id, @RequestBody ProdutoRequest request) {
        return new ResponseEntity<>(this.produtoService.update(id, request), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Atualizar Estoque")
    @PutMapping("atualizaEstoque/{id}/{quantidade}")
    public ResponseEntity<Void> updateEstoque(@PathVariable("id") Integer id,
            @PathVariable("quantidade") Integer quantidade) {
        this.produtoService.updateStock(id, quantidade);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Atualizar Produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> update(@PathVariable Integer id,
            @RequestHeader(value = "token") String token) {
        return new ResponseEntity<>(this.produtoService.delete(id, token), HttpStatus.ACCEPTED);
    }
}
