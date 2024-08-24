package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sol.R;

public class Login extends AppCompatActivity {

    private ImageView botonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarElementos();
        configurarBotones();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void inicializarElementos() {
        botonRegresar = findViewById(R.id.flechaatras);
    }

    private void configurarBotones() {
        // Configura el listener para la ImageView
        botonRegresar.setOnClickListener(this::regresarAPaginaInicio);
    }

    private void regresarAPaginaInicio(View v) {
        // Acción para regresar a la pantalla anterior
        Intent intent = new Intent(Login.this, PaginaInicio.class);
        startActivity(intent);
        finish(); // Opcional: Finaliza la actividad actual para que no esté en el stack
    }

}
