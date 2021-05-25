package br.com.bandtec.projetocontinuada3.repositorio;

import br.com.bandtec.projetocontinuada3.dominio.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findByCaixa(Integer caixa);
}
