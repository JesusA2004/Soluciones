package com.SolucionesParaPlagas.android.Controlador;

import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.SolucionesParaPlagas.android.Vista.PaginaInicio;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;

public class Sesion {

    private Controlador<JsonCliente> controladorJsonCliente = new ControladorJsonCliente("");
    private ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
    private ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
    private ControladorCarrito controladorCarrito = ControladorCarrito.obtenerInstancia();
    private ControladorPedido controladorPedido = ControladorPedido.obtenerInstancia();

    private void limpiarSesion() {
        controladorDetalleCliente.limpiarRepositorio();
        controladorJsonCliente.limipiarRepositorio();
        controladorClienteIndividual.limpiarRepositorio();
        controladorCarrito.vaciarCarrito();
        controladorPedido.limpiarRepositorio();
    }

    private void cerrarSesion() {
        limpiarSesion();
    }

    // Método para mostrar un cuadro de diálogo y confirmar si el usuario quiere cerrar la sesión
    public void confirmarCerrarSesion(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Si el usuario confirma, se limpia la sesión
                        cerrarSesion();
                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                        irALogin(context); // Redirigir al login
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // No hacer nada si el usuario cancela
                        dialog.dismiss();
                    }
                });
        // Mostrar el cuadro de diálogo
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Redirigir al login
    private void irALogin(Context context) {
        Intent intent = new Intent(context, PaginaInicio.class);
        context.startActivity(intent);
    }

}
