
package com.examen.gestionpedidos.repository;
import com.examen.gestionpedidos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto, Long> {}
