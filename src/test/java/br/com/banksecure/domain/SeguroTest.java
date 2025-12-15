package br.com.banksecure.domain;

import com.banksecure.domain.Seguro;
import com.banksecure.enums.TipoDeSeguroEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLOutput;

public class SeguroTest {
    @Test
    void deveMostrarDadosSeguro() {
        Seguro seguro = new Seguro(1L, TipoDeSeguroEnum.SEGURO_AUTO, "d", new BigDecimal("1000"), new BigDecimal("100"));
        System.out.println(seguro.mostrarDadosDoSeguro());
    }
}
