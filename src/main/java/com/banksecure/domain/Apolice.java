package com.banksecure.domain;

import com.banksecure.enums.TipoDeSeguroEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Apolice {

    private Long id;
    private Long cliente_id;
    private Long seguro_id;
    private Long funcionario_id;
    private BigDecimal valorFinal;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean renovada;
    private String nomeCliente;
    private TipoDeSeguroEnum nomeSeguro;
    private String nomeFuncionario;

    public Apolice(Long id, Long cliente_id, Long seguro_id, Long funcionario_id, BigDecimal valorFinal, LocalDate dataInicio, LocalDate dataFim, boolean renovada) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.seguro_id = seguro_id;
        this.funcionario_id = funcionario_id;
        this.valorFinal = valorFinal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.renovada = renovada;
    }

    public Apolice(Long cliente_id, Long seguro_id, Long funcionario_id, BigDecimal valorFinal, LocalDate dataInicio, LocalDate dataFim, boolean renovada) {
        this.cliente_id = cliente_id;
        this.seguro_id = seguro_id;
        this.funcionario_id = funcionario_id;
        this.valorFinal = valorFinal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.renovada = renovada;
    }

    public Apolice() {
    }

    public boolean isRenovada() {
        return renovada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public Long getSeguro_id() {
        return seguro_id;
    }

    public Long getFuncionario_id() {
        return funcionario_id;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public TipoDeSeguroEnum getNomeSeguro() {
        return nomeSeguro;
    }

    public void setNomeSeguro(TipoDeSeguroEnum nomeSeguro) {
        this.nomeSeguro = nomeSeguro;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }
    public void setSeguro_id(Long seguro_id) {
        this.seguro_id = seguro_id;
    }
    public void setFuncionario_id(Long funcionario_id) {
        this.funcionario_id = funcionario_id;
    }
    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Cliente: ").append(getNomeCliente()).append("\n");
        sb.append("Seguro: ").append(getNomeSeguro()).append("\n");
        sb.append("Funcionario: ").append(getNomeFuncionario()).append("\n");
        sb.append("Valor: ").append(valorFinal).append("\n");
        sb.append("Data inicio: ").append(dataInicio).append("\n");
        sb.append("Data fim: ").append(dataFim).append("\n");
        sb.append("Renovada: ").append(renovada).append("\n");
        sb.append("=========================\n");
        return sb.toString();
    }

    public String mostrarDadosDaApolice(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder dados = new StringBuilder();
        dados.append("================== Dados da apolice ===================\n");
        dados.append("Apolice ID: " + id + "\n");
        dados.append("Cliente: " + getNomeCliente() + "\n");
        dados.append("Seguro: " + getNomeSeguro() + "\n");
        dados.append("Funcionario: " + getNomeFuncionario()+ "\n");
        dados.append("Valor Final: " + valorFinal + "\n");
        dados.append("Data de Inicio da apolice: ")
                .append(dataInicio.format(formatter))
                .append("\n");
        dados.append("Data de fim da apolice: ")
                .append(dataFim.format(formatter))
                .append("\n");
        dados.append("Renovada: ").append(renovada).append("\n");

        return dados.toString();
    }

    public String renovarDadosDaApolice(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder dados = new StringBuilder();
        dados.append("================== Dados da apolice ===================\n");
        dados.append("Apolice ID: " + id + "\n");
        dados.append("Renovada: ").append(renovada).append("\n");

        return dados.toString();
    }
}
