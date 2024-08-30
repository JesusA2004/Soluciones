package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;

public class ConsultarPerfil extends AppCompatActivity {

    Button btnGuardarCambios;
    ImageView btnProductos, btnMenu, btnCerrarSesion;
    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datoscliente);
        inicializarElementos();
        configurarBotones();
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnGuardarCambios = findViewById(R.id.btnSubirCambios);
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(ConsultarPerfil.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(ConsultarPerfil.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        limpiarSesion();
        Intent intent = new Intent(ConsultarPerfil.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void limpiarSesion(){
        sesion.limpiarSesion();
    }

}