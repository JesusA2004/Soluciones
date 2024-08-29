package com.SolucionesParaPlagas.android.Controlador;

import java.util.HashMap;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;

public class Sesion {

    // Se crea un carrito nuevo vacio
    HashMap<String, Integer> carrito = new HashMap<>();
    private Controlador<JsonCliente> controladorJsonCliente = new ControladorJsonCliente("");
    private ControladorDetalleCliente controladorDetalleCliente = new ControladorDetalleCliente();
    private ControladorClienteIndividual controladorClienteIndividual = new ControladorClienteIndividual();

    public HashMap limpiarSesion(){
        controladorDetalleCliente.limpiarRepositorio();
        controladorJsonCliente.limipiarRepositorio();
        controladorClienteIndividual.limpiarRepositorio();
        return carrito;
    }

}