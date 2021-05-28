package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.dominio.Funcionario;
import br.com.bandtec.projetocontinuada3.dominio.Pedido;
import br.com.bandtec.projetocontinuada3.dominio.PedidoRequisicao;
import br.com.bandtec.projetocontinuada3.repositorio.FuncionarioRepository;
import br.com.bandtec.projetocontinuada3.repositorio.PedidoRepository;
import br.com.bandtec.projetocontinuada3.resposta.PedidoResposta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PedidoControllerTest {

    @Autowired
    private PedidoController pedidoController;

    @Autowired
    private FuncionarioController funcionarioController;

    @MockBean
    PedidoRepository pedidoRepository;

    @MockBean
    FuncionarioRepository funcionarioRepository;

    @Test
    @DisplayName("GET /pedidos - Quando houverem registros - status 200 e número certo de registros")
    void getPedidosComRegistros() {
        Pedido pedido = new Pedido();
        pedido.setNomeCliente("Anna");
        pedido.setValor(15.00);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Beatriz");
        funcionario.setCaixa(1);

        pedido.setFuncionario(funcionario);

        List<Pedido> pedidosTestes = Arrays.asList(pedido);

        Mockito.when(pedidoRepository.findAll()).thenReturn(pedidosTestes);
        ResponseEntity<List<Pedido>> resposta = pedidoController.getPedidos();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    @DisplayName("GET /pedidos - Quando NÃO houverem registros - status 204 e sem corpo")
    void getPedidosSemRegistros() {
        Mockito.when(pedidoRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Pedido>> resposta = pedidoController.getPedidos();
        assertEquals(204, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void postPedidoSemErros() {
        Pedido pedido = new Pedido();
        pedido.setNomeCliente("Anna");
        pedido.setValor(15.00);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(101);
        pedido.setFuncionario(funcionario);

        Mockito.when(funcionarioRepository.existsById(pedido.getFuncionario().getId())).thenReturn(true);
        ResponseEntity resposta = pedidoController.postPedido(pedido);
        assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    void getPedidosPorFuncionario() {
        Pedido pedido = new Pedido();
        Funcionario funcionario = new Funcionario();
        Integer idFuncionario = 1;
        Mockito.when(pedidoRepository.existsById(idFuncionario)).thenReturn(true);
        ResponseEntity pedidosTestes = pedidoController.getPedidosPorFuncionario(idFuncionario);
        assertEquals(200, pedidosTestes.getStatusCodeValue());
    }

    @Test
    void deletePedidoValido() {
        Integer idPedido = 1;
        Mockito.when(pedidoRepository.existsById(idPedido)).thenReturn(true);
        ResponseEntity resposta = pedidoController.deletePedido(idPedido);
        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    void deletePedidoInvalido() {
        int idPedido = 1;
        Mockito.when(pedidoRepository.existsById(idPedido)).thenReturn(false);
        ResponseEntity resposta = pedidoController.deletePedido(idPedido);
        assertEquals(404, resposta.getStatusCodeValue());
    }

    @Test
    void desfazer() throws InterruptedException{
        Thread.sleep(50000);
        ResponseEntity resposta = pedidoController.desfazer();
        assertEquals(200, resposta);
    }
}