

package com.examen.gestionpedidos.controller;

import com.examen.gestionpedidos.dto.request.ClienteRequest;
import com.examen.gestionpedidos.entity.Cliente;
import com.examen.gestionpedidos.response.BaseResponse;
import com.examen.gestionpedidos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<BaseResponse<Cliente>> registrarCliente(@Valid @RequestBody ClienteRequest request) {
        Cliente clienteCreado = clienteService.crear(request);

        BaseResponse<Cliente> response = BaseResponse.<Cliente>builder()
                .codigo(HttpStatus.CREATED.value())
                .mensaje("Cliente registrado exitosamente")
                .objeto(clienteCreado)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}