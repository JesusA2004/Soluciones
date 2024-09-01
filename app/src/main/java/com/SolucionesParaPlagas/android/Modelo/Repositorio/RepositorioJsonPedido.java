package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.JsonPedido;

public class RepositorioJsonPedido extends Repositorio<JsonPedido> {

    // Implementación Singleton
    private static RepositorioJsonPedido instancia;

    public static Repositorio<JsonPedido> obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioJsonPedido();
        }
        return instancia;
    }

}
