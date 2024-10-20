package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import java.sql.ResultSet;
import java.time.LocalDate;
import android.widget.Toast;
import java.sql.SQLException;
import android.content.Context;
import java.time.format.DateTimeFormatter;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioLista;

public abstract class ControladorListas<Tipo> {

    // Elementos que herdean las clases que extiendan de esta 
    protected String nameTable = "";
    protected Conector conector;
    protected Context contexto;
    protected RepositorioLista<Tipo> repositorioLista;

    public ControladorListas(Context context){
        contexto = context;
        conector = new Conector(contexto);
    }

    public ControladorListas(RepositorioLista<Tipo> repositorioLista, Context context){
        this.repositorioLista = repositorioLista;
        conector = new Conector(context);
    }

    public List<Tipo> obtenerRepositorio(){
        return repositorioLista.getDatos();
    }

    // Metodos para el manejo de CRUDS
    public boolean insertarObjetos(Tipo objeto)throws SQLException {
        return insertObjects(objeto);
    }

    // Metodos para el manejo de Listas
    public List<Tipo> obtenerLista() {
        return getList();
    }

    public List<Tipo> obtenerLista(String parametro,String campo) {
        return getList(parametro,campo);
    }

    public List<Tipo> obtenerLista(String parametro,int campo) {
        return getList(parametro,campo);
    }

    public List<Object[]> obtenerDatosGrafica(){
        return getListGraph();
    }

    public void limpiarRepositorio() {
        repositorioLista.clearList();
    }

    // METODOS QUE HEREDAN LAS CLASES QUE EXTIENDEN DE ESTA 

    protected void obtenerConexionSQL(){
        conector.conexion = conector.JavaToMySQL();
    }

    protected void manejarExcepcion(SQLException ex) {
        Toast.makeText(contexto, "Error en la base de datos", Toast.LENGTH_SHORT).show();
        // Habilitar en caso de querer seguir un registro de los errores producidos por la base de datos
        //Logger.getLogger(ControladorListas.class.getName()).log(Level.SEVERE, null, ex);
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

    public Tipo obtenerObjeto(int id){
        return getObjeto(id);
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

    public void avisoUsuario(String mensaje){
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }

    // Métodos abstractos para manejar CRUD
    protected abstract boolean insertObjects(Tipo objeto);
    protected abstract Tipo getObjeto(int id);

    // Metodos para convertir Base de Datos a Objetos de Java
    protected abstract List<Tipo> BDToObjects(Conector conector);

    // Métodos abstractos para manejar listas
    protected abstract List<Tipo> getList();
    protected abstract List<Tipo> getList(String parametro,String campo);
    protected abstract List<Tipo> getList(String parametro,int campo);
    protected abstract List<Object[]> getListGraph();

}
