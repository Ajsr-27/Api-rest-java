package com.talentoapirest.talanto_java.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: producto o categoría inexistente
    @ExceptionHandler({ ProductoNoEncontradoException.class, CategoriaNoEncontradaException.class })
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(RuntimeException e) {
        return construirRespuesta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // 400: stock insuficiente
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> manejarStockInsuficiente(StockInsuficienteException e) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 400: datos inválidos detectados por el Validador
    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<Map<String, Object>> manejarDatosInvalidos(DatosInvalidosException e) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("status", HttpStatus.BAD_REQUEST.value());
        cuerpo.put("mensaje", e.getMessage());
        cuerpo.put("errores", e.getErrores());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
    }

    // 400: JSON mal formado o con tipos incorrectos (por ejemplo, precio: "abc")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(HttpMessageNotReadableException e) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "El cuerpo de la petición es inválido o está mal formado");
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String mensaje) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("status", status.value());
        cuerpo.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(cuerpo);
    }
}