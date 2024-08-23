package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorRegistroCliente;
import com.SolucionesParaPlagas.android.Controlador.HiloCargarDatos;
import com.SolucionesParaPlagas.android.Controlador.HiloImpresiones;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.example.sol.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Etiqueta para identificar los logs
    private ControladorProducto contP = ControladorProducto.obtenerInstancia();
    private ControladorClienteIndividual contC = new ControladorClienteIndividual();
    private ControladorRegistroCliente controladorRegistroCliente = new ControladorRegistroCliente();
    private Controlador<JsonProducto> controlador = new ControladorJsonProducto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Obtén los datos y espera a que se carguen
        HiloCargarDatos hiloCargarDatos = new HiloCargarDatos();
        HiloImpresiones hiloImpresiones = new HiloImpresiones();
        hiloCargarDatos.start();
        try {
            hiloCargarDatos.join();
            if(controlador.datosCarg()){
                hiloImpresiones.start();
            }
        }catch (InterruptedException e){
            System.out.println("Error "+e.getMessage());
        }
    }

    private void obtenerCliente() {
        Controlador<JsonCliente> controladorJCliente = new ControladorJsonCliente("VARM7107244Y2");
        controladorJCliente.realizarSolicitud();
        imprimirC();
    }

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
        controlador.realizarSolicitud();
    }

}
