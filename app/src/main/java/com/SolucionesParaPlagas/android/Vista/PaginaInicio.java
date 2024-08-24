package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sol.R;

public class PaginaInicio extends AppCompatActivity {

    private Button botonIniciarSesion;
    private Button botonCrearCuenta;

    // Metodos de manipulacion de la interfaz (layout)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);
        inicializarElementos();
        configurarBotones();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Metodos para configurar los elementos dentro de los layout y que se usaran por el usuario
    private void inicializarElementos() {
        botonIniciarSesion = findViewById(R.id.btnInicioSesion);
        botonCrearCuenta = findViewById(R.id.btnCrearCuenta);
    }

    private void configurarBotones() {
        // Configura el listener del botón Iniciar Sesión
        botonIniciarSesion.setOnClickListener(this::irALogin);
        botonCrearCuenta.setOnClickListener(this::irACrearCuenta);
    }

    private void irALogin(View v) {
        Intent intent = new Intent(PaginaInicio.this, Login.class);
        startActivity(intent);
    }

    private void irACrearCuenta(View v){
        Intent intent = new Intent(PaginaInicio.this, RegistroDatos.class);
        startActivity(intent);
    }

}
