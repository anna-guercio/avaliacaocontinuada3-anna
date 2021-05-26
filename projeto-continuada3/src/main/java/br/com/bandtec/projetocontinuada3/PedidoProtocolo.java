package br.com.bandtec.projetocontinuada3;

import br.com.bandtec.projetocontinuada3.dominio.Pedido;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PedidoProtocolo {

    // Atributos
    @Id
    private String id;

    private String status;

    private Pedido pedido;

    // Construtor
    public PedidoProtocolo(String id, String status, Pedido pedido) {
        this.id = id;
        this.status = status;
        this.pedido = pedido;
    }

    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
