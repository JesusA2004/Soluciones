package com.SolucionesParaPlagas.android.Modelo.Entidad;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

    private int noCliente;        // noCliente: int(5) zerofill
    private String clienteRFC;    // clienteRFC: varchar(40)
    private String nombreC;       // nombreC: varchar(100)
    private String razonSocial;    // razonSocial: varchar(100)
    private String email;         // email: varchar(50)
    private String telefonoC;     // telefonoC: varchar(10)
    private String calle;         // calle: varchar(240)
    private String colonia;       // colonia: varchar(50)
    private String localidad;     // localidad: varchar(50)
    private String municipio;     // municipio: varchar(50)
    private String estado;        // estado: varchar(50)
    private int clienteCP;        // clienteCP: int
    private int diasCredito;      // diasCredito: int
    private float montoCredito;   // montoCredito: float
    private String urlFotoPerfil; // urlFotoPerfil: varchar(100)

}
