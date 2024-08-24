package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sol.R;

public class PaginaInicio extends AppCompatActivity {

    private Button botonIniciarSesion;
    private Button botonCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);
        inicializarElementos();
        // botonIniciarSesion.setOnClickListener(v -> iniciarSesion());
        // botonCrearCuenta.setOnClickListener(v -> crearCuenta());
    }

    private void inicializarElementos(){
        botonIniciarSesion = findViewById(R.id.button);
        botonCrearCuenta = findViewById(R.id.txtCrearCuenta);
    }

    private void iniciarSesion(){

    }

    private void crearCuenta(){

    }

}
