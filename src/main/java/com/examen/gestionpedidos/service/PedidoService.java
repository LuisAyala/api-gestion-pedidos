
package com.examen.gestionpedidos.service;

import com.examen.gestionpedidos.dto.request.PedidoRequest;
import com.examen.gestionpedidos.dto.response.PedidoResponse;
import java.util.List;

public interface PedidoService {
    PedidoResponse crearPedido(PedidoRequest request);
    PedidoResponse buscarPorId(Long id);
    List<PedidoResponse> listarPorCliente(Long clienteId);
}