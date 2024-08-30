package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonCliente;

public class ControladorJsonCliente extends Controlador<JsonCliente> {

    private String EndPoint = "Clients?$filter=RFC eq ";
    private ControladorClienteIndividual controladorClienteIndividual = new ControladorClienteIndividual();
    private ControladorDetalleCliente controladorDetalleCliente = new ControladorDetalleCliente();

    public ControladorJsonCliente(String RFC) {
        super(RepositorioJsonCliente.obtenerInstancia());
        this.EndPoint = EndPoint + "'" + String.format(RFC) + "'";    }

    // Constructor para cliente detallado
    public ControladorJsonCliente(String id, String parametro){
        super(RepositorioJsonCliente.obtenerInstancia());
        this.EndPoint = "Clients/" + id;
        cargarClienteDetallado();
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

    private void cargarClienteDetallado(){
        Call<DetalleCliente> call = jsonApi.obtenerCliente(EndPoint);
        call.enqueue(new Callback<DetalleCliente>() {
            @Override
            public void onResponse(Call<DetalleCliente> call, Response<DetalleCliente> response) {
                if (!response.isSuccessful()) {
                    manejarError(response.code());
                    return;
                }
                DetalleCliente datos = response.body();
                if (datos != null) {
                    controladorDetalleCliente.enviarDatoRepositorio(datos);
                }
            }
            @Override
            public void onFailure(Call<DetalleCliente> call, Throwable t) {
                manejarError(t.getMessage());
            }
        });
    }

}

