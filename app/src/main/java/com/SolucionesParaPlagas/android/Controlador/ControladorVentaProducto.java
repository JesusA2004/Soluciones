package com.SolucionesParaPlagas.android.Controlador;

import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.VentaProducto;

public class ControladorVentaProducto extends Controlador<VentaProducto> {

    // Este controlador no necesita de un repositorio local
    public ControladorVentaProducto(Context contexto) {
        super(contexto);
        nameTable = "venta";
    }

    @Override
    protected boolean insertObject(VentaProducto objeto) {
        String query = "INSERT INTO " + nameTable + " VALUES ("
                + "0, "
                + objeto.getCantidad() + ", "
                + objeto.getTotal() + ", "
                + objeto.getFolio() + ", "
                + objeto.getIdCarrito() + ");";
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean deleteObject(int id) {
        String query = "DELETE FROM " + nameTable + " WHERE idVenta = " + id + ";";
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean updateObject(VentaProducto objeto) {
        String query = "UPDATE " + nameTable + " SET "
                + "cantidad = " + objeto.getCantidad() + ", "
                + "total = " + objeto.getTotal() + ", "
                + "folio = " + objeto.getFolio() + ", "
                + "idNotaVenta = " + objeto.getIdCarrito() + " "
                + "WHERE idVenta = " + objeto.getIdVenta() + ";";
        return ejecutarActualizacion(query);
    }

    @Override
    protected VentaProducto getObject(int id) {
        String query = "SELECT * FROM " + nameTable + " WHERE idVenta = " + id + ";";
        conector.registro = ejecutarConsulta(query);
        return BDToObject(conector);
    }

    @Override
    protected VentaProducto getObject(String campo) {
        // Si necesitas obtener un objeto por un campo específico, implementa esta lógica
        return null;
    }

    @Override
    protected VentaProducto getObject(String RFC, String telefono) {
        return null;
    }

    @Override
    protected VentaProducto BDToObject(Conector conector) {
        if (conector.registro != null) {
            try {
                if (conector.registro.next()) {
                    VentaProducto ventaProducto = new VentaProducto();
                    ventaProducto.setIdVenta(conector.registro.getInt("idVenta"));
                    ventaProducto.setCantidad(conector.registro.getInt("cantidad"));
                    ventaProducto.setTotal(conector.registro.getFloat("total"));
                    ventaProducto.setFolio(conector.registro.getInt("folio"));
                    ventaProducto.setIdCarrito(conector.registro.getInt("idNotaVenta"));
                    return ventaProducto;
                }
            } catch (SQLException ex) {
                manejarExcepcion(ex);
            }
        }
        return null;
    }

}
