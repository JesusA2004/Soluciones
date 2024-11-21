package com.SolucionesParaPlagas.android.Controlador;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import android.widget.Toast;
import android.os.StrictMode;
import java.sql.DriverManager;
import android.content.Context;

public class Conector {

    public Context contexto;
    public Connection conexion = null;
    public Statement comando = null;
    public ResultSet registro;

    public Conector(Context contexto) {
        this.contexto = contexto;
    }

    public Connection JavaToMySQL() {
        String servidor = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5745655";
        String usuario = "sql5745655";
        String password = "EXqaHS7lPY";
        // Permitir operaciones de red en el hilo principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Thread conexionHilo = new Thread(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(servidor, usuario, password);
                if (conexion != null) {
                    ((android.app.Activity) contexto).runOnUiThread(() ->
                            Toast.makeText(contexto, "Conexión exitosa con MySQL", Toast.LENGTH_SHORT).show()
                    );
                }
            } catch (Exception e) {
                ((android.app.Activity) contexto).runOnUiThread(() ->
                        Toast.makeText(contexto, "Error en la conexión: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
                e.printStackTrace();
            }
        });

        try {
            conexionHilo.start();
            conexionHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return conexion;
    }

}
