package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import java.util.HashMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Controlador.Sesion;

public class MostrarProductos extends AppCompatActivity {

    Button btnCarrito;
    Button btnMenu;
    Button btnCerrarSesion;
    RecyclerView productos;
    HashMap<String, Integer> carrito = new HashMap<>();
    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout);
        inicializarElementos();
        configurarBotones();
    }

    private void inicializarElementos() {

    }

    private void obtenerElementos(){
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("carrito")){
            carrito = (HashMap<String, Integer>) intent.getSerializableExtra("carrito");
        }
    }

    private void configurarBotones() {
        btnCarrito.setOnClickListener(this::irACarrito);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(MostrarProductos.this,Menu.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        carrito = sesion.limpiarSesion();
        Intent intent = new Intent(MostrarProductos.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void irACarrito(View v){
        Intent intent = new Intent(MostrarProductos.this,CarritoCompras.class);
        intent.putExtra("carrito",carrito);
        startActivity(intent);
    }

}