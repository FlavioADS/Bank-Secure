package com.banksecure.service;

import com.banksecure.domain.Apolice;
import com.banksecure.domain.Cliente;
import com.banksecure.domain.Funcionario;
import com.banksecure.domain.Seguro;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.infra.DAO.FuncionarioDAO;
import com.banksecure.infra.DAO.SeguroDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ApoliceService {

    private ApoliceDAO apoliceDAO;

    public void validarApoliceDAO(Apolice apolice) {

        if (apolice == null) {
            throw new IllegalArgumentException("Apolice vazia!");
        }

        if (apolice.getCliente_id() == null ||
                apolice.getSeguro_id() == null ||
                apolice.getFuncionario_id() == null ||
                apolice.getValorFinal() == null ||
                apolice.getDataInicio() == null ||
                apolice.getDataFim() == null) {

            throw new DadosInvalidosException("Dados da apólice incompleto");
        }

        if (apolice.getValorFinal().compareTo(BigDecimal.ZERO) == -1){
            throw new DadosInvalidosException("Valor final da apólice inválido");
        }

        if (apolice.getDataInicio().isBefore(LocalDate.now()) ||
                apolice.getDataFim().isBefore(apolice.getDataInicio()) || apolice.getDataFim().isEqual(LocalDate.now())) {
            throw new DadosInvalidosException("Data da apólice inválida");
        }
    }

    public void registrarVenda(Long idSeguro, Long idCliente, Long idFuncionario, LocalDate dataInicio, LocalDate dataFim) {
        if (idSeguro == null ||  idCliente == null || idFuncionario == null) {
            throw new DadosInvalidosException("Dados incompletos para registrar venda!");
        }

        if (idSeguro <= 0 ||  idCliente <= 0 || idFuncionario <= 0) {
            throw new DadosInvalidosException("IDs invalidos para registrar venda!");
        }

        apoliceDAO = new ApoliceDAO();
        SeguroDAO seguroDAO = new SeguroDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        CotacaoService  cotacaoService = new CotacaoService();

        Seguro seguro = seguroDAO.getById(idSeguro);
        Cliente cliente = clienteDAO.getById(idCliente);


        BigDecimal valorComTaxa = cotacaoService.setTaxaPadrao(seguro.getValorBase());
        if (cotacaoService.bonusIdade(cliente.getDataNascimento())){
            valorComTaxa = valorComTaxa.add(BigDecimal.valueOf(100));
        }

        BigDecimal valorFinal = cotacaoService.taxaRisco(valorComTaxa);

        Apolice apolice = new Apolice(idCliente, idSeguro, idFuncionario, valorFinal, dataInicio, dataFim, false);
        apoliceDAO.save(apolice);
    }

    public void renovarApolice(Long idApolice){
        if (idApolice == null) {
            throw new DadosInvalidosException("Apolice não encontrada para renovação!");
        }

        apoliceDAO = new ApoliceDAO();
        Apolice apolice = apoliceDAO.getById(idApolice);

        LocalDate novaDataFim = apolice.getDataFim().plusYears(1);

        apoliceDAO.renovarApolice(apolice, apolice.getValorFinal(), novaDataFim);
    }

    public void apolicesParaRenovar(){
        apoliceDAO = new ApoliceDAO();
        List<Apolice> apolices = apoliceDAO.getByDueDate();

        if (apolices.isEmpty()){
            throw new DadosInvalidosException("Não existe apolice para renovar!");
        }

        System.out.println("Apolices a vencer nos próximos 30 dias");
        for (Apolice ap:apolices){
            System.out.println(ap.mostrarDadosDaApolice());
        }
    }


    public void mostrarApolices(){
        apoliceDAO = new ApoliceDAO();
        List<Apolice> apolices = apoliceDAO.getAll();

        if (apolices.isEmpty()){
            throw new DadosInvalidosException("Nenhuma apólice encontrada");
        }

        for (Apolice ap:apolices){
            System.out.println(ap.mostrarDadosDaApolice());
        }
    }
}
