package com.SolucionesParaPlagas.android.Vista;

import android.net.Uri;
import android.os.Bundle;
import com.example.sol.R;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Validaciones;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;


public class AgregarDireccion extends AppCompatActivity {

    private ImageView botonRegresar;
    private EditText usuarioCalle, usuarioColonia, usuarioLocalidad, usuarioNoInterior, usuarioNoExterior, usuarioCP, usuarioMunicipio;
    private Spinner usuarioEstado;
    private Button btnRegistrar;
    private String tipoEnvio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro2);
        inicializarElementos();
        recibirElementos();
        configurarBotones();
    }

    private void recibirElementos() {
        Intent intent = getIntent();
        if (intent != null) {
            tipoEnvio = intent.getStringExtra("tipoDireccion");
        }
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
        btnRegistrar.setText("Guardar información");
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarADatosDireccion);
        btnRegistrar.setOnClickListener(this::mandarRegistro);
    }

    private void mandarRegistro(View v){
        if (validarCampos()) {
            notificarUsuario();
        }
    }

    private void notificarUsuario(){
        new AlertDialog.Builder(this)
                .setTitle("Nota")
                .setMessage("Te solicitamos confirmar tus datos de direccion antes del envio de los mismos.¡Gracias!.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mandarCorreoOWhatsapp(null);
                    }
                })
                .show();
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

    private void regresarADatosDireccion(View v) {
        // Acción para regresar a la pantalla anterior
        Intent intent = new Intent(AgregarDireccion.this, EditarDirecciones.class);
        startActivity(intent);
        finish();
    }

    private String construirMensaje(){
        StringBuilder mensajeCorreo = new StringBuilder();
        ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
        DetalleCliente cliente = controladorDetalleCliente.obtenerCliente();
        mensajeCorreo.append("El cliente: "+cliente.getCommercialName()+" desea agregar una dirección "+tipoEnvio+"\n\n");
        mensajeCorreo.append("------------------------------------------"+"\n\n");
        mensajeCorreo.append("Dirección del cliente: " + "\n\n");
        mensajeCorreo.append("Calle: " + usuarioCalle.getText().toString()+",\n\n");
        mensajeCorreo.append("Colonia: " + usuarioColonia.getText().toString()+",\n\n");
        mensajeCorreo.append("Localidad: " + usuarioLocalidad.getText().toString()+",\n\n");
        mensajeCorreo.append("No. Interior: " + usuarioNoInterior.getText().toString()+",\n\n");
        mensajeCorreo.append("No. Exterior: " + usuarioNoExterior.getText().toString()+",\n\n");
        mensajeCorreo.append("Codigo Postal: " + usuarioCP.getText().toString()+",\n\n");
        mensajeCorreo.append("Municipio: " + usuarioMunicipio.getText().toString()+",\n\n");
        mensajeCorreo.append("Estado: " + usuarioEstado.getSelectedItem().toString());
        return mensajeCorreo.toString();
    }

    private boolean mandarCorreoOWhatsapp(View v) {
        // Construye el mensaje que se enviará por correo y WhatsApp
        String mensaje = construirMensaje();
        // Configura el Intent para enviar un correo electrónico
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@solucionesparaplagas.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Registro de direccion de un cliente en la APP");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        // Configura el Intent para enviar un mensaje por WhatsApp
        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
        whatsappIntent.setPackage("com.whatsapp");
        // Número de teléfono en formato internacional sin el "+"
        String phoneNumber = "7771308184";
        whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(mensaje)));
        try {
            // Crea un Intent chooser para permitir al usuario elegir entre correo electrónico y WhatsApp
            Intent chooserIntent = Intent.createChooser(emailIntent, "Enviar registro direccion...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{whatsappIntent});
            startActivity(chooserIntent);
            // Ejecuta el código después de que el usuario haya elegido una opción
            new Thread(() -> {
                try {
                    // Espera un momento para asegurar que la acción se complete
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runOnUiThread(() -> {
                    regresarADatosDireccion(v);
                });
            }).start();
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error al enviar los datos. Por favor, inténtalo nuevamente.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}