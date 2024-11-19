package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;

import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;
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
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;

public class ConsultarPerfil extends AppCompatActivity implements AdaptadorPerfil.OnChildClickListener {

    private Button btnGuardarCambios;
    private ExpandableListView datosPersonales;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private Cliente clienteCompleto = new Cliente();
    private Cliente clienteCambios;
    private ControladorCliente controladorCliente = ControladorCliente.obtenerInstancia(this);

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
        clienteCompleto = controladorCliente.obtenerObjeto();
        // Verificamos si ya existe un cliente en el repositorio con cambios
        if(clienteCambios == null){
            clienteCambios = new Cliente();
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
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    private void irAGuardarCambios(View v) {
        notificarUsuario();
    }

    private void notificarUsuario(){
        new AlertDialog.Builder(this)
                .setTitle("Nota")
                .setMessage("Te solicitamos confirmar tus datos personales antes del envio de los mismos.¡Gracias!.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ACTUALIZAR LA BASE DE DATOS
                    }
                })
                .show();
    }

    private void configurarExpandableListViews() {
        // Datos para el ExpandableListView de datos personales
        List<String> datosPersonalesTitles = new ArrayList<>();
        HashMap<String, List<String>> datosPersonalesData = new HashMap<>();

        datosPersonalesTitles.add("Datos personales");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Nombre: " + clienteCambios.getNombreC());
        basicInfo.add("Nombre Legal: " + clienteCambios.getRazonSocial());
        basicInfo.add("Email: " + clienteCambios.getEmail());
        basicInfo.add("RFC: " + clienteCambios.getClienteRFC());
        if (clienteCambios.getTelefonoC() != null) {
            basicInfo.add("Telefono: " + clienteCambios.getTelefonoC());
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
        // Si el repositorio ya tiene el cliente con cambios, lo limpiamos
        if(controladorCliente.obtenerRepositorio().size() == 2) {
            controladorCliente.limpiarCliente();
        }
        controladorCliente.enviarDatoRepositorio(clienteCambios);
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
