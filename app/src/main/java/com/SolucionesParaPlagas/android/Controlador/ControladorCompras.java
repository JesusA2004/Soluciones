package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compras;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCompras;

/*
* Esta clase unicamente se usara para consultas
* Consulta de la lista de pedidos del cliente
*/

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

    // No se permite insertar
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
                int idNotaVentaActual = -1;
                while (conector.registro.next()) {
                    int idNotaVenta = conector.registro.getInt("idNotaVenta");
                    // Si encontramos una nueva nota de venta, creamos un nuevo objeto Compras
                    if (idNotaVenta != idNotaVentaActual) {
                        if (compraActual != null) {
                            listaCompras.add(compraActual); // Agregamos la compra previa a la lista
                        }
                        compraActual = new Compras();
                        compraActual.setIdNotaVenta(idNotaVenta);
                        compraActual.setFecha(conector.registro.getDate("fecha"));
                        compraActual.setSubtotal(conector.registro.getFloat("subtotal"));
                        compraActual.setIva(conector.registro.getFloat("iva"));
                        compraActual.setPagoTotal(conector.registro.getFloat("pagoTotal"));
                        compraActual.setEstatus(conector.registro.getString("estatus"));
                        compraActual.setNoCliente(conector.registro.getInt("noCliente"));
                        compraActual.setNoEmpleado(conector.registro.getInt("noEmpleado"));
                        // compraActual.setProductos(new HashMap<>()); // Inicializamos el HashMap para productos
                        idNotaVentaActual = idNotaVenta;
                    }
                    // Añadimos productos al carrito actual
                    int folioProducto = conector.registro.getInt("folio");
                    int cantidad = conector.registro.getInt("cantidad");
                    compraActual.getProductos().put(folioProducto, cantidad);
                }
                // Agregar la última compra procesada a la lista
                if (compraActual != null) {
                    listaCompras.add(compraActual);
                }
            } catch (SQLException ex) {
                avisoUsuario("Error en la base de datos: " + ex.getMessage());
            }
        }
        return listaCompras;
    }

    @Override
    protected List<Compras> getList() {
        limpiarRepositorio(); // Limpiar los datos del repositorio previo
        List<Compras> listaCompras = new ArrayList<>();
        // Consulta que une las notas de venta con las ventas y los productos
        String query = "SELECT nv.idNotaVenta, nv.fecha, nv.subtotal, nv.iva, nv.pagoTotal, " +
                "nv.estatus, nv.noCliente, nv.noEmpleado, p.folio, v.cantidad " +
                "FROM notaVenta nv " +
                "JOIN venta v ON nv.idNotaVenta = v.idNotaVenta " +
                "JOIN producto p ON v.folio = p.folio";
        conector.registro = ejecutarConsulta(query);
        listaCompras = BDToObjects(conector); // Convertimos el ResultSet en lista de compras
        return listaCompras;
    }


    @Override
    protected List<Compras> getList(String parametro, String campo) {
        return Collections.emptyList();
    }

    @Override
    protected List<Compras> getList(String parametro, int campo) {
        return Collections.emptyList();
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
