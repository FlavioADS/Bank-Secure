package br.com.banksecure.service;

import com.banksecure.domain.Cliente;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    ClienteDAO clienteDAO;

    @InjectMocks
    ClienteService clienteService;

    @Test
    void deveValidarClienteSemAcessarDAO() {

        Cliente clienteValido = new Cliente(
                1L,
                "Flávio",
                "12345678901",
                LocalDate.of(2000, 1, 10)
        );

        assertDoesNotThrow(() -> clienteService.validarClienteDAO(clienteValido));

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveLancarErroSeNomeForVazio() {

        Cliente clienteInvalido = new Cliente(
                "",
                "12345678901",
                LocalDate.of(2000, 1, 10)
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> clienteService.validarClienteDAO(clienteInvalido));

        assertEquals("Dados invalidos: os campos do Cliente não podem ser vazios ou negativos.", e.getMessage());

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveLancarErroCpfInvalido() {
        Cliente clienteInvalido = new Cliente(
                "Flávio",
                "123ABC",
                LocalDate.of(2000, 1, 10)
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> clienteService.validarClienteDAO(clienteInvalido));

        assertEquals("CPF inválido: O cpf deve ter 11 digitos.", e.getMessage());

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveLancarErroParaCpfComTamanhoDiferente() {
        Cliente clienteInvalido = new Cliente(
                "Flávio",
                "123",
                LocalDate.of(2000, 1, 10)
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> clienteService.validarClienteDAO(clienteInvalido));

        assertEquals("CPF inválido: O cpf deve ter 11 digitos.", e.getMessage());

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveLancarErroNascimentoFuturo() {
        Cliente clienteInvalido = new Cliente(
                "Flávio",
                "12345678901",
                LocalDate.now().plusDays(1)
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> clienteService.validarClienteDAO(clienteInvalido));

        assertEquals("Data de nascimento inválida: não pode ser no futuro.", e.getMessage());

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveValidarDeleteComSucesso() {
        Cliente cliente = new Cliente(1L, "Flávio", "08103545485", LocalDate.of(1990, 1, 1));

        assertDoesNotThrow(() -> clienteService.validarDeleteCliente(cliente));
        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveFalharAoDeletarClienteNull() {
        assertThrows(DadosInvalidosException.class,
                () -> clienteService.validarDeleteCliente(null));

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveFalharAoDeletarClienteSemId() {
        Cliente cliente = new Cliente("Flávio", "08103545485", LocalDate.of(1990, 1, 1));

        assertThrows(DadosInvalidosException.class,
                () -> clienteService.validarDeleteCliente(cliente));

        verifyNoInteractions(clienteDAO);
    }

    @Test
    void deveFalharCpfInvalidoParaDelete() {
        Cliente cliente = new Cliente(1L, "Flávio", "123", LocalDate.of(1990, 1, 1));

        assertThrows(DadosInvalidosException.class,
                () -> clienteService.validarDeleteCliente(cliente));

        verifyNoInteractions(clienteDAO);
    }
}


