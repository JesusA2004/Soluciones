package com.SolucionesParaPlagas.android.Controlador;

import java.sql.ResultSet;
import java.time.LocalDate;
import android.widget.Toast;
import java.sql.SQLException;
import android.content.Context;
import java.time.format.DateTimeFormatter;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;

public abstract class Controlador<Tipo>{

    protected String nameTable = "";
    protected Context contexto;
    protected Conector conector;
    protected Repositorio<Tipo> repositorio;

    public Controlador(Repositorio<Tipo> repositorio, Context context){
        this.repositorio = repositorio;
        contexto = context;
        conector = new Conector(contexto);
    }

    // Constructor para el controlador sin repositorio
    public Controlador(Context context){
        contexto = context;
        conector = new Conector(contexto);
    }

    public void objetoToRepositorio(String RFC, String telefono){
        repositorio.setObjeto(getObject(RFC, telefono));
    }

    public void limpiarRepositorio(){
        repositorio.clearObjeto();
    }

    public void avisoUsuario(String mensaje){
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }

    protected void obtenerConexionSQL(){
        conector.conexion = conector.JavaToMySQL();
    }

    protected void manejarExcepcion(SQLException ex) {
        Toast.makeText(contexto, "Error en la base de datos", Toast.LENGTH_SHORT).show();
    }

    public Tipo obtenerObjeto(){
        return repositorio.getObjeto();
    }

    protected String obtenerFecha(){
        // Obtén la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Define el formato de fecha para SQL
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Formatea la fecha
        String fechaFormateada = fechaActual.format(formatter);
        return fechaFormateada;
    }

    protected boolean ejecutarActualizacion(String query) {
        try{
            obtenerConexionSQL();
            conector.comando = conector.conexion.createStatement();
            conector.comando.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            manejarExcepcion(ex);
            return false;
        }catch(Exception e){
            return false;
        }
    }

    protected ResultSet ejecutarConsulta(String query) {
        try {
            obtenerConexionSQL();
            conector.comando = conector.conexion.createStatement();
            conector.registro = conector.comando.executeQuery(query);
            return conector.registro;
        } catch (SQLException ex) {
            manejarExcepcion(ex);
            return null;
        }
    }

    // Métodos abstractos para manejar CRUD
    protected abstract boolean insertObject(Tipo objeto);
    protected abstract boolean deleteObject(int id);
    protected abstract boolean updateObject(Tipo objeto);
    protected abstract Tipo getObject(int id);
    protected abstract Tipo getObject(String campo);
    protected abstract Tipo getObject(String RFC, String telefono);

    // Metodos para convertir Base de Datos a Objetos de Java
    protected abstract Tipo BDToObject(Conector conector);

}
