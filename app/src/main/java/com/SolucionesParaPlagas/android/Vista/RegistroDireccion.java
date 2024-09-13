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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Validaciones;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class RegistroDireccion extends AppCompatActivity {

    private ImageView botonRegresar;
    private EditText usuarioCalle, usuarioColonia, usuarioLocalidad, usuarioNoInterior, usuarioNoExterior, usuarioCP, usuarioMunicipio;
    private Spinner usuarioEstado;
    private Button btnRegistrar;

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
        botonRegresar = findViewById(R.id.iconoatras);
        btnRegistrar = findViewById(R.id.btnSubir);
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

    private void mandarRegistro(View v){
        if (validarCampos()) {
            notificarUsuario();
            if (mandarCorreo()) {
                regresarAPaginaInicio(v);
            }
        }
    }

    private boolean mandarCorreo(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amjo220898@upemor.edu.mx"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Registro de Cliente en la APP");
        emailIntent.putExtra(Intent.EXTRA_TEXT, construirMensaje());
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error al enviar el correo. Por favor, inténtalo nuevamente.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void notificarUsuario(){
        new AlertDialog.Builder(this)
                .setTitle("Nota")
                .setMessage("Te solicitamos confirmar tus datos perosnales y adjuntes en el correo electronico el documento que valide tu situación fiscal para completar tu registro con nosotros.¡Gracias!.")
                .setPositiveButton("OK", null)
                .show();
    }

    private String construirMensaje(){
        ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
        ClienteIndividual clienteIndividual = controladorClienteIndividual.obtenerCliente();
        StringBuilder mensajeCorreo = new StringBuilder();
        mensajeCorreo.append("Nombre cliente: " + razonSocial+",\n\n");
        mensajeCorreo.append("RFC: " + rfc+",\n\n");
        mensajeCorreo.append("Telefono: " + telefono+",\n\n");
        mensajeCorreo.append("Correo: " + correo+",\n\n");
        mensajeCorreo.append("------------------------------------------"+"\n\n");
        mensajeCorreo.append("Dirección del cliente: " + "\n\n");
        mensajeCorreo.append("Calle: " + usuarioCalle+",\n\n");
        mensajeCorreo.append("Colonia: " + usuarioColonia+",\n\n");
        mensajeCorreo.append("Localidad: " + usuarioLocalidad+",\n\n");
        mensajeCorreo.append("No. Interior: " + usuarioNoInterior+",\n\n");
        mensajeCorreo.append("No. Exterior: " + usuarioNoExterior+",\n\n");
        mensajeCorreo.append("Codigo Postal: " + usuarioCP+",\n\n");
        mensajeCorreo.append("Municipio: " + usuarioMunicipio+",\n\n");
        mensajeCorreo.append("Estado: " + usuarioEstado.getSelectedItem().toString()+",\n\n");
        return mensajeCorreo.toString();
    }

}
