package com.talentoapirest.talanto_java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talentoapirest.talanto_java.exception.ProductoNoEncontradoException;
import com.talentoapirest.talanto_java.model.Producto;

@Service 
public class ProductoService {

    private List<Producto> listaProductos;
    private int siguienteId = 1;

    public ProductoService(){
        listaProductos = new ArrayList<>();
    }
    
    // Metodo de creacion de productos
    public Producto crearProducto (Producto productoNuevo){

        productoNuevo.setId(siguienteId);

        listaProductos.add (productoNuevo);

        siguienteId++;
        return productoNuevo;
    }

    // Metodo de listado de productos
    public List<Producto> listarProductos(){
        return listaProductos;
    }

    //Metodo de busqueda por id de producto
    public Producto buscarProducto(int id)
        throws ProductoNoEncontradoException {

        for (Producto productoActual : listaProductos){
            if (productoActual.getId() == id ){
                return productoActual;
            }
        }

        throw new ProductoNoEncontradoException(
        "El producto con ID " + id + " no existe.");
    }
    
    // Metodo de actualizacion de producto por ID
    public Producto actualizarProducto(
        int id, Producto productoActualizado)
        throws ProductoNoEncontradoException {

            Producto productoEncontrado = buscarProducto(id);

            productoEncontrado.setNombre(productoActualizado.getNombre());
            productoEncontrado.setPrecio(productoActualizado.getPrecio());
            productoEncontrado.setStock(productoActualizado.getStock());
            productoEncontrado.setCategoria(productoActualizado.getCategoria());


        return productoEncontrado;
    }
    
    //Metodo de eliminacion de producto por ID    
    public void eliminarProducto( int id)throws ProductoNoEncontradoException {

        Producto productoEncontrado = buscarProducto(id);

        listaProductos.remove(productoEncontrado);
        
    }   
}
