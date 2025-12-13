package com.banksecure.app;

import com.banksecure.domain.Apolice;
import com.banksecure.domain.Cliente;
import com.banksecure.domain.Funcionario;
import com.banksecure.domain.Seguro;
import com.banksecure.enums.TipoDeSeguroEnum;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.infra.DAO.SeguroDAO;
import com.banksecure.service.ApoliceService;
import com.banksecure.service.CotacaoService;
import com.banksecure.service.SeguroService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Funcionario func = new Funcionario();
        Seguro seguro = new Seguro();
        Cliente cliente = new Cliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        SeguroDAO seguroDAO = new SeguroDAO();
        SeguroService seguroService = new SeguroService();
      //  Apolice apolice = new Apolice();
       // ApoliceDAO apoliceDAO = new ApoliceDAO();
     //  ApoliceService apoliceService = new ApoliceService();
        CotacaoService cotacaoService = new CotacaoService();

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


            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("1 - Login");
            System.out.println("2 - Visualizar seguros");
            System.out.println("3 - Sair");
            System.out.println("\nselecione a opção desejada");
            opc = sc.nextInt();

            switch (opc){
                case 1:
                    boolean certo = true;
                    do {
                        System.out.println("\n---------bank secure---------");
                        System.out.println("Digite seu usuario: ");
                        String usuario = sc.next();
                        System.out.println("Digite sua senha: ");
                        String senha = sc.next();
                        if(usuario.equals("diflale") && senha.equals("1234") || usuario.equals( "jejairoy") && senha.equals("4321")) {
                            System.out.println("Login realizado com sucesso!");
                            certo = true;
                            int opcFunc ;
                            do{
                                System.out.println("-------------------------------------------------------------------------------------");
                                System.out.println("1 - cadastrar cliente");
                                System.out.println("2 - visualizar apolioces");
                                System.out.println("3 - visualizar cotacação");
                                System.out.println("4 - dashboard");
                                System.out.println("5 - gestão de seguros");
                                System.out.println("6 - voltar");
                                System.out.println("\nselecione a opção desejada");
                                opcFunc = sc.nextInt();


                                switch (opcFunc){
                                    case 1:
                                        System.out.println(" ----Digite os dados do cliente a ser cadastrado-----");
                                        System.out.println("Nome: ");
                                       String nome = sc.next();
                                        System.out.println("CPF:");
                                        String cpf = sc.next();
                                        System.out.println("Data de nascimento (DD-MM-AAAA):");
                                        LocalDate dtNasc = LocalDate.parse(sc.next());

                                        cliente.setNome(nome);
                                        cliente.setCpf(cpf);
                                        cliente.setDataNascimento(dtNasc);

                                        System.out.println(cliente.getNome());
                                        System.out.println(cliente.getCpf());
                                        System.out.println(cliente.getDataNascimento());
                                        clienteDAO.save(cliente);

                                        System.out.println("Cliente cadastrado com sucesso!");


                                        break;
                                    case 2:
                                        System.out.println("----Cadastrar apolice----");
                                        System.out.println("ID do cliente: ");
                                        Long clienteId = sc.nextLong();
                                        System.out.println("Nome do cliente: ");
                                        String nomeCliente = sc.next();
                                        System.out.println("ID do seguro: ");
                                        Long seguroId = sc.nextLong();
                                        System.out.println("Nome do seguro: ");
                                        String nomeSeguro = sc.next();
                                        System.out.println("ID do funcionario: ");
                                        Long funcionarioId = sc.nextLong();
                                        System.out.println("Nome do funcionario: ");
                                        String nomeFuncionario = sc.next();
                                        System.out.println("Data de inicio da apolice (DD-MM-AAAA): ");
                                        LocalDate dataInicio = LocalDate.parse(sc.next());
                                        System.out.println("Data de fim da apolice (DD-MM-AAAA): ");
                                        LocalDate dataFim = LocalDate.parse(sc.next());
                                        System.out.println("Valor final da apolice: ");
                                       BigDecimal valorComTaxa = cotacaoService.setTaxaPadrao(seguro.getValorBase());

                                      if(cotacaoService.bonusIdade(cliente.getDataNascimento())){
                                            valorComTaxa = valorComTaxa.add(BigDecimal.valueOf(100));
                                      }

                                       BigDecimal valorFinalComRisco = cotacaoService.taxaRisco(valorComTaxa);


                                   //     apolice.setNomeCliente(nomeCliente);
                                     //   apolice.setNomeSeguro(TipoDeSeguroEnum.valueOf(nomeSeguro));
                                    //    apolice.setNomeFuncionario(nomeFuncionario);
                                    //    apolice.setCliente_id(clienteId);
                                     //   apolice.setFuncionario_id(funcionarioId);
;                                   //     apolice.setSeguro_id(seguroId);
                                     //   apolice.setDataInicio(dataInicio);
                                     //   apolice.setDataFim(dataFim);
                                     //   apolice.setValorFinal(valorFinalComRisco);

                                     //   apoliceService.validarApoliceDAO(apolice);

                                       // apoliceService.registrarVenda(seguroId, clienteId, funcionarioId);


                                        break;
                                    case 3:
                                        Dashboard dashboard = new Dashboard();
                                        int opcDash;
                                        do {
                                            System.out.println("visualzação das cotações");
                                            System.out.println("\n---- seguro de vida ----");
                                          BigDecimal valorTaxa = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(200000));
                                            if(cotacaoService.bonusIdade(LocalDate.parse("12-05-1980"))){
                                                valorTaxa.add(BigDecimal.valueOf(100));
                                                System.out.println("Bônus de idade aplicado!");
                                                cotacaoService.taxaRisco(valorTaxa);
                                            }

                                            System.out.println("Valor final do seguro de vida: R$" + cotacaoService.taxaRisco(valorTaxa));

                                            System.out.println("\n---- seguro  residencial ----");
                                            BigDecimal valorTaxaResi = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(300000));
                                            if(cotacaoService.bonusIdade(LocalDate.parse("12-05-2001"))){
                                                valorTaxa.add(BigDecimal.valueOf(100));
                                                System.out.println("Bônus de idade aplicado!");
                                                cotacaoService.taxaRisco(valorTaxa);
                                            }

                                            System.out.println("Valor final do seguro  residencial: R$" + cotacaoService.taxaRisco(valorTaxa));

                                            System.out.println("\n---- seguro de automovel ----");
                                            BigDecimal valorTaxaAuto = cotacaoService.setTaxaPadrao(BigDecimal.valueOf(20000));
                                            if(cotacaoService.bonusIdade(LocalDate.parse("12-05-1980"))){
                                                valorTaxa.add(BigDecimal.valueOf(100));
                                                System.out.println("Bônus de idade aplicado!");
                                                cotacaoService.taxaRisco(valorTaxa);
                                            }

                                            System.out.println("Valor final do seguro de automovel: R$" + cotacaoService.taxaRisco(valorTaxa));


                                        }while( false);
                                        break;

                                    case 4:

                                        break;
                                    case 5:
                                        System.out.println("-----Cadastrar seguro----");
                                        System.out.println("Tipo de seguro (Vida/Residencial/Automovel): ");
                                        String tipoSeguro = sc.next();
                                        System.out.println("Descrição do seguro: ");
                                        String descricao = sc.next();
                                        System.out.println("Cobertura do seguro: ");
                                        BigDecimal cobertura = BigDecimal.valueOf(sc.nextDouble());
                                        System.out.println("Valor base do seguro: ");
                                        BigDecimal valorBase = BigDecimal.valueOf(sc.nextDouble());

                                        seguro.setTipo(TipoDeSeguroEnum.valueOf(tipoSeguro));
                                        seguro.setDescricao(descricao);
                                        seguro.setCobertura(cobertura);
                                        seguro.setValorBase(valorBase);

                                        seguroService.validarSeguroDAO(seguro);

                                        seguroDAO.save(seguro);
                                        System.out.println("Seguro cadastrado com sucesso!");



                                        break;
                                }
                            }while(opcFunc != 6);

                        }else{
                            System.out.println("Usuario ou senha incorretos, tente novamente.");

                            certo = false;
                        }

                    }while (certo == false);
                    break;
                case 2:


                        System.out.println("Cobertura completa de R$200.000,00 para segurança de vida");
                        System.out.println("Cobertura de R$300.000,00 para danos à residência");
                        System.out.println("Cobertura de R$20.000,00 para danos ao veículo");
                        System.out.println("0 - Voltar");
                        System.out.println("\nselecione a opção desejada");
                        int opcSair = sc.nextInt();


                    break;
                case 5:
                    int opc5;
                    do {
                        Dashboard dash = new Dashboard();
                        System.out.println("" +
                                "********************************************************************" +
                                "\n**************************** Dashboard *****************************" +
                                "\n********************************************************************");
                        dash.exibirDashboard();
                        System.out.println("\n2 - voltar");
                        opc5 = sc.nextInt();

                    }while(opc5 != 2);
            }

        } while (opc !=3);

    }
}