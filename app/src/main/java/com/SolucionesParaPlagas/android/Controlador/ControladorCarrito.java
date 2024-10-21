package com.SolucionesParaPlagas.android.Controlador;

import java.util.HashMap;
import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compras;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCarrito;

public class ControladorCarrito extends Controlador<Compras>{

    // No es necesario el uso de un repositorio local
    public ControladorCarrito(Context contexto){
        super(RepositorioCarrito.obtenerInstancia(), contexto);
        nameTable = "notaVenta";
    }

    private static ControladorCarrito instancia;

    public static ControladorCarrito obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorCarrito(context);
        }
        return instancia;
    }

    // Metodo para insertar un Compras en la base de datos pero el objeto ya debe tener valores
    @Override
    protected boolean insertObject(Compras compras) {
        String query = "INSERT INTO " + nameTable + " VALUES ("
                + "0, "
                + "'" + obtenerFecha() + "', " // Metodo heredado de la clase padre
                + compras.getSubtotal() + ", "
                + compras.getIva() + ", "
                + compras.getPagoTotal() + ", "
                + compras.getEstatus() + ", "
                + compras.getNoCliente() + ","
                + "1" + ");"; // 1 indica que la venta la realiza un cliente (compra)
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean deleteObject(int id) {
        String query = "DELETE FROM " + nameTable + " WHERE idNotaVenta = " + id + ";";
        return ejecutarActualizacion(query);
    }

    // No se le permitirá actualizar al cliente ya que los cambios se haran automaticos
    @Override
    protected boolean updateObject(Compras compras) {
        // En la base de datos habra un disparador que actulice el Compras
        return true;
    }

    @Override
    protected Compras getObject(int id) {
        // Obtenemos el ultimo id ingresado de la nota venta
        String q = "SELECT MAX(idNotaVenta) AS maxId FROM " + nameTable + ";";
        conector.registro = ejecutarConsulta(q);
        try {
            if (conector.registro.next()) {
                id = conector.registro.getInt(1);
            }
        } catch (SQLException ex) {
            manejarExcepcion(ex);
            return null;
        }
        String query = "SELECT nv.idNotaVenta, nv.fecha, nv.subtotal, nv.iva, nv.pagoTotal, " +
                "nv.estatus, nv.noCliente, nv.noEmpleado, p.folio, v.cantidad " +
                "FROM notaVenta nv " +
                "JOIN venta v ON nv.idNotaVenta = v.idNotaVenta " +
                "JOIN producto p ON v.folio = p.folio " +
                "WHERE nv.idNotaVenta = " + id + ";";
        conector.registro = ejecutarConsulta(query);
        return BDToObject(conector);
    }

    // Metodos no usables ya que no se usara esta clase para consulta del Compras de compras
    @Override
    protected Compras getObject(String campo) {
        limpiarRepositorio();
        String query = "SELECT * FROM " + nameTable + " WHERE fechaApartado = '" + campo + "';";
        conector.registro = ejecutarConsulta(query);
        repositorio.setObjeto(BDToObject(conector));
        return repositorio.getObjeto();
    }

    @Override
    protected Compras getObject(String parametro, String campo) {
        return null;
    }

    @Override
    protected Compras BDToObject(Conector conector) {
        Compras compras = new Compras();
        HashMap<Integer, Integer> productos = new HashMap<>();
        boolean detallesGeneralesSeteados = false;  // Variable para controlar que los detalles generales se seteen una sola vez
        try {
            while (conector.registro.next()) {
                // Solo se llena una vez, en la primera fila
                if (!detallesGeneralesSeteados) {
                    compras.setIdNotaVenta(conector.registro.getInt("idNotaVenta"));
                    compras.setFecha(conector.registro.getDate("fecha"));
                    compras.setSubtotal(conector.registro.getFloat("subtotal"));
                    compras.setIva(conector.registro.getFloat("iva"));
                    compras.setPagoTotal(conector.registro.getFloat("pagoTotal"));
                    compras.setNoCliente(conector.registro.getInt("noCliente"));
                    compras.setEstatus(conector.registro.getString("estatus"));
                    detallesGeneralesSeteados = true;  // Marcamos que ya hemos seteado los detalles generales
                }
                // Llenamos el HashMap con el folio del producto y la cantidad
                int folio = conector.registro.getInt("folio");
                int cantidad = conector.registro.getInt("cantidad");
                productos.put(folio, cantidad);
            }
            compras.setProductos(productos);  // Asignamos el HashMap a la instancia de Compras
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        }
        return compras;
    }

}
