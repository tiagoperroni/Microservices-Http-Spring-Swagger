package com.tiagoperroni.estoque.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.tiagoperroni.estoque.dto.produto.ProdutoRequest;
import com.tiagoperroni.estoque.dto.produto.ProdutoResponse;
import com.tiagoperroni.estoque.exceptions.ModelNotFoundException;
import com.tiagoperroni.estoque.exceptions.ValidationsException;
import com.tiagoperroni.estoque.model.Produto;
import com.tiagoperroni.estoque.repository.ProdutoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Value("${secret.key.token}")
    private String TOKEN;

    public List<ProdutoResponse> getAll() {      
        return this.produtoRepository.findAll()
                .stream().map(ProdutoResponse::convert).collect(Collectors.toList());
    }

    public ProdutoResponse findById(Integer id) {
        System.out.println(id);
        var produto = this.produtoRepository.findById(id)
                .orElseThrow(
                        () -> new ModelNotFoundException(String.format("Produto de id %s não foi encontrado.", id)));
        return ProdutoResponse.convert(produto);
    }

    public List<ProdutoResponse> findByName(String nome) {
        List<Produto> produtos = this.produtoRepository.findByNomeIgnoreCaseContaining(nome)
                .orElse(null);
        if (produtos.size() == 0) {
            throw new ModelNotFoundException(String.format("Não foi encontrado produtos com '%s'.", nome));
        }
        return produtos.stream().map(ProdutoResponse::convert).collect(Collectors.toList());
    }

    public List<ProdutoResponse> findByCategoriaId(Integer categoriaId) {
        var produtos = this.produtoRepository.findByCategoriaId(categoriaId);
        return produtos.stream().map(ProdutoResponse::convert).collect(Collectors.toList());
    }

    public ProdutoResponse create(ProdutoRequest request) {
        this.validDataFields(request);
        var categoria = this.categoriaService.findById(request.getCategoriaId());
        var produto = Produto.convert(request);
        produto.setCategoria(categoria);
        produto.setDataCriacao(LocalDateTime.now());
        return ProdutoResponse.convert(this.produtoRepository.save(produto));
    }

    public ProdutoResponse update(Integer id, ProdutoRequest request) {
        this.validDataFields(request);
        var produto = Produto.convertResponse(this.findById(id));
        var categoria = this.categoriaService.findById(request.getCategoriaId());
        BeanUtils.copyProperties(request, produto, "id", "dataCriacao", "categoria");      
        produto.setCategoria(categoria);
        return ProdutoResponse.convert(this.produtoRepository.save(produto));

    }

    public void updateStock(Integer id, Integer quantidade) {
        var prod = Produto.convertResponse(this.findById(id));
        prod.setQuantidade(prod.getQuantidade() - quantidade);
        this.produtoRepository.save(prod);
    }

    public HashMap<String, Object> delete(Integer id, String token) {
        this.validateToken(token);
        this.findById(id);
        this.produtoRepository.deleteById(id);
        var resposta = new HashMap<String, Object>();
        resposta.put("msg", String.format("O Produto de id %s foi deletado.", id));
        return resposta;
     }

     public void validDataFields(ProdutoRequest request) {
        if (request.getNome() == null) {
            throw new ValidationsException("O campo 'Nome' deve ser preenchido.");
        }
        if (request.getPreco() == null) {
            throw new ValidationsException("O campo 'Preço' deve ser preenchido.");
        }

        if (request.getQuantidade() == null) {
            throw new ValidationsException("O campo 'Quantidade' deve ser preenchido.");
        }

        if (request.getCategoriaId() == null) {
            throw new ValidationsException("O campo 'Categoria' deve ser preenchido.");
        }
        
     }

     public void validateToken(String token) {
        if (token == null) {
            throw new ValidationsException("O token precisa ser fornecido.");
        }
        System.out.println(token + " , " + TOKEN);
         if (!token.equals(TOKEN)) {
             throw new ValidationsException("O token fornecido é inválido.");
         }
     }
}
