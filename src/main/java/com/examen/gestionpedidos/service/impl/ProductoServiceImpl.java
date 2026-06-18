
package com.examen.gestionpedidos.service.impl;

import com.examen.gestionpedidos.dto.request.ProductoRequest;
import com.examen.gestionpedidos.entity.Producto;
import com.examen.gestionpedidos.repository.ProductoRepository;
import com.examen.gestionpedidos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public Producto crear(ProductoRequest request) {
        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .stock(request.getStock())
                .estado(true) // Todo producto nuevo inicia como Activo
                .build();
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
}
