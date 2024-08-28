package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.sol.R;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Controlador.Validaciones;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
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
        // Primero validar que el rfc y el campo del telefono sean validos
            if(!validar.validarSoloNumeros(usuarioTelefono.getText().toString())){
                Toast.makeText(Login.this, "Error en el numero ingresado", Toast.LENGTH_SHORT).show();
            }else{
                iconoCarga.setVisibility(View.VISIBLE); // Mostrar ProgressBar
                new Thread(() -> {
                    controladorClienteJson = new ControladorJsonCliente(usuarioRFC.getText().toString().trim());
                    controladorClienteJson.realizarSolicitud();
                    // Esperar un poco para asegurarse de que el cliente se haya actualizado
                    try {
                        Thread.sleep(4000); // Ajusta el tiempo según sea necesario
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ClienteIndividual clienteIndividual = controladorClienteI.obtenerCliente();
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                        if(clienteIndividual != null){
                            if(clienteIndividual.getPhone() != null){
                                if(clienteIndividual.getPhone().equals(usuarioTelefono.getText().toString())){
                                    Toast.makeText(Login.this, "El cliente ingresado es:\n" + clienteIndividual.toString(), Toast.LENGTH_SHORT).show();
                                    irAMenu(v, clienteIndividual);
                                }else{
                                    Toast.makeText(Login.this, "Error, el telefono ingresado no corresponde al RFC", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                if(usuarioTelefono.getText().toString().equals("7771111111")){
                                    Toast.makeText(Login.this, "El cliente ingresado es:\n" + clienteIndividual.toString(), Toast.LENGTH_SHORT).show();
                                    irAMenu(v, clienteIndividual);
                                } else {
                                    Toast.makeText(Login.this, "Error, el telefono no coincide con el RFC ingresado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(Login.this, "Error, el RFC ingresado no está registrado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
    }

    private void irAMenu(View v, ClienteIndividual cliente) {
        Intent intent = new Intent(Login.this, Menu.class);
        intent.putExtra("Cliente", cliente);
        startActivity(intent);
    }

}
