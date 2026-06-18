
package com.examen.gestionpedidos.controller;

import com.examen.gestionpedidos.dto.request.PedidoRequest;
import com.examen.gestionpedidos.dto.response.PedidoResponse;
import com.examen.gestionpedidos.response.BaseResponse;
import com.examen.gestionpedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // 1. Crear pedidos para un cliente conteniendo varios productos
    @PostMapping
    public ResponseEntity<BaseResponse<PedidoResponse>> registrarPedido(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse nuevoPedido = pedidoService.crearPedido(request);

        BaseResponse<PedidoResponse> response = BaseResponse.<PedidoResponse>builder()
                .codigo(HttpStatus.CREATED.value())
                .mensaje("Pedido procesado y creado correctamente")
                .objeto(nuevoPedido)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Consultar un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PedidoResponse>> obtenerPorId(@PathVariable Long id) {
        PedidoResponse pedido = pedidoService.buscarPorId(id);

        BaseResponse<PedidoResponse> response = BaseResponse.<PedidoResponse>builder()
                .codigo(HttpStatus.OK.value())
                .mensaje("Pedido encontrado")
                .objeto(pedido)
                .build();

        return ResponseEntity.ok(response);
    }

    // 3. Listar los pedidos de un cliente específico
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<BaseResponse<List<PedidoResponse>>> listarPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponse> pedidos = pedidoService.listarPorCliente(clienteId);

        BaseResponse<List<PedidoResponse>> response = BaseResponse.<List<PedidoResponse>>builder()
                .codigo(HttpStatus.OK.value())
                .mensaje("Historial de pedidos del cliente obtenido")
                .objeto(pedidos)
                .build();

        return ResponseEntity.ok(response);
    }
}
