package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.RespuestaClienteApi;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.RegistroCliente;
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
    Call<RespuestaClienteApi> registrarCliente(@Url String url, @Body RegistroCliente cliente);
    @DELETE
    Call<Void> eliminarCliente(@Url String url); // En url de eliminar ya va incluido el id a eliminar

}
