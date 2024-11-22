package com.SolucionesParaPlagas.android.Controlador;

import java.util.HashMap;
import java.sql.SQLException;
import android.content.Context;
import android.util.Log;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCarrito;

public class ControladorCarrito extends Controlador<Compra>{

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
    protected boolean insertObject(Compra compra) {
        String query = "INSERT INTO " + nameTable + " VALUES ("
                + "0, "
                + "'" + compra.getFecha() + "', "
                + compra.getSubtotal() + ", "
                + compra.getIva() + ", "
                + compra.getPagoTotal() + ", "
                + compra.getEstatus() + ", "
                + compra.getNoCliente() + ","
                + "2" + ");"; // 2 indica que la venta la realiza un cliente (compra)
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean deleteObject(int id) {
        String query = "DELETE FROM " + nameTable + " WHERE idNotaVenta = " + id + ";";
        return ejecutarActualizacion(query);
    }

    // No se le permitirá actualizar al cliente ya que los cambios se haran automaticos
    @Override
    protected boolean updateObject(Compra compra) {
        // En la base de datos habra un disparador que actulice el Compras
        return true;
    }

    @Override
    protected Compra getObject(int id) {
        // Obtener el cliente actual del repositorio local
        ControladorCliente controladorCliente = ControladorCliente.obtenerInstancia(null);
        Cliente cliente = controladorCliente.obtenerObjeto();

        // Query para buscar una nota de venta que cumpla las condiciones
        String query = "SELECT nv.idNotaVenta, nv.fecha, nv.subtotal, nv.iva, nv.pagoTotal," +
                " nv.estatus, nv.noCliente, nv.noEmpleado , v.folio,v.cantidad" +
                " FROM notaVenta nv" +
                " JOIN venta v on nv.idNotaVenta= v.idNotaVenta" +
                " WHERE nv.noCliente = " + cliente.getNoCliente() +
                " AND nv.idNotaVenta = " + id +
                " AND nv.noEmpleado = 2" +
                " AND nv.estatus = 'En Compra App';";

        conector.registro = ejecutarConsulta(query);

        Compra compra = BDToObject(conector);

        if (compra != null && compra.getIdNotaVenta() != 0) {
            // Si se encontró una compra válida, retornarla
            repositorio.setObjeto(compra);
        } else {
            // Si no se encontró una compra válida, crear una nueva nota de venta
            repositorio.setObjeto(crearNuevaNotaVenta());
        }
        return repositorio.getObjeto();
    }

    public Compra obtenerCarritoEnCompra(Context context){
        // Obtener el cliente actual del repositorio local
        Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(context);
        Cliente cliente = controladorCliente.obtenerObjeto();

        // Query para buscar una nota de venta que cumpla las condiciones
        String query = "SELECT nv.idNotaVenta, nv.fecha, nv.subtotal, nv.iva, nv.pagoTotal," +
                " nv.estatus, nv.noCliente, nv.noEmpleado , v.folio,v.cantidad" +
                " FROM notaVenta nv" +
                " JOIN venta v on nv.idNotaVenta= v.idNotaVenta" +
                " WHERE nv.noCliente = " + cliente.getNoCliente() +
                " AND nv.noEmpleado = 2" +
                " AND nv.estatus = 'En Compra App';";

        conector.registro = ejecutarConsulta(query);

        Compra compra = BDToObject(conector);

        if (compra != null && compra.getIdNotaVenta() != 0) {
            // Si se encontró una compra válida, retornarla
            repositorio.setObjeto(compra);
        } else {
            // Si no se encontró una compra válida, crear una nueva nota de venta
            repositorio.setObjeto(crearNuevaNotaVenta());
        }
        return repositorio.getObjeto();
    }

    public boolean finalizarCompra(int idTicket) {
        // Query para actualizar el estado del ticket
        String query = "UPDATE " + nameTable + " " +
                "SET estatus = 'Compra hecha' " +
                "WHERE idNotaVenta = " + idTicket + ";";

        // Ejecutar la actualización en la base de datos
        return ejecutarActualizacion(query);
    }

    // Método para crear una nueva nota de venta
    private Compra crearNuevaNotaVenta() {
        Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(null);
        Cliente cliente = controladorCliente.obtenerObjeto();
        Log.d("Cliente", "Cliente obtenido: " + cliente);

        if (cliente == null) {
            // Manejar caso donde no se puede obtener el cliente
            return null;
        }

        Compra nuevaCompra = new Compra();
        nuevaCompra.setFecha(obtenerFecha());
        nuevaCompra.setSubtotal(0.0f);
        nuevaCompra.setIva(0.0f);
        nuevaCompra.setPagoTotal(0.0f);
        nuevaCompra.setEstatus("En compra");
        nuevaCompra.setNoCliente(cliente.getNoCliente()); // ID del cliente autenticado
        nuevaCompra.setNoEmpleado(2); // ID designado para compras en la app
        nuevaCompra.setProductos(new HashMap<>());

        // Insertar la nueva compra en la base de datos
        if (insertObject(nuevaCompra)) {
            // Retornar la compra recién creada
            return nuevaCompra;
        } else {
            // Retornar null si no se pudo insertar
            return null;
        }
    }

    // Metodos no usables ya que no se usara esta clase para consulta del Compras de compras
    @Override
    protected Compra getObject(String campo) {
        limpiarRepositorio();
        String query = "SELECT * FROM " + nameTable + " WHERE fechaApartado = '" + campo + "';";
        conector.registro = ejecutarConsulta(query);
        repositorio.setObjeto(BDToObject(conector));
        return repositorio.getObjeto();
    }

    @Override
    protected Compra getObject(String parametro, String campo) {
        return null;
    }

    @Override
    protected Compra BDToObject(Conector conector) {
        Compra compra = new Compra();
        HashMap<Integer, Integer> productos = new HashMap<>();
        boolean detallesGeneralesSeteados = false;  // Variable para controlar que los detalles generales se seteen una sola vez
        try {
            while (conector.registro.next()) {
                // Solo se llena una vez, en la primera fila
                if (!detallesGeneralesSeteados) {
                    compra.setIdNotaVenta(conector.registro.getInt("idNotaVenta"));
                    compra.setFecha(conector.registro.getString("fecha"));
                    compra.setSubtotal(conector.registro.getFloat("subtotal"));
                    compra.setIva(conector.registro.getFloat("iva"));
                    compra.setPagoTotal(conector.registro.getFloat("pagoTotal"));
                    compra.setNoCliente(conector.registro.getInt("noCliente"));
                    compra.setNoEmpleado(conector.registro.getInt("noEmpleado"));
                    compra.setEstatus(conector.registro.getString("estatus"));
                    detallesGeneralesSeteados = true;  // Marcamos que ya hemos seteado los detalles generales
                }
                // Llenamos el HashMap con el folio del producto y la cantidad
                int folio = conector.registro.getInt("folio");
                int cantidad = conector.registro.getInt("cantidad");
                productos.put(folio, cantidad);
            }
            compra.setProductos(productos);  // Asignamos el HashMap a la instancia de Compras
        } catch (SQLException ex) {
            manejarExcepcion(ex);
        }
        return compra;
    }

}
