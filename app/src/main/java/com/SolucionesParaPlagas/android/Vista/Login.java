package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.net.ConnectivityManager;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class Login extends AppCompatActivity {

    private ProgressBar iconoCarga;
    private ImageView botonRegresar;
    private Button botonIniciarSesion;
    private EditText usuarioRFC, usuarioTelefono;
    private TextView txtMsjCargando, txtTitutlo, txtRFC, txtTelefono;
    private ControladorValidaciones validar = new ControladorValidaciones();
    private Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(this);

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
        // Verificar que el dispositivo cuenta con internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Validar que el RFC y el campo del teléfono sean válidos
            if (validarCampos()) {
                mostrarPantallaCarga();
                // Hacemos la solicitud a la base de datos y mandamos los datos al repositorio local
                controladorCliente.objetoToRepositorio(usuarioRFC.getText().toString(), usuarioTelefono.getText().toString());

                // Obtenemos los datos del repositorio local
                Cliente cliente = controladorCliente.obtenerObjeto();
                ocultarPantallaCarga(); // Ocultar pantalla de carga

                // Si llego el objeto al repositorio entonces existe en la base de datos
                if (cliente != null) {
                    if (cliente.getTelefonoC().equals(usuarioTelefono.getText().toString())) {
                        irAMenu(v);
                    } else {
                        avisoUsuario("Error, el teléfono ingresado no corresponde al RFC");
                    }
                } else {
                    avisoUsuario("Error, cliente no registrado");
                }
            }
        } else {
            avisoUsuario("No tienes conexión a internet");
        }
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(Login.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void avisoUsuario(String mensaje){
        Toast.makeText(Login.this,mensaje, Toast.LENGTH_SHORT).show();
    }

}
