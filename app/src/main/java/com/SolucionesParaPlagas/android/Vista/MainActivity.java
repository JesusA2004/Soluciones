package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;
import com.example.sol.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Etiqueta para identificar los logs
    private ControladorProducto contP = new ControladorProducto();
    private ControladorCliente contC = new ControladorCliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //obtenerDatos();
        obtenerCliente();
    }

    private void obtenerCliente(){
        Controlador<JsonCliente> controladorJCliente = new ControladorJsonCliente("VARM7107244Y2");
        controladorJCliente.realizarSolicitud();
        imprimirC();
    }

    private void imprimirC(){
        for (Cliente cliente : contC.obtenerRepositorio()) {
            Log.d(TAG, "Cliente:");
            Log.d(TAG, "ID: " + cliente.getID());
            Log.d(TAG, "Nombre: " + cliente.getLegalName());
            Log.d(TAG, "RFC: " + cliente.getRFC());
            Log.d(TAG, "-------------------------");
        }
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
