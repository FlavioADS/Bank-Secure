package br.com.banksecure.service;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.service.ApoliceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
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
        apoliceDAO = mock(ApoliceDAO.class);
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
    void deveLancarErroQuandoValorFinalForNegativo() {
        Apolice apoliceInvalida = new Apolice(1L,1L,1L,new BigDecimal(-10),LocalDate.now(),LocalDate.now().plusYears(1),false);

        assertThrows(DadosInvalidosException.class, () -> service.validarApoliceDAO(apoliceInvalida));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroQuandoDataFimIgualDataInicio() {
        Apolice apoliceInvalida = new Apolice(1L,1L,1L,new BigDecimal(20000),LocalDate.now(),LocalDate.now(),false);

        assertThrows(DadosInvalidosException.class, () -> service.validarApoliceDAO(apoliceInvalida));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroComDataFimInvalida(){
        Apolice apoliceInvalida = new Apolice(1L,1L,1L,new BigDecimal(20000),LocalDate.now(),LocalDate.now().minusDays(1),false);

        assertThrows(DadosInvalidosException.class, () -> service.validarApoliceDAO(apoliceInvalida));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroQuandoDataInicioInvalida() {
        Apolice apoliceInvalida = new Apolice(1L,1L,1L,new BigDecimal(20000),LocalDate.now().minusDays(1),LocalDate.now(),false);

        assertThrows(DadosInvalidosException.class, () -> service.validarApoliceDAO(apoliceInvalida));
        verifyNoInteractions(apoliceDAO);

    }

    @Test
    void deveLancarErroAoRegistrarVendaComCamposNull(){
        assertThrows(DadosInvalidosException.class, () -> service.registrarVenda(null, 1L,1L,LocalDate.now(), LocalDate.now().plusYears(1)));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroAoRegistrarVendaComCamposNegativos(){
        assertThrows(DadosInvalidosException.class, () -> service.registrarVenda(-3L, 1L,1L, LocalDate.now(), LocalDate.now().plusYears(1)));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroAoRenovarApoliceComIdNulo(){
        assertThrows(DadosInvalidosException.class, () -> service.renovarApolice(null));
        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveRetornasTodasAsApolices(){
        Apolice apoliceMockada = new Apolice(
                1L, 1L, 1L, new BigDecimal("1500.00"),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1),
                false
        );
        List<Apolice> apolicesEsperadas = Collections.singletonList(apoliceMockada);

        try (MockedConstruction<ApoliceDAO> apoliceMockConstruction = Mockito.mockConstruction(ApoliceDAO.class, (mock, context) -> {

            when(mock.getAll()).thenReturn(apolicesEsperadas);

        })) {
            assertDoesNotThrow(() -> service.mostrarApolices(), "O serviço não deve lançar exceção quando há apólices.");
            ApoliceDAO constructedMock = apoliceMockConstruction.constructed().get(0);
            verify(constructedMock, times(1)).getAll();

        }
    }

    @Test
    void deveRetornarTodasAsApolicesRenovar(){
        Apolice apoliceMockada = new Apolice(
                1L, 1L, 1L, new BigDecimal("1500.00"),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                false
        );
        List<Apolice> apolicesEsperadas = Collections.singletonList(apoliceMockada);

        try (MockedConstruction<ApoliceDAO> apoliceMockConstruction = Mockito.mockConstruction(ApoliceDAO.class, (mock, context) -> {

            when(mock.getByDueDate()).thenReturn(apolicesEsperadas);

        })) {
            assertDoesNotThrow(() -> service.apolicesParaRenovar(), "O serviço não deve lançar exceção quando há apólices.");
            ApoliceDAO constructedMock = apoliceMockConstruction.constructed().get(0);
            verify(constructedMock, times(1)).getByDueDate();

        }
    }

    @Test
    void deveRetornarErroSeNaoTiverApolice(){

        List apolicesEsperadas = Collections.EMPTY_LIST;

        try (MockedConstruction<ApoliceDAO> apoliceMockConstruction = Mockito.mockConstruction(ApoliceDAO.class, (mock, context) -> {

            when(mock.getAll()).thenReturn(apolicesEsperadas);

        })) {
            assertThrows(DadosInvalidosException.class, () -> service.mostrarApolices());
            ApoliceDAO constructedMock = apoliceMockConstruction.constructed().get(0);
            verify(constructedMock, times(1)).getAll();

        }
    }

    @Test
    void deveRetornarErroSeNaoTiverApoliceRenovar(){
        List apolicesEsperadas = Collections.EMPTY_LIST;

        try (MockedConstruction<ApoliceDAO> apoliceMockConstruction = Mockito.mockConstruction(ApoliceDAO.class, (mock, context) -> {

            when(mock.getByDueDate()).thenReturn(apolicesEsperadas);

        })) {
            assertThrows(DadosInvalidosException.class, () -> service.apolicesParaRenovar());
            ApoliceDAO constructedMock = apoliceMockConstruction.constructed().get(0);
            verify(constructedMock, times(1)).getByDueDate();

        }
    }
}
