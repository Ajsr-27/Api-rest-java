package com.talentoapirest.talanto_java.exception;

import java.util.Map;

public class DatosInvalidosException extends RuntimeException {

    private final Map<String, String> errores;

    public DatosInvalidosException(Map<String, String> errores) {
        super("Los datos enviados no son válidos");
        this.errores = errores;
    }

    public Map<String, String> getErrores() {
        return errores;
    }
}