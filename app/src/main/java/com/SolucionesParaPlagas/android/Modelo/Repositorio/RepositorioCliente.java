package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

public class RepositorioCliente extends Repositorio<Cliente> {

    // Implementación Singleton
    private static Repositorio<Cliente> instancia;

    public static Repositorio<Cliente> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCliente();
        }
        return instancia;
    }

}
