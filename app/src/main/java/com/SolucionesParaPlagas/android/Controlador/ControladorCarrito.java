package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCarrito;

import java.util.HashMap;

public class ControladorCarrito{

    RepositorioCarrito repositorioCarrito = RepositorioCarrito.obtenerInstancia();

    // Implementación Singleton
    private static ControladorCarrito instancia;

    public static ControladorCarrito obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorCarrito();
        }
        return instancia;
    }

    public void vaciarCarrito(){
        repositorioCarrito.vaciarCarrito();
    }

    public HashMap<String, Integer> obtenerCarrito(){
        return repositorioCarrito.obtenerCarrito();
    }

    public String obtenerIdProducto(int posicion){
        return repositorioCarrito.obtenerIdProducto(posicion);
    }

    public int obtenerCantidadProducto(String idProducto){
        return repositorioCarrito.obtenerCantidadProducto(idProducto);
    }

    public void agregarProducto(String idProducto, int cantidad) {
        repositorioCarrito.agregarProducto(idProducto, cantidad);
    }

    public void eliminarProducto(String idProducto) {
        repositorioCarrito.eliminarProducto(idProducto);
    }

    public void actualizarCantidad(String idProducto, int nuevaCantidad) {
        repositorioCarrito.actualizarCantidad(idProducto, nuevaCantidad);
    }

    public boolean existeEnCarrito(String idProducto){
        return repositorioCarrito.existeEnCarrito(idProducto);
    }

}
