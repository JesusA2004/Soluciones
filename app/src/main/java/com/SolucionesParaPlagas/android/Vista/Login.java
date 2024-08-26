package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.example.sol.R;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private ImageView botonRegresar;
    private EditText usuarioRFC;
    private EditText usuarioTelefono;
    private Button botonIniciarSesion;
    private ProgressBar iconoCarga;
    private ControladorJsonCliente controladorClienteJson;
    private ControladorClienteIndividual controladorClienteI = new ControladorClienteIndividual();
    private ControladorDetalleCliente controladorDetalleCliente = new ControladorDetalleCliente();

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
        iconoCarga.setVisibility(View.GONE); // Inicialmente oculto
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarAPaginaInicio);
        botonIniciarSesion.setOnClickListener(this::iniciarSesion);
    }

    private void regresarAPaginaInicio(View v) {
        Intent intent = new Intent(Login.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void iniciarSesion(View v) {
        iconoCarga.setVisibility(View.VISIBLE); // Mostrar ProgressBar
        new Thread(() -> {
            controladorClienteJson = new ControladorJsonCliente(usuarioRFC.getText().toString().trim());
            controladorClienteJson.realizarSolicitud();
            // Esperar un poco para asegurarse de que el cliente se haya actualizado
            try {
                Thread.sleep(1500); // Ajusta el tiempo según sea necesario
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ClienteIndividual clienteIndividual = controladorClienteI.obtenerCliente();
            runOnUiThread(() -> {
                iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                if (clienteIndividual != null) {
                    Toast.makeText(Login.this, "El cliente ingresado es:\n" + clienteIndividual.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Exito", "El cliente es: " + clienteIndividual.toString());
                    irAMenu(v, clienteIndividual);
                } else {
                    Toast.makeText(Login.this, "Error, el RFC ingresado no está registrado", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void irAMenu(View v, ClienteIndividual clienteCompleto) {
        Intent intent = new Intent(Login.this, Menu.class);
        // intent.putExtra("Cliente", clienteCompleto);
        startActivity(intent);
    }

}
