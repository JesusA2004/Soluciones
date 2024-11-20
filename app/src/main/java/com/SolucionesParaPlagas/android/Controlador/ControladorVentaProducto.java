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

    private static ControladorVentaProducto instancia;

    public static ControladorVentaProducto obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorVentaProducto(context);
        }
        return instancia;
    }

    @Override
    protected boolean insertObject(VentaProducto objeto) {
        String query = "INSERT INTO " + nameTable + " VALUES ("
                + "0, "
                + objeto.getCantidad() + ", "
                + objeto.getTotal() + ", "
                + objeto.getFolio() + ", "
                + objeto.getIdNotaVenta() + ");";
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean deleteObject(int id) {
        return true;
    }

    public boolean eliminarProducto(int id, int folio){
        String query = "delete from " + nameTable +
            " WHERE idNotaVenta = " + id +
            "AND folio = " + folio + ";";

        // Ejecutar la actualización en la base de datos
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean updateObject(VentaProducto objeto) {
        String query = "UPDATE " + nameTable + " SET "
                + "cantidad = " + objeto.getCantidad() + ", "
                + "total = " + objeto.getTotal() + ", "
                + "folio = " + objeto.getFolio() + ", "
                + "idNotaVenta = " + objeto.getIdNotaVenta() + " "
                + "WHERE idVenta = " + objeto.getIdVenta() + ";";
        return ejecutarActualizacion(query);
    }

    public int obtenerCantidadProducto(int ticket, int folio) {
        String query = "SELECT cantidad FROM " + nameTable +
                " WHERE idNotaVenta = " + ticket +
                "AND folio = " + folio + ";";
        conector.registro = ejecutarConsulta(query);
        try {
            if (conector.registro.next()) {
                // Retorna la cantidad obtenida
                return conector.registro.getInt("cantidad");
            } else {
                // Si no se encontró el registro, retorna -1
                return -1;
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
            // En caso de error, retorna -1
            return -1;
        }
    }

    public int obtenerUltimaNotaVenta() {
        String query = "SELECT MAX(idNotaVenta) FROM venta;";
        conector.registro = ejecutarConsulta(query);
        try {
            if (conector.registro != null && conector.registro.next()) {
                return conector.registro.getInt(1);
            }
        } catch (SQLException e) {
            manejarExcepcion(e);
        }
        return -1;  // Si no hay ventas, retorna -1
    }

    public boolean actualizarCantidad(int id, int cantidad, int folio) {
        String query = "UPDATE " + nameTable + " " +
                "SET cantidad = " + cantidad +
                "WHERE idNotaVenta = " + id +
                "AND folio = " + folio + ";";

        // Ejecutar la actualización en la base de datos
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
        // Este metodo es para futuras actualizaciones
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
                    ventaProducto.setIdNotaVenta(conector.registro.getInt("idNotaVenta"));
                    return ventaProducto;
                }
            } catch (SQLException ex) {
                manejarExcepcion(ex);
            }
        }
        return null;
    }

}
