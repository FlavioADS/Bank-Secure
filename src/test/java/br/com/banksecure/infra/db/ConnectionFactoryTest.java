package br.com.banksecure.infra.db;

import com.banksecure.infra.db.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class ConnectionFactoryTest {

    @Test
    void testGetConnection() {
        Connection mockConnection = Mockito.mock(Connection.class);

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection("jdbc:h2:file:./data/banksecure")).thenReturn(mockConnection);

            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.getConnection();

            assertEquals(mockConnection, connection);
            mockedDriverManager.verify(() -> DriverManager.getConnection("jdbc:h2:file:./data/banksecure"));
        }
    }

    @Test
    void testGetConnectionThrowsRunTimeException() {
        SQLException sqlException = new SQLException("Falha na conexão");
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection("jdbc:h2:file:./data/banksecure")).thenThrow(sqlException);

            ConnectionFactory factory = new ConnectionFactory();
            RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, factory::getConnection);
            assertEquals(sqlException, thrown.getCause());
            mockedDriverManager.verify(() ->  DriverManager.getConnection("jdbc:h2:file:./data/banksecure"));
        }
    }
}
