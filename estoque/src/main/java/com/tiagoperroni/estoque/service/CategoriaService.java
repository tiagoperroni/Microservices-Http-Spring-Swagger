package com.tiagoperroni.estoque.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.tiagoperroni.estoque.dto.categoria.CategoriaRequest;
import com.tiagoperroni.estoque.dto.categoria.CategoriaResponse;
import com.tiagoperroni.estoque.dto.produto.ProdutoResponse;
import com.tiagoperroni.estoque.exceptions.ModelNotFoundException;
import com.tiagoperroni.estoque.exceptions.ValidationsException;
import com.tiagoperroni.estoque.model.Categoria;
import com.tiagoperroni.estoque.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoService produtoService;

    public CategoriaResponse findByIdResponse(Integer id) {
        return CategoriaResponse.convert(this.categoriaRepository
                .findById(id).orElseThrow(() -> new ModelNotFoundException(
                        String.format("A categoria de id %s não foi encontrada.", id))));

    }

    public Categoria findById(Integer id) {
        return this.categoriaRepository
                .findById(id).orElseThrow(() -> new ModelNotFoundException(
                        String.format("A categoria de id %s não foi encontrada.", id)));

    }

    public List<CategoriaResponse> getAll() {
        return this.categoriaRepository.findAll().stream()
                .map(CategoriaResponse::convert).collect(Collectors.toList());
    }

    public List<CategoriaResponse> getByName(String nome) {
        var categorias = this.categoriaRepository.findByNomeIgnoreCaseContaining(nome).orElse(null);
        if (categorias.size() == 0) {
            throw new ModelNotFoundException(String.format("Não foram encontradas Categorias com '%s'.", nome));
        }
        return categorias.stream().map(CategoriaResponse::convert).collect(Collectors.toList());

    }

    public CategoriaResponse create(CategoriaRequest categoriaRequest) {
        this.validationData(categoriaRequest);
        var categoria = Categoria.convert(categoriaRequest);
        categoria.setDataCriacao(LocalDateTime.now());
        return CategoriaResponse.convert(this.categoriaRepository.save(categoria));
    }

    public CategoriaResponse update(Integer id, CategoriaRequest request) {
        this.validationData(request);
        var categoria = this.findById(id);
        categoria.setNome(request.getNome());
        return CategoriaResponse.convert(this.categoriaRepository.save(categoria));
    }

    public HashMap<String, Object> delete(Integer id) {
        this.findById(id);
        var produtos = this.produtoService.getAll();
        for (ProdutoResponse list : produtos) {
            if (list.getCategoria().getId() == id) {
                throw new ValidationsException(String
                .format("A Categoria de id %s não pode ser deletada pois existem produtos cadastrados", id));
        }
    }
        this.categoriaRepository.deleteById(id);
        var resposta = new HashMap<String, Object>();
        resposta.put("msg", String.format("Categoria de id %s deletado com sucesso.", id));
        return resposta;
    }

    public void validationData(CategoriaRequest request) {
        if (request.getNome() == null) {
            throw new ValidationsException("O campo nome deve ser preenchido.");
        }
    }
}
