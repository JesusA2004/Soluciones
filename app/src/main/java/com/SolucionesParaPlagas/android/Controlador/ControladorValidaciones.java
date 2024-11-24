package com.SolucionesParaPlagas.android.Controlador;

import android.widget.Toast;
import android.content.Intent;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.SolucionesParaPlagas.android.Vista.PaginaInicio;

public class ControladorValidaciones {

    // Expresiones regulares para validar RFCs
    private static final String RFCPersonaFisica = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$";
    private static final String RFCPersonaMoral = "^[A-ZÑ&]{3}\\d{6}$";

    //Constructor
    public ControladorValidaciones(){

    }

    /**
     * Valida un RFC.
     *
     * @param RFC El RFC a validar.
     * @return true si el RFC es válido, false en caso contrario.
     */
    public boolean validarRFC(String RFC) {
        if (RFC == null || RFC.isEmpty()) {
            return false;
        }
        // Normaliza el RFC a mayúsculas
        RFC = RFC.toUpperCase();
        // Valida el RFC según el formato
        if (RFC.length() == 12) {
            return true;
        } else if (RFC.length() == 13) {
            return true;
        }
        return false;
    }

    /**
     * Valida un RFC para persona física.
     *
     * @param RFC El RFC a validar.
     * @return true si el RFC es válido, false en caso contrario.
     */
    private boolean validarRFCFisica(String RFC) {
        return Pattern.matches(RFCPersonaFisica, RFC);
    }

    /**
     * Valida un RFC para persona moral.
     *
     * @param RFC El RFC a validar.
     * @return true si el RFC es válido, false en caso contrario.
     */
    private boolean validarRFCMoral(String RFC) {
        return Pattern.matches(RFCPersonaMoral, RFC);
    }

    /**
     * Valida que un campo contenga solo números.
     *
     * @param campo El campo a validar.
     * @return true si el campo contiene solo números, false en caso contrario.
     */
    public boolean validarSoloNumeros(String campo) {
        for (int i = 0; i < campo.length(); i++) {
            if (!Character.isDigit(campo.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean validarStringVacio(String campo){
        if(campo.isEmpty()){
            // Si el campo está vacío, devuelve true
            return true;
        }
        // Si el campo no está vacío, devuelve false
        return false;
    }

    public boolean validarStringNull(String campo){
        if(campo == null){
            return true;
        }else{
            return false;
        }
    }

    public String capitalizarLetras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        StringBuilder resultado = new StringBuilder();
        boolean capitalizar = true;
        for (char c : texto.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizar = true;
                resultado.append(c);
            } else if (capitalizar) {
                resultado.append(Character.toUpperCase(c));
                capitalizar = false;
            } else {
                resultado.append(Character.toLowerCase(c));
            }
        }
        return resultado.toString();
    }

    // Método para mostrar un cuadro de diálogo y confirmar si el usuario quiere cerrar la sesión
    public void confirmarCerrarSesion(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Si el usuario confirma, se limpia la sesión
                        cerrarSesion(context);
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

    private void cerrarSesion(Context contexto){
        ControladorCliente controladorCliente = ControladorCliente.obtenerInstancia(contexto);
        ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia(contexto);
        controladorCliente.limpiarRepositorio();
        controladorProducto.limpiarRepositorio();
    }

    public void limpiarRepositorios(Context contexto){
        cerrarSesion(contexto);
    }

    // Redirigir al login
    private void irALogin(Context context) {
        Intent intent = new Intent(context, PaginaInicio.class);
        context.startActivity(intent);
    }

}
