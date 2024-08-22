package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioProducto;

import java.util.List;

public class ControladorProducto{

    private Repositorio<Producto> repositorioProducto = RepositorioProducto.obtenerInstancia();

    public List<Producto> obtenerRepositorio() {
        return repositorioProducto.getDatos();
    }

    public void enviarDatosRepositorio(List<Producto> listaProductos){
        repositorioProducto.setDatos(listaProductos);
    }

}
