package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.RegistroCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.RespuestaClienteApi;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ControladorRegistroCliente {

    private Retrofit retrofit;
    protected JsonApi jsonApi;
    private static final String TAG = "ControladorRegistroCliente"; // Etiqueta para logs

    public ControladorRegistroCliente() {
        // Obtén la instancia de Retrofit usando el método de conexión HTTPS
        Conector conector = new Conector("Admin");
        this.retrofit = conector.solicitudHTTPS();
        this.jsonApi = retrofit.create(JsonApi.class);
    }

    // Método para realizar el POST
    public void registrarCliente(RegistroCliente cliente) {
        String endPoint = "Clients";
        Call<RespuestaClienteApi> call = jsonApi.registrarCliente(cliente);
        call.enqueue(new Callback<RespuestaClienteApi>() {
            @Override
            public void onResponse(Call<RespuestaClienteApi> call, Response<RespuestaClienteApi> response) {
                if (!response.isSuccessful()) {
                    manejarError(response.code());
                    // return;
                }
                RespuestaClienteApi respuesta = response.body();
                if (respuesta != null) {
                    Log.d(TAG, "Cliente registrado exitosamente: " + respuesta.getuuid());
                } else {
                    Log.d(TAG, "Cliente registrado exitosamente, pero la respuesta esta vacia.");
                }
            }
            @Override
            public void onFailure(Call<RespuestaClienteApi> call, Throwable t) {
                manejarError(t.getMessage());
            }
        });
    }

    protected void manejarError(Object error) {
        Log.e(TAG, "Error: " + error);
        if (error instanceof String) {
            Log.e(TAG, "Error detallado: " + (String) error);
        }
    }

}
