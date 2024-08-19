package com.SolucionesParaPlagas.android.Controlador;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioCliente;
import java.util.List;

public class ControladorCliente{

    private Repositorio<Cliente> repositorioCliente = RepositorioCliente.obtenerInstancia();

    public List<Cliente> obtenerRepositorio() {
        return repositorioCliente.getDatos();
    }

    public void enviarDatosRepositorio(List<Cliente> listaClientes){
        repositorioCliente.setDatos(listaClientes);
    }

    public void enviarDatoRepositorio(Cliente cliente){
        repositorioCliente.setDato(cliente);
    }



}
