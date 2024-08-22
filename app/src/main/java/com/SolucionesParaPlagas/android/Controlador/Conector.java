package com.SolucionesParaPlagas.android.Controlador;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Clase dedicada a realizar solicitudes HTTPS y uso de la librería Retrofit
public class Conector {

    // Instancia única de Retrofit (Singleton)
    private static Retrofit retrofit = null;
    private static String apiKey = "";
    private static final String baseUrl = "https://api.bind.com.mx/api/";
    private final String apiAdmin = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImFkbWlufDEyMTU4MCIsIkludGVybmFsSUQiOiJhZjYwNTczZS0zNDhjLTQxMzEtYjBiMi00ZDY4ZWMwM2U4YTAiLCJuYmYiOjE3MjE4NjE0NjIsImV4cCI6MTc1MzM5NzQ2MiwiaWF0IjoxNzIxODYxNDYyLCJpc3MiOiJNaW5udF9Tb2x1dGlvbnNfU0FfREVfQ1YiLCJhdWQiOiJCaW5kX0VSUF9BUElfVXNlcnMifQ.1DqPMprCYYcMrh2DLl6FMozNzzkfQchUd5nHWhSQAyg";
    private final String apiVendedor = "";
    private final String apiCliente = "";

    public Conector(String tipoUsuario){
        validarTipoUsuario(tipoUsuario);
    }

    private void validarTipoUsuario(String tipoUsuario){
        // Realizamos un switch case para comprobar el tipo de usuario
        switch(tipoUsuario){
            case "Admin":
                apiKey = apiAdmin;
                break;
            case "Empleado":
                apiKey = apiVendedor;
                break;
            case "Usuario":
                apiKey = apiCliente;
                break;
        }
    }

    public Retrofit solicitudHTTPS(){
        if (retrofit == null) {
            synchronized (Conector.class) {
                if (retrofit == null) {
                    retrofit = crearRetrofit();
                }
            }
        }
        return retrofit;
    }

    private Retrofit crearRetrofit() {
        OkHttpClient okHttpClient = crearOkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient crearOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .addHeader("Authorization", "Bearer " + apiKey);
                    Request newRequest = requestBuilder.build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

}
