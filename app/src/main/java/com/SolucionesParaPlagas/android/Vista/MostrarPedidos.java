package com.SolucionesParaPlagas.android.Vista;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPedidos;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorProductos;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorPedido;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.JsonPedido;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonPedido;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

import java.util.List;

public class MostrarPedidos extends AppCompatActivity {

    ImageView btnMenu, btnProductos, btnCerrarSesion;
    Sesion sesion = new Sesion();
    private ProgressBar iconoCarga;
    RecyclerView pedidos;
    private DetalleCliente clienteCompleto = new DetalleCliente();
    private Controlador<JsonPedido> controladorJsonPedido;
    private ControladorPedido controladorPedido = ControladorPedido.obtenerInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrarpedidos);
        inicializarElementos();
        // Primero obtenemos los datos del repositorio de nuestro cliente para despues solicitar sus pedidos
        inicializarCliente();
        cargarPedidos(clienteCompleto.getID());
        configurarBotones();
    }

    private void inicializarElementos() {
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnProductos = findViewById(R.id.iconoVerProductos);
        iconoCarga = findViewById(R.id.iconoCarga);
        pedidos = findViewById(R.id.listaPedidos);
        pedidos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void inicializarCliente(){
        // Obtenemos el cliente ya que es el unico que es el unico en el repositorio
        ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
        clienteCompleto = controladorDetalleCliente.obtenerCliente();
    }

    private void cargarPedidos(String idCliente){
        controladorJsonPedido = new ControladorJsonPedido(idCliente);
        iconoCarga.setVisibility(View.VISIBLE); // Mostrar ProgressBar
        new Thread(() -> {
            controladorJsonPedido.realizarSolicitud();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                cargarLista();
            });
        }).start();
    }

    private void cargarLista(){
        List<PedidoIndividual> listaPedidos = controladorPedido.obtenerRepositorio();
        if (listaPedidos != null && !listaPedidos.isEmpty()) {
            AdaptadorPedidos adaptador = new AdaptadorPedidos(listaPedidos);
            pedidos.setAdapter(adaptador);
        }
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