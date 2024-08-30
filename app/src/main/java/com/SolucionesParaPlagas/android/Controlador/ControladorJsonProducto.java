package com.SolucionesParaPlagas.android.Controlador;

import android.util.Log;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonProducto;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControladorJsonProducto extends Controlador<JsonProducto> {

    private static final String INITIAL_ENDPOINT = "Products";
    private static final String TAG = "ControladorJsonProducto";
    private ControladorProducto contP = ControladorProducto.obtenerInstancia();
    private boolean key = false;

    // Constructor que hereda del controlador con Generics
    public ControladorJsonProducto() {
        super(RepositorioJsonProducto.obtenerInstancia());
    }

    @Override
    protected Call<JsonProducto> obtenerDatos() {
        return getJsonApi().obtenerProductos(INITIAL_ENDPOINT);
    }

    @Override
    protected void procesarDatos(JsonProducto datos) {
        if (datos == null) {
            Log.e(TAG, "Datos recibidos son nulos.");
            return;
        }
        List<Producto> productos = datos.getValue();
        if (productos != null) {
            // Enviamos los datos al repositorio de productos
            contP.enviarDatosRepositorio(productos);
        } else {
            Log.d(TAG, "Lista de productos es nula.");
        }
        String nextLink = datos.getNextLink();
        if (nextLink != null) {
            Log.d(TAG, "NextLink: " + nextLink);
            // Llama recursivamente para obtener la siguiente página de datos
            procesarSiguientePagina(nextLink);
        } else {
            Log.d(TAG, "No hay mas paginas.");
            // contP.imprimirRepositorio();
            key = true; // Indica que todos los datos han sido cargados
        }
    }

    private void procesarSiguientePagina(String nextLink) {
        Call<JsonProducto> call = getJsonApi().obtenerProductos(nextLink);
        call.enqueue(new Callback<JsonProducto>() {
            @Override
            public void onResponse(Call<JsonProducto> call, Response<JsonProducto> response) {
                if (response.isSuccessful()) {
                    JsonProducto datos = response.body();
                    if (datos != null) {
                        Log.d(TAG, "Datos de la siguiente pagina recibidos: " + datos);
                        // Procesa los datos y llama recursivamente para la siguiente página
                        procesarDatos(datos);
                    } else {
                        Log.d(TAG, "Datos de la siguiente pagina son nulos.");
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                    manejarError(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonProducto> call, Throwable t) {
                Log.e(TAG, "Excepcion al procesar la siguiente pagina: " + t.getMessage());
                manejarError(t.getMessage());
            }
        });
    }

    private JsonApi getJsonApi() {
        return super.jsonApi;
    }

    public boolean datosCargados() {
        return key;
    }

}
