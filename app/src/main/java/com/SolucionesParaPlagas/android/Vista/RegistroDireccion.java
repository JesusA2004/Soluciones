package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Validaciones;
import com.example.sol.R;

public class RegistroDireccion extends AppCompatActivity {

    private ImageView botonMenu;
    private ImageView botonRegresar;
    private ImageView botonSiguiente;
    private EditText usuarioCalle;
    private EditText usuarioColonia;
    private EditText usuarioLocalidad;
    private EditText usuarioNoInterior;
    private EditText usuarioNoExterior;
    private EditText usuarioCP;
    private EditText usuarioMunicipio;
    private Spinner usuarioEstado;

    // Datos recibidos de la actividad anterior
    private String rfc, razonSocial, telefono, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro2);
        inicializarElementos();
        recibirDatosDeRegistroDatos();
        configurarBotones();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void inicializarElementos() {
        botonMenu = findViewById(R.id.flechaatras);
        botonRegresar = findViewById(R.id.iconoatras);
        botonSiguiente = findViewById(R.id.iconosiguiente); // Añadimos el botón para ir a la siguiente actividad
        usuarioCalle = findViewById(R.id.entradaCalle);
        usuarioColonia = findViewById(R.id.entradaColonia);
        usuarioLocalidad = findViewById(R.id.entradaLocalidad);
        usuarioNoInterior = findViewById(R.id.entradaNoInterior);
        usuarioNoExterior = findViewById(R.id.entradaNoExterior);
        usuarioCP = findViewById(R.id.entradaCP);
        usuarioMunicipio = findViewById(R.id.entradaMunicipio);
        usuarioEstado = findViewById(R.id.comboboxEstado);
    }

    private void recibirDatosDeRegistroDatos() {
        // Recibe los datos enviados desde la actividad anterior
        Intent intent = getIntent();
        rfc = intent.getStringExtra("RFC");
        razonSocial = intent.getStringExtra("RazonSocial");
        telefono = intent.getStringExtra("Telefono");
        correo = intent.getStringExtra("Correo");
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarRegistrarDatos);
        botonSiguiente.setOnClickListener(this::irASubirDocumento);
        botonMenu.setOnClickListener(this::regresarAPaginaInicio);
    }

    private void regresarRegistrarDatos(View v){
        Intent intent = new Intent(RegistroDireccion.this, RegistroDatos.class);
        startActivity(intent);
        finish();
    }

    private void regresarAPaginaInicio(View v) {
        // Acción para regresar a la pantalla anterior
        Intent intent = new Intent(RegistroDireccion.this, PaginaInicio.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos() {
        Validaciones validaciones = new Validaciones();
        // Validar que los campos no estén vacíos
        if (validaciones.validarStringVacio(usuarioCalle.getText().toString()) ||
                validaciones.validarStringVacio(usuarioColonia.getText().toString()) ||
                validaciones.validarStringVacio(usuarioLocalidad.getText().toString()) ||
                validaciones.validarStringVacio(usuarioNoInterior.getText().toString()) ||
                validaciones.validarStringVacio(usuarioNoExterior.getText().toString()) ||
                validaciones.validarStringVacio(usuarioCP.getText().toString()) ||
                validaciones.validarStringVacio(usuarioMunicipio.getText().toString())) {
            Toast.makeText(this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validar que el campo CP solo contenga números
        if (!validaciones.validarSoloNumeros(usuarioCP.getText().toString())) {
            Toast.makeText(this, "El código postal debe contener solo números.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void irASubirDocumento(View v) {
        if (validarCampos()) {
            Intent intent = new Intent(RegistroDireccion.this, SubirDocumento.class);
            // Pasamos los datos de RegistroDatos a la siguiente actividad
            intent.putExtra("RFC", rfc);
            intent.putExtra("RazonSocial", razonSocial);
            intent.putExtra("Telefono", telefono);
            intent.putExtra("Correo", correo);
            // Pasamos los datos ingresados en RegistroDireccion
            intent.putExtra("Calle", usuarioCalle.getText().toString());
            intent.putExtra("Colonia", usuarioColonia.getText().toString());
            intent.putExtra("Localidad", usuarioLocalidad.getText().toString());
            intent.putExtra("NoInterior", usuarioNoInterior.getText().toString());
            intent.putExtra("NoExterior", usuarioNoExterior.getText().toString());
            intent.putExtra("CP", usuarioCP.getText().toString());
            intent.putExtra("Municipio", usuarioMunicipio.getText().toString());
            intent.putExtra("Estado", usuarioEstado.getSelectedItem().toString());
            startActivity(intent);
            finish();
        }
    }
    
}
