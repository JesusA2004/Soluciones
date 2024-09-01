package com.SolucionesParaPlagas.android.Controlador;

import java.util.List;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.Repositorio;
import com.SolucionesParaPlagas.android.Modelo.Repositorio.RepositorioDetalleCliente;

public class ControladorDetalleCliente {

    // Repositorio que maneja los datos de DetalleCliente
    private Repositorio<DetalleCliente> repositorioCliente = RepositorioDetalleCliente.obtenerInstancia();

    // Instancia única de la clase
    private static ControladorDetalleCliente instancia;

    // Constructor privado para evitar instancias externas
    private ControladorDetalleCliente() {}

    // Método para obtener la única instancia de la clase
    public static ControladorDetalleCliente obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorDetalleCliente();
        }
        return instancia;
    }

    // Métodos para manejar el repositorio de DetalleCliente
    public List<DetalleCliente> obtenerRepositorio() {
        return repositorioCliente.getDatos();
    }

    public void enviarDatosRepositorio(List<DetalleCliente> listaDetalleClientes) {
        repositorioCliente.setDatos(listaDetalleClientes);
    }

    public void enviarDatoRepositorio(DetalleCliente detalleCliente) {
        repositorioCliente.setDato(detalleCliente);
    }

    public DetalleCliente obtenerCliente() {
        return repositorioCliente.getDato();
    }

    public void limpiarRepositorio() {
        repositorioCliente.clearList();
    }

}
