package com.SolucionesParaPlagas.android.Controlador;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import android.widget.Toast;
import java.sql.SQLException;
import java.sql.DriverManager;
import android.content.Context;

public class Conector {

    public Connection conexion = null;
    public Statement comando = null;
    public ResultSet registro;
    // Necesitas pasar el contexto de la actividad o aplicación para usar Toast
    private Context contexto;

    // Constructor para inicializar el contexto
    public Conector(Context contexto) {
        this.contexto = contexto;
    }

    public Connection JavaToMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Datos de la conexión con la base de datos
            String servidor = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5745655";
            String usuario = "sql5745655";
            String password = "EXqaHS7lPY";
            // Crear la conexión
            conexion = DriverManager.getConnection(servidor, usuario, password);
            // Mostrar mensaje si la conexión fue exitosa
            Toast.makeText(contexto, "Conexión exitosa", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException ex) {
            Toast.makeText(contexto, "No se pudo encontrar la clase Conexión", Toast.LENGTH_LONG).show();
        } catch (SQLException ex) {
            Toast.makeText(contexto, "No se pudo conectar a la base de datos", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(contexto, "Error con la información gestionada en la aplicación", Toast.LENGTH_LONG).show();
        }
        return conexion;
    }

}
