package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

public class RespuestaClienteApi {

    private String response;

    // Constructor
    public RespuestaClienteApi(String response) {
        this.response = response;
    }

    // Getter
    public String getResponse() {
        return response;
    }

    // Setter
    public void setResponse(String response) {
        this.response = response;
    }

}
