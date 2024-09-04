package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import java.util.HashMap;

public class RepositorioCarrito {

    private HashMap<String, Integer> carrito = new HashMap<>();

    // Implementación Singleton
    private static RepositorioCarrito instancia;

    public static RepositorioCarrito obtenerInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCarrito();
        }
        return instancia;
    }

    // Constructor privado para evitar la instanciación
    private RepositorioCarrito(){

    }

    public void vaciarCarrito(){
        carrito.clear();
    }

    public HashMap<String, Integer> obtenerCarrito() {
        return carrito;
    }

    public void agregarProducto(String idProducto, int cantidad) {
        carrito.put(idProducto, cantidad);
    }

    public void eliminarProducto(String idProducto) {
        carrito.remove(idProducto);
    }

    public void actualizarCantidad(String idProducto, int nuevaCantidad) {
        if (carrito.containsKey(idProducto)) {
            carrito.put(idProducto, nuevaCantidad);
        }
    }

    public boolean existeEnCarrito(String idProducto){
        if(carrito.containsKey(idProducto)){
            return true;
        }else{
            return false;
        }
    }

    public String obtenerIdProducto(int posicion) {
        if (posicion >= 0 && posicion < carrito.size()) {
            return (String) carrito.keySet().toArray()[posicion];
        } else {
            return null; // O lanza una excepción si lo prefieres
        }
    }

    public int obtenerCantidadProducto(String idProducto){
        return carrito.get(idProducto);
    }

}
