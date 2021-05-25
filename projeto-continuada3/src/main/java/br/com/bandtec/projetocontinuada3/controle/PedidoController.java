package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.PilhaObj;
import br.com.bandtec.projetocontinuada3.repositorio.FuncionarioRepository;
import br.com.bandtec.projetocontinuada3.repositorio.PedidoRepository;
import br.com.bandtec.projetocontinuada3.dominio.Pedido;
import br.com.bandtec.projetocontinuada3.resposta.PedidoResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PilhaObj<Pedido> pilha = new PilhaObj<>(10);

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Consulta todos os pedidos
    @GetMapping
    public ResponseEntity getPedidos(){
        return ResponseEntity.status(200).body(
                repository.findAll().stream().map(PedidoResposta::new).collect(Collectors.toList()));
    }

    // Cria um novo pedido se existir o id daquele funcionario
    @PostMapping
    public ResponseEntity postPedido(@RequestBody @Valid Pedido novoPedido){
        if (funcionarioRepository.existsById(novoPedido.getFuncionario().getId())) {
            pilha.push(novoPedido);
            repository.save(novoPedido);
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(400).body("Este funcionário não existe!");
        }
    }

    // Consulta todos os pedidos de um determinado funcionario (de acordo com o idFuncionario)
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity getPedidosPorFuncionario(@PathVariable Integer idFuncionario){
        return ResponseEntity.status(200).body(repository.findByFuncionarioId(idFuncionario));
    }

    // Exclui um pedido pelo id desse pedido
    @DeleteMapping("/{idPedido}")
    public ResponseEntity deletePedido(@PathVariable Integer idPedido) {
        if (repository.existsById(idPedido)) {
            repository.deleteById(idPedido);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // Endpoint para DESFAZER
    @DeleteMapping("/desfazer")
    public ResponseEntity desfazer() {
        if (!pilha.isEmpty()) {
            repository.deleteById(pilha.peek().getId());
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).body("Não há operações para serem desfeitas");
        }
    }
}
