
package com.examen.gestionpedidos.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
