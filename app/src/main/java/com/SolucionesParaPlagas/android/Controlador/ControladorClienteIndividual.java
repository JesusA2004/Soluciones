package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCliente;
import java.util.List;

public class ControladorClienteIndividual {

    // Repositorio que maneja los datos de ClienteIndividual
    private Repositorio<ClienteIndividual> repositorioCliente = RepositorioCliente.obtenerInstancia();

    // Instancia única de la clase
    private static ControladorClienteIndividual instancia;

    // Constructor privado para evitar instancias externas
    private ControladorClienteIndividual() {}

    // Método para obtener la única instancia de la clase
    public static ControladorClienteIndividual obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorClienteIndividual();
        }
        return instancia;
    }

    // Métodos para manejar el repositorio de ClienteIndividual
    public List<ClienteIndividual> obtenerRepositorio() {
        return repositorioCliente.getDatos();
    }

    public void enviarDatosRepositorio(List<ClienteIndividual> listaClienteIndividuals) {
        repositorioCliente.setDatos(listaClienteIndividuals);
    }

    public void enviarDatoRepositorio(ClienteIndividual clienteIndividual) {
        repositorioCliente.setDato(clienteIndividual);
    }

    public ClienteIndividual obtenerCliente() {
        return repositorioCliente.getDato();
    }

    public void limpiarRepositorio() {
        repositorioCliente.clearList();
    }

}
