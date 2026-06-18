
package com.examen.gestionpedidos.exception;

import com.examen.gestionpedidos.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura cuando un Pedido no se encuentra (404 Not Found)
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handlePedidoNotFound(PedidoNotFoundException ex) {
        BaseResponse<Object> response = BaseResponse.builder()
                .codigo(HttpStatus.NOT_FOUND.value())
                .mensaje(ex.getMessage())
                .objeto(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Captura fallos de reglas de negocio relacionados con stock (400 Bad Request)
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<BaseResponse<Object>> handleStockInsuficiente(StockInsuficienteException ex) {
        BaseResponse<Object> response = BaseResponse.builder()
                .codigo(HttpStatus.BAD_REQUEST.value())
                .mensaje(ex.getMessage())
                .objeto(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Captura errores de validación de los DTOs (como @NotBlank, @Min, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        BaseResponse<Map<String, String>> response = BaseResponse.<Map<String, String>>builder()
                .codigo(HttpStatus.BAD_REQUEST.value())
                .mensaje("Error de validación en los datos de entrada")
                .objeto(errores)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Captura cualquier otro error genérico inesperado (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGlobalException(Exception ex) {
        BaseResponse<Object> response = BaseResponse.builder()
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .mensaje("Ocurrió un error interno en el servidor: " + ex.getMessage())
                .objeto(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
