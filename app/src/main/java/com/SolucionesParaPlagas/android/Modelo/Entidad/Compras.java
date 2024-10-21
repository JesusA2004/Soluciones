package com.SolucionesParaPlagas.android.Modelo.Entidad;

import lombok.Setter;
import lombok.Getter;
import java.util.Date;
import java.util.HashMap;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Clase para mostrar las compras hechas
// Su unico uso es 'Read'

public class Compras {

    private int idCompra;
    private Date fechaCompra;
    private float subtotal;
    private float iva;
    private float total;
    // Key -> id del producto, Value -> cantidad de productos
    private HashMap<Integer, Integer> productos;

}
