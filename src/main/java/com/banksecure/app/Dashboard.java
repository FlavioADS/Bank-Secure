package com.banksecure.app;

import com.banksecure.domain.Apolice;
import com.banksecure.domain.Funcionario;
import com.banksecure.domain.Seguro;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.infra.DAO.FuncionarioDAO;
import com.banksecure.infra.DAO.SeguroDAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard {

    private Map<Integer, Integer> qtdApolicesPorSeguro = new HashMap<>();
    private Map<Integer, BigDecimal> valorTotalPorSeguro = new HashMap<>();
    private Map<Integer, Integer> qtdApolicesPorFuncionario = new HashMap<>();
    private Map<Integer, BigDecimal> valorTotalPorFuncionario = new HashMap<>();

    public Map<Integer, Integer> getQtdApolicesPorSeguro() {return qtdApolicesPorSeguro;}
    public Map<Integer, BigDecimal> getValorTotalPorSeguro() {return valorTotalPorSeguro;}
    public Map<Integer, Integer> getQtdApolicesPorFuncionario() {return qtdApolicesPorFuncionario;}
    public Map<Integer, BigDecimal> getValorTotalPorFuncionario() {return valorTotalPorFuncionario;}

    public void inicializaBanco() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.iniciaTabela();

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.inicializaTabelas();

        SeguroDAO seguroDAO = new SeguroDAO();
        seguroDAO.iniciaTabelas();

        ApoliceDAO apoliceDAO = new ApoliceDAO();
        if (apoliceDAO.getAll().isEmpty()) {
            apoliceDAO.popularRegistro();
        }
    }

    public void exibirDashSeguros() {
        qtdApolicesPorSeguro.clear();
        valorTotalPorSeguro.clear();
        inicializaBanco();
        ApoliceDAO apoliceDAO = new ApoliceDAO();
        List<Apolice> apolicesVendidas = apoliceDAO.getAll();

        for (Apolice apolice : apolicesVendidas) {
            int seguroId = apolice.getSeguro_id().intValue();

            qtdApolicesPorSeguro.put(seguroId, qtdApolicesPorSeguro.getOrDefault(seguroId, 0) + 1);
            BigDecimal valorAtual = valorTotalPorSeguro
                    .getOrDefault(seguroId, BigDecimal.ZERO);

            valorTotalPorSeguro.put(
                    seguroId,
                    valorAtual.add(apolice.getValorFinal())
            );

        }

        SeguroDAO seguroDAO= new SeguroDAO();
        System.out.println("\n=========================== vendas por Seguro ===========================");
        for (Map.Entry<Integer, Integer> entry : qtdApolicesPorSeguro.entrySet()) {
            int seguroId = entry.getKey();
            int qtd = entry.getValue();
            BigDecimal valorTotal = valorTotalPorSeguro.get(seguroId);
            String nomeSeguro = seguroDAO.getNomeById(seguroId);
            System.out.printf("Tipo: %s  | Apolices: %d | Total Arrecadado: R$ %.2f%n", nomeSeguro, qtd, valorTotal.doubleValue());

        }
    }

    public void exibirDashFuncionario() {
        qtdApolicesPorFuncionario.clear();
        valorTotalPorFuncionario.clear();
        inicializaBanco();
        ApoliceDAO apoliceDAO = new ApoliceDAO();
        List<Apolice> apolicesVendidas = apoliceDAO.getAll();

        for (Apolice apolice : apolicesVendidas) {
            int funcionarioId = apolice.getFuncionario_id().intValue();
            qtdApolicesPorFuncionario.put(funcionarioId, qtdApolicesPorFuncionario.getOrDefault(funcionarioId, 0) + 1);
            valorTotalPorFuncionario.put(funcionarioId, valorTotalPorFuncionario.getOrDefault(funcionarioId, BigDecimal.ZERO).add(apolice.getValorFinal()));
        }

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        System.out.println("\n======================== Vendas por Funcionário =========================");
        for (Map.Entry<Integer, Integer> entry : qtdApolicesPorFuncionario.entrySet()) {
            int funcionarioId = entry.getKey();
            int qtd = entry.getValue();
            BigDecimal valorTotal = valorTotalPorFuncionario.get(funcionarioId);
            String nomeFuncionario = funcionarioDAO.getNomeById(funcionarioId);
            System.out.printf("Funcionario(%s)   | Apolices: %d     | Total Arrecadado: R$ %.2f%n", nomeFuncionario, qtd, valorTotal.doubleValue());
        }
    }

    public void exibeGraficoDeBarrasSeguros() {
        SeguroDAO seguroDAO = new SeguroDAO();
        System.out.println("\n======================= Gráfico de Barras - Seguros =====================");
        for (Map.Entry<Integer, Integer> entry : qtdApolicesPorSeguro.entrySet()) {
            String nome = seguroDAO.getNomeById(entry.getKey());
            System.out.print(nome + " | ");

            for (int i = 0; i < entry.getValue(); i++) {
                System.out.print("*");
            }
            System.out.println(" (" + entry.getValue() + ")");
        }
    }

    public void exibirGraficoFuncionario() {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        System.out.println("\n==================== Gráfico de Barras - Funcionários ===================");

        for (Map.Entry<Integer, Integer> entry : qtdApolicesPorFuncionario.entrySet()) {
            String nome = funcionarioDAO.getNomeById(entry.getKey());
            System.out.print("Funcionario(" + nome + ") | ");
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.print("*");
            }
            System.out.println(" (" + entry.getValue() + ")");
        }
    }

    public void exibirDashboard() {
        exibirDashSeguros();
        exibirDashFuncionario();
        exibeGraficoDeBarrasSeguros();
        exibirGraficoFuncionario();
    }
}
