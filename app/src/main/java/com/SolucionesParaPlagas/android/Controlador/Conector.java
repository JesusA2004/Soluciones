package com.SolucionesParaPlagas.android.Controlador;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Clase dedicada a realizar solicitudes HTTPS y uso de la librería Retrofit
public class Conector {

    // Instancia única de Retrofit (Singleton)
    private static Retrofit retrofit = null;

    // Constructor privado para evitar la instanciación directa
    private Conector() {
    }

    // Método para obtener la instancia de Retrofit
    public static Retrofit solicitudHTTPS(String baseUrl) {
        if (retrofit == null) {
            synchronized (Conector.class) {
                if (retrofit == null) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                Request originalRequest = chain.request();
                                Request.Builder requestBuilder = originalRequest.newBuilder()
                                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImFkbWlufDEyMTU4MCIsIkludGVybmFsSUQiOiJhZjYwNTczZS0zNDhjLTQxMzEtYjBiMi00ZDY4ZWMwM2U4YTAiLCJuYmYiOjE3MjE4NjE0NjIsImV4cCI6MTc1MzM5NzQ2MiwiaWF0IjoxNzIxODYxNDYyLCJpc3MiOiJNaW5udF9Tb2x1dGlvbnNfU0FfREVfQ1YiLCJhdWQiOiJCaW5kX0VSUF9BUElfVXNlcnMifQ.1DqPMprCYYcMrh2DLl6FMozNzzkfQchUd5nHWhSQAyg");
                                Request newRequest = requestBuilder.build();
                                return chain.proceed(newRequest);
                            })
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

}
