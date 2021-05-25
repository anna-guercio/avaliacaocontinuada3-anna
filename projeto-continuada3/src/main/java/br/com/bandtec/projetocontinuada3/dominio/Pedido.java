package br.com.bandtec.projetocontinuada3.dominio;

import br.com.bandtec.projetocontinuada3.dominio.Funcionario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Pedido {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nomeCliente; // nome do cliente que fez o pedido

    @Positive
    @NotNull
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
