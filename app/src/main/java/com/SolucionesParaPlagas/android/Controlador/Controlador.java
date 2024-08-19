package com.SolucionesParaPlagas.android.Controlador;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import java.util.List;

public abstract class Controlador<T> {

    private Retrofit retrofit;
    protected JsonApi jsonApi;
    private final String baseUrl = "https://api.bind.com.mx/api/";
    protected Repositorio<T> repositorio;

    public Controlador(Repositorio<T> repositorio) {
        // Usa el método estático para obtener la instancia de Retrofit
        this.retrofit = Conector.solicitudHTTPS(baseUrl);
        this.jsonApi = retrofit.create(JsonApi.class);
        this.repositorio = repositorio;
    }

    public void realizarSolicitud() {
        Call<T> call = obtenerDatos();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (!response.isSuccessful()) {
                    manejarError(response.code());
                    return;
                }
                T datos = response.body();
                if (datos != null) {
                    procesarDatos(datos);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                manejarError(t.getMessage());
            }
        });
    }

    public List<T> obtenerRepositorio() {
        return repositorio.getDatos();
    }

    protected void procesarDatos(T datos) {
        repositorio.setDatos(extraerDatos(datos));
    }

    protected void manejarError(Object error) {
        System.out.println("Error: " + error);
    }

    protected abstract List<T> extraerDatos(T datos);
    protected abstract Call<T> obtenerDatos();

}
