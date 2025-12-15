    package com.banksecure.infra.db;
    import java.sql.*;

    public class ConnectionFactory {
    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:h2:file:./data/banksecure");

        } catch (SQLException e) {
            throw new RuntimeException(e);
           }
        }
    }

