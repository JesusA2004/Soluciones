package com.SolucionesParaPlagas.android.Controlador;

import android.util.Log;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioJsonCliente;
import java.util.List;
import retrofit2.Call;

public class ControladorJsonCliente extends Controlador<JsonCliente>{

    private String EndPoint = "Clients?$filter=RFC eq ";
    private static final String TAG = "Main3"; // Etiqueta para identificar los logs
    private ControladorCliente controladorCliente = new ControladorCliente();

    public ControladorJsonCliente(String RFC) {
        super(RepositorioJsonCliente.obtenerInstancia());
        // Construir el endpoint con el RFC proporcionado
        this.EndPoint = EndPoint + "'" + String.format(RFC) + "'";
        Log.d(TAG,"Endpoint: "+ EndPoint);
    }

    @Override
    protected List<JsonCliente> extraerDatos(JsonCliente datos) {
        // Retorna una lista con el JsonProducto para compatibilidad con el tipo de retorno
        return List.of(datos);
    }

    @Override
    protected Call<JsonCliente> obtenerDatos() {
        return getJsonApi().obtenerClientes(EndPoint);
    }

    @Override
    protected void procesarDatos(JsonCliente datos) {
        // Suponiendo que datos.getValue() devuelve una lista de Clientes
        List<Cliente> clientes = datos.getValue();
        Log.d(TAG, "Clientes: " + clientes);
        if (clientes != null) {
            for (Cliente cli : clientes) {
                Log.d(TAG, "Cliente: " + cli.getID() + " - " + cli.getClientName());
            }
            controladorCliente.enviarDatosRepositorio(clientes);
        }
    }

    private JsonApi getJsonApi() {
        // Obtén la instancia de JsonApi del controlador base
        return super.jsonApi;
    }

}
