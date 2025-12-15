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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        SeguroDAO seguroDAO = new SeguroDAO();
        ApoliceDAO apoliceDAO = new ApoliceDAO();

        Seguro seguro = new Seguro();
        Cliente cliente = new Cliente();
        SeguroService seguroService = new SeguroService();
        Apolice apolice = new Apolice();

        ApoliceService apoliceService = new ApoliceService();
        CotacaoService cotacaoService = new CotacaoService();

        Dashboard dashboard = new Dashboard();

        funcionarioDAO.inicializaTabelas();
        seguroDAO.iniciaTabelas();
        clienteDAO.iniciaTabela();
        apoliceDAO.iniciaTabela();

        int opc = 0;

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

            try {
                opc = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Opção inválida.");
                sc.nextLine();
                opc = 0;
            }

            switch (opc) {

                case 1:
                    boolean certo;
                    do {
                        certo = true;
                        try {
                            System.out.println("\nDigite seu usuario: ");
                            String usuario = sc.next();
                            System.out.println("Digite sua senha: ");
                            String senha = sc.next();

                            if ((usuario.equals("diflale") && senha.equals("1234")) ||
                                    (usuario.equals("jejairoy") && senha.equals("4321"))) {

                                System.out.println("Login realizado com sucesso!");

                                int opcFunc = 0;
                                do {
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

                                    try {
                                        opcFunc = sc.nextInt();
                                        sc.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("Opção inválida.");
                                        sc.nextLine();
                                        opcFunc = 0;
                                    }

                                    switch (opcFunc) {

                                        case 1:
                                            try {
                                                System.out.println("Nome:");
                                                String nome = sc.nextLine();

                                                System.out.println("CPF:");
                                                String cpf = sc.next();
                                                System.out.println("Data de nascimento (DD-MM-AAAA):");
                                                LocalDate dtNasc = LocalDate.parse(sc.next(),
                                                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                                                cliente.setNome(nome);
                                                cliente.setCpf(cpf);
                                                cliente.setDataNascimento(dtNasc);
                                                clienteDAO.save(cliente);

                                                System.out.println("Cliente cadastrado com sucesso!");
                                            } catch (DadosInvalidosException e) {
                                                System.out.println(e.getMessage());
                                            } catch (Exception e) {
                                                System.out.println("Erro inesperado.");
                                            }
                                            break;

                                        case 2:
                                            try {
                                                System.out.println("----Clientes disponiveis----\n");
                                                clienteDAO.getAll().forEach(System.out::println);

                                                System.out.println("ID do cliente: ");
                                                Long clienteId = sc.nextLong();

                                                System.out.println("----Seguros disponiveis----\n");
                                                seguroDAO.getAll().forEach(System.out::println);

                                                System.out.println("ID do seguro: ");
                                                Long seguroId = sc.nextLong();

                                                System.out.println("----Funcionarios Disponiveis----\n");
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

                                            } catch (DadosInvalidosException e) {
                                                System.out.println("Erro ao cadastrar apólice: " + e.getMessage());
                                            } catch (Exception e) {
                                                System.out.println("Erro inesperado. Tente novamente.");
                                            }
                                            break;


                                        case 3:
                                            try {
                                                int opcDash;
                                                do {
                                                    System.out.println("\n===================Visualização das cotações=========================");

                                                    BigDecimal valorTaxa = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(70));
                                                    if (cotacaoService.bonusIdade(LocalDate.parse("2000-05-12"))) {
                                                        valorTaxa = valorTaxa.add(BigDecimal.valueOf(100));
                                                        System.out.println("Bônus de idade aplicado!");
                                                    }
                                                    System.out.println("Seguro de Vida: R$" + cotacaoService.taxaRisco(valorTaxa));

                                                    BigDecimal valorTaxaResi = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(55));
                                                    if (cotacaoService.bonusIdade(LocalDate.parse("1970-05-11"))) {
                                                        valorTaxaResi = valorTaxaResi.add(BigDecimal.valueOf(100));
                                                        System.out.println("Bônus de idade aplicado!");
                                                    }
                                                    System.out.println("Seguro Residencial: R$" + cotacaoService.taxaRisco(valorTaxaResi));

                                                    BigDecimal valorTaxaAuto = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(100));
                                                    if (cotacaoService.bonusIdade(LocalDate.parse("1951-11-12"))) {
                                                        valorTaxaAuto = valorTaxaAuto.add(BigDecimal.valueOf(100));
                                                        System.out.println("Bônus de idade aplicado!");
                                                    }
                                                    System.out.println("Seguro Automóvel: R$" + cotacaoService.taxaRisco(valorTaxaAuto));

                                                } while (false);

                                            } catch (Exception e) {
                                                System.out.println("Erro ao exibir cotações.");
                                            }
                                            break;


                                        case 4:
                                            try {
                                                dashboard.exibirDashboard();
                                            } catch (Exception e) {
                                                System.out.println("Erro no dashboard.");
                                            }
                                            break;

                                        case 5:
                                            int opcGestao = 0;
                                            do {
                                                try {
                                                    System.out.println("1 - Deletar Seguro");
                                                    System.out.println("2 - Alterar Seguro");
                                                    System.out.println("3 - Cadastrar Seguro");
                                                    System.out.println("4 - Voltar");

                                                    opcGestao = sc.nextInt();
                                                    sc.nextLine();

                                                    switch (opcGestao) {

                                                        case 1:
                                                            try {
                                                                System.out.println("\n=====Deletar Seguro=====\n");
                                                                System.out.println("=====Seguros Disponíveis=====\n");
                                                                seguroDAO.getAll().forEach(System.out::println);

                                                                System.out.println("ID do seguro a ser deletado: ");
                                                                Long idSeguroDel = sc.nextLong();
                                                                sc.nextLine();

                                                                Seguro seguroDel = seguroDAO.getById(idSeguroDel);
                                                                seguroService.validarDeleteSeguro(seguroDel);
                                                                seguroDAO.delete(seguroDel);

                                                            } catch (DadosInvalidosException e) {
                                                                System.out.println("Erro ao deletar seguro: " + e.getMessage());
                                                            } catch (Exception e) {
                                                                System.out.println("Erro inesperado ao deletar seguro.");
                                                            }
                                                            break;

                                                        case 2:
                                                            try {
                                                                System.out.println("=====Alterar Seguro=====\n");
                                                                System.out.println("=====Seguros Disponíveis=====\n");
                                                                seguroDAO.getAll().forEach(System.out::println);

                                                                System.out.println("ID do seguro a ser alterado: ");
                                                                Long idSeguroAlt = sc.nextLong();
                                                                sc.nextLine(); // limpa buffer

                                                                Seguro seguroAlt = seguroDAO.getById(idSeguroAlt);

                                                                System.out.println("Nova descrição: ");
                                                                String novaDesc = sc.nextLine();

                                                                seguroAlt.setDescricao(novaDesc);
                                                                seguroDAO.update(seguroAlt);

                                                                System.out.println("Seguro alterado com sucesso!");

                                                            } catch (DadosInvalidosException e) {
                                                                System.out.println("Erro ao alterar seguro: " + e.getMessage());
                                                            } catch (Exception e) {
                                                                System.out.println("Erro inesperado ao alterar seguro.");
                                                            }
                                                            break;

                                                        case 3:
                                                            try {
                                                                System.out.println("=====Cadastrar Seguro=====\n");

                                                                System.out.println("Tipo de seguro (SEGURO_VIDA, SEGURO_AUTO, SEGURO_RESIDENCIAL): ");
                                                                String tipoSeguro = sc.nextLine().toUpperCase();
                                                                TipoDeSeguroEnum tipo = TipoDeSeguroEnum.valueOf(tipoSeguro);

                                                                System.out.println("Descrição: ");
                                                                String descSeguro = sc.nextLine();

                                                                System.out.println("Cobertura (somente números): ");
                                                                BigDecimal cobertura = sc.nextBigDecimal();

                                                                System.out.println("Valor prêmio base (somente números): ");
                                                                BigDecimal valorBase = sc.nextBigDecimal();
                                                                sc.nextLine(); // limpa buffer

                                                                Seguro novoSeguro = new Seguro(tipo, descSeguro, cobertura, valorBase);
                                                                seguroDAO.save(novoSeguro);

                                                                System.out.println("Seguro cadastrado com sucesso!");

                                                            } catch (IllegalArgumentException e) {
                                                                System.out.println("Tipo de seguro inválido.");
                                                                sc.nextLine(); // limpa buffer
                                                            } catch (DadosInvalidosException e) {
                                                                System.out.println("Erro ao cadastrar seguro: " + e.getMessage());
                                                            } catch (Exception e) {
                                                                System.out.println("Erro inesperado ao cadastrar seguro.");
                                                                sc.nextLine(); // limpa buffer
                                                            }
                                                            break;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Erro no menu de gestão.");
                                                    sc.nextLine();
                                                    opcGestao = 0;
                                                }
                                            } while (opcGestao != 4);
                                            break;

                                        case 6:
                                            try {
                                                apoliceDAO.getAll().forEach(System.out::println);
                                            } catch (Exception e) {
                                                System.out.println("Erro ao listar apólices.");
                                            }
                                            break;

                                        case 7:
                                            try {
                                                clienteDAO.getAll().forEach(System.out::println);
                                            } catch (Exception e) {
                                                System.out.println("Erro ao listar clientes.");
                                            }
                                            break;
                                        case 8:
                                            try {
                                                apoliceService.apolicesParaRenovar();

                                                System.out.println("\nDeseja renovar alguma apólice? (1 - Sim / 2 - Não)");
                                                int escolhaRen = sc.nextInt();

                                                if (escolhaRen == 1) {
                                                    System.out.println("Informe o ID da apólice a renovar: ");
                                                    Long idRen = sc.nextLong();

                                                    try {
                                                        apoliceService.renovarApolice(idRen);

                                                        System.out.println("Apólice renovada com sucesso!");
                                                        System.out.println(apoliceDAO.getById(idRen).renovarDadosDaApolice());

                                                        System.out.println("Apólices:");
                                                        apoliceService.mostrarApolices();

                                                    } catch (DadosInvalidosException e) {
                                                        System.out.println("Erro ao renovar apólice: " + e.getMessage());
                                                    } catch (Exception e) {
                                                        System.out.println("Erro inesperado ao renovar apólice.");
                                                    }
                                                }

                                            } catch (DadosInvalidosException e) {
                                                System.out.println("Aviso: " + e.getMessage());
                                            } catch (Exception e) {
                                                System.out.println("Erro ao listar apólices a vencer.");
                                            }
                                            break;
                                    }

                                } while (opcFunc != 9);

                            } else {
                                System.out.println("Usuário ou senha inválidos.");
                                certo = false;
                            }

                        } catch (Exception e) {
                            System.out.println("Erro no login.");
                            sc.nextLine();
                            certo = false;
                        }

                    } while (!certo);
                    break;

                case 2:
                    System.out.println("Visualização de seguros");
                    seguroDAO.getAll().forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opc != 3);


    }
    private static String readLine(Scanner sc) {
        String line = "";
        while (line.isBlank()) {
            if (!sc.hasNextLine()) return "";
            line = sc.nextLine();
        }
        return line.trim();
    }
}

