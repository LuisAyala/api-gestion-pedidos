
package com.examen.gestionpedidos.mapper;

import com.examen.gestionpedidos.dto.response.PedidoResponse;
import com.examen.gestionpedidos.entity.Pedido;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponse toResponse(Pedido pedido) {
        if (pedido == null) return null;

        return PedidoResponse.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido())
                .total(pedido.getTotal())
                .estado(pedido.getEstado())
                .detalles(pedido.getDetalles().stream()
                        .map(detalle -> PedidoResponse.DetalleResponse.builder()
                                .productoId(detalle.getProductoId())
                                .nombreProducto(detalle.getNombreProducto())
                                .cantidad(detalle.getCantidad())
                                .precioUnitario(detalle.getPrecioUnitario())
                                .subtotal(detalle.getSubtotal())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}