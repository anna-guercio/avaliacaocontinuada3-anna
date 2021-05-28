package br.com.bandtec.projetocontinuada3.agendamento;

import br.com.bandtec.projetocontinuada3.repositorio.PedidoRepository;
import br.com.bandtec.projetocontinuada3.utils.FilaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PedidoScheduled {

    @Autowired
    private PedidoRepository repository;

    FilaObj<PedidoProtocolo> fila = new FilaObj<>(10);

    @Scheduled(fixedRate = 50000)
    public void agendar(){
        if (fila.isEmpty()){
            System.out.println("Fila vazia");
        } else {
            fila.peek().setStatusProtocolo("Inserido na Fila");
            System.out.println(fila.peek().getStatusProtocolo());
            repository.save(fila.poll().getPedido());
        }
    }

    // Getter and Setter
    public PedidoRepository getRepository() {
        return repository;
    }

    public void setRepository(PedidoRepository repository) {
        this.repository = repository;
    }

    public FilaObj<PedidoProtocolo> getFila() {
        return fila;
    }

    public void setFila(FilaObj<PedidoProtocolo> fila) {
        this.fila = fila;
    }
}
