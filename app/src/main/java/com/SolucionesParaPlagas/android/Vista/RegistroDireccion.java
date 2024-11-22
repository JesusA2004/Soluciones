package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class RegistroDireccion extends AppCompatActivity {

    private Button btnRegistrar;
    private Spinner usuarioEstado;
    private ImageView botonRegresar;
    private EditText usuarioCalle, usuarioColonia, usuarioLocalidad, usuarioCP, usuarioMunicipio;
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

    private void inicializarElementos() {
        botonRegresar = findViewById(R.id.iconoatras);
        btnRegistrar = findViewById(R.id.btnSubir);
        usuarioCalle = findViewById(R.id.entradaCalle);
        usuarioColonia = findViewById(R.id.entradaColonia);
        usuarioLocalidad = findViewById(R.id.entradaLocalidad);
        usuarioCP = findViewById(R.id.entradaCP);
        usuarioMunicipio = findViewById(R.id.entradaMunicipio);
        usuarioEstado = findViewById(R.id.comboboxEstado);
    }

    private void recibirDatosDeRegistroDatos() {
        // Recibe los datos enviados desde la actividad anterior
        Intent intent = getIntent();
        if(intent != null){
            rfc = intent.getStringExtra("RFC");
            razonSocial = intent.getStringExtra("RazonSocial");
            telefono = intent.getStringExtra("Telefono");
            correo = intent.getStringExtra("Correo");
        }
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarRegistrarDatos);
        btnRegistrar.setOnClickListener(this::mandarRegistro);
    }

    private void regresarRegistrarDatos(View v){
        Intent intent = new Intent(RegistroDireccion.this, RegistroDatos.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos() {
        ControladorValidaciones validaciones = new ControladorValidaciones();
        // Validar que los campos no estén vacíos
        if (validaciones.validarStringVacio(usuarioCalle.getText().toString()) ||
                validaciones.validarStringVacio(usuarioColonia.getText().toString()) ||
                validaciones.validarStringVacio(usuarioLocalidad.getText().toString()) ||
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

    private void mandarRegistro(View v){
        if (validarCampos()) {
            Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(this);

            Cliente cliente = new Cliente();
            cliente.setClienteRFC(rfc);
            cliente.setNombreC(razonSocial);
            cliente.setRazonSocial(razonSocial);
            cliente.setEmail(correo);
            cliente.setTelefonoC(telefono);
            cliente.setCalle(usuarioCalle.getText().toString());
            cliente.setColonia(usuarioColonia.getText().toString());
            cliente.setLocalidad(usuarioLocalidad.getText().toString());
            cliente.setEstado(usuarioEstado.toString());
            int cp = Integer.parseInt(usuarioCP.getText().toString());
            cliente.setClienteCP(cp);
            cliente.setMunicipio(usuarioMunicipio.getText().toString());

            controladorCliente.registrarObjeto(cliente);
        }
    }

}
