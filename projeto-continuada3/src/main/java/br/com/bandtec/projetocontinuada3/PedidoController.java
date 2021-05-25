package br.com.bandtec.projetocontinuada3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

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
    public ResponseEntity postPedido(@RequestBody Pedido novoPedido){
        if (funcionarioRepository.existsById(novoPedido.getFuncionario().getId())) {
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
}
