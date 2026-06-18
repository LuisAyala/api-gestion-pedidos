
package com.examen.gestionpedidos.repository;
import com.examen.gestionpedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId); // Nos servirá para el endpoint 6
}
