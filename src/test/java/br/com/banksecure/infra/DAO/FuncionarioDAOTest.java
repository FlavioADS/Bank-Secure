package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Funcionario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionarioDAOTest {

    @Test
    void deveAlterarASenhaDoFuncionario(){
        Funcionario real = new Funcionario(1L, "usuarioTeste", "senhaAntiga");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setSenha("senhaNova");
        spyFuncionario.setSenha("senhaFinal");

        spyFuncionario.setUsuario("usuarioAlterado");

        verify(spyFuncionario, atLeast(2)).setSenha(anyString());
        verify(spyFuncionario,atLeast(1)).setUsuario(anyString());

        assertEquals("senhaFinal", spyFuncionario.getSenha());
        assertEquals("usuarioAlterado", spyFuncionario.getUsuario());

    }

    @Test
    void deveAlterarOUsuarioDoFuncionario(){
        Funcionario real = new Funcionario(2L, "usuarioOriginal", "senha123");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setUsuario("usuarioModificado");
        spyFuncionario.setUsuario("usuarioFinal");

        spyFuncionario.setSenha("novaSenha");

        verify(spyFuncionario, atLeast(2)).setUsuario(anyString());
        verify(spyFuncionario, atLeast(1)).setSenha(anyString());

        assertEquals("usuarioFinal", spyFuncionario.getUsuario());
        assertEquals("novaSenha", spyFuncionario.getSenha());
    }

    @Test
    void deveVerificarChamadasDosMetodos() {
        Funcionario real = new Funcionario(3L, "usuarioX", "senhaX");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setUsuario("usuarioY");
        spyFuncionario.setSenha("senhaY");

        verify(spyFuncionario, times(1)).setUsuario("usuarioY");
        verify(spyFuncionario, times(1)).setSenha("senhaY");
    }

    @Test
    void deveValidarEstadoFinalDoFuncionario() {
        Funcionario real = new Funcionario(4L, "usuarioInicial", "senhaInicial");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setUsuario("usuarioMeio");
        spyFuncionario.setSenha("senhaMeio");

        spyFuncionario.setUsuario("usuarioFinal");
        spyFuncionario.setSenha("senhaFinal");

        assertEquals("usuarioFinal", spyFuncionario.getUsuario());
        assertEquals("senhaFinal", spyFuncionario.getSenha());
    }

    @Test
    void deveContarNumeroDeAlteracoes() {
        Funcionario real = new Funcionario(5L, "usuarioA", "senhaA");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setUsuario("usuarioB");
        spyFuncionario.setUsuario("usuarioC");
        spyFuncionario.setSenha("senhaB");
        spyFuncionario.setSenha("senhaC");

        verify(spyFuncionario, times(2)).setUsuario(anyString());
        verify(spyFuncionario, times(2)).setSenha(anyString());
    }

    @Test
    void deveTestarGetters() {
        Funcionario real = new Funcionario(6L, "usuarioGetter", "senhaGetter");
        Funcionario spyFuncionario = spy(real);

        String usuario = spyFuncionario.getUsuario();
        String senha = spyFuncionario.getSenha();

        verify(spyFuncionario, times(1)).getUsuario();
        verify(spyFuncionario, times(1)).getSenha();

        assertEquals("usuarioGetter", usuario);
        assertEquals("senhaGetter", senha);
    }

    @Test
    void deveMostrarDadosDoFuncionario() {
        Funcionario funcionario = new Funcionario(7L, "usuarioShow", "senhaShow");

        assertEquals(7L, funcionario.getId());
        assertEquals("usuarioShow", funcionario.getUsuario());
        assertEquals("senhaShow", funcionario.getSenha());
    }

    @Test
    void deveSetarDadosDoFuncionario() {
        Funcionario funcionario = new Funcionario(8L, "usuarioSet", "senhaSet");

        funcionario.setUsuario("novoUsuario");
        funcionario.setSenha("novaSenha");

        assertEquals("novoUsuario", funcionario.getUsuario());
        assertEquals("novaSenha", funcionario.getSenha());
    }


}
