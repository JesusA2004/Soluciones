package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonCliente;

public class RepositorioJsonCliente extends Repositorio<JsonCliente> {

    // Implementación Singleton
    private static RepositorioJsonCliente instancia;

    public static Repositorio<JsonCliente> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioJsonCliente();
        }
        return instancia;
    }

}
