package com.banksecure.app;

import com.banksecure.domain.Apolice;
import com.banksecure.domain.Cliente;
import com.banksecure.domain.Funcionario;
import com.banksecure.domain.Seguro;
import com.banksecure.enums.TipoDeSeguroEnum;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.infra.DAO.FuncionarioDAO;
import com.banksecure.infra.DAO.SeguroDAO;
import com.banksecure.service.ApoliceService;
import com.banksecure.service.CotacaoService;
import com.banksecure.service.SeguroService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Funcionario func = new Funcionario();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Seguro seguro = new Seguro();
        Cliente cliente = new Cliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        SeguroDAO seguroDAO = new SeguroDAO();
        SeguroService seguroService = new SeguroService();
        Apolice apolice = new Apolice();
        ApoliceDAO apoliceDAO = new ApoliceDAO();
        ApoliceService apoliceService = new ApoliceService();
        CotacaoService cotacaoService = new CotacaoService();
        Dashboard dashboard = new Dashboard();

        funcionarioDAO.inicializaTabelas();
        seguroDAO.iniciaTabelas();
        clienteDAO.iniciaTabela();
        clienteDAO.popularRegistros();
        apoliceDAO.iniciaTabela();

        int opc;


        System.out.println("""
        ██████╗  █████╗ ███╗   ██╗██╗  ██╗  ███████╗███████╗███████╗██╗   ██╗██████╗ ███████╗
        ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝  ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██╔════╝
        ██████╔╝███████║██╔██╗ ██║█████╔╝   ███████╗█████╗  ██      ██║   ██║██████╔╝█████╗
        ██╔══██╗██╔══██║██║╚██╗██║██╔═██╗   ╚════██║██╔══╝  ██      ██║   ██║██╔══██╗██╔══╝
        ██████ ║██║  ██║██║ ╚████║██║  ██╗  ███████║███████╗███████╗╚██████╔╝██║  ██║███████╗
        ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝  ╚══════╝╚══════╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
        """);
        do {


            System.out.println("\n=====================================================================================");

            System.out.println("1 - Login");
            System.out.println("2 - Visualizar Seguros");
            System.out.println("3 - Sair");
            System.out.println("\nSelecione a opção desejada");
            opc = sc.nextInt();

            switch (opc){
                case 1:
                    boolean certo = true;
                    do {
                        System.out.println("""
        ██████╗  █████╗ ███╗   ██╗██╗  ██╗  ███████╗███████╗███████╗██╗   ██╗██████╗ ███████╗
        ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝  ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██╔════╝
        ██████╔╝███████║██╔██╗ ██║█████╔╝   ███████╗█████╗  ██      ██║   ██║██████╔╝█████╗
        ██╔══██╗██╔══██║██║╚██╗██║██╔═██╗   ╚════██║██╔══╝  ██      ██║   ██║██╔══██╗██╔══╝
        ██████ ║██║  ██║██║ ╚████║██║  ██╗  ███████║███████╗███████╗╚██████╔╝██║  ██║███████╗
        ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝  ╚══════╝╚══════╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
        """);
                        System.out.println("\n=====================================================================================");
                        System.out.println("Digite seu usuario: ");
                        String usuario = sc.next();
                        System.out.println("Digite sua senha: ");
                        String senha = sc.next();
                        if(usuario.equals("diflale") && senha.equals("1234") || usuario.equals( "jejairoy") && senha.equals("4321")) {
                            System.out.println("Login realizado com sucesso!");
                            certo = true;
                            int opcFunc ;
                            do{
                                System.out.println("\n=====================================================================================");
                                System.out.println("1 - Cadastrar Cliente");
                                System.out.println("2 - Registrar Apólices");
                                System.out.println("3 - Visualizar Cotação");
                                System.out.println("4 - Dashboard");
                                System.out.println("5 - Gestão de Sistema");
                                System.out.println("6 - Listar Apólices");
                                System.out.println("7 - Listar Clientes");
                                System.out.println("8 - Renovação Apólices");
                                System.out.println("9 - Voltar");
                                System.out.println("\nSelecione a opção desejada");
                                opcFunc = sc.nextInt();


                                switch (opcFunc){
                                    case 1:
                                        try {
                                            System.out.println(" ----Digite os dados do cliente a ser cadastrado-----");
                                            System.out.println("Nome: ");
                                            String nome = sc.next();
                                            System.out.println("CPF:");
                                            String cpf = sc.next();
                                            System.out.println("Data de nascimento (DD-MM-AAAA):");
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                            LocalDate dtNasc = LocalDate.parse(sc.next(), formatter);

                                            cliente.setNome(nome);
                                            cliente.setCpf(cpf);
                                            cliente.setDataNascimento(dtNasc);

                                            clienteDAO.save(cliente);

                                            System.out.println("Cliente cadastrado com sucesso!");

                                        } catch (DadosInvalidosException e) {
                                            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
                                        } catch (Exception e) {
                                            System.out.println("Erro inesperado. Tente novamente.");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("----Clientes disponiveis----\n" );
                                        clienteDAO.getAll().forEach(System.out::println);

                                        System.out.println("ID do cliente: ");
                                        Long clienteId = sc.nextLong();

                                        System.out.println("----Seguros disponiveis----\n" );
                                        seguroDAO.getAll().forEach(System.out::println);

                                        System.out.println("ID do seguro: ");
                                        Long seguroId = sc.nextLong();

                                        System.out.println("----Funcionarios Disponiveis----\n" );
                                        funcionarioDAO.getAll().forEach(System.out::println);

                                        System.out.println("ID do funcionario: ");
                                        Long funcionarioId = sc.nextLong();

                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                        System.out.println("Data de inicio da apolice (DD-MM-AAAA): ");
                                        LocalDate dataInicio = LocalDate.parse(sc.next(), formatter);

                                        System.out.println("Data de fim da apolice (DD-MM-AAAA): ");
                                        LocalDate dataFim = LocalDate.parse(sc.next(), formatter);

                                        apolice.setCliente_id(clienteId);
                                        apolice.setFuncionario_id(funcionarioId);
                                        apolice.setSeguro_id(seguroId);
                                        apolice.setDataInicio(dataInicio);
                                        apolice.setDataFim(dataFim);

                                        apoliceService.registrarVenda(seguroId, clienteId, funcionarioId);
                                        System.out.println("Apólice cadastrada com sucesso!");
                                        apoliceDAO.getAll().forEach(System.out::println);


                                        break;
                                    case 3:
                                        int opcDash;
                                        do {
                                            System.out.println("Visualzação das cotações");
                                            System.out.println("\n=====Seguro de Vida=====");
                                            BigDecimal valorTaxa = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(70));
                                            if(cotacaoService.bonusIdade(LocalDate.parse("2000-05-12"))){
                                                valorTaxa =  valorTaxa.add(BigDecimal.valueOf(100));
                                                System.out.println("Bônus de idade aplicado!");
                                                cotacaoService.taxaRisco(valorTaxa);
                                            }

                                            System.out.println("Valor final do seguro de vida: R$" + cotacaoService.taxaRisco(valorTaxa));

                                            System.out.println("\n=====Seguro Residencial=====");
                                            BigDecimal valorTaxaResi = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(55));
                                            if(cotacaoService.bonusIdade(LocalDate.parse("1970-05-11"))){
                                                valorTaxaResi = valorTaxaResi.add(BigDecimal.valueOf(100));
                                                System.out.println("Bônus de idade aplicado!");
                                                cotacaoService.taxaRisco(valorTaxaResi);
                                            }

                                            System.out.println("Valor final do seguro  residencial: R$" + cotacaoService.taxaRisco(valorTaxaResi));

                                            System.out.println("\n=====Seguro de Automovel=====");
                                            BigDecimal valorTaxaAuto = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(100));
                                            if(cotacaoService.bonusIdade(LocalDate.parse("1951-11-12"))){
                                                valorTaxaAuto = valorTaxaAuto.add(BigDecimal.valueOf(100));
                                                System.out.println("Bônus de idade aplicado!");
                                                cotacaoService.taxaRisco(valorTaxaAuto);
                                            }

                                            System.out.println("Valor final do seguro de automovel: R$" + cotacaoService.taxaRisco(valorTaxaAuto));


                                        }while(false);
                                        break;

                                    case 4:
                                        System.out.println("\n=====Dashboard=====");
                                        dashboard.exibirDashboard();
                                        break;
                                    case 5:
                                        int opcGestao;
                                        do {
                                            System.out.println("1 - Deletar Seguro");
                                            System.out.println("2 - Alterar seguro");
                                            System.out.println("3 - Cadastrar Seguro");
                                            System.out.println("4 - Voltar");
                                            System.out.println("\nSelecione a opção desejada");
                                            opcGestao = sc.nextInt();

                                            switch (opcGestao){
                                                case 1:
                                                    System.out.println("\n=====Deletar Seguro=====\n" );
                                                    System.out.println("=====Seguros Disponiveis=====\n" );
                                                    seguroDAO.getAll().forEach(System.out::println);
                                                    System.out.println("ID do seguro a ser deletado: ");
                                                    Long idSeguroDel = sc.nextLong();

                                                    Seguro seguroDel = seguroDAO.getById(idSeguroDel);
                                                    seguroService.validarDeleteSeguro(seguroDel);
                                                    seguroDAO.delete(seguroDel);
                                                    break;
                                                case 2:
                                                    System.out.println("=====Alterar seguro=====\n" );
                                                    System.out.println("=====Seguros disponiveis=====\n" );
                                                    seguroDAO.getAll().forEach(System.out::println);
                                                    System.out.println("ID do seguro a ser alterado: ");
                                                    Long idSeguroAlt = sc.nextLong();
                                                    seguro.setId(idSeguroAlt);
                                                    Seguro seguroAlt = seguroDAO.getById(idSeguroAlt);
                                                    System.out.println("Nova descrição: ");
                                                    String novaDesc = sc.next();
                                                    seguro.setDescricao(novaDesc);
                                                    seguroDAO.update(seguro);
                                                    System.out.println("Seguro alterado com sucesso!");
                                                    break;
                                                case 3:
                                                    System.out.println("=====Cadastrar Seguro=====\n" );
                                                    System.out.println("Tipo de seguro (VIDA, RESIDENCIAL, AUTOMOVEL): ");
                                                    String tipoSeguro = sc.next();
                                                    seguro.setTipo(TipoDeSeguroEnum.valueOf(tipoSeguro));
                                                    System.out.println("Descrição: ");
                                                    String descSeguro = sc.next();
                                                    seguro.setDescricao(descSeguro);
                                                    System.out.println("Cobertura (somente números): ");
                                                    BigDecimal cobertura = sc.nextBigDecimal();
                                                    seguro.setCobertura(cobertura);
                                                    System.out.println("Valor prêmio base (somente números): ");
                                                    BigDecimal valorBase = sc.nextBigDecimal();
                                                    seguro.setValorBase(valorBase);
                                                    seguroDAO.save(seguro);
                                                    System.out.println("Seguro cadastrado com sucesso!");
                                                    break;

                                            }

                                        }while (opcGestao != 4);
                                        break;
                                    case 6:
                                        System.out.println("\n=====Lista de Apólices=====\n" );
                                        apoliceDAO.getAll().forEach(System.out::println);
                                        break;
                                    case 7:
                                        System.out.println("\n=====Lista de Clientes=====\n" );
                                        clienteDAO.getAll().forEach(System.out::println);
                                        break;
                                    case 8:
                                        try {
                                            List<Apolice> apolicesAVencer = apoliceDAO.getByDueDate();

                                            if (apolicesAVencer.isEmpty()) {
                                                System.out.println("Nenhuma apólice a vencer nos próximos 30 dias.");
                                            } else {
                                                System.out.println("\n=====Apólices a vencer nos próximos 30 dias=====");
                                                apolicesAVencer.forEach(System.out::println);

                                                System.out.println("\nDeseja renovar alguma apólice? (1 - Sim / 2 - Não)");
                                                int escolhaRen = sc.nextInt();
                                                if (escolhaRen == 1) {
                                                    System.out.println("Informe o ID da apólice a renovar: ");
                                                    Long idRen = sc.nextLong();

                                                    try {
                                                        apoliceService.renovarApolice(idRen);
                                                        System.out.println("Apólice renovada com sucesso!");
                                                        System.out.println(apoliceDAO.getById(idRen));
                                                    } catch (DadosInvalidosException e) {
                                                        System.out.println("Erro ao renovar apólice: " + e.getMessage());
                                                    } catch (Exception e) {
                                                        System.out.println("Erro inesperado ao renovar apólice.");
                                                    }
                                                }
                                            }
                                        } catch (DadosInvalidosException e) {
                                            System.out.println("Aviso: " + e.getMessage());
                                        } catch (Exception e) {
                                            System.out.println("Erro ao listar apólices a vencer.");
                                        }
                                        break;
                                }
                            }while(opcFunc != 9);

                        }else{
                            System.out.println("Usuario ou senha incorretos, tente novamente.");

                            certo = false;
                        }

                    }while (certo == false);
                    break;
                case 2:
                    System.out.println("=====Seguros Disponiveis=====\n");
                    System.out.println("\n=====Seguro de Vida=====");
                    System.out.println("Cobertura de R$500.000,00 para morte acidental");
                    System.out.println("Cobertura de R$250.000,00 para invalidez permanente");
                    System.out.println("Prêmio mensal: R$170,00");
                    System.out.println("\n=====Seguro Residencial=====");
                    System.out.println("Cobertura de R$300.000,00 para danos estruturais");
                    System.out.println("Cobertura de R$100.000,00 para roubo e furto");
                    System.out.println("Prêmio mensal: R$120,00");
                    System.out.println("\n=====Seguro de Automóvel=====");
                    System.out.println("Cobertura de R$50.000,00 para colisão");
                    System.out.println("Cobertura de R$20.000,00 para roubo e furto");
                    System.out.println("Prêmio mensal: R$200,00");
                    System.out.println("0 - Voltar");
                    System.out.println("\nSelecione a opção desejada");
                    int opcSair = sc.nextInt();

                    break;

            }

        } while (opc !=3);

    }
}
