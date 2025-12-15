package br.com.banksecure.domain;

import com.banksecure.domain.Funcionario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FuncionarioTest {
    @Test
    void deveMostrarOsDadosdoFuncionario(){
        Funcionario funcionario = new Funcionario(1L, "UsuarioTeste", "SenhaTest");
        System.out.println(funcionario.mostrarDadosDoFuncionario());
    }

    @Test
    void testConstrutorAndGetters(){
        Funcionario funcionario = new Funcionario(1L, "Joao", "senha123");
        assertEquals("Joao", funcionario.getUsuario());
        assertEquals("senha123", funcionario.getSenha());
    }

    @Test
    void testToString(){
        Funcionario funcionario = new Funcionario(2L, "Maria", "4569");
        assertNotNull(funcionario.toString());
    }
}
