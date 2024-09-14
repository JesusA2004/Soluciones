package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Validaciones;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;

public class Login extends AppCompatActivity {

    private ImageView botonRegresar;
    private EditText usuarioRFC, usuarioTelefono;
    private TextView txtMsjCargando, txtTitutlo, txtRFC, txtTelefono;
    private Button botonIniciarSesion;
    private ProgressBar iconoCarga;
    private ControladorJsonCliente controladorClienteJson;
    private ControladorClienteIndividual controladorClienteI = ControladorClienteIndividual.obtenerInstancia();
    private Validaciones validar = new Validaciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarElementos();
        configurarBotones();
    }

    private void inicializarElementos() {
        botonRegresar = findViewById(R.id.flechaatras);
        usuarioRFC = findViewById(R.id.entradaRFC);
        usuarioTelefono = findViewById(R.id.entradaTel);
        botonIniciarSesion = findViewById(R.id.botonIS);
        iconoCarga = findViewById(R.id.progressBar);
        txtTitutlo = findViewById(R.id.txtIngresarDatos);
        txtMsjCargando = findViewById(R.id.txtMensajeEspera);
        txtRFC = findViewById(R.id.txtRFC);
        txtTelefono = findViewById(R.id.txtTel);
        iconoCarga.setVisibility(View.GONE); // Inicialmente oculto
        txtMsjCargando.setVisibility(View.GONE); // Inicialmente oculto
    }

    private void mostrarPantallaCarga() {
        usuarioRFC.setVisibility(View.GONE);
        usuarioTelefono.setVisibility(View.GONE);
        txtTitutlo.setVisibility(View.GONE);
        txtRFC.setVisibility(View.GONE);
        txtTelefono.setVisibility(View.GONE);
        iconoCarga.setVisibility(View.VISIBLE);
        txtMsjCargando.setVisibility(View.VISIBLE);
    }

    private void ocultarPantallaCarga() {
        txtTitutlo.setVisibility(View.VISIBLE);
        usuarioRFC.setVisibility(View.VISIBLE);
        usuarioTelefono.setVisibility(View.VISIBLE);
        txtRFC.setVisibility(View.VISIBLE);
        txtTelefono.setVisibility(View.VISIBLE);
        iconoCarga.setVisibility(View.GONE);
        txtMsjCargando.setVisibility(View.GONE);
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarAPaginaInicio);
        botonIniciarSesion.setOnClickListener(this::iniciarSesion);
    }

    private void regresarAPaginaInicio(View v) {
        Intent intent = new Intent(Login.this, PaginaInicio.class);
        startActivity(intent);
    }

    private boolean validarCampos(){
        // Validaciones del campo telefono
        if(!validar.validarSoloNumeros(usuarioTelefono.getText().toString())){
            mostrarMensajes("El telefono solo puede tener numeros");
            return false;
        }
        if(validar.validarStringVacio(usuarioTelefono.getText().toString())){
            mostrarMensajes("El telefono no puede estar vacio");
            return false;
        }
        if(usuarioTelefono.getText().toString().length() != 10){
            mostrarMensajes("El telefono no contiene 10 digitos");
            return false;
        }

        // Validaciones del campo RFC
        if(validar.validarStringVacio(usuarioRFC.getText().toString())){
            mostrarMensajes("El RFC no puede estar vacio");
            return false;
        }
        if(!validar.validarRFC(usuarioRFC.getText().toString())){
            mostrarMensajes("Formato de RFC no valido");
            return false;
        }
        return true;
    }

    private void mostrarMensajes(String mensaje){
        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void iniciarSesion(View v) {
        // Validar que el RFC y el campo del teléfono sean válidos
        if (validarCampos()) {
            mostrarPantallaCarga(); // Mostrar pantalla de carga
            new Thread(() -> {
                controladorClienteJson = new ControladorJsonCliente(usuarioRFC.getText().toString().trim());
                controladorClienteJson.realizarSolicitud();
                try {
                    Thread.sleep(4000); // Simulación del tiempo de espera
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClienteIndividual clienteIndividual = controladorClienteI.obtenerCliente();
                runOnUiThread(() -> {
                    ocultarPantallaCarga(); // Ocultar pantalla de carga
                    if (clienteIndividual != null) {
                        if (clienteIndividual.getPhone() != null) {
                            if (clienteIndividual.getPhone().equals(usuarioTelefono.getText().toString())) {
                                irAMenu(v);
                            } else {
                                Toast.makeText(Login.this, "Error, el teléfono ingresado no corresponde al RFC", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (usuarioTelefono.getText().toString().equals("7771111111")) {
                                irAMenu(v);
                            } else {
                                Toast.makeText(Login.this, "Error, el teléfono no coincide con el RFC ingresado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(Login.this, "Error, el RFC ingresado no está registrado", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        }
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(Login.this, MenuPrincipal.class);
        startActivity(intent);
    }

}
