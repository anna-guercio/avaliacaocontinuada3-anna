package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.utils.PilhaObj;
import br.com.bandtec.projetocontinuada3.repositorio.FuncionarioRepository;
import br.com.bandtec.projetocontinuada3.dominio.Funcionario;
import br.com.bandtec.projetocontinuada3.resposta.FuncionarioResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    private PilhaObj<Funcionario> pilha = new PilhaObj<>(10);

    // Consulta todos os funcionarios
    @GetMapping
    public ResponseEntity getFuncionarios() {
        List<Funcionario> funcionarios = repository.findAll();
        return funcionarios.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(funcionarios.stream().map(FuncionarioResposta::new).collect(Collectors.toList()));
    }

    // Criação de um novo funcionário
    @PostMapping
    public ResponseEntity postFuncionario(@RequestBody @Valid Funcionario novoFuncionario) {
        repository.save(novoFuncionario);
        return ResponseEntity.status(201).build();
    }

    // Exclui um funcionario pelo idFuncionario
    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity deleteFuncionario(@PathVariable Integer idFuncionario) {
        if (repository.existsById(idFuncionario)) {
            repository.deleteById(idFuncionario);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // Consulta o funcionario de um caixa especifico
    @GetMapping("/caixa/{caixa}")
    public ResponseEntity getFuncionarioPorCaixa(@PathVariable Integer caixa){
        List<Funcionario> funcionariosCaixa = repository.findByCaixa(caixa);
        return funcionariosCaixa.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(funcionariosCaixa);
    }

    // Endpoint para DESFAZER
    @DeleteMapping("/desfazer")
    public ResponseEntity desfazer(){
        if (repository.existsById(pilha.peek().getId())) {
            repository.delete(pilha.pop());
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(204).body("Não tem nenhuma operação a ser desfeita");
        }
    }
}
