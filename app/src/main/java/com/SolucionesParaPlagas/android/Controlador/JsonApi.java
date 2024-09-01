package com.SolucionesParaPlagas.android.Controlador;

import retrofit2.Call;
import retrofit2.http.Url;
import retrofit2.http.GET;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.JsonPedido;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

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

    // Metodos para pedidos
    @GET
    Call<JsonPedido> obtenerPedidos(@Url String url);

}
