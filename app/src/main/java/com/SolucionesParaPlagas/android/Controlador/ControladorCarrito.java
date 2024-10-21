package com.SolucionesParaPlagas.android.Controlador;

import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Carrito;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCarrito;

/*
* Esta clase esta destinada a:
* Insertar en la base de datos una nota de venta
* Actualizar la nota de venta en la bd
* Eliminar la nota de venta (carrito actual)
*/

public class ControladorCarrito extends Controlador<Carrito>{

    // No es necesario el uso de un repositorio local
    public ControladorCarrito(Context contexto){
        super(contexto);
        nameTable = "notaVenta";
    }

    // Metodo para insertar un carrito en la base de datos pero el objeto ya debe tener valores
    @Override
    protected boolean insertObject(Carrito carrito) {
        String query = "INSERT INTO " + nameTable + " VALUES ("
                + "0, "
                + "'" + obtenerFecha() + "', "
                + carrito.getSubtotal() + ", "
                + carrito.getIva() + ", "
                + carrito.getTotal() + ", "
                + carrito.getIdCliente() + ","
                + "1" + ");"; // 1 indica que la venta la realiza un cliente (compra)
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean deleteObject(int id) {
        String query = "DELETE FROM " + nameTable + " WHERE idCarrito = " + id + ";";
        return ejecutarActualizacion(query);
    }

    // No se le permitira actualizar los siguientes atributos:
    // idNotaVenta, noCliente, noEmpleado
    @Override
    protected boolean updateObject(Carrito carrito) {
        String query = "UPDATE " + nameTable + " SET "
                + "fecha = '" + obtenerFecha() + "', "
                + "subtotal = " + carrito.getSubtotal() + ", "
                + "iva = " + carrito.getIva() + ", "
                + "pagoTotal = " + carrito.getTotal() + " "
                + "WHERE idCarrito = " + carrito.getIdCarrito() + ";";
        return ejecutarActualizacion(query);
    }

    @Override
    protected Carrito getObject(int id) {
        String query = "SELECT * FROM " + nameTable + " WHERE idCarrito = " + id + ";";
        conector.registro = ejecutarConsulta(query);
        return BDToObject(conector);
    }

    @Override
    protected Carrito getObject(String campo) {
        limpiarRepositorio();
        String query = "SELECT * FROM " + nameTable + " WHERE fechaApartado = '" + campo + "';";
        conector.registro = ejecutarConsulta(query);
        repositorio.setObjeto(BDToObject(conector));
        return repositorio.getObjeto();
    }

    @Override
    protected Carrito getObject(String parametro, String campo) {
        return null;
    }

    @Override
    protected Carrito BDToObject(Conector conector) {
        if (conector.registro != null) {
            try {
                if (conector.registro.next()) {
                    Carrito carrito = new Carrito();
                    carrito.setIdCarrito(conector.registro.getInt("idNotaVenta"));
                    carrito.setFechaCarrito(conector.registro.getDate("fecha"));
                    carrito.setSubtotal(conector.registro.getFloat("subtotal"));
                    carrito.setIva(conector.registro.getFloat("iva"));
                    carrito.setTotal(conector.registro.getFloat("pagoTotal"));
                    carrito.setIdCliente(conector.registro.getInt("noCliente"));
                    return carrito;
                }
            } catch (SQLException ex) {
                manejarExcepcion(ex);
            }
        }
        return null;
    }

}
