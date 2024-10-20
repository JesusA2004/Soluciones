package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;

public class RepositorioProducto extends RepositorioLista<Producto> {

    // Implementación Singleton
    private static RepositorioLista<Producto> instancia;

    public static RepositorioLista<Producto> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioProducto();
        }
        return instancia;
    }

}
