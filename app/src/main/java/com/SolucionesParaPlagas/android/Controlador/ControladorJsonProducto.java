package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonProducto;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

public class ControladorJsonProducto extends Controlador<JsonProducto> {

    private static final String INITIAL_ENDPOINT = "products";
    private final ControladorProducto controladorProducto;
    private static final String TAG = "Main2"; // Etiqueta para identificar los logs

    public ControladorJsonProducto() {
        super(RepositorioJsonProducto.obtenerInstancia());
        this.controladorProducto = new ControladorProducto();
    }

    @Override
    protected Call<JsonProducto> obtenerDatos() {
        // Llama al método adecuado en JsonApi para obtener JsonProducto
        return getJsonApi().obtenerProductos(INITIAL_ENDPOINT);
    }

    @Override
    protected List<JsonProducto> extraerDatos(JsonProducto datos) {
        // Retorna una lista con el JsonProducto para compatibilidad con el tipo de retorno
        return List.of(datos);
    }

    @Override
    protected void procesarDatos(JsonProducto datos) {
        // Obtiene los productos desde JsonProducto
        List<Producto> productos = datos.getValue();

        if (productos != null && !productos.isEmpty()) {
            // Añade los productos obtenidos al repositorio
            controladorProducto.enviarDatosRepositorio(productos);
        }

        // Verifica si hay un nextLink para continuar con la paginación
        String nextLink = datos.getNextLink();
        // Log.d(TAG, "Link: " + nextLink);
        if (nextLink != null && !nextLink.isEmpty()) {
            realizarSolicitudPaginada(nextLink);
        }
    }

    private void realizarSolicitudPaginada(String nextLink) {
        // Realiza una nueva solicitud con el siguiente link de paginación
        Call<JsonProducto> call = getJsonApi().obtenerProductos(nextLink);
        call.enqueue(new Callback<JsonProducto>() {
            @Override
            public void onResponse(Call<JsonProducto> call, Response<JsonProducto> response) {
                if (!response.isSuccessful()) {
                    manejarError(response.code());
                    return;
                }
                JsonProducto datos = response.body();
                if (datos != null) {
                    procesarDatos(datos); // Llama recursivamente para manejar la siguiente página
                }
            }
            @Override
            public void onFailure(Call<JsonProducto> call, Throwable t) {
                manejarError(t.getMessage());
            }
        });
    }

    private JsonApi getJsonApi() {
        // Obtén la instancia de JsonApi del controlador base
        return super.jsonApi;
    }

}
