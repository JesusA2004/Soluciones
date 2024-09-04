package com.SolucionesParaPlagas.android.Controlador;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioProducto;

public class ControladorProducto{

    private Repositorio<Producto> repositorioProducto = RepositorioProducto.obtenerInstancia();
    private static ControladorProducto instancia;
    private static final String TAG = "ControladorProducto";

    public static ControladorProducto obtenerInstancia(){
        if(instancia == null){
            instancia = new ControladorProducto();
        }
        return instancia;
    }

    public List<Producto> obtenerRepositorio() {
        return repositorioProducto.getDatos();
    }

    public void enviarDatosRepositorio(List<Producto> listaProductos){
        repositorioProducto.setDatos(listaProductos);
    }

    public void imprimirRepositorio(){
        int contador = 0;
        for (Producto producto : obtenerRepositorio()) {
            contador++;
            Log.d(TAG, "Producto:");
            Log.d(TAG, "Title: " + producto.getTitle());
            /*
            Log.d(TAG, "Description: " + producto.getDescription());
            Log.d(TAG, "Type: " + producto.getType());
            */
            Log.d(TAG, "Weight: " + producto.getWeight());
            Log.d(TAG, "Unit: " + producto.getUnit());
            Log.d(TAG, "UrlImagen: " + producto.getImageUrl());
            Log.d(TAG, "-------------------------");
        }
        Log.d(TAG, "Total: " + contador);
    }

    public List<Producto> productosPorNombre(String nombre){
        List<Producto> productos = new ArrayList<>();
        for(Producto producto : repositorioProducto.getDatos()){
            // Busqueda parcial por nombre del producto
            if(producto.getTitle().startsWith(nombre)){
                productos.add(producto);
            }
        }
        return productos;
    }

    public Producto obtenerProducto(String id){
        Producto producto = new Producto();
        for(Producto prod : repositorioProducto.getDatos()){
            if(prod.getID().equals(id)){
                producto = prod;
            }
        }
        return producto;
    }

}
