package com.banksecure.infra.DAO;

import com.banksecure.domain.Cliente;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;
import com.banksecure.service.ClienteService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private ClienteService clienteService = new ClienteService();

    public void iniciaTabela(){
        this.createTable();


    }

    public void popularRegistros(){
        try{
            Cliente cliente1 = new Cliente("Flavão", "80758585472", LocalDate.of(2004, 9, 15));

            Cliente cliente2 = new Cliente("Eduardo", "58142587954", LocalDate.of(2000, 1, 25));

            this.save(cliente1);
            this.save(cliente2);

        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao popular tabela de clientes");
        }
    }

    public void createTable(){
                String sql = """
                CREATE TABLE IF NOT EXISTS clientes(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    cpf VARCHAR(11) NOT NULL,
                    data_nascimento DATE NOT NULL
                );
            """;
        try (Connection con = new ConnectionFactory().getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao criar tabela de clientes");
        }
    }


    public List<Cliente> getAll() {
        String sql = "SELECT * FROM clientes c ORDER BY c.id";

        try (Connection con = new ConnectionFactory().getConnection();
             Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {

            List<Cliente> clientes = new ArrayList<>();

            while (result.next()) {
                clientes.add(new Cliente(
                        result.getLong("id"),
                        result.getString("nome"),
                        result.getString("cpf"),
                        result.getDate("data_nascimento").toLocalDate()));
            }
            if (clientes.isEmpty()) {
                throw new EstruturaBancoException("Nenhum cliente encontrado");
            }
            return clientes;
        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao listar clientes no banco de dados");
        }
    }


    public void save(Cliente cliente) {
        clienteService.validarClienteDAO(cliente);


        String sql = "INSERT INTO clientes(nome, cpf, data_nascimento) VALUES (?, ?, ?)";

        try (Connection con = new ConnectionFactory().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(cliente.getDataNascimento()));

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DadosInvalidosException("Erro ao salvar Cliente no banco de dados",e);

        }
    }



    public Cliente getById(Long clienteId) {

        try (   Connection con = new ConnectionFactory().getConnection();
                Statement stmt = con.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM clientes c WHERE c.id = " + clienteId)
        ) {
            List<Cliente> clientes = new ArrayList<>();

            while (result.next()) {
                clientes.add(new Cliente(
                        result.getLong("id"),
                        result.getString("nome"),
                        result.getString("cpf"),
                        result.getDate("data_nascimento").toLocalDate()));
            }
            if (clientes.isEmpty()) {
                throw new EstruturaBancoException("Nenhum cliente encontrado para o id informado");
            }
            return clientes.get(0);

        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao listar clientes no banco de dados");
        }
    }

    public void delete(Cliente cliente) {
        clienteService.validarDeleteCliente(cliente);

        String sql = "DELETE FROM clientes WHERE id = ? AND cpf = ?";

        try (Connection con = new ConnectionFactory().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, cliente.getId());
            stmt.setString(2, cliente.getCpf());

            int afetados = stmt.executeUpdate();

            if (afetados == 0) {
                throw new DadosInvalidosException("Cliente não encontrado para exclusão.");
            }

            System.out.println("Cliente removido com sucesso");

        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao deletar cliente no banco de dados");
        }
    }


}
