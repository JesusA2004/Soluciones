package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class RegistroDatos extends AppCompatActivity {

    private ImageView botonRegresar;
    private ImageView botonSiguiente;
    private EditText usuarioRFC;
    private EditText usuarioRazonS;
    private EditText usuarioTelefono;
    private EditText usuarioCorreo;
    private ControladorValidaciones validaciones; // Objeto para realizar las validaciones

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
        usuarioRFC = findViewById(R.id.entradaRFC);
        usuarioRazonS = findViewById(R.id.entradaRazonSocial);
        usuarioTelefono = findViewById(R.id.entradaTel);
        usuarioCorreo = findViewById(R.id.entradaCorreo);
        validaciones = new ControladorValidaciones(); // Inicializa el objeto Validaciones
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarAPaginaInicio);
        botonSiguiente.setOnClickListener(this::irARegistrarDireccion);
    }

    private void regresarAPaginaInicio(View v) {
        Intent intent = new Intent(RegistroDatos.this, PaginaInicio.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos() {
        // Validar si el RFC es correcto
        if (validaciones.validarStringVacio(usuarioRFC.getText().toString()) ||
                !validaciones.validarRFC(usuarioRFC.getText().toString())) {
            Toast.makeText(this, "Por favor ingresa un RFC válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validar que el campo Razon Social no esté vacío
        if (validaciones.validarStringVacio(usuarioRazonS.getText().toString())) {
            Toast.makeText(this, "Por favor ingresa la razón social", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validar que el campo Teléfono no esté vacío y contenga solo números
        if (validaciones.validarStringVacio(usuarioTelefono.getText().toString()) ||
                !validaciones.validarSoloNumeros(usuarioTelefono.getText().toString())) {
            Toast.makeText(this, "Por favor ingresa un número de teléfono válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validar que el campo Correo no esté vacío
        if (validaciones.validarStringVacio(usuarioCorreo.getText().toString())) {
            Toast.makeText(this, "Por favor ingresa un correo electrónico", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void irARegistrarDireccion(View v) {
        if (validarCampos()) {
            Intent intent = new Intent(RegistroDatos.this, RegistroDireccion.class);
            intent.putExtra("RFC", usuarioRFC.getText().toString());
            intent.putExtra("RazonSocial", usuarioRazonS.getText().toString());
            intent.putExtra("Telefono", usuarioTelefono.getText().toString());
            intent.putExtra("Correo", usuarioCorreo.getText().toString());
            startActivity(intent);
        }
    }

}
