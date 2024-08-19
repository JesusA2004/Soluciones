package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;
import com.example.sol.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Etiqueta para identificar los logs
    private ControladorProducto contP = new ControladorProducto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        obtenerDatos();
    }

    private void obtenerDatos() {
        Controlador<JsonProducto> controlador = new ControladorJsonProducto() {
            @Override
            protected void procesarDatos(JsonProducto datos) {
                super.procesarDatos(datos);
                // Cuando no haya más links, se imprime el total
                if (datos.getNextLink() == null) {
                    imprimir();
                }
            }
        };
        controlador.realizarSolicitud();
    }

    private void imprimir() {
        int contador = 0;
        for (Producto producto : contP.obtenerRepositorio()) {
            contador++;
            Log.d(TAG, "Producto:");
            Log.d(TAG, "Title: " + producto.getTitle());
            Log.d(TAG, "Description: " + producto.getDescription());
            Log.d(TAG, "Unit: " + producto.getUnit());
            Log.d(TAG, "Type: " + producto.getType());
            Log.d(TAG, "Weight: " + producto.getWeight());
            Log.d(TAG, "-------------------------");
        }
        // Log.d(TAG,"Total: "+contador);
    }

}
