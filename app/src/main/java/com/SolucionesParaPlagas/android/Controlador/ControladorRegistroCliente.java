package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.RegistroCliente;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ControladorRegistroCliente {

    private Retrofit retrofit;
    protected JsonApi jsonApi;
    private final String baseUrl = "https://api.bind.com.mx/api/"; // Base URL de la API
    private static final String TAG = "ControladorRegistroCliente"; // Etiqueta para logs

    public ControladorRegistroCliente() {
        // Obtén la instancia de Retrofit usando el método de conexión HTTPS
        this.retrofit = Conector.solicitudHTTPS(baseUrl);
        this.jsonApi = retrofit.create(JsonApi.class);
    }

    // Método para realizar el POST
    public void registrarCliente(RegistroCliente cliente) {
        String endPoint = "Clients";
        Call<Void> call = jsonApi.registrarCliente(endPoint, cliente);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    manejarError(response.code());
                    return;
                }
                Log.d(TAG, "Cliente registrado exitosamente");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                manejarError(t.getMessage());
            }
        });
    }

    // Método para manejar errores
    protected void manejarError(Object error) {
        Log.e(TAG, "Error: " + error);
    }

}
