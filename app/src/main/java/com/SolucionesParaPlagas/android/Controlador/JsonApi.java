package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JsonApi {

    @GET
    Call<JsonProducto> obtenerProductos(@Url String url);

    @GET
    Call<Producto> obtenerProducto(@Url String url);

    @GET
    Call<JsonCliente> obtenerClientes(@Url String url);

    @GET
    Call<Cliente> obtenerCliente(@Url String url);

}
