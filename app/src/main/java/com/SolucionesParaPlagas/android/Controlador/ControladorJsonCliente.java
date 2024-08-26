package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonCliente;
import java.util.List;
import retrofit2.Call;

public class ControladorJsonCliente extends Controlador<JsonCliente> {

    private String EndPoint = "Clients?$filter=RFC eq ";
    private ControladorClienteIndividual controladorClienteIndividual = new ControladorClienteIndividual();
    private ControladorDetalleCliente controladorDetalleCliente = new ControladorDetalleCliente();

    public ControladorJsonCliente(String RFC) {
        super(RepositorioJsonCliente.obtenerInstancia());
        // Construir el endpoint con el RFC proporcionado
        this.EndPoint = EndPoint + "'" + String.format(RFC) + "'";
    }

    @Override
    protected Call<JsonCliente> obtenerDatos() {
        return getJsonApi().obtenerClientes(EndPoint);
    }

    @Override
    protected boolean datosCargados() {
        return !controladorClienteIndividual.obtenerRepositorio().isEmpty();
    }

    @Override
    protected void procesarDatos(JsonCliente datos) {
        // Suponiendo que datos.getValue() devuelve una lista de Clientes
        List<ClienteIndividual> clientesIndividuales = datos.getValue();
        if (clientesIndividuales != null) {
            controladorClienteIndividual.enviarDatosRepositorio(clientesIndividuales);
        }
    }

    private JsonApi getJsonApi() {
        return super.jsonApi;
    }

    public interface ClienteCallback {
        void onClienteLoaded(DetalleCliente cliente);
        void onError(String error);
    }

}

