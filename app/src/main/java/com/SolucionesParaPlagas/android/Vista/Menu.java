package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import android.view.View;
import com.example.sol.R;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class Menu extends AppCompatActivity {

    TextView bienvenida;
    ImageView btnConsultarEC;
    ImageView btnConsultarProductos;

    // Botones menu lateral
    ImageView btnConsultarPerfil;
    ImageView btnPedidos;
    ImageView btnCerrarSesion;

    private DetalleCliente clienteCompleto = new DetalleCliente();
    private ClienteIndividual clienteIndividual = new ClienteIndividual();
    private Controlador<JsonCliente> controladorJsonCliente;
    private ControladorDetalleCliente controladorDetalleCliente = new ControladorDetalleCliente();

    // Hacer solicutud del cliente detallado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuopciones);
        inicializarElementos();
        // Obtenemos el cliente del login pero sin detalles
        recibirCliente();
        // Cargamos los detalles del cliente
        cargarClienteD();
        bienvenida.setText("¡Hola! " + clienteIndividual.getClientName());
        configurarBotones();
    }

    private void inicializarElementos() {
        /*
        bienvenida = findViewById(R.id.titulo);
        btnConsultarEC = findViewById(R.id.btnConsultarEC);
        btnConsultarPerfil = findViewById(R.id.btnConsultarPerfil);
        btnConsultarProductos = findViewById(R.id.btnConsultarProductos);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        */
    }

    private void configurarBotones() {
        btnConsultarEC.setOnClickListener(this::irAConsultarEstadoCuenta);
        btnConsultarPerfil.setOnClickListener(this::irAConsultarPerfil);
        btnConsultarProductos.setOnClickListener(this::irAConsultarProductos);
        btnCerrarSesion.setOnClickListener(this::cerrarSesion);
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

    private void irAConsultarEstadoCuenta(View v){
        Intent intent = new Intent(Menu.this, EstadoCuenta.class);
        intent.putExtra("ClienteCompleto",clienteCompleto);
        startActivity(intent);
    }

    private void irAConsultarPerfil(View v){
        Intent intent = new Intent(Menu.this, ConsultarPerfil.class);
        intent.putExtra("ClienteCompleto",clienteCompleto);
        startActivity(intent);
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
        clienteCompleto = new DetalleCliente();
        clienteIndividual = new ClienteIndividual();
        // Limpiar la lista del carrito
        controladorDetalleCliente.limpiarRepositorio();
        controladorJsonCliente.limipiarRepositorio();
    }

}