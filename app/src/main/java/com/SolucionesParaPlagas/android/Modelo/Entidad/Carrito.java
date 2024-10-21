package com.SolucionesParaPlagas.android.Modelo.Entidad;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Carrito {

    private int idNotaVenta;
    private Date fecha;
    private float subtotal;
    private float iva;
    private float pagoTotal;
    private String estatus;
    private int noCliente;
    private int noEmpleado;

    // Como es una compra el id del empleado sera 1 por defecto

}
