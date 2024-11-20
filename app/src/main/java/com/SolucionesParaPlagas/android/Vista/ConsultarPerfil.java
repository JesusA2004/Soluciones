package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import com.example.sol.R;
import java.util.ArrayList;
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
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class ConsultarPerfil extends AppCompatActivity implements AdaptadorPerfil.OnChildClickListener {

    private Button btnGuardarCambios;
    private ExpandableListView datosPersonales;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private Cliente cliente = new Cliente();
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
                        cliente.setNombreC(dato);
                        break;
                    case "Nombre Legal":
                        cliente.setRazonSocial(dato);
                        break;
                    case "Email":
                        cliente.setEmail(dato);
                        break;
                    case "RFC":
                        cliente.setClienteRFC(dato);
                        break;
                    case "Telefono":
                        cliente.setTelefonoC(dato);
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
        cliente = controladorCliente.obtenerObjeto();
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
        controladorCliente.actualizarObjeto(cliente);
    }

    private void configurarExpandableListViews() {
        // Datos para el ExpandableListView de datos personales
        List<String> datosPersonalesTitles = new ArrayList<>();
        HashMap<String, List<String>> datosPersonalesData = new HashMap<>();
        datosPersonalesTitles.add("Datos personales");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Nombre: " + cliente.getNombreC());
        basicInfo.add("Nombre Legal: " + cliente.getRazonSocial());
        basicInfo.add("Email: " + cliente.getEmail());
        basicInfo.add("RFC: " + cliente.getClienteRFC());
        if (cliente.getTelefonoC() != null) {
            basicInfo.add("Telefono: " + cliente.getTelefonoC());
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
                    intent.putExtra("dato", cliente.getNombreC());
                    startActivity(intent);
                    break;
                case 1:
                    // Acción para "Nombre Legal"
                    intent.putExtra("campo", "Nombre Legal");
                    intent.putExtra("dato", cliente.getRazonSocial());
                    startActivity(intent);
                    break;
                case 2:
                    // Acción para "Email"
                    intent.putExtra("campo", "Email");
                    intent.putExtra("dato", cliente.getEmail());
                    startActivity(intent);
                    break;
                case 3:
                    // Acción para "RFC"
                    intent.putExtra("campo", "RFC");
                    intent.putExtra("dato", cliente.getClienteRFC());
                    startActivity(intent);
                    break;
                case 4:
                    // Acción para "Telefono"
                    String telefono = cliente.getTelefonoC();
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
