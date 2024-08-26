package com.SolucionesParaPlagas.android.Controlador;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;

public abstract class Controlador<T> {

    // Clase que usa Generics para obtener listas de datos de la API
    // Solo sirve para obtener datos

    private Retrofit retrofit;
    protected JsonApi jsonApi;
    protected Repositorio<T> repositorio;

    public Controlador(Repositorio<T> repositorio) {
        Conector conector = new Conector("Admin");
        this.retrofit = conector.solicitudHTTPS();
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

    public T obtenerDato(){
        return repositorio.getDato();
    }

    public void limipiarRepositorio(){
        repositorio.clearList();
    }

    public boolean datosCarg(){
        return datosCargados();
    }

    protected void manejarError(Object error) {
        System.out.println("Error: " + error);
    }

    protected abstract void procesarDatos(T datos);
    protected abstract Call<T> obtenerDatos();
    protected abstract boolean datosCargados();

}
