package com.SolucionesParaPlagas.android.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.Toast;

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
            } catch (Exception e) {
                e.printStackTrace();
                // Mostrar mensaje al usuario en el hilo principal
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(contexto, "No se pudo conectar a la base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        });

        try {
            conexionHilo.start();
            conexionHilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Mostrar mensaje de error en el hilo principal
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(contexto, "Error al intentar conectar en un hilo: " + e.getMessage(), Toast.LENGTH_LONG).show()
            );
        }

        return conexion;
    }

}
