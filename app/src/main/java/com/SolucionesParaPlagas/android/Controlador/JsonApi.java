package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.RegistroCliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Url;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;

public interface JsonApi {

    // Métodos para productos
    @GET
    Call<JsonProducto> obtenerProductos(@Url String url);
    @GET
    Call<Producto> obtenerProducto(@Url String url);

    // Métodos para clientes
    @GET
    Call<JsonCliente> obtenerClientes(@Url String url);
    @GET
    Call<DetalleCliente> obtenerCliente(@Url String url);
    @POST
    Call<Void> registrarCliente(@Url String url, @Body RegistroCliente cliente);
    @DELETE
    Call<Void> eliminarCliente(@Url String url); // En url de eliminar ya va incluido el id a eliminar


}
