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

    private int idCarrito;
    private Date fechaCarrito;
    private float subtotal;
    private float iva;
    private float total;
    private int idCliente;
    private int noCliente;

}
