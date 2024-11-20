package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;
import com.example.sol.R;

public class EditarDireccion extends AppCompatActivity {

    private EditText campo;
    private String dato, titulo;
    private TextView tit;
    private Button btnConfirmar;
    private ImageView btnMenu, btnCerrarSesion, btnProductos, btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editardatocliente);
        inicializarElementos();
        recibirElementos(); // Llamar para recibir datos
        configurarBotones();
    }

    private void inicializarElementos() {
        btnConfirmar = findViewById(R.id.btnConfirmacion);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnAtras = findViewById(R.id.iconoAtras);
        campo = findViewById(R.id.entrada);
        tit = findViewById(R.id.textView);
    }

    private void recibirElementos() {
        Intent intent = getIntent();
        if (intent != null) {
            dato = intent.getStringExtra("dato");
            titulo = intent.getStringExtra("campo"); // Cambia esto si es necesario
            campo.setText(dato);
            // campo.setTextColor(Color.parseColor("#000000"));
            tit.setText(titulo+": ");
        }
    }

    private void configurarBotones() {
        btnConfirmar.setOnClickListener(this::guardarCambios);
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
        btnAtras.setOnClickListener(this::regresarAEditarPerfil);
    }

    private void guardarCambios(View v) {
        Intent intent = new Intent(EditarDireccion.this, ConsultarPerfil.class);
        intent.putExtra("campo", campo.getText().toString());
        intent.putExtra("titulo", titulo);
        startActivity(intent);
    }

    private void regresarAProductos(View v) {
        Intent intent = new Intent(EditarDireccion.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(EditarDireccion.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    private void regresarAEditarPerfil(View v) {
        Intent intent = new Intent(EditarDireccion.this, ConsultarPerfil.class);
        intent.putExtra("campo", campo.getText().toString());
        intent.putExtra("titulo", tit.getText().toString());
        startActivity(intent);
    }

}
