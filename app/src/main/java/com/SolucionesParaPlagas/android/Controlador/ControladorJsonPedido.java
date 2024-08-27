package com.SolucionesParaPlagas.android.Controlador;

import retrofit2.Call;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.JsonPedido;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;

public class ControladorJsonPedido extends Controlador<JsonPedido>{

    public ControladorJsonPedido(Repositorio<JsonPedido> repositorio) {
        super(repositorio);
    }

    @Override
    protected void procesarDatos(JsonPedido datos) {

    }

    @Override
    protected Call<JsonPedido> obtenerDatos() {
        return null;
    }

    @Override
    protected boolean datosCargados() {
        return false;
    }

}
