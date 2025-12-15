package br.com.banksecure.service;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.service.ApoliceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApoliceServiceTest {

    private ApoliceDAO apoliceDAO;
    private ApoliceService service;

    @BeforeEach
    void setup() {
        apoliceDAO = Mockito.mock(ApoliceDAO.class);
        service = new ApoliceService();
    }

    private Apolice criarApoliceValida() {
        return new Apolice(
                1L,
                1L,
                1L,
                new BigDecimal("1000"),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                false
        );
    }

    @Test
    void deveValidarApoliceValidaSemErro() {
        Apolice apolice = criarApoliceValida();

        assertDoesNotThrow(() -> service.validarApoliceDAO(apolice));

        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroQuandoApoliceNull() {
        assertThrows(IllegalArgumentException.class, () -> service.validarApoliceDAO(null));

        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroQuandoCamposObrigatoriosNull() {
        Apolice apoliceInvalida = new Apolice(
                null,
                1L,
                1L,
                new BigDecimal("1000"),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                false
        );

        assertThrows(DadosInvalidosException.class, () -> service.validarApoliceDAO(apoliceInvalida));

        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroAoRegistrarVendaComCamposNull(){
        assertThrows(DadosInvalidosException.class, () -> service.registrarVenda(null, null,null, null, null ));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroAoRegistrarVendaComCamposNegativos(){
        assertThrows(DadosInvalidosException.class, () -> service.registrarVenda(-3L, 1L,1L,  LocalDate.now(), LocalDate.of(2026,12, 21) ));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroAoRenovarApoliceComIdNulo(){
        assertThrows(DadosInvalidosException.class, () -> service.renovarApolice(null));
        verifyNoInteractions(apoliceDAO);
    }
}
