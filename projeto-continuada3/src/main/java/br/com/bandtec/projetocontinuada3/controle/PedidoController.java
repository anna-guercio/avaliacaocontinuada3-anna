package br.com.bandtec.projetocontinuada3.controle;

import br.com.bandtec.projetocontinuada3.agendamento.PedidoScheduled;
import br.com.bandtec.projetocontinuada3.agendamento.PedidoProtocolo;
import br.com.bandtec.projetocontinuada3.dominio.Funcionario;
import br.com.bandtec.projetocontinuada3.dominio.PedidoRequisicao;
import br.com.bandtec.projetocontinuada3.util.FilaObj;
import br.com.bandtec.projetocontinuada3.util.PilhaObj;
import br.com.bandtec.projetocontinuada3.repositorio.FuncionarioRepository;
import br.com.bandtec.projetocontinuada3.repositorio.PedidoRepository;
import br.com.bandtec.projetocontinuada3.dominio.Pedido;
import br.com.bandtec.projetocontinuada3.resposta.PedidoResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private static final FilaObj<PedidoRequisicao> fila = new FilaObj<>(10);
    private final PilhaObj<PedidoRequisicao> pilha = new PilhaObj<>(10);
    @Autowired
    private PedidoRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PedidoScheduled scheduled;

    // Consulta todos os pedidos
    @GetMapping
    public ResponseEntity getPedidos() {
        List<Pedido> pedidos = repository.findAll();
        return pedidos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(pedidos.stream().map(PedidoResposta::new).collect(Collectors.toList()));
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
        if (repository.existsById(idFuncionario)) {
            return ResponseEntity.status(200).body(repository.findByFuncionarioId(idFuncionario));
        } else {
            return ResponseEntity.status(404).build();
        }
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
        return ResponseEntity.status(204).body("Não tem nenhuma operação a ser desfeita");
    }


    // Endpoint especial para requisição assíncrona
    @PostMapping("/requisicao")
    public ResponseEntity postRequisicao(@RequestBody @Valid Pedido novaRequisicao) {
        String uuid = UUID.randomUUID().toString();
        PedidoProtocolo protocolo = new PedidoProtocolo(uuid, "Salvando a requisição", novaRequisicao);

        // Salvando na Fila
        scheduled.getFila().insert(protocolo);
        // Retorna o número do protocolo
        return ResponseEntity.status(202).body("Protocolo = " + protocolo.getId());
    }


    // LEITURA DE ARQUIVO - IMPORTAÇÃO DE DADOS
    @PostMapping("/learquivo")
    public ResponseEntity postImportacao(@RequestParam MultipartFile arquivo) throws IOException {
        BufferedReader entrada = null;
        byte[] conteudo = arquivo.getBytes();
        Path path = Paths.get(arquivo.getOriginalFilename());
        Files.write(path, conteudo);
        String registro;
        String tipoRegistro;
        int contRegistro = 0;
        Integer idPedido = 100;
        Integer idFuncionario = 101;
        Integer caixa = 01;
        String nomeCliente = "";
        String nomeFuncionario = "";
        Double valor = 15.00;

        // Abre o arquivo
        try {
            entrada = new BufferedReader(new FileReader(arquivo.getOriginalFilename()));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        // Lê os registros do arquivo
        try {
        // Lê um registro
        registro = entrada.readLine();

        while (registro != null) {
            // Obtém o tipo do registro
            tipoRegistro = registro.substring(0, 2); // obtém os 2 primeiros caracteres do registro
            if (tipoRegistro.equals("00")) {
                System.out.println("Header");
                System.out.println("Tipo de arquivo: " + registro.substring(2, 9));
                System.out.println("Data/hora de geração do arquivo: " + registro.substring(9, 25));
                System.out.println("Versão do layout: " + registro.substring(25, 27));
            } else if (tipoRegistro.equals("03")) {
                System.out.println("\nTrailer");
                int qtdRegistro = Integer.parseInt(registro.substring(2, 7));
                if (qtdRegistro == contRegistro) {
                    System.out.println("Quantidade de registros gravados compatível com quantidade lida");
                } else {
                    System.out.println("Quantidade de registros gravados não confere com quantidade lida");
                }

            } else if (tipoRegistro.equals("01")) {
                if (contRegistro == 0) {
                    System.out.println();
                    System.out.printf("%-8s %-11s %-5s\n", "IDPEDIDO", "NOMECLIENTE", "VALOR");
                }
                idPedido = Integer.parseInt(registro.substring(2, 11).trim());
                nomeCliente = registro.substring(11, 50).trim();
                valor = Double.parseDouble(registro.substring(50, 59).replace(',', '.').trim());
            } else if (tipoRegistro.equals("02")) {
                if (contRegistro == 0) {
                    System.out.println();
                    System.out.printf("%-13s %-15s %-5s\n", "IDFUNCIONARIO", "NOMEFUNCIONARIO", "CAIXA");
                }
                idFuncionario = Integer.parseInt(registro.substring(02, 06).trim());
                nomeFuncionario = registro.substring(06, 52).trim();
                caixa = Integer.parseInt(registro.substring(52, 54).trim());

                Funcionario funcionario = new Funcionario();
                funcionario.setNome(nomeFuncionario);
                funcionario.setCaixa(caixa);

                Pedido pedido = new Pedido();
                pedido.setNomeCliente(nomeCliente);
                pedido.setValor(valor);
                pedido.setFuncionario(funcionario);

                funcionarioRepository.save(funcionario);
                repository.save(pedido);

                System.out.printf("%10d %-40s %10.2f %10d %-40s %2d\n", idPedido, nomeCliente, valor,
                        idFuncionario, nomeFuncionario, caixa);
                contRegistro++;

            } else {
                System.out.println("Tipo de registro inválido");
            }
            registro = entrada.readLine();
        }

        // Fecha o arquivo
        entrada.close();

        } catch(IOException e){
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
        }
        return ResponseEntity.status(201).body("Criação do arquivo");

    }
}
