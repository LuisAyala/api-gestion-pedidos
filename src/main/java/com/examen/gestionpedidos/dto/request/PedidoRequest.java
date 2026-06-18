package com.examen.gestionpedidos.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    private List<ItemPedidoRequest> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemPedidoRequest {
        @NotNull(message = "El ID del producto es obligatorio")
        private Long productoId;

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad solicitada debe ser mayor a cero") // Regla de negocio 3 [cite: 124]
        private Integer cantidad;
    }
}
