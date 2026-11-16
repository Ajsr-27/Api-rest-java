package com.talentoapirest.talanto_java.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talentoapirest.talanto_java.exception.CategoriaNoEncontradaException;
import com.talentoapirest.talanto_java.exception.ProductoNoEncontradoException;
import com.talentoapirest.talanto_java.model.Categoria;
import com.talentoapirest.talanto_java.model.Producto;
import com.talentoapirest.talanto_java.repository.CategoriaRepository;
import com.talentoapirest.talanto_java.repository.ProductoRepository;
import com.talentoapirest.talanto_java.util.Validador;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Crear un producto
    public Producto crearProducto(Producto productoNuevo) {
        Validador.validarProducto(productoNuevo);

        // Evita que un "id" enviado en el JSON actualice un producto existente
        productoNuevo.setId(0);
        asignarCategoriaExistente(productoNuevo);
        return productoRepository.save(productoNuevo);
    }

    // Listar todos los productos
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar un producto por su ID
    @Transactional(readOnly = true)
    public Producto buscarProducto(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(
                        "El producto con ID " + id + " no existe."));
    }

    // Actualizar un producto existente
    public Producto actualizarProducto(int id, Producto productoActualizado) {
        Producto productoExistente = buscarProducto(id);

        Validador.validarProducto(productoActualizado);
        asignarCategoriaExistente(productoActualizado);

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setCategoria(productoActualizado.getCategoria());

        return productoRepository.save(productoExistente);
    }

    // Eliminar un producto existente
    public void eliminarProducto(int id) {
        Producto productoExistente = buscarProducto(id);
        productoRepository.delete(productoExistente);
    }

    // Reemplaza la categoría recibida por la que existe en la base de datos.
    // Si no se envía categoría, el producto queda sin categoría.
    private void asignarCategoriaExistente(Producto producto) {
        if (producto.getCategoria() == null
                || producto.getCategoria().getId() <= 0) {
            producto.setCategoria(null);
            return;
        }

        int idCategoria = producto.getCategoria().getId();

        Categoria categoriaExistente = categoriaRepository
                .findById(idCategoria)
                .orElseThrow(() -> new CategoriaNoEncontradaException(
                        "La categoría con ID " + idCategoria + " no existe."));

        producto.setCategoria(categoriaExistente);
    }
}