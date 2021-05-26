package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.Agendamento;
import br.com.bandtec.projetocontinuada3.PedidoProtocolo;
import br.com.bandtec.projetocontinuada3.dominio.PedidoRequisicao;
import br.com.bandtec.projetocontinuada3.utils.FilaObj;
import br.com.bandtec.projetocontinuada3.utils.PilhaObj;
import br.com.bandtec.projetocontinuada3.repositorio.FuncionarioRepository;
import br.com.bandtec.projetocontinuada3.repositorio.PedidoRepository;
import br.com.bandtec.projetocontinuada3.dominio.Pedido;
import br.com.bandtec.projetocontinuada3.resposta.PedidoResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PilhaObj<PedidoRequisicao> pilha = new PilhaObj<>(10);
    private static FilaObj<PedidoRequisicao> fila = new FilaObj<>(10);


    @Autowired
    private PedidoRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private Agendamento agendamento;

    // Consulta todos os pedidos
    @GetMapping
    public ResponseEntity getPedidos() {
        return ResponseEntity.status(200).body(
                repository.findAll().stream().map(PedidoResposta::new).collect(Collectors.toList()));
    }

    // Cria um novo pedido se existir o id daquele funcionario
    @PostMapping
    public ResponseEntity postPedido(@RequestBody @Valid Pedido novoPedido) {
        if (funcionarioRepository.existsById(novoPedido.getFuncionario().getId())) {
            pilha.push(new PedidoRequisicao(novoPedido, "post"));
            fila.insert(new PedidoRequisicao(novoPedido, "post"));
            repository.save(novoPedido);
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(400).body("Este funcionário não existe!");
        }
    }

    // Consulta todos os pedidos de um determinado funcionario (de acordo com o idFuncionario)
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity getPedidosPorFuncionario(@PathVariable Integer idFuncionario) {
        return ResponseEntity.status(200).body(repository.findByFuncionarioId(idFuncionario));
    }

    // Exclui um pedido pelo id desse pedido
    @DeleteMapping("/{idPedido}")
    public ResponseEntity deletePedido(@PathVariable Integer idPedido) {
        if (repository.existsById(idPedido)) {
            Optional<Pedido> pedido = repository.findById(idPedido);
            pilha.push(new PedidoRequisicao(pedido.get(), "delete"));
            repository.deleteById(idPedido);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // Endpoint para DESFAZER
    @GetMapping("/desfazer")
    public ResponseEntity desfazer() {
        if (!pilha.isEmpty()) {
            PedidoRequisicao pedidoRequisicao = pilha.pop();
            if (pedidoRequisicao.getMetodo().equals("post")) {
                repository.deleteById(pedidoRequisicao.getPedido().getId());
                return ResponseEntity.status(200).build();
            } else if (pedidoRequisicao.getMetodo().equals("delete")) {
                repository.save(pedidoRequisicao.getPedido());
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(204).body("Não há operações para serem desfeitas");
    }

    // Endpoint especial para requisição assíncrona
    @PostMapping("/requisicao")
    public ResponseEntity postRequisicao(@RequestBody @Valid Pedido novaRequisicao) {
        String uuid = UUID.randomUUID().toString();
        PedidoProtocolo protocolo = new PedidoProtocolo(uuid, "Salvando requisição", novaRequisicao);
        agendamento.getFila().insert(protocolo);
        return ResponseEntity.status(202).body("Protocolo: " + protocolo.getId());
    }
}