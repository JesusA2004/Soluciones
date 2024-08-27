package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;

public class RepositorioPedido extends Repositorio<PedidoIndividual>{

    private static RepositorioPedido instancia;

    private RepositorioPedido(){
        super();
    }

    public static RepositorioPedido obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioPedido();
        }
        return instancia;
    }

}
