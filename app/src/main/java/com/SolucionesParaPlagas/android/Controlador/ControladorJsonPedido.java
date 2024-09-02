package com.SolucionesParaPlagas.android.Controlador;

import android.util.Log;

import retrofit2.Call;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.JsonPedido;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonPedido;

import java.util.List;

public class ControladorJsonPedido extends Controlador<JsonPedido> {

    private String EndPoint;
    private ControladorPedido controladorPedido = ControladorPedido.obtenerInstancia();
    
    // Constructor para obtener pedidos por ID del cliente
    public ControladorJsonPedido(String clientID) {
        super(RepositorioJsonPedido.obtenerInstancia());
        Log.d("ID",""+clientID);
        this.EndPoint = "Orders?$filter=ClientID eq guid'" + clientID + "'";
    }

    @Override
    protected Call<JsonPedido> obtenerDatos() {
        return getJsonApi().obtenerPedidos(EndPoint);
    }

    @Override
    protected void procesarDatos(JsonPedido datos) {
        List<PedidoIndividual> pedidos = datos.getValue();
        if(pedidos != null){
            controladorPedido.enviarDatosRepositorio(pedidos);
        }else{
            // La lista de pedidos es vacia es necesario avisar al usario
        }
    }

    private JsonApi getJsonApi() {
        return super.jsonApi;
    }
    
}
