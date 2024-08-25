package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioDetalleCliente;
import retrofit2.Call;
import android.util.Log;

public class ControladorDetalleCliente extends Controlador<DetalleCliente> {

    private String endpoint;

    public ControladorDetalleCliente(String idCliente) {
        super(RepositorioDetalleCliente.obtenerInstancia());
        // Construir el endpoint con el ID del cliente proporcionado
        this.endpoint = "Clients/" + idCliente;
        Log.d("Prueba",endpoint);
    }

    @Override
    protected void procesarDatos(DetalleCliente cliente) {
        // Procesar el detalle del cliente y almacenar en el repositorio
        if (cliente != null) {
            Log.d("Cliente",""+cliente.toString());
            repositorio.setDato(cliente);
        }
    }

    @Override
    protected Call<DetalleCliente> obtenerDatos() {
        // Realizar la solicitud GET para obtener los detalles del cliente
        return getJsonApi().obtenerCliente(endpoint);
    }

    @Override
    protected boolean datosCargados() {
        if(repositorio.getDatos().isEmpty()){
            return false;
        }
        return true;
    }

    private JsonApi getJsonApi() {
        // Obtén la instancia de JsonApi del controlador base
        return super.jsonApi;
    }

}
