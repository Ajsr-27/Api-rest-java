package com.talentoapirest.talanto_java.util;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.talentoapirest.talanto_java.exception.DatosInvalidosException;
import com.talentoapirest.talanto_java.model.Categoria;
import com.talentoapirest.talanto_java.model.Producto;

/**
 * Concentra las validaciones de datos de las entidades.
 * Si algún campo es inválido, lanza DatosInvalidosException (HTTP 400)
 * con el detalle de todos los errores encontrados.
 */
public class Validador {

    private static final int LARGO_MAXIMO_NOMBRE = 100;

    // Clase de utilidad: no se instancia
    private Validador() {
    }

    public static void validarProducto(Producto producto) {
        Map<String, String> errores = new LinkedHashMap<>();

        if (producto == null) {
            throw new DatosInvalidosException(
                    Map.of("producto", "Los datos del producto son obligatorios"));
        }

        validarNombre(producto.getNombre(), "nombre", errores);

        BigDecimal precio = producto.getPrecio();
        if (precio == null) {
            errores.put("precio", "El precio es obligatorio");
        } else if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            errores.put("precio", "El precio debe ser mayor a 0");
        }

        if (producto.getStock() < 0) {
            errores.put("stock", "El stock no puede ser negativo");
        }

        lanzarSiHayErrores(errores);
    }

    public static void validarCategoria(Categoria categoria) {
        Map<String, String> errores = new LinkedHashMap<>();

        if (categoria == null) {
            throw new DatosInvalidosException(
                    Map.of("categoria", "Los datos de la categoría son obligatorios"));
        }

        validarNombre(categoria.getNombre(), "nombre", errores);

        lanzarSiHayErrores(errores);
    }

    // Validación compartida del nombre (obligatorio y con largo máximo)
    private static void validarNombre(String nombre, String campo, Map<String, String> errores) {
        if (nombre == null || nombre.isBlank()) {
            errores.put(campo, "El nombre es obligatorio");
        } else if (nombre.length() > LARGO_MAXIMO_NOMBRE) {
            errores.put(campo, "El nombre no puede superar los " + LARGO_MAXIMO_NOMBRE + " caracteres");
        }
    }

    private static void lanzarSiHayErrores(Map<String, String> errores) {
        if (!errores.isEmpty()) {
            throw new DatosInvalidosException(errores);
        }
    }
}