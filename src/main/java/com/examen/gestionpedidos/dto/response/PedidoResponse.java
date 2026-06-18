package com.examen.gestionpedidos.dto.response;

import lombok.*;
        import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {
    private Long id;
    private String cliente; // Nombre + Apellido combinados
    private Double total;
    private String estado;
    private List<DetalleResponse> detalles;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleResponse {
        private Long productoId;
        private String nombreProducto;
        private Integer cantidad;
        private Double precioUnitario;
        private Double subtotal;
    }
}