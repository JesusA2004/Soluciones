package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.view.View;
import com.example.sol.R;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;

public class EditarDatosP extends AppCompatActivity {

    EditText campo;
    String dato,titulo;
    TextView tit;
    Button btnConfirmar;
    ImageView btnMenu, btnCerrarSesion, btnProductos, btnAtras;
    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editardatocliente);
        inicializarElementos();
        configurarBotones();
    }

    private void inicializarElementos() {
        /*campo = findViewById(R.id.campo);
        titulo = findViewById(R.id.titulo);*/
        btnConfirmar = findViewById(R.id.btnConfirmacion);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnAtras = findViewById(R.id.iconoAtras);
    }

    private void recibirElementos(){
        Intent intent = getIntent();
        if(intent != null){
            dato = intent.getStringExtra("campo");
            titulo = intent.getStringExtra("titulo");
            campo.setText(dato);
            tit.setText(titulo);
        }
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
        btnAtras.setOnClickListener(this::regresarAEditarPerfil);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(EditarDatosP.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(EditarDatosP.this,Menu.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        limpiarSesion();
        Intent intent = new Intent(EditarDatosP.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void limpiarSesion(){
        sesion.limpiarSesion();
    }

    private void regresarAEditarPerfil(View v){
        Intent intent = new Intent(EditarDatosP.this, ConsultarPerfil.class);
        intent.putExtra("campo", dato);
        intent.putExtra("titulo", tit.getText().toString());
        startActivity(intent);
    }

}