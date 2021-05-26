package br.com.bandtec.projetocontinuada3.dominio;

public class PedidoRequisicao {

    // Atributos
    private Pedido pedido;
    private String metodo;

    // Construtor
    public PedidoRequisicao(Pedido pedido, String metodo) {
        this.pedido = pedido;
        this.metodo = metodo;
    }

    // Getter and Settes
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
