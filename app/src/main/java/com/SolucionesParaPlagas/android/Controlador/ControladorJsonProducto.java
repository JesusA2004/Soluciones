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

    private static final String INITIAL_ENDPOINT = "products";
    private static final String TAG = "ControladorJsonProducto";
    private static ControladorJsonProducto instancia;

    public static ControladorJsonProducto obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorJsonProducto();
        }
        return instancia;
    }

    public ControladorJsonProducto() {
        super(RepositorioJsonProducto.obtenerInstancia());
    }

    @Override
    protected Call<JsonProducto> obtenerDatos() {
        return getJsonApi().obtenerProductos(INITIAL_ENDPOINT);
    }

    @Override
    protected List<JsonProducto> extraerDatos(JsonProducto datos) {
        return List.of(datos);
    }

    @Override
    protected void procesarDatos(JsonProducto datos) {
        List<Producto> productos = datos.getValue();
        if (productos != null) {
            ControladorProducto.obtenerInstancia().enviarDatosRepositorio(productos);
        }
        String nextLink = datos.getNextLink();
        if (nextLink != null) {
            Log.d(TAG, "NextLink: " + nextLink);
            realizarSolicitudPaginada(nextLink);
        } else {
            if (dataLoadedListener != null) {
                dataLoadedListener.onDataLoaded();
            }
        }
    }

    private void realizarSolicitudPaginada(String nextLink) {
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
                    procesarDatos(datos);
                }
            }

            @Override
            public void onFailure(Call<JsonProducto> call, Throwable t) {
                manejarError(t.getMessage());
            }
        });
    }

    private JsonApi getJsonApi() {
        return super.jsonApi;
    }

    // Interfaz para escuchar eventos de carga de datos
    public interface DataLoadedListener {
        void onDataLoaded();
    }

    private DataLoadedListener dataLoadedListener;

    public void setDataLoadedListener(DataLoadedListener listener) {
        this.dataLoadedListener = listener;
    }

}
