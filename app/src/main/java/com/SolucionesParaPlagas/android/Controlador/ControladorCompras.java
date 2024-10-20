package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compras;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCompras;

public class ControladorCompras extends ControladorListas<Compras> {

    public ControladorCompras(Context context) {
        super(RepositorioCompras.obtenerInstancia(), context);
        nameTable = "notaVenta";
    }

    private static ControladorCompras instancia;

    public static ControladorCompras obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorCompras(context);
        }
        return instancia;
    }

    @Override
    protected boolean insertObjects(Compras objeto) {
        return false;
    }

    @Override
    protected Compras getObjeto(int id) {
        return null;
    }

    // Método para convertir el ResultSet en una lista de objetos Compras
    @Override
    protected List<Compras> BDToObjects(Conector conector) {
        List<Compras> listaCompras = new ArrayList<>();
        if (conector.registro != null) {
            try {
                Compras compraActual = null;
                int idCompraActual = -1;

                while (conector.registro.next()) {
                    int idCompra = conector.registro.getInt("idCompra");

                    // Si es una nueva compra, creamos un nuevo objeto Compras
                    if (idCompra != idCompraActual) {
                        if (compraActual != null) {
                            listaCompras.add(compraActual);
                        }
                        compraActual = new Compras();
                        compraActual.setIdCompra(idCompra);
                        compraActual.setFechaCompra(conector.registro.getDate("fechaCompra"));
                        compraActual.setSubtotal(conector.registro.getFloat("subtotal"));
                        compraActual.setIva(conector.registro.getFloat("iva"));
                        compraActual.setTotal(conector.registro.getFloat("total"));
                        compraActual.setProductos(new HashMap<>());  // Inicializar el HashMap
                        idCompraActual = idCompra;
                    }

                    // Añadir producto y cantidad al HashMap
                    int idProducto = conector.registro.getInt("idProducto");
                    int cantidad = conector.registro.getInt("cantidad");
                    compraActual.getProductos().put(idProducto, cantidad);
                }

                // Agregar la última compra procesada a la lista
                if (compraActual != null) {
                    listaCompras.add(compraActual);
                }
            } catch (SQLException ex) {
                avisoUsuario("Error en la base de datos: " + ex.getMessage());
            }
            return listaCompras;
        }
        return null;
    }

    @Override
    protected List<Compras> getList() {
        return Collections.emptyList();
    }

    @Override
    protected List<Compras> getList(String parametro, String campo) {
        return Collections.emptyList();
    }

    @Override
    protected List<Compras> getList(String parametro, int campo) {
        return Collections.emptyList();
    }

    // Método para obtener la lista de compras por cliente
    public List<Compras> getComprasPorCliente(int idCliente) {
        limpiarRepositorio();
        String query = "CALL obtenerComprasPorCliente(" + idCliente + ")";  // Llamar al procedimiento almacenado
        conector.registro = ejecutarConsulta(query);
        repositorioLista.setDatos(BDToObjects(conector));
        return repositorioLista.getDatos();
    }

    // Método para obtener datos para gráficos (ajustado)
    @Override
    protected List<Object[]> getListGraph() {
        List<Object[]> listaCompras = new ArrayList<>();
        String query = "SELECT nv.fecha, COUNT(*) AS totalCompras FROM notaVenta nv GROUP BY nv.fecha ORDER BY nv.fecha";
        try {
            conector.registro = ejecutarConsulta(query);
            while (conector.registro.next()) {
                String fecha = conector.registro.getString("fecha");
                int total = conector.registro.getInt("totalCompras");
                listaCompras.add(new Object[]{fecha, total});
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        }
        return listaCompras;
    }

}
