package com.talentoapirest.talanto_java.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentoapirest.talanto_java.model.Producto;
import com.talentoapirest.talanto_java.service.ProductoService;

@RestController 
@RequestMapping ("/productos")
public class ProductoController {
    
    private final ProductoService productoService;
    
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @GetMapping 
    public ResponseEntity<List<Producto>> listar (){
        return ResponseEntity.ok(productoService.listarProductos()); //200
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Producto> buscarProducto (@PathVariable int id){
        return ResponseEntity.ok(productoService.buscarProducto(id)); //200
    }

    @PostMapping 
    public ResponseEntity<Producto> crearProducto (@RequestBody Producto productoNuevo){
        Producto productoCreado = productoService.crearProducto(productoNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);  // 201
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Producto> actualizarProducto (@PathVariable int id, @RequestBody Producto productoActualizado){
        return ResponseEntity.ok(productoService.actualizarProducto(id, productoActualizado)); //200
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> eliminarProducto (@PathVariable int id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build(); //204
    }   

}