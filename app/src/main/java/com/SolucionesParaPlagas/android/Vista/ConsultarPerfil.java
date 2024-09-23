package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import com.example.sol.R;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.content.DialogInterface;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;

public class ConsultarPerfil extends AppCompatActivity implements AdaptadorPerfil.OnChildClickListener {

    private Button btnGuardarCambios;
    private ExpandableListView datosPersonales;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private DetalleCliente clienteCompleto = new DetalleCliente();
    private DetalleCliente clienteCambios;
    private ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datoscliente);
        inicializarCliente();
        recibirElementos();
        inicializarElementos();
        configurarExpandableListViews();
        configurarBotones();
    }

    private void recibirElementos() {
        Intent intent = getIntent();
        if (intent != null) {
            String dato = intent.getStringExtra("campo");
            String titulo = intent.getStringExtra("titulo");
            if (titulo != null) {
                switch (titulo) {
                    case "Nombre Comercial":
                        clienteCambios.setCommercialName(dato);
                        break;
                    case "Nombre Legal":
                        clienteCambios.setLegalName(dato);
                        break;
                    case "Email":
                        clienteCambios.setEmail(dato);
                        break;
                    case "RFC":
                        clienteCambios.setRFC(dato);
                        break;
                    case "Telefono":
                        clienteCambios.setTelephones(dato);
                        break;
                    default:
                        // Manejar otros casos o ignorar
                        break;
                }
            }
        }
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnGuardarCambios = findViewById(R.id.btnSubirCambios);
        datosPersonales = findViewById(R.id.datosPersonales);
    }

    private void inicializarCliente() {
        // Obtenemos el cliente ya que es el único en el repositorio
        clienteCompleto = controladorDetalleCliente.obtenerCliente();
        clienteCambios = controladorDetalleCliente.obtenerCliente(1);
        // Verificamos si ya existe un cliente en el repositorio con cambios
        if(clienteCambios == null){
            clienteCambios = new DetalleCliente();
            clienteCambios.setCommercialName(clienteCompleto.getCommercialName());
            clienteCambios.setLegalName(clienteCompleto.getLegalName());
            clienteCambios.setEmail(clienteCompleto.getEmail());
            clienteCambios.setRFC(clienteCompleto.getRFC());
            clienteCambios.setTelephones(clienteCompleto.getTelephones());
        }
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnGuardarCambios.setOnClickListener(this::irAGuardarCambios);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void regresarAProductos(View v) {
        Intent intent = new Intent(ConsultarPerfil.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(ConsultarPerfil.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        Sesion sesion = new Sesion();
        sesion.confirmarCerrarSesion(this);
    }

    private void irAGuardarCambios(View v) {
        notificarUsuario();
    }

    private boolean mandarCorreoOWhatsapp(View v) {
        // Construye el mensaje que se enviará por correo y WhatsApp
        String mensaje = construirMensaje();
        if (mensaje.isEmpty()) {
            Toast.makeText(this, "No hay cambios en los datos.", Toast.LENGTH_LONG).show();
            return false;
        }
        // Configura el Intent para enviar un correo electrónico
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@solucionesparaplagas.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cambio en los datos del Cliente en la APP");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        // Configura el Intent para enviar un mensaje por WhatsApp
        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
        whatsappIntent.setPackage("com.whatsapp");
        // Número de teléfono en formato internacional sin el "+"
        String phoneNumber = "7771308184";
        whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(mensaje)));
        try {
            // Crea un Intent chooser para permitir al usuario elegir entre correo electrónico y WhatsApp
            Intent chooserIntent = Intent.createChooser(emailIntent, "Enviar registro...");
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
                    irAMenu(v);
                });
            }).start();
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error al enviar los datos. Por favor, inténtalo nuevamente.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void notificarUsuario(){
        new AlertDialog.Builder(this)
                .setTitle("Nota")
                .setMessage("Te solicitamos confirmar tus datos personales antes del envio de los mismos.¡Gracias!.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mandarCorreoOWhatsapp(null);
                    }
                })
                .show();
    }

    private String construirMensaje(){
        StringBuilder mensajeCorreo = new StringBuilder();
        mensajeCorreo.append("El cliente: "+clienteCompleto.getCommercialName()+" y RFC: "+clienteCompleto.getRFC());
        mensajeCorreo.append("\nha realizado los siguientes cambios en sus datos\n");
        mensajeCorreo.append("------------------------------------------\n\n");
        mensajeCorreo.append("El cual quiere realizar los siguientes cambios: \n");
        boolean key = false;
        if(!clienteCambios.getCommercialName().equals(clienteCompleto.getCommercialName())){
            mensajeCorreo.append("El anterior nombre comercial era: "+clienteCompleto.getCommercialName()+"\n");
            mensajeCorreo.append(" y ahora desea cambiarlo a: "+clienteCambios.getCommercialName()+"\n");
            key = true;
        }
        if(!clienteCambios.getLegalName().equals(clienteCompleto.getLegalName())){
            if(clienteCambios.getLegalName() == null){
                mensajeCorreo.append("El cliente no contaba con un nombre legal");
            }else{
                mensajeCorreo.append("El anterior nombre legal era: "+clienteCompleto.getLegalName()+"\n");
            }
            mensajeCorreo.append(" y ahora desea cambiarlo a: "+clienteCambios.getLegalName()+"\n");
            key = true;
        }
        if(!clienteCambios.getEmail().equals(clienteCompleto.getEmail())){
            if(clienteCambios.getEmail() == null){
                mensajeCorreo.append("El cliente no contaba con un email");
            }else{
                mensajeCorreo.append("El anterior email era: "+clienteCompleto.getEmail()+"\n");
            }
            mensajeCorreo.append(" y ahora desea cambiarlo a: "+clienteCambios.getEmail()+"\n");
            key = true;
        }
        if(!clienteCambios.getRFC().equals(clienteCompleto.getRFC())) {
            mensajeCorreo.append("El anterior RFC era: "+clienteCompleto.getRFC()+"\n");
            mensajeCorreo.append(" y ahora desea cambiarlo a: "+clienteCambios.getRFC()+"\n");
            key = true;
        }
        if(!clienteCambios.getTelephones().equals(clienteCompleto.getTelephones())){
            if(clienteCompleto.getTelephones() == null){
                mensajeCorreo.append("El cliente no contaba con un teléfono");
            }else{
                mensajeCorreo.append("El anterior telefono era: "+clienteCompleto.getTelephones()+"\n");
            }
            mensajeCorreo.append(" y ahora desea cambiarlo a: "+clienteCambios.getTelephones()+"\n");
            key = true;
        }
        if(key){
            return mensajeCorreo.toString();
        }
        return "";
    }

    private void configurarExpandableListViews() {
        // Datos para el ExpandableListView de datos personales
        List<String> datosPersonalesTitles = new ArrayList<>();
        HashMap<String, List<String>> datosPersonalesData = new HashMap<>();

        datosPersonalesTitles.add("Datos personales");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Nombre Comercial: " + clienteCambios.getCommercialName());
        basicInfo.add("Nombre Legal: " + clienteCambios.getLegalName());
        basicInfo.add("Email: " + clienteCambios.getEmail());
        basicInfo.add("RFC: " + clienteCambios.getRFC());
        if (clienteCambios.getTelephones() != null) {
            basicInfo.add("Telefono: " + clienteCambios.getTelephones());
        } else {
            basicInfo.add("Telefono: Añadir telefono");
        }
        datosPersonalesData.put(datosPersonalesTitles.get(0), basicInfo);

        // Configurar el primer ExpandableListView
        AdaptadorPerfil adaptadorDatosPersonales = new AdaptadorPerfil(this, datosPersonalesTitles, datosPersonalesData, this);
        datosPersonales.setAdapter(adaptadorDatosPersonales);
    }

    @Override
    public void onChildClick(int groupPosition, int childPosition) {
        // Define las acciones para los clics en los hijos
        if (groupPosition == 0) { // Datos personales
            Intent intent = new Intent(ConsultarPerfil.this, EditarDatosP.class);
            switch (childPosition) {
                case 0:
                    // Acción para "Nombre Comercial"
                    intent.putExtra("campo", "Nombre Comercial");
                    intent.putExtra("dato", clienteCambios.getCommercialName());
                    startActivity(intent);
                    break;
                case 1:
                    // Acción para "Nombre Legal"
                    intent.putExtra("campo", "Nombre Legal");
                    intent.putExtra("dato", clienteCambios.getLegalName());
                    startActivity(intent);
                    break;
                case 2:
                    // Acción para "Email"
                    intent.putExtra("campo", "Email");
                    intent.putExtra("dato", clienteCambios.getEmail());
                    startActivity(intent);
                    break;
                case 3:
                    // Acción para "RFC"
                    intent.putExtra("campo", "RFC");
                    intent.putExtra("dato", clienteCambios.getRFC());
                    startActivity(intent);
                    break;
                case 4:
                    // Acción para "Telefono"
                    String telefono = clienteCambios.getTelephones() != null ? clienteCambios.getTelephones() : "";
                    intent.putExtra("campo", "Telefono");
                    intent.putExtra("dato", telefono);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

}
