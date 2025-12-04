package com.banksecure.service;


import com.banksecure.domain.Seguro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class CotacaoService {

    Seguro seguro = new Seguro();
    BigDecimal valorIncial = BigDecimal.valueOf(100);

    public BigDecimal setTaxaPadrao(){

        BigDecimal taxaPadrao = (valorIncial.multiply(BigDecimal.valueOf(0.05))).add(valorIncial);

        return taxaPadrao;
    }

    public boolean bonusIdade(LocalDate anoNasc){
        LocalDate dataAtual = LocalDate.now();

        int idade = Period.between(anoNasc, dataAtual).getYears();

        if (idade >= 60){
            return true;
        }else{
            return false;
        }
    }

    public BigDecimal taxaRisco(BigDecimal valorPremio){

        BigDecimal taxaRisco = (valorPremio.multiply(BigDecimal.valueOf(1.10))).add(valorPremio);

        return taxaRisco;
    }
}
