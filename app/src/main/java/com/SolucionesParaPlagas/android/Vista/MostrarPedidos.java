package com.SolucionesParaPlagas.android.Vista;

import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.example.sol.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;

public class MostrarPedidos extends AppCompatActivity {

    ImageView btnMenu, btnProductos, btnCerrarSesion;
    Sesion sesion = new Sesion();
    private DetalleCliente clienteCompleto = new DetalleCliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrarpedidos);
        inicializarElementos();
        inicializarCliente();
        configurarBotones();
    }

    private void inicializarElementos() {
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnProductos = findViewById(R.id.iconoVerProductos);
    }

    private void inicializarCliente(){
        // Obtenemos el cliente ya que es el unico que es el unico en el repositorio
        ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
        clienteCompleto = controladorDetalleCliente.obtenerCliente();
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(MostrarPedidos.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(MostrarPedidos.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        limpiarSesion();
        Intent intent = new Intent(MostrarPedidos.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void limpiarSesion(){
        sesion.limpiarSesion();
    }

}