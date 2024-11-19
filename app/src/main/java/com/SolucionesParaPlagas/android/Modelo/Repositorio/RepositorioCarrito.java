package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;

// Clase para mostrar el carrito de compras actual

public class RepositorioCarrito extends Repositorio<Compra>{

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
