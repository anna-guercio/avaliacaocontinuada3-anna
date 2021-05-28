package br.com.bandtec.projetocontinuada3.agendamento;

import br.com.bandtec.projetocontinuada3.dominio.Pedido;

import javax.persistence.Id;

public class PedidoProtocolo {

    // Atributos
    @Id
    private String id;

    private String statusProtocolo;

    private Pedido pedido;

    // Construtor
    public PedidoProtocolo(String id, String statusProtocolo, Pedido pedido) {
        this.id = id;
        this.statusProtocolo = statusProtocolo;
        this.pedido = pedido;
    }

    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusProtocolo() {
        return statusProtocolo;
    }

    public void setStatusProtocolo(String statusProtocolo) {
        this.statusProtocolo = statusProtocolo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
