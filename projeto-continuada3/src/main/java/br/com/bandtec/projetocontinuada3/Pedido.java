package br.com.bandtec.projetocontinuada3;

import javax.persistence.*;

@Entity
public class Pedido {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeCliente; // nome do cliente que fez o pedido

    private Double valor; // qual o valor final do pedido

    @ManyToOne
    private Funcionario funcionario; // Qual o funcionario que atendeu este pedido e qual caixa ele pertence

    // Get and Set
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
