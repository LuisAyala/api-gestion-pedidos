
package com.examen.gestionpedidos.service.impl;

import com.examen.gestionpedidos.dto.request.PedidoRequest;
import com.examen.gestionpedidos.dto.response.PedidoResponse;
import com.examen.gestionpedidos.entity.Cliente;
import com.examen.gestionpedidos.entity.Pedido;
import com.examen.gestionpedidos.entity.Producto;
import com.examen.gestionpedidos.exception.PedidoNotFoundException;
import com.examen.gestionpedidos.exception.StockInsuficienteException;
import com.examen.gestionpedidos.repository.PedidoRepository;
import com.examen.gestionpedidos.repository.ProductoRepository;
import com.examen.gestionpedidos.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Cliente clienteDummy;
    private Producto productoDummy;
    private PedidoRequest pedidoRequestDummy;

    @BeforeEach
    void setUp() {
        // Inicializamos datos de prueba comunes
        clienteDummy = Cliente.builder()
                .id(1L)
                .nombre("Luis")
                .apellido("Pérez")
                .dni("12345678")
                .correo("luis@correo.com")
                .build();

        productoDummy = Producto.builder()
                .id(10L)
                .nombre("Laptop Zenbook")
                .precio(1500.0)
                .stock(5)
                .estado(true)
                .build();

        // Creamos una petición con un artículo solicitando 2 unidades
        PedidoRequest.ItemPedidoRequest item = new PedidoRequest.ItemPedidoRequest(10L, 2);
        List<PedidoRequest.ItemPedidoRequest> items = new ArrayList<>();
        items.add(item);

        pedidoRequestDummy = PedidoRequest.builder()
                .clienteId(1L)
                .items(items)
                .build();
    }

    @Test
    @DisplayName("Debería crear un pedido exitosamente cuando hay stock disponible (Happy Path)")
    void crearPedidoExitoso() {
        // Arrange (Configuración de los mocks)
        when(clienteService.buscarPorId(1L)).thenReturn(clienteDummy);
        when(productoRepository.findById(10L)).thenReturn(Optional.of(productoDummy));

        // Simulamos que al guardar el pedido, el repositorio le asigna el ID 100L
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedidoAGuardar = invocation.getArgument(0);
            pedidoAGuardar.setId(100L);
            return pedidoAGuardar;
        });

        // Act (Ejecución del método a probar)
        PedidoResponse response = pedidoService.crearPedido(pedidoRequestDummy);

        // Assert (Validaciones de resultados)
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("CREADO", response.getEstado());
        assertEquals(3000.0, response.getTotal()); // 2 unidades * 1500.0 = 3000.0
        assertEquals(3, productoDummy.getStock()); // El stock debió bajar de 5 a 3

        verify(clienteService, times(1)).buscarPorId(1L);
        verify(productoRepository, times(1)).findById(10L);
        verify(productoRepository, times(1)).save(productoDummy);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería lanzar StockInsuficienteException cuando la cantidad supera el stock disponible")
    void crearPedidoConStockInsuficiente() {
        // Arrange (Configuración para fallar: stock es 5 pero pediremos 10)
        pedidoRequestDummy.getItems().getFirst().setCantidad(10);

        when(clienteService.buscarPorId(1L)).thenReturn(clienteDummy);
        when(productoRepository.findById(10L)).thenReturn(Optional.of(productoDummy));

        // Act & Assert
        assertThrows(StockInsuficienteException.class, () ->
                pedidoService.crearPedido(pedidoRequestDummy)
        );



        // Verificamos que jamás se intentó guardar un pedido ni se redujo el stock
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería lanzar PedidoNotFoundException cuando el producto no existe en el catálogo")
    void crearPedidoConProductoInexistente() {
        // Arrange
        when(clienteService.buscarPorId(1L)).thenReturn(clienteDummy);
        when(productoRepository.findById(10L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PedidoNotFoundException.class, () ->
            pedidoService.crearPedido(pedidoRequestDummy)
        );

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}
