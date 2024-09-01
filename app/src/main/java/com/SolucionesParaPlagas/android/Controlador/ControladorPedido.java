package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioPedido;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;

public class ControladorPedido {

    private Repositorio<PedidoIndividual> repositorioPedido = RepositorioPedido.obtenerInstancia();
    // Instancia única de la clase
    private static ControladorPedido instancia;

    // Constructor privado para evitar instancias externas
    private ControladorPedido() {

    }

    // Método para obtener la única instancia de la clase
    public static ControladorPedido obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorPedido();
        }
        return instancia;
    }

    public List<PedidoIndividual> obtenerRepositorio() {
        return repositorioPedido.getDatos();
    }

    public void enviarDatosRepositorio(List<PedidoIndividual> listaPedidoIndividuals){
        repositorioPedido.setDatos(listaPedidoIndividuals);
    }

    public void enviarDatoRepositorio(PedidoIndividual PedidoIndividual){
        repositorioPedido.setDato(PedidoIndividual);
    }

    public void limpiarRepositorio(){
        repositorioPedido.clearList();
    }

}
