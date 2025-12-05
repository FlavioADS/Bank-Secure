package br.com.banksecure.service;

import com.banksecure.domain.Seguro;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.DAO.SeguroDAO;
import com.banksecure.service.SeguroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class SeguroServiceTest {

    @Mock
    SeguroDAO seguroDAO;

    @InjectMocks
    SeguroService seguroService;

    @Test
    void deveValidarSeguroValidoSemChamarDAO() {
        Seguro seguroValido = new Seguro(

                "Seguro Vida",
                "Cobertura total",
                new BigDecimal("20000"),
                new BigDecimal("100")
        );

        assertDoesNotThrow(() -> seguroService.validarSeguroDAO(seguroValido));

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeTituloNulo() {
        Seguro invalido = new Seguro(
                null,
                "Desc",
                new BigDecimal("100"),
                new BigDecimal("10")
        );

        EstruturaBancoException e = assertThrows(EstruturaBancoException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Registros fornecidos não coincidem com a tabela Seguro", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeDescricaoNula() {
        Seguro invalido = new Seguro(
                "Seguro",
                null,
                new BigDecimal("100"),
                new BigDecimal("10")
        );

        EstruturaBancoException e = assertThrows(EstruturaBancoException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Registros fornecidos não coincidem com a tabela Seguro", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeCoberturaNula() {
        Seguro invalido = new Seguro(
                "Seguro",
                "Teste",
                null,
                new BigDecimal("10")
        );

        EstruturaBancoException e = assertThrows(EstruturaBancoException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Registros fornecidos não coincidem com a tabela Seguro", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeValorBaseNulo() {
        Seguro invalido = new Seguro(
                "Seguro",
                "Teste",
                new BigDecimal("100"),
                null
        );

        EstruturaBancoException e = assertThrows(EstruturaBancoException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Registros fornecidos não coincidem com a tabela Seguro", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeTituloVazio() {
        Seguro invalido = new Seguro(
                "  ",
                "Lorem ipsum",
                new BigDecimal("100"),
                new BigDecimal("10")
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Dados invalidos: campos não podem ser vazios ou negativos.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeDescricaoVazia() {
        Seguro invalido = new Seguro(
                "Seguro",
                " ",
                new BigDecimal("100"),
                new BigDecimal("10")
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Dados invalidos: campos não podem ser vazios ou negativos.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroSeValoresNegativos() {
        Seguro invalido = new Seguro(
                "Seguro",
                "Teste",
                new BigDecimal("-1"),
                new BigDecimal("10")
        );

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class,
                () -> seguroService.validarSeguroDAO(invalido));

        assertEquals("Dados invalidos: campos não podem ser vazios ou negativos.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveValidarDeleteSeguroSemChamarDAO() {

        Seguro seguroValido = new Seguro(
                1L,
                "Seguro Vida",
                "Cobertura total",
                new BigDecimal("20000"),
                new BigDecimal("100")
        );

        assertDoesNotThrow(() -> seguroService.validarDeleteSeguro(seguroValido));

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroAoExcluirSeguroNulo() {

        DadosInvalidosException e = assertThrows(
                DadosInvalidosException.class,
                () -> seguroService.validarDeleteSeguro(null)
        );

        assertEquals("Seguro inválido para exclusão.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroAoExcluirSeguroSemId() {

        Seguro seguro = new Seguro(
                null,
                "Seguro Vida",
                "Cobertura total",
                new BigDecimal("20000"),
                new BigDecimal("100")
        );

        DadosInvalidosException e = assertThrows(
                DadosInvalidosException.class,
                () -> seguroService.validarDeleteSeguro(seguro)
        );

        assertEquals("ID inválido para exclusão.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroAoExcluirSeguroComIdNegativo() {

        Seguro seguro = new Seguro(
                -1L,
                "Seguro Vida",
                "Cobertura total",
                new BigDecimal("20000"),
                new BigDecimal("100")
        );

        DadosInvalidosException e = assertThrows(
                DadosInvalidosException.class,
                () -> seguroService.validarDeleteSeguro(seguro)
        );

        assertEquals("ID inválido para exclusão.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroAoExcluirSeguroSemTitulo() {

        Seguro seguro = new Seguro(
                1L,
                null,
                "Cobertura total",
                new BigDecimal("20000"),
                new BigDecimal("100")
        );

        DadosInvalidosException e = assertThrows(
                DadosInvalidosException.class,
                () -> seguroService.validarDeleteSeguro(seguro)
        );

        assertEquals("Título inválido para exclusão.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroAoExcluirSeguroComTituloVazio() {

        Seguro seguro = new Seguro(
                1L,
                "   ",
                "Cobertura total",
                new BigDecimal("20000"),
                new BigDecimal("100")
        );

        DadosInvalidosException e = assertThrows(
                DadosInvalidosException.class,
                () -> seguroService.validarDeleteSeguro(seguro)
        );

        assertEquals("Título inválido para exclusão.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }

    @Test
    void deveLancarErroAoExcluirSeguroComValorBaseInvalido() {

        Seguro seguro = new Seguro(
                1L,
                "Seguro Vida",
                "Cobertura total",
                new BigDecimal("20000"),
                BigDecimal.ZERO
        );

        DadosInvalidosException e = assertThrows(
                DadosInvalidosException.class,
                () -> seguroService.validarDeleteSeguro(seguro)
        );

        assertEquals("Valor base inválido para exclusão.", e.getMessage());

        verifyNoInteractions(seguroDAO);
    }
}
