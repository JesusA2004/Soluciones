package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sol.R;

public class RegistroDatos extends AppCompatActivity {

    private ImageView botonRegresar;
    private ImageView botonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        inicializarElementos();
        configurarBotones();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void inicializarElementos() {
        botonRegresar = findViewById(R.id.flechaatras);
        botonSiguiente = findViewById(R.id.iconosiguiente);
    }

    private void configurarBotones() {
        // Configura el listener para la ImageView
        botonRegresar.setOnClickListener(this::regresarAPaginaInicio);
        botonSiguiente.setOnClickListener(this::irARegistrarDireccion);
    }

    private void regresarAPaginaInicio(View v) {
        // Acción para regresar a la pantalla anterior
        Intent intent = new Intent(RegistroDatos.this, PaginaInicio.class);
        startActivity(intent);
        finish(); // Opcional: Finaliza la actividad actual para que no esté en el stack
    }

    private void irARegistrarDireccion(View v){
        Intent intent = new Intent(RegistroDatos.this, RegistroDireccion.class);
        startActivity(intent);
        finish();
    }

}