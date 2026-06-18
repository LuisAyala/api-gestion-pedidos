package com.examen.gestionpedidos.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse <T>{
    private Integer codigo;
    private String  mensaje;
    private T       objeto;
}
