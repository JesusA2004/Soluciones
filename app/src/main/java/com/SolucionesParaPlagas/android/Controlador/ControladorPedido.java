package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioPedido;

import java.util.List;

public class ControladorPedido {

    private Repositorio<PedidoIndividual> repositorioPedido = RepositorioPedido.obtenerInstancia();

    public List<PedidoIndividual> obtenerRepositorio() {
        return repositorioPedido.getDatos();
    }

    public void enviarDatosRepositorio(List<PedidoIndividual> listaPedidoIndividuals){
        repositorioPedido.setDatos(listaPedidoIndividuals);
    }

    public void enviarDatoRepositorio(PedidoIndividual PedidoIndividual){
        repositorioPedido.setDato(PedidoIndividual);
    }

    public PedidoIndividual obtenerPedido(){
        return repositorioPedido.getDato();
    }

    public void limpiarRepositorio(){
        repositorioPedido.clearList();
    }

}
