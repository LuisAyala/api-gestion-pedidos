
package com.examen.gestionpedidos.service.impl;

import com.examen.gestionpedidos.dto.request.PedidoRequest;
import com.examen.gestionpedidos.dto.response.PedidoResponse;
import com.examen.gestionpedidos.entity.Cliente;
import com.examen.gestionpedidos.entity.DetallePedido;
import com.examen.gestionpedidos.entity.Pedido;
import com.examen.gestionpedidos.entity.Producto;
import com.examen.gestionpedidos.exception.PedidoNotFoundException;
import com.examen.gestionpedidos.exception.StockInsuficienteException;
import com.examen.gestionpedidos.mapper.PedidoMapper;
import com.examen.gestionpedidos.repository.PedidoRepository;
import com.examen.gestionpedidos.repository.ProductoRepository;
import com.examen.gestionpedidos.service.ClienteService;
import com.examen.gestionpedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ClienteService clienteService;

    @Override
    @Transactional
    public PedidoResponse crearPedido(PedidoRequest request) {
        // 1. Validar y obtener el cliente
        Cliente cliente = clienteService.buscarPorId(request.getClienteId());

        // 2. Preparar la instancia del Pedido inicial
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .estado("CREADO") // Estado por defecto solicitado
                .total(0.0)
                .detalles(new ArrayList<>())
                .build();

        double totalAcumulado = 0.0;
        List<DetallePedido> detalles = new ArrayList<>();

        // 3. Iterar y procesar cada artículo enviado en la petición
        for (PedidoRequest.ItemPedidoRequest item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new PedidoNotFoundException("Producto no encontrado con el ID: " + item.getProductoId()));

            // Regla de Negocio: Validar stock suficiente
            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto '"
                        + producto.getNombre() + "'. Stock disponible: " + producto.getStock()
                        + ", Solicitado: " + item.getCantidad());
            }

            // Regla de Negocio: Descontar stock
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            // Calcular Subtotal
            double subtotal = producto.getPrecio() * item.getCantidad();
            totalAcumulado += subtotal;

            // Construir el Detalle
            DetallePedido detalle = DetallePedido.builder()
                    .productoId(producto.getId())
                    .nombreProducto(producto.getNombre())
                    .cantidad(item.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .subtotal(subtotal)
                    .pedido(pedido)
                    .build();

            detalles.add(detalle);
        }

        // Asignar los totales y la lista de detalles consolidados al Pedido
        pedido.setTotal(totalAcumulado);
        pedido.setDetalles(detalles);

        // Guardar en Base de Datos (Por CascadeType.ALL se guardarán también los detalles)
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Retornar transformado en DTO limpio
        return PedidoMapper.toResponse(pedidoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con el ID: " + id));
        return PedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> listarPorCliente(Long clienteId) {
        // Validamos primero que el cliente exista
        clienteService.buscarPorId(clienteId);

        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        return pedidos.stream()
                .map(PedidoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
