package com.tiagoperroni.estoque.controller;

import java.util.HashMap;
import java.util.List;

import com.tiagoperroni.estoque.dto.categoria.CategoriaRequest;
import com.tiagoperroni.estoque.dto.categoria.CategoriaResponse;
import com.tiagoperroni.estoque.service.CategoriaService;

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
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @ApiOperation(value = "Listar Todos")
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getAll() {
        return new ResponseEntity<>(this.categoriaService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Listar por Id")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.categoriaService.findByIdResponse(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Listar por Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CategoriaResponse>> getByNone(@PathVariable("nome") String nome) {
        return new ResponseEntity<>(this.categoriaService.getByName(nome), HttpStatus.OK);
    }

    @ApiOperation(value = "Salvar Categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@RequestBody CategoriaRequest request) {
        return new ResponseEntity<>(this.categoriaService.create(request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Categoria")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(@PathVariable("id") Integer id,
            @RequestBody CategoriaRequest request) {
        return new ResponseEntity<>(this.categoriaService.update(id, request), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Deletar Categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.categoriaService.delete(id), HttpStatus.ACCEPTED);
    }

}
