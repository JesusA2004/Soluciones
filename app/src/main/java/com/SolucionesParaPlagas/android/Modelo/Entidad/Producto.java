package com.SolucionesParaPlagas.android.Modelo.Entidad;

import lombok.Setter;
import lombok.Getter;
import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;  // Recomendado para Serializable
    private int folio;             // folio: int(5) zerofill
    private String nombreProd;     // nombreProd: varchar(60)
    private String tipo;           // tipo: varchar(100)
    private String unidadM;        // unidadM: varchar(15)
    private int existencia;        // existencia: int
    private float peso;            // peso: float
    private String descripcion;     // descripcion: text
    private float precio;          // precio: float
    private String urlImagen;      // urlImagen: varchar(100)
    private Integer idProveedor;   // idProveedor: int unsigned (usamos Integer para manejar null)

}
