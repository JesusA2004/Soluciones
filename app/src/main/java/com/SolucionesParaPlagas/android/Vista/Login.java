package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.example.sol.R;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

public class Login extends AppCompatActivity {

    private ImageView botonRegresar;
    private EditText usuarioRFC;
    private EditText usuarioTelefono;
    private Button botonIniciarSesion;
    // Objetos para obtener los datos del cliente
    private ClienteIndividual clienteIndividual = new ClienteIndividual();
    private DetalleCliente clienteCompleto = new DetalleCliente();
    // Controladores a usar
    private Controlador<JsonCliente> controladorClienteJson;
    private ControladorClienteIndividual controladorClienteI = new ControladorClienteIndividual();
    private Controlador<DetalleCliente> controladorClienteCompleto;

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
        usuarioRFC = findViewById(R.id.entradaRFC);
        usuarioTelefono = findViewById(R.id.entradaTel);
        botonIniciarSesion = findViewById(R.id.botonIS);
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarAPaginaInicio);
        botonIniciarSesion.setOnClickListener(this::iniciarSesion);
    }

    private void regresarAPaginaInicio(View v) {
        // Acción para regresar a la pantalla anterior
        Intent intent = new Intent(Login.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void iniciarSesion(View v){
        controladorClienteJson = new ControladorJsonCliente(usuarioRFC.getText().toString().trim());
        controladorClienteJson.realizarSolicitud();
        // Verficar que el cliente se guardo en el repositorio
        if(controladorClienteJson.datosCarg()){
            clienteIndividual = controladorClienteI.obtenerCliente();
            controladorClienteCompleto = new ControladorDetalleCliente(clienteIndividual.getID());
            controladorClienteCompleto.realizarSolicitud();
            if(controladorClienteCompleto.datosCarg()){
                // Objeto a usar durante toda la app
                clienteCompleto = controladorClienteCompleto.obtenerDato();
                Toast.makeText(this, "El cliente ingresado es:\n"+clienteCompleto.toString(), Toast.LENGTH_SHORT).show();
                irAMenu(v,clienteCompleto);  // Esta línea estaba fuera de las llaves del if
            }
        } else {
            Toast.makeText(this, "Error, el RFC ingresado no esta registrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void irAMenu(View v,DetalleCliente clienteCompleto){
        Intent intent = new Intent(Login.this, Menu.class);
        intent.putExtra("Cliente",clienteCompleto);
        startActivity(intent);
    }

}
