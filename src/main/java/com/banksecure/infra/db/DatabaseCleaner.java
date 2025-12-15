package com.banksecure.infra.db;

import com.banksecure.exception.EstruturaBancoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCleaner {
    public static void limparTabelas(){
        try(Connection con = new ConnectionFactory().getConnection();
            Statement stmt = con.createStatement()) {

            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");

            stmt.execute("TRUNCATE TABLE apolice");
            stmt.execute("TRUNCATE TABLE seguro");
            stmt.execute("TRUNCATE TABLE clientes");
            stmt.execute("TRUNCATE TABLE funcionarios");

            stmt.execute("ALTER TABLE apolice ALTER COLUMN ID RESTART WITH 1");
            stmt.execute("ALTER TABLE seguro ALTER COLUMN ID RESTART WITH 1");
            stmt.execute("ALTER TABLE clientes ALTER COLUMN ID RESTART WITH 1");
            stmt.execute("ALTER TABLE funcionarios ALTER COLUMN ID RESTART WITH 1");

            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");

        }catch (SQLException e){
            throw new EstruturaBancoException("Erro ao limpar tabelas: "  + e.getMessage());
        }
    }
}
