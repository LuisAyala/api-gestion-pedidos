
package com.examen.gestionpedidos.controller;

import com.examen.gestionpedidos.dto.request.ProductoRequest;
import com.examen.gestionpedidos.entity.Producto;
import com.examen.gestionpedidos.response.BaseResponse;
import com.examen.gestionpedidos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<BaseResponse<Producto>> registrarProducto(@Valid @RequestBody ProductoRequest request) {
        Producto productoCreado = productoService.crear(request);

        BaseResponse<Producto> response = BaseResponse.<Producto>builder()
                .codigo(HttpStatus.CREATED.value())
                .mensaje("Producto registrado exitosamente")
                .objeto(productoCreado)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Producto>>> listarProductos() {
        List<Producto> lista = productoService.listarTodos();

        BaseResponse<List<Producto>> response = BaseResponse.<List<Producto>>builder()
                .codigo(HttpStatus.OK.value())
                .mensaje("Catálogo de productos obtenido correctamente")
                .objeto(lista)
                .build();

        return ResponseEntity.ok(response);
    }
}