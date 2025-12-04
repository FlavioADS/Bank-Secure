package br.com.banksecure.service;

import com.banksecure.domain.Seguro;
import com.banksecure.service.CotacaoService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CotacaoServiceTest {

    @Test
    void setTaxaPadrao() {
        Seguro seguro = new Seguro();
        seguro.setValorBase(BigDecimal.valueOf(70));
        BigDecimal valorInicial = seguro.getValorBase();

        BigDecimal taxaPadrao = (valorInicial.multiply(BigDecimal.valueOf(0.05))).add(valorInicial);

        assertEquals(BigDecimal.valueOf(73.5), taxaPadrao.setScale(1, RoundingMode.HALF_UP));
    }

    @Test
    void bonusIdade() {

        LocalDate anoNasc = LocalDate.parse("1960-10-23");

        CotacaoService cotacao = new CotacaoService();
        Boolean deuCerto = cotacao.bonusIdade(anoNasc);

        assertEquals(Boolean.TRUE, deuCerto);
    }

    @Test
    void taxaRisco() {

        BigDecimal valorPremio = BigDecimal.valueOf(70.50);

        BigDecimal taxaRisco = (valorPremio.multiply(BigDecimal.valueOf(1.10))).add(valorPremio);

        assertEquals(BigDecimal.valueOf(148.05), taxaRisco);
    }
}