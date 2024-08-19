package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;

public class RepositorioJsonProducto extends Repositorio<JsonProducto> {

    // Implementación Singleton
    private static RepositorioJsonProducto instancia;

    public static Repositorio<JsonProducto> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioJsonProducto();
        }
        return instancia;
    }

}
