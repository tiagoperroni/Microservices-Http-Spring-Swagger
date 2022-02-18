package com.tiagoperroni.clientes.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.tiagoperroni.clientes.dto.ClienteRequest;
import com.tiagoperroni.clientes.dto.ClienteResponse;
import com.tiagoperroni.clientes.exceptions.ClienteNotFoundException;
import com.tiagoperroni.clientes.exceptions.ValidationDataException;
import com.tiagoperroni.clientes.model.Cliente;
import com.tiagoperroni.clientes.repository.ClienteRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponse> getAll() {
        return this.clienteRepository.findAll()
                .stream().map(ClienteResponse::convertCliente)
                .collect(Collectors.toList());
    }

    public ClienteResponse findById(Long id) {
        return ClienteResponse.convertCliente(this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(
                        String.format("O cliente de id %s não foi encontrado.", id))));
    }

    public Cliente findByCPFInternal(String cpf) {
        return this.clienteRepository.findByCpf(cpf).orElse(null);
    }

    public ClienteResponse findByCPF(String cpf) {
        var cliente = this.clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNotFoundException(
                        String.format("Cliente com cpf '%s' não foi encontrado.", cpf)));
        return ClienteResponse.convertCliente(cliente);
    }

    public List<ClienteResponse> findByNome(String nome) {
        var cliente = this.clienteRepository.findByNomeIgnoreCaseContaining(nome);
        if (cliente.isEmpty()) {
            throw new ClienteNotFoundException(String.format("Não foram encontrados clientes com '%s'.", nome));
        }

        return cliente.stream().map(ClienteResponse::convertCliente).collect(Collectors.toList());
    }

    public ClienteResponse create(ClienteRequest request) {
        this.validaDadosRecebidos(request);
        this.validaCPF(request);
        request.setDataCadastro(LocalDateTime.now());
        return ClienteResponse
                .convertCliente(this.clienteRepository.save(Cliente.convertRequest(request)));
    }

    public ClienteResponse update(Long id, ClienteRequest request) {
        var cliente = Cliente.convertResponse(this.findById(id));
        this.validaCPFUpdate(id, request);
        BeanUtils.copyProperties(request, cliente, "id", "dataCadastro");
        this.clienteRepository.save(cliente);
        return ClienteResponse.convertCliente(cliente);

    }

    public HashMap<String, String> delete(Long id) {
        this.findById(id);
        this.clienteRepository.deleteById(id);
        var resposta = new HashMap<String, String>();
        resposta.put("Mensagem", "Cliente deletado com sucesso.");
        return resposta;
    }

    public void validaDadosRecebidos(ClienteRequest request) {
        if (request.getNome() == null) {
            throw new ValidationDataException("O campo Nome deve ser informado.");
        }
        if (request.getEmail() == null) {
            throw new ValidationDataException("O campo E-mail deve ser informado.");
        }
        if (request.getCpf() == null) {
            throw new ValidationDataException("O campo CPF deve ser informado.");
        }
        if (request.getDataNascimento() == null) {
            throw new ValidationDataException("O campo Data de Nascimento deve ser informado.");
        }
    }

    public void validaCPF(ClienteRequest request) {
        if (request.getCpf().length() != 11 && request.getCpf().length() != 14) {
            throw new ValidationDataException("O CPF precisa conter 11 ou 14 caracteres.");
        }
        if (this.findByCPFInternal(request.getCpf()) != null) {
            throw new ValidationDataException("O CPF informado já existe na base de dados.");
        }
    }

    public void validaCPFUpdate(Long id, ClienteRequest request) {
        var cliente = this.findByCPFInternal(request.getCpf());
        if (request.getCpf().length() != 11 && request.getCpf().length() != 14) {
            throw new ValidationDataException("O CPF precisa conter 11 ou 14 caracteres.");
        }
        if (cliente != null && cliente.getId() != id) {
            throw new ValidationDataException("O CPF informado já existe na base de dados.");
        }
    }

}
