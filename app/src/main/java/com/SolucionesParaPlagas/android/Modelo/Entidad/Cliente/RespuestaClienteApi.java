package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

public class RespuestaClienteApi {

    private String uuid;

    // Constructor
    public RespuestaClienteApi(String uuid) {
        this.uuid = uuid;
    }

    // Getter
    public String getuuid() {
        return uuid;
    }

    // Setter
    public void setuuid(String uuid) {
        this.uuid = uuid;
    }

}
