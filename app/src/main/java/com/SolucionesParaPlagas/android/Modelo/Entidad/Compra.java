package com.SolucionesParaPlagas.android.Modelo.Entidad;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.util.HashMap;

import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor

// Clase para mostrar las compras hechas

public class Compra {

    private int idNotaVenta;
    private String fecha;
    private float subtotal;
    private float iva;
    private float pagoTotal;
    private String estatus;
    private int noCliente;
    private int noEmpleado;
    // Key -> id del producto, Value -> cantidad de productos
    private HashMap<Integer, Integer> productos;

    public Compra(){
        productos = new HashMap<Integer, Integer>();
        noEmpleado = 2; // Id asignado para las compras hechas en la app
    }

}
