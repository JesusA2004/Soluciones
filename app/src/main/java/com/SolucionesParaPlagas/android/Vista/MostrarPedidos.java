package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorPedido;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPedidos;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.JsonPedido;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonPedido;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;

public class MostrarPedidos extends AppCompatActivity {

    private ImageView btnMenu, btnProductos, btnCerrarSesion;
    private TextView txtMsjCargando, txtPedidosV;
    private Button btnCatalogo;
    private ProgressBar iconoCarga;
    private RecyclerView pedidos;
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
        txtMsjCargando = findViewById(R.id.txtMensajeEspera);
        btnCatalogo = findViewById(R.id.btnPedido);
        txtPedidosV = findViewById(R.id.txtPedidosV);
        btnCatalogo.setVisibility(View.GONE);
        txtPedidosV.setVisibility(View.GONE);
        pedidos = findViewById(R.id.listaPedidos);
        pedidos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void despertarElementos(){
        if(controladorPedido.obtenerRepositorio().isEmpty()){
            btnCatalogo.setVisibility(View.VISIBLE);
            txtPedidosV.setVisibility(View.VISIBLE);
        }
    }

    private void inicializarCliente(){
        // Obtenemos el cliente ya que es el unico que es el unico en el repositorio
        ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
        clienteCompleto = controladorDetalleCliente.obtenerCliente();
    }

    private void cargarPedidos(String idCliente){
        controladorJsonPedido = new ControladorJsonPedido(idCliente);
        iconoCarga.setVisibility(View.VISIBLE); // Mostrar ProgressBar
        txtMsjCargando.setVisibility(View.VISIBLE);
        new Thread(() -> {
            controladorJsonPedido.realizarSolicitud();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                txtMsjCargando.setVisibility(View.GONE);
                cargarLista();
                despertarElementos();
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
        btnCatalogo.setOnClickListener(this::regresarAProductos);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(MostrarPedidos.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(MostrarPedidos.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        Sesion sesion = new Sesion();
        sesion.confirmarCerrarSesion(this);
    }

}