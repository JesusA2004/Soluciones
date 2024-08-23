package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;

public class HiloCargarDatos extends Thread{

    private Controlador<JsonProducto> controlador = new ControladorJsonProducto();

    @Override
    public void run() {
        controlador.realizarSolicitud();
    }
}
