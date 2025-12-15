package br.com.banksecure.infra.db;

import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;
import com.banksecure.infra.db.DatabaseCleaner;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DatabaseCleanerTest {

    @Test
    void deveLimparTabelasComSucesso() throws Exception {

        Connection connectionMock = mock(Connection.class);
        Statement statementMock = mock(Statement.class);

        when(connectionMock.createStatement()).thenReturn(statementMock);

        try (MockedConstruction<ConnectionFactory> mocked =
                     mockConstruction(ConnectionFactory.class,
                             (mock, context) ->
                                     when(mock.getConnection()).thenReturn(connectionMock))) {

            DatabaseCleaner.limparTabelas();

            verify(statementMock).execute("SET REFERENTIAL_INTEGRITY FALSE");

            verify(statementMock).execute("TRUNCATE TABLE apolice");
            verify(statementMock).execute("TRUNCATE TABLE seguro");
            verify(statementMock).execute("TRUNCATE TABLE clientes");
            verify(statementMock).execute("TRUNCATE TABLE funcionarios");

            verify(statementMock).execute("ALTER TABLE apolice ALTER COLUMN ID RESTART WITH 1");
            verify(statementMock).execute("ALTER TABLE seguro ALTER COLUMN ID RESTART WITH 1");
            verify(statementMock).execute("ALTER TABLE clientes ALTER COLUMN ID RESTART WITH 1");
            verify(statementMock).execute("ALTER TABLE funcionarios ALTER COLUMN ID RESTART WITH 1");

            verify(statementMock).execute("SET REFERENTIAL_INTEGRITY TRUE");
        }
    }

    @Test
    void deveLancarExcecaoQuandoOcorrerErroSql() throws Exception {

        Connection connectionMock = mock(Connection.class);
        Statement statementMock = mock(Statement.class);

        when(connectionMock.createStatement()).thenReturn(statementMock);
        doThrow(new SQLException("Erro SQL"))
                .when(statementMock).execute(anyString());

        try (MockedConstruction<ConnectionFactory> mocked =
                     mockConstruction(ConnectionFactory.class,
                             (mock, context) ->
                                     when(mock.getConnection()).thenReturn(connectionMock))) {

            EstruturaBancoException exception =
                    assertThrows(EstruturaBancoException.class,
                            DatabaseCleaner::limparTabelas);

            assertTrue(exception.getMessage().contains("Erro ao limpar tabelas"));
        }
    }
}
