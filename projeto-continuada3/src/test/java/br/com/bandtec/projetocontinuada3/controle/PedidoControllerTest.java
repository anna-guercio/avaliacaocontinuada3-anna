package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.dominio.Funcionario;
import br.com.bandtec.projetocontinuada3.dominio.Pedido;
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
    @DisplayName("GET /impostos - Quando houverem registros - status 200 e número certo de registros")
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
    @DisplayName("GET /impostos - Quando NÃO houverem registros - status 204 e sem corpo")
    void getPedidosSemRegistros() {
        Mockito.when(pedidoRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Pedido>> resposta = pedidoController.getPedidos();
        assertEquals(204, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }


    @Test
    void postPedidoSemErros() {
        Pedido pedido = new Pedido();
        pedido.setId(100);
        pedido.setNomeCliente("Anna");
        pedido.setValor(15.00);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(101);
        funcionario.setNome("Beatriz");
        funcionario.setCaixa(1);
        funcionario.setPedidos(Arrays.asList(pedido));

        pedido.setFuncionario(funcionario);

        Mockito.when(pedidoRepository.existsById(pedido.getFuncionario().getId())).thenReturn(true);
        ResponseEntity resposta = pedidoController.postPedido(pedido);

        assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    void getPedidosPorFuncionario() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setNomeCliente("Anna");
        pedido.setValor(15.00);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Beatriz");
        funcionario.setCaixa(1);
        funcionario.setId(1);

        pedido.setFuncionario(funcionario);

        List<Pedido> pedidosTestes = Arrays.asList(pedido);

        Mockito.when(pedidoRepository.findByFuncionarioId(1)).thenReturn(pedidosTestes);
        ResponseEntity<List<Pedido>> pedidoTestes = pedidoController.getPedidosPorFuncionario(1);
        assertEquals(200, pedidoTestes.getStatusCodeValue());
        assertEquals(1, pedidoTestes.getBody().size());
    }

    @Test
    void deletePedido() {
    }

    @Test
    void desfazer() {
    }

    @Test
    void postRequisicao() {
    }
}