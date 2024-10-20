package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Carrito;

public class RepositorioCarrito extends Repositorio<Carrito>{

    private static RepositorioCarrito instancia;

    private RepositorioCarrito(){
        super();
    }

    public static RepositorioCarrito obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCarrito();
        }
        return instancia;
    }

}
