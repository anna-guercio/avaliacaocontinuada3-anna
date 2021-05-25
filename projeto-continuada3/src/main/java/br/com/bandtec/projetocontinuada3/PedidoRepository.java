package br.com.bandtec.projetocontinuada3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Consulta todos os pedidos de um determinado funcionario
    List<Pedido> findByFuncionarioId(Integer idFuncionario);
}
