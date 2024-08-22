package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class RepositorioCliente extends Repositorio<ClienteIndividual> {

    // Implementación Singleton
    private static Repositorio<ClienteIndividual> instancia;

    public static Repositorio<ClienteIndividual> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCliente();
        }
        return instancia;
    }

}
