package com.examen.gestionpedidos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El DNI es obligatoro")
    @Size(min=8, max = 15, message = "El DNI debe tener entre 8 y 15 caracteres")
    private String dni;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es invalido")
    private String correo;
}
