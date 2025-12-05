package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Seguro;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.SeguroDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeguroDAOtest {

    @Mock
    private SeguroDAO seguroDAO;

    @BeforeEach
    public void setup(){
        seguroDAO = new SeguroDAO();
        seguroDAO.iniciaTabelas();
    }

    @Test
    void deveInicializarSeguro(){
        SeguroDAO dao = new SeguroDAO();
        dao.iniciaTabelas();
    }

    @Test
    void deveSalvarUmSeguro(){
        Seguro novoSeguro = new Seguro("Seguro de Automovél", "Cobertura para danos ao veiculo: FordKa, ABC-1234",
                new BigDecimal("20000"),
                new BigDecimal("100"));
        SeguroDAO dao = new SeguroDAO();
        dao.save(novoSeguro);
    }

    @Test
    void deveMostrarOsSegurosSalvos(){
        SeguroDAO dao = new SeguroDAO();
        System.out.println(seguroDAO.getAll());
    }

    @Test
    void deveRetornarQuantidadeDeSegurosSalvos(){
        SeguroDAO dao = new SeguroDAO();
        assertEquals(3, seguroDAO.getAll().size());
    }

    @Test
    void deveRetornarErroDadosInvalidos() {
        SeguroDAO dao = new SeguroDAO();

        Seguro seguroInvalido = new Seguro(null,
                "",
                "",
                new BigDecimal("0"),
                new BigDecimal("-100"));

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> {
            dao.save(seguroInvalido);
        });

        assertEquals("Dados invalidos: campos não podem ser vazios ou negativos.", e.getMessage());
    }

    @Test
    void deveFalharAoDeletarSeguroSemId() {
        Seguro seguroSemId = new  Seguro("Teste", "desc",
                new BigDecimal("10"), new BigDecimal("2"));

        assertThrows(DadosInvalidosException.class, () -> seguroDAO.delete(seguroSemId));
    }

    @Test
    void deveFalharAoDeletarSeguroInexistente() {
        Seguro seguro = new Seguro("Seguro Viagem", "desc",
                new BigDecimal("1000"), new BigDecimal("40"));

        seguroDAO.save(seguro);

        Seguro seguroComIdIncorreto = new  Seguro("Outro", "desc", new BigDecimal("1"), new BigDecimal("1"));

        seguroComIdIncorreto.setId(999L);

        assertThrows(DadosInvalidosException.class,
                () -> seguroDAO.delete(seguroComIdIncorreto));
    }
}

