package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorRegistroCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.example.sol.R;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Etiqueta para identificar los logs
    private ControladorProducto contP = ControladorProducto.obtenerInstancia();
    private ControladorClienteIndividual contC = new ControladorClienteIndividual();
    private ControladorRegistroCliente controladorRegistroCliente = new ControladorRegistroCliente();
    private CountDownLatch dataLatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Obtén los datos y espera a que se carguen
        obtenerDatos();
        // Imprime los datos después de que se hayan cargado
        imprimir();
    }

    /*
    private void obtenerCliente() {
        Controlador<JsonCliente> controladorJCliente = new ControladorJsonCliente("VARM7107244Y2");
        controladorJCliente.realizarSolicitud();

        // Configura el listener para el controladorJsonCliente
        controladorJCliente.setOnDataLoadedListener(new Controlador.OnDataLoadedListener<JsonCliente>() {
            @Override
            public void onDataLoaded(JsonCliente data) {
                imprimirC();
                dataLatch.countDown(); // Señala que se ha completado la carga de datos del cliente
            }
        });
    }*/

    private void imprimirC() {
        for (ClienteIndividual cliente : contC.obtenerRepositorio()) {
            Log.d(TAG, "Cliente:");
            Log.d(TAG, "ID: " + cliente.getID());
            Log.d(TAG, "Nombre: " + cliente.getLegalName());
            Log.d(TAG, "RFC: " + cliente.getRFC());
            Log.d(TAG, "-------------------------");
        }
    }

    private void obtenerDatos() {
        Controlador<JsonProducto> controlador = ControladorJsonProducto.obtenerInstancia();
        ((ControladorJsonProducto) controlador).setDataLoadedListener(new ControladorJsonProducto.DataLoadedListener() {
            @Override
            public void onDataLoaded() {
                dataLatch.countDown(); // Señala que se ha completado la carga de datos del producto
            }
        });
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
        Log.d(TAG, "Total: " + contador);
    }

}
