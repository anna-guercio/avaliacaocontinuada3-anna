package br.com.bandtec.projetocontinuada3.resposta;

import br.com.bandtec.projetocontinuada3.dominio.Pedido;

public class PedidoResposta {

    // Atributos
    private String nomeCliente;
    private Double valorPedido;
    private String nomeFuncionario;
    private Integer numCaixa;

    // Construtor
    public PedidoResposta(Pedido entidade) {
        this.nomeCliente = entidade.getNomeCliente();
        this.valorPedido = entidade.getValor();
        this.nomeFuncionario = entidade.getFuncionario().getNome();
        this.numCaixa = entidade.getFuncionario().getCaixa();
    }

    // Getters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public Double getValorPedido() {
        return valorPedido;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public Integer getNumCaixa() {
        return numCaixa;
    }
}
