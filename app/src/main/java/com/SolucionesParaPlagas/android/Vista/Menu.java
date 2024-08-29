package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

import java.util.Locale;

public class Menu extends AppCompatActivity {

    // Botones menu principal
    TextView txtBienvenida;
    ImageView btnProductos,btnEstadoCuenta, btnSitioWeb, btnMiPerfil;

    // Botones menu lateral
    ImageView btnConsultarPerfil, btnPedidos, btnCerrarSesion;

    private DetalleCliente clienteCompleto = new DetalleCliente();
    private ClienteIndividual clienteIndividual = new ClienteIndividual();
    private Controlador<JsonCliente> controladorJsonCliente;
    private ControladorDetalleCliente controladorDetalleCliente = new ControladorDetalleCliente();
    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuopciones);
        inicializarElementos();
        // Obtenemos el cliente del login pero sin detalles
        recibirCliente();
        mostrarDatos();
        // Cargamos los detalles del cliente
        cargarClienteD();
        configurarBotones();
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconolistaproductos);
        btnEstadoCuenta = findViewById(R.id.iconoestadocuenta);
        btnSitioWeb = findViewById(R.id.logotipsinfondo);
        btnMiPerfil = findViewById(R.id.iconomiperfil);
        txtBienvenida = findViewById(R.id.bienvenidaNombre);
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::irAConsultarProductos);
        btnEstadoCuenta.setOnClickListener(this::irAEc);
        btnSitioWeb.setOnClickListener(this::irASitio);
        btnMiPerfil.setOnClickListener(this::irAMenuLateral);
    }

    private void irASitio(View v) {
        // Crear un Intent para abrir la URL en el navegador
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://solucionesparaplagas.com"));
        startActivity(intent);
    }

    private void irAMenuLateral(View v){
        // Desplazar el menu lateral aqui
    }

    private void irAEc(View v){
        Intent intent = new Intent(Menu.this, EstadoCuenta.class);
        startActivity(intent);
    }

    private void recibirCliente(){
        Intent intent = getIntent();
        if(intent != null){
            clienteIndividual = intent.getParcelableExtra("Cliente");
        }
    }

    private void cargarClienteD(){
        new Thread(() -> {
            controladorJsonCliente = new ControladorJsonCliente(clienteIndividual.getID(), "sobrecarga");
            runOnUiThread(() -> {
                clienteCompleto = controladorDetalleCliente.obtenerCliente();
            });
        }).start();
    }

    private void irAConsultarProductos(View v){
        Intent intent = new Intent(Menu.this, MostrarProductos.class);
        intent.putExtra("ClienteCompleto",clienteCompleto);
        startActivity(intent);
    }

    private void cerrarSesion(View v){
        Intent intent = new Intent(Menu.this, PaginaInicio.class);
        limpiarSesion();
        startActivity(intent);
    }

    private void limpiarSesion(){
        sesion.limpiarSesion();
    }

    private void mostrarDatos() {
        String nombreCliente = clienteIndividual.getClientName();
        txtBienvenida.setText(capitalizarPrimeraLetra(nombreCliente));
    }

    private String capitalizarPrimeraLetra(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return texto.substring(0, 1).toUpperCase(Locale.ROOT) + texto.substring(1).toLowerCase(Locale.ROOT);
    }


}