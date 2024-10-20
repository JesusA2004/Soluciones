package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Compras;

public class RepositorioCompras extends RepositorioLista<Compras>{

    private static RepositorioCompras instancia;

    private RepositorioCompras() {
        super();
    }

    public static RepositorioCompras obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCompras();
        }
        return instancia;
    }

}
