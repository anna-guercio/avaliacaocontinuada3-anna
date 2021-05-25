package br.com.bandtec.projetocontinuada3;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Funcionario {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome; // nome do funcionario

    private Integer caixa; // Qual caixa este funcionario pertence (ex: caixa 1, caixa 2...)

    @OneToMany(mappedBy = "funcionario") // Atributo em Pedido que tem relacionamento para Funcionario é "funcionario"
    @JsonIgnore
    private List<Pedido> pedidos;

    // Get and Set
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCaixa() {
        return caixa;
    }

    public void setCaixa(Integer caixa) {
        this.caixa = caixa;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
