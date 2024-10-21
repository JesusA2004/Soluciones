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
    // Contexto de la interfaz donde se presentan los datos
    protected Context contexto;
    protected Conector conector;
    // Repositorio para manejar solo un objeto a la vez
    protected Repositorio<Tipo> repositorio;

    // Constructor para casos que se usa el repositorio
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
        // Importante validar que exista en la base de datos
        // Caso contrario dar aviso para evitar valores nulos en el repositorio
        Tipo objeto = getObject(RFC, telefono);
        if(objeto != null){
            repositorio.setObjeto(objeto);
        }else {
            avisoUsuario("Error, datos no encontrados");
        }
    }

    // Si se cierran sesiones es necesario limpiar el repositorio local
    public void limpiarRepositorio(){
        repositorio.clearObjeto();
    }

    // Metodo para comodidad del programador para mandar avisos en pantallas
    public void avisoUsuario(String mensaje){
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }

    protected void obtenerConexionSQL(){
        conector.conexion = conector.JavaToMySQL();
    }

    protected void manejarExcepcionSQL(SQLException ex) {
        avisoUsuario("Error en la base de datos: "+ex.getMessage());
    }

    protected void manejarExcepcion(Exception e) {
        avisoUsuario("Error: "+e.getMessage());
    }

    public Tipo obtenerObjeto(){
        return repositorio.getObjeto();
    }

    // Metodo para obtener fechas automaticas
    protected String obtenerFecha(){
        // Obtén la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Define el formato de fecha para SQL
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Formatea la fecha
        String fechaFormateada = fechaActual.format(formatter);
        return fechaFormateada;
    }

    // Metodo para querys complejos (update, call... etc)
    protected boolean ejecutarActualizacion(String query) {
        try{
            obtenerConexionSQL();
            conector.comando = conector.conexion.createStatement();
            conector.comando.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            manejarExcepcionSQL(ex);
            return false;
        }catch(Exception e){
            manejarExcepcion(e);
            return false;
        }
    }

    // Metodo para querys de solo consultas
    protected ResultSet ejecutarConsulta(String query) {
        try {
            obtenerConexionSQL();
            conector.comando = conector.conexion.createStatement();
            conector.registro = conector.comando.executeQuery(query);
            return conector.registro;
        } catch (SQLException ex) {
            manejarExcepcion(ex);
            return null;
        }catch (Exception e){
            manejarExcepcion(e);
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
