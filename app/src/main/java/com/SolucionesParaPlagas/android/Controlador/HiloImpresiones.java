package com.SolucionesParaPlagas.android.Controlador;

public class HiloImpresiones extends Thread{

    private ControladorProducto controladorProducto = new ControladorProducto();

    @Override
    public void run() {
        controladorProducto.imprimirRepositorio();
    }
}
