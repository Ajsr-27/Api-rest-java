package com.talentoapirest.talanto_java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> manejarNoEncontrado(ProductoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A ocurrido un error : " + e.getMessage()); // 404
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> manejarStockInsuficiente(StockInsuficienteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A ocurrido un error : " + e.getMessage()); // 400
    }
}
