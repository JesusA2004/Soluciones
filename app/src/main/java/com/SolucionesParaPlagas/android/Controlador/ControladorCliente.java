package com.SolucionesParaPlagas.android.Controlador;

import java.sql.SQLException;
import android.content.Context;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCliente;

public class ControladorCliente extends Controlador<Cliente>{

    private static ControladorCliente instancia;

    public ControladorCliente(Context context) {
        super(RepositorioCliente.obtenerInstancia(), context);
        nameTable = "cliente";
    }

    public static ControladorCliente obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new ControladorCliente(context);
        }
        return instancia;
    }

    @Override
    protected boolean insertObject(Cliente cliente) {
        String query = "INSERT INTO " + nameTable + " (clienteRFC, nombreC, razonSocial, email, telefonoC, calle, colonia, localidad, municipio, estado, clienteCP) "
                + "VALUES ("
                + "'" + cliente.getClienteRFC() + "', "
                + "'" + cliente.getNombreC() + "', "
                + "'" + cliente.getRazonSocial() + "', "
                + "'" + cliente.getEmail() + "', "
                + "'" + cliente.getTelefonoC() + "', "
                + "'" + cliente.getCalle() + "', "
                + "'" + cliente.getColonia() + "', "
                + "'" + cliente.getLocalidad() + "', "
                + "'" + cliente.getMunicipio() + "', "
                + "'" + cliente.getEstado() + "', "
                + cliente.getClienteCP() + ");";
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean deleteObject(int id) {
        String query = "DELETE FROM " + nameTable + " WHERE noCliente = " + id + ";";
        return ejecutarActualizacion(query);
    }

    @Override
    protected boolean updateObject(Cliente cliente) {
        String query = "UPDATE " + nameTable + " SET "
                + "clienteRFC = '" + cliente.getClienteRFC() + "', "
                + "nombreC = '" + cliente.getNombreC() + "', "
                + "razonSocial = '" + cliente.getRazonSocial() + "', "
                + "email = '" + cliente.getEmail() + "', "
                + "telefonoC = '" + cliente.getTelefonoC() + "', "
                + "calle = '" + cliente.getCalle() + "', "
                + "colonia = '" + cliente.getColonia() + "', "
                + "localidad = '" + cliente.getLocalidad() + "', "
                + "municipio = '" + cliente.getMunicipio() + "', "
                + "estado = '" + cliente.getEstado() + "', "
                + "clienteCP = " + cliente.getClienteCP()
                + " WHERE noCliente = " + cliente.getNoCliente() + ";";
        return ejecutarActualizacion(query);
    }

    @Override
    protected Cliente getObject(int id) {
        String query = "SELECT * FROM " + nameTable + " WHERE noCliente = " + id + ";";
        conector.registro = ejecutarConsulta(query);
        repositorio.setObjeto(BDToObject(conector));
        return repositorio.getObjeto();
    }

    // Metodo para obtener un cliente por un campo especifio
    @Override
    protected Cliente getObject(String campo) {
        String query = "SELECT * FROM " + nameTable + " WHERE clienteRFC = " + campo + ";";
        conector.registro = ejecutarConsulta(query);
        repositorio.setObjeto(BDToObject(conector));
        return repositorio.getObjeto();
    }

    @Override
    protected Cliente getObject(String RFC, String telefono) {
        limpiarRepositorio();
        String query = "SELECT * FROM " + nameTable + " WHERE clienteRFC = '" + RFC + "' and telefonoC = '" + telefono + "';";
        conector.registro = ejecutarConsulta(query);
        repositorio.setObjeto(BDToObject(conector));
        return repositorio.getObjeto();
    }

    @Override
    protected Cliente BDToObject(Conector conector) {
        if (conector.registro != null) {
            try {
                if (conector.registro.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setNoCliente(conector.registro.getInt("noCliente"));
                    cliente.setClienteRFC(conector.registro.getString("clienteRFC"));
                    cliente.setNombreC(conector.registro.getString("nombreC"));
                    cliente.setRazonSocial(conector.registro.getString("razonSocial"));
                    cliente.setEmail(conector.registro.getString("email"));
                    cliente.setTelefonoC(conector.registro.getString("telefonoC"));
                    cliente.setCalle(conector.registro.getString("calle"));
                    cliente.setColonia(conector.registro.getString("colonia"));
                    cliente.setLocalidad(conector.registro.getString("localidad"));
                    cliente.setMunicipio(conector.registro.getString("municipio"));
                    cliente.setEstado(conector.registro.getString("estado"));
                    cliente.setClienteCP(conector.registro.getInt("clienteCP"));
                    return cliente;
                }
            } catch (SQLException ex) {
                manejarExcepcion(ex);
            }
        }
        return null;
    }

    @Override
    protected Cliente obtenerCarrito() {
        return null;
    }

    @Override
    protected Cliente obtenerCarritoSinDetalles() {
        return null;
    }

}
