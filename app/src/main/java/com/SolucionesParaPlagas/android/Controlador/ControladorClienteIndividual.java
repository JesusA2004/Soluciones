package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCliente;
import java.util.List;

public class ControladorClienteIndividual {

    private Repositorio<ClienteIndividual> repositorioCliente = RepositorioCliente.obtenerInstancia();

    public List<ClienteIndividual> obtenerRepositorio() {
        return repositorioCliente.getDatos();
    }

    public void enviarDatosRepositorio(List<ClienteIndividual> listaClienteIndividuals){
        repositorioCliente.setDatos(listaClienteIndividuals);
    }

    public void enviarDatoRepositorio(ClienteIndividual clienteIndividual){
        repositorioCliente.setDato(clienteIndividual);
    }

    public ClienteIndividual obtenerCliente(){
        return repositorioCliente.getDato();
    }

    public void limpiarRepositorio(){
        repositorioCliente.clearList();
    }

}
