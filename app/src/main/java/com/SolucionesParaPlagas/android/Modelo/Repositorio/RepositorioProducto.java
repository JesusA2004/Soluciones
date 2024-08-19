package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;

public class RepositorioProducto extends Repositorio<Producto> {

    // Implementación Singleton
    private static Repositorio<Producto> instancia;

    public static Repositorio<Producto> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioProducto();
        }
        return instancia;
    }

}
