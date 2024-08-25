package com.SolucionesParaPlagas.android.Controlador;

import java.util.regex.Pattern;

public class Validaciones {

    // Expresiones regulares para validar RFCs
    private static final String RFCPersonaFisica = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$";
    private static final String RFCPersonaMoral = "^[A-ZÑ&]{3}\\d{6}$";

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
            return validarRFCMoral(RFC);
        } else if (RFC.length() == 13) {
            return validarRFCFisica(RFC);
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

}
