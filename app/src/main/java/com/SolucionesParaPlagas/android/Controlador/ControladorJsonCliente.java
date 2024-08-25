package com.SolucionesParaPlagas.android.Controlador;

import android.util.Log;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonCliente;
import java.util.List;
import retrofit2.Call;

public class ControladorJsonCliente extends Controlador<JsonCliente>{

    private String EndPoint = "Clients?$filter=RFC eq ";
    private static final String TAG = "Main3"; // Etiqueta para identificar los logs
    private ControladorClienteIndividual controladorClienteIndividual = new ControladorClienteIndividual();

    public ControladorJsonCliente(String RFC) {
        super(RepositorioJsonCliente.obtenerInstancia());
        // Construir el endpoint con el RFC proporcionado
        this.EndPoint = EndPoint + "'" + String.format(RFC) + "'";
        Log.d(TAG,"Endpoint: "+ EndPoint);
    }

    @Override
    protected Call<JsonCliente> obtenerDatos() {
        return getJsonApi().obtenerClientes(EndPoint);
    }

    @Override
    protected boolean datosCargados(){
        if(controladorClienteIndividual.obtenerRepositorio().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    protected void procesarDatos(JsonCliente datos) {
        // Suponiendo que datos.getValue() devuelve una lista de Clientes
        List<ClienteIndividual> clientesIndividuales = datos.getValue();
        Log.d(TAG, "Clientes: " + clientesIndividuales);
        if (clientesIndividuales != null) {
            for (ClienteIndividual cli : clientesIndividuales) {
                Log.d(TAG, "Cliente: " + cli.getID() + " - " + cli.getClientName());
            }
            controladorClienteIndividual.enviarDatosRepositorio(clientesIndividuales);
        }
    }

    private JsonApi getJsonApi() {
        // Obtén la instancia de JsonApi del controlador base
        return super.jsonApi;
    }

}
