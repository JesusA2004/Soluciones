package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

public class RepositorioCliente extends Repositorio<Cliente>{

    private static RepositorioCliente instancia;

    // Constructor privado para evitar instancias externas
    private RepositorioCliente(){
        super();
    }

    public static RepositorioCliente obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCliente();
        }
        return instancia;
    }

}
