
package com.examen.gestionpedidos.service;

import com.examen.gestionpedidos.dto.request.ProductoRequest;
import com.examen.gestionpedidos.entity.Producto;
import java.util.List;

public interface ProductoService {
    Producto crear(ProductoRequest request);
    List<Producto> listarTodos();
}
