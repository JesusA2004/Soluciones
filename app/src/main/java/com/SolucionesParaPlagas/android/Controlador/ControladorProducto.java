package com.SolucionesParaPlagas.android.Controlador;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioProducto;

public class ControladorProducto extends ControladorListas<Producto> {

    private static ControladorProducto instancia;

    public ControladorProducto(Context context) {
        super(RepositorioProducto.obtenerInstancia(), context);
        nameTable = "producto";
    }

    public static ControladorProducto obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorProducto(context);
        }
        return instancia;
    }

    // En este controlador no se insertaran objetos ya que solo es para consultas
    @Override
    protected boolean insertObjects(Producto objeto) {
        return false;
    }

    @Override
    protected Producto getObjeto(int id) {
        for(Producto producto : repositorioLista.getDatos()){
            if(producto.getFolio() == id){
                return producto;
            }
        }
        return null;
    }

    @Override
    protected List<Producto> BDToObjects(Conector conector) {
        List<Producto> lista = new ArrayList<>();
        if (conector.registro != null) {
            try {
                while (conector.registro.next()) {
                    Producto producto = new Producto();
                    producto.setFolio(conector.registro.getInt(1));
                    producto.setNombreProd(conector.registro.getString(2));
                    producto.setTipo(conector.registro.getString(3));
                    producto.setUnidadM(conector.registro.getString(4));
                    producto.setExistencia(conector.registro.getInt(5));
                    producto.setPeso(conector.registro.getFloat(6));
                    producto.setDescripcion(conector.registro.getString(7));
                    producto.setPrecio(conector.registro.getFloat(8));
                    producto.setUrlImagen(conector.registro.getString(9));
                    producto.setIdProveedor(conector.registro.getInt(10));
                    lista.add(producto);
                }
            } catch (SQLException ex) {
                manejarExcepcion(ex);
            }
        }
        return lista;
    }

    @Override
    protected List<Producto> getList() {
        limpiarRepositorio();
        String query = "Select * from " + nameTable;
        repositorioLista.setDatos(BDToObjects(conector));
        return repositorioLista.getDatos();
    }

    @Override
    protected List<Producto> getList(String parametro, String campo) {
        limpiarRepositorio();
        String query = "call buscarApartados('"+parametro+"', '"+campo+"');";
        conector.registro = ejecutarConsulta(query);
        repositorioLista.setDatos(BDToObjects(conector));
        return repositorioLista.getDatos();
    }

    @Override
    protected List<Producto> getList(String parametro, int campo) {
        return Collections.emptyList();
    }

    // Modificar este metodo para obtener una grafica
    @Override
    protected List<Object[]> getListGraph() {
        List<Object[]> listaProductos = new ArrayList<>();
        String query = "select fechaapartado, count(*) as total_apartados from apartados group by fechaapartado order by fechaapartado;";
        try {
            conector.registro = ejecutarConsulta(query);
            while (conector.registro.next()) {
                String fecha = conector.registro.getString("fechaapartado");
                int total = conector.registro.getInt("total_apartados");
                listaProductos.add(new Object[]{fecha, total});
            }
        }catch (SQLException ex) {
        }
        return listaProductos;
    }

}
