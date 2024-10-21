package com.SolucionesParaPlagas.android.Modelo.Entidad;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VentaProducto {

    private int idVenta;
    private int cantidad;
    private float total;
    private int folio;
    private int idNotaVenta;

}
