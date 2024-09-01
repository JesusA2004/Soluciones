package com.SolucionesParaPlagas.android.Controlador;

import java.util.HashMap;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;

public class Sesion {

    // Se crea un carrito nuevo vacio
    HashMap<String, Integer> carrito = new HashMap<>();
    private Controlador<JsonCliente> controladorJsonCliente = new ControladorJsonCliente("");
    private ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
    private ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();

    public HashMap limpiarSesion(){
        controladorDetalleCliente.limpiarRepositorio();
        controladorJsonCliente.limipiarRepositorio();
        controladorClienteIndividual.limpiarRepositorio();
        return carrito;
    }

}
