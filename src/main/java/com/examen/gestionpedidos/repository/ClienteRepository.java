
package com.examen.gestionpedidos.repository;
import com.examen.gestionpedidos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
