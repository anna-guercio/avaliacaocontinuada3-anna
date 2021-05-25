package br.com.bandtec.projetocontinuada3.resposta;

import br.com.bandtec.projetocontinuada3.dominio.Funcionario;

public class FuncionarioResposta {

    // Atributos
    private String nomeFuncionario;
    private Integer caixa;

    // Construtor
    public FuncionarioResposta(Funcionario entidade) {
        this.nomeFuncionario = entidade.getNome();
        this.caixa = entidade.getCaixa();
    }

    // Getters and Setters
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public Integer getCaixa() {
        return caixa;
    }

    public void setCaixa(Integer caixa) {
        this.caixa = caixa;
    }
}
