
package com.examen.gestionpedidos.service.impl;

import com.examen.gestionpedidos.dto.request.ClienteRequest;
import com.examen.gestionpedidos.entity.Cliente;
import com.examen.gestionpedidos.exception.PedidoNotFoundException;
import com.examen.gestionpedidos.repository.ClienteRepository;
import com.examen.gestionpedidos.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente crear(ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .correo(request.getCorreo())
                .build();
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Cliente no encontrado con el ID: " + id));
    }
}