
package com.examen.gestionpedidos.service;

import com.examen.gestionpedidos.dto.request.ClienteRequest;
import com.examen.gestionpedidos.entity.Cliente;

public interface ClienteService {
    Cliente crear(ClienteRequest request);
    Cliente buscarPorId(Long id);
}
