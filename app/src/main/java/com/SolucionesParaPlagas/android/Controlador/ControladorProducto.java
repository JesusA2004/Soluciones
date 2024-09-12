package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import java.util.ArrayList;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioProducto;

public class ControladorProducto{

    private Repositorio<Producto> repositorioProducto = RepositorioProducto.obtenerInstancia();
    private static ControladorProducto instancia;

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

    public List<Producto> productosParcial(String nombre){
        nombre = nombre.toLowerCase();
        List<Producto> productos = new ArrayList<>();
        for(Producto producto : repositorioProducto.getDatos()){
            // Busqueda parcial por nombre del producto
            if(producto.getTitle().toLowerCase().contains(nombre)){
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
