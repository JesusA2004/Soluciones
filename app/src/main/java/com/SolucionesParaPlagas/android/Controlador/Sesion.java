package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;

public class Sesion {

    // Se crea un carrito nuevo vacio
    private Controlador<JsonCliente> controladorJsonCliente = new ControladorJsonCliente("");
    private ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
    private ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
    private ControladorCarrito controladorCarrito = ControladorCarrito.obtenerInstancia();

    public void limpiarSesion(){
        controladorDetalleCliente.limpiarRepositorio();
        controladorJsonCliente.limipiarRepositorio();
        controladorClienteIndividual.limpiarRepositorio();
        controladorCarrito.vaciarCarrito();
    }

}
