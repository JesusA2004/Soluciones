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
        // Vincula la actividad con el archivo de layout activity_main2.xml
        setContentView(R.layout.activity_main2);
        // Invocar los controladores para obtener y mostrar datos
        obtenerDatos();
    }

    private void obtenerDatos() {
        // Crear instancia del controlador JSON producto
        Controlador<JsonProducto> controlador = new ControladorJsonProducto();

        // Realizar solicitud para obtener datos
        controlador.realizarSolicitud();

        // Usar un `Handler` para esperar a que los datos se carguen
        new Handler().postDelayed(() -> {
            // Obtener y registrar los datos en Logcat desde el repositorio a través del controlador
            int contador = 0;
            for (Producto producto : contP.obtenerRepositorio()) {
                contador ++;
                Log.d(TAG, "Producto:");
                Log.d(TAG, "Title: " + producto.getTitle());
                Log.d(TAG, "Description: " + producto.getDescription());
                Log.d(TAG, "Unit: " + producto.getUnit());
                Log.d(TAG, "Type: " + producto.getType());
                Log.d(TAG, "Weight: " + producto.getWeight());
                Log.d(TAG, "-------------------------");
            }
            Log.d(TAG,"Total: "+contador);
        }, 2000); // Espera de 2 segundos para simular la carga de datos
    }

}
