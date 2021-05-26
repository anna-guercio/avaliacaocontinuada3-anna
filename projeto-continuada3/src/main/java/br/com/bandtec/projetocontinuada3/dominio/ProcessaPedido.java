package br.com.bandtec.projetocontinuada3.dominio;

import br.com.bandtec.projetocontinuada3.controle.PedidoController;
import br.com.bandtec.projetocontinuada3.utils.FilaObj;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProcessaPedido {

    @Scheduled(fixedRate = 5000)
    public void processaPedido(){
        FilaObj<PedidoRequisicao> fila = PedidoController.getFila();



    }
}
