package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

public class RepositorioDetalleCliente extends Repositorio<DetalleCliente>{

    // Implementación Singleton
    private static Repositorio<DetalleCliente> instancia;

    public static Repositorio<DetalleCliente> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioDetalleCliente();
        }
        return instancia;
    }
}
