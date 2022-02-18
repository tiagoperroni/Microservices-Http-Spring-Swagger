package com.tiagoperroni.pedidos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tiagoperroni.pedidos.dto.PedidoRequest;
import com.tiagoperroni.pedidos.dto.PedidoRequestList;
import com.tiagoperroni.pedidos.exceptions.EstoqueValidationException;
import com.tiagoperroni.pedidos.model.Cliente;
import com.tiagoperroni.pedidos.model.Pedidos;
import com.tiagoperroni.pedidos.model.Produto;
import com.tiagoperroni.pedidos.repository.CategoriaRepository;
import com.tiagoperroni.pedidos.repository.ClienteRepository;
import com.tiagoperroni.pedidos.repository.PedidosRepository;
import com.tiagoperroni.pedidos.repository.ProdutoRepository;
import com.tiagoperroni.pedidos.utils.RequisicaoCliente;
import com.tiagoperroni.pedidos.utils.RequisicaoEstoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private RequisicaoCliente requisicaoCliente;

    @Autowired
    private RequisicaoEstoque requisicaoEstoque;

    public List<Pedidos> getAll() {
        return this.pedidosRepository.findAll();
    }

    public List<Pedidos> findByCpf(String cpf) {
        this.getClienteByCpf(cpf);
        List<Pedidos> listaPedidos = new ArrayList<>();
        this.getAll().forEach(pedido -> {
            if (pedido.getCliente().getCpf().equals(cpf)) {
                listaPedidos.add(pedido);
            }          
        });  
        return listaPedidos;        
    }

    public Pedidos create(PedidoRequest request) {
        var pedido = new Pedidos();
        List<Produto> listProdutos = new ArrayList<>();
        pedido.setNumero(UUID.randomUUID().toString());
        pedido.setDataPedido(LocalDateTime.now());

        var cliente = getClienteByCpf(request.getClienteCpf());
      
        pedido.setCliente(cliente);
        validarEstoque(request);

        for (PedidoRequestList prod : request.getProdutos()) {
            var produtos = requisicaoEstoque.getProdutoById(prod.getProdutoId());
            produtos.setQuantidade(prod.getQuantidade());
            listProdutos.add(produtos);
            for (Produto prods : listProdutos) {
                this.categoriaRepository.save(prods.getCategoria());
                this.produtoRepository.save(prods);
            }
        }

        pedido.setProdutos(listProdutos);  
            
        pedido.calculaTotal(listProdutos);       
        this.atualizaEstoque(request);
        pedido.setValorTotal(pedido.getValorTotal().doubleValue());
        return this.pedidosRepository.save(pedido);

    }

    public void validarEstoque(PedidoRequest request) {
        for (PedidoRequestList prods : request.getProdutos()) {
            var produtos = this.requisicaoEstoque.getProdutoById(prods.getProdutoId());
            if (produtos.getQuantidade() < prods.getQuantidade()) {
                throw new EstoqueValidationException(
                        String.format("O produto '%s' não possui estoque suficiente. Estoque atual %s",
                                produtos.getNome(), produtos.getQuantidade()));
            }
        }
    }

    public void atualizaEstoque(PedidoRequest request) {
        for (PedidoRequestList produtos : request.getProdutos()) {
            this.requisicaoEstoque.updateEstoque(produtos.getProdutoId(), produtos.getQuantidade());
        }
    }

    public Cliente getClienteByCpf(String cpf) {        
        var clientePedido = this.clienteRepository.findByCpf(cpf).orElse(null);
        if (clientePedido == null) {
            var cliente = this.requisicaoCliente.getClienteByCpf(cpf);
            return this.clienteRepository.save(cliente);
        }      
        return clientePedido;
    }

    public List<Cliente> getAllCliente() {
        return this.clienteRepository.findAll();
    }
}
