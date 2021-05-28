package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.dominio.Funcionario;
import br.com.bandtec.projetocontinuada3.dominio.Pedido;
import br.com.bandtec.projetocontinuada3.repositorio.FuncionarioRepository;
import br.com.bandtec.projetocontinuada3.repositorio.PedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FuncionarioControllerTest {

    @Autowired
    private FuncionarioController funcionarioController;

    @MockBean
    PedidoRepository pedidoRepository;

    @MockBean
    FuncionarioRepository funcionarioRepository;

    @Test
    @DisplayName("GET /funcionarios - Quando houverem registros - status 200 e número certo de registros")
    void getFuncionariosComRegistros() {
        List<Funcionario> funcionarioTeste = Arrays.asList(new Funcionario());
        Mockito.when(funcionarioRepository.findAll()).thenReturn(funcionarioTeste);
        ResponseEntity<List<Funcionario>> resposta = funcionarioController.getFuncionarios();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    @DisplayName("GET /funcionarios - Quando NÃO houverem registros - status 204 e sem corpo")
    void getFuncionariosSemRegistros() {
        Mockito.when(funcionarioRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Funcionario>> resposta = funcionarioController.getFuncionarios();
        assertEquals(204, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }


    @Test
    void postFuncionarioSemErros() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Anna");
        funcionario.setCaixa(1);
        ResponseEntity resposta = funcionarioController.postFuncionario(funcionario);
        assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    void deleteFuncionarioValido() {
        Integer idFuncionario = 1;
        Mockito.when(funcionarioRepository.existsById(idFuncionario)).thenReturn(true);
        ResponseEntity resposta = funcionarioController.deleteFuncionario(idFuncionario);
        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    void deleteFuncionarioInvalido() {
        Integer idFuncionario = 1;
        Mockito.when(funcionarioRepository.existsById(idFuncionario)).thenReturn(false);
        ResponseEntity resposta = funcionarioController.deleteFuncionario(idFuncionario);
        assertEquals(404, resposta.getStatusCodeValue());
    }


    @Test
    void getFuncionarioPorCaixaValido() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCaixa(1);
        List<Funcionario> funcionarioTeste = Arrays.asList(funcionario);
        Mockito.when(funcionarioRepository.findByCaixa(1)).thenReturn(funcionarioTeste);
        ResponseEntity<List<Funcionario>> funcionarioTestes = funcionarioController.getFuncionarioPorCaixa(1);
        assertEquals(200, funcionarioTestes.getStatusCodeValue());
        assertEquals(1, funcionarioTestes.getBody().size());
    }

    @Test
    void getFuncionariosPorCaixaInvalido() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCaixa(1);

        Mockito.when(funcionarioRepository.findByCaixa(1)).thenReturn(new ArrayList<>());
        ResponseEntity<List<Funcionario>> resposta = funcionarioController.getFuncionarioPorCaixa(1);
        assertEquals(204, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }
}