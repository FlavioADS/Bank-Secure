package com.banksecure.service;

import com.banksecure.domain.Cliente;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ClienteDAO;

import java.time.LocalDate;

public class ClienteService {

    public void validarClienteDAO(Cliente cliente) {

        ClienteDAO clienteDAO = new ClienteDAO();

        if(cliente.getNome() == null || cliente.getNome().trim().isEmpty()
                || cliente.getCpf() == null
                || cliente.getCpf().trim().isEmpty()
                || cliente.getDataNascimento() == null) {
            throw new DadosInvalidosException("Dados invalidos: os campos do Cliente não podem ser vazios ou negativos.");
        }

        if(cliente.getDataNascimento().isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("Data de nascimento inválida: não pode ser no futuro.");
        }

        if(cliente.getCpf().trim().isEmpty() || cliente.getCpf().length() != 11 || !cliente.getCpf().matches("\\d{11}")) {
            throw new DadosInvalidosException("CPF inválido: O cpf deve ter 11 digitos.");
        }
    }

    public void validarDeleteCliente(Cliente cliente) {

        if (cliente == null) {
            throw new DadosInvalidosException("Cliente inválido para exclusão.");
        }

        if (cliente.getId() == null || cliente.getId() <= 0) {
            throw new DadosInvalidosException("ID inválido para exclusão.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty() || cliente.getCpf().length() != 11) {
            throw new DadosInvalidosException("CPF inválido informado para exclusão.");
        }
    }


}
