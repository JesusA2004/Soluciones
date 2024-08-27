package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import java.util.HashMap;
import com.example.sol.R;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;

public class CarritoCompras extends AppCompatActivity {

    HashMap<String, Producto> carrito = new HashMap<>();
    private ControladorImagenes controladorImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void obtenerElementos(){
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("carrito")){
                carrito = (HashMap<String, Producto>) intent.getSerializableExtra("carrito");
            }
        }
    }

}