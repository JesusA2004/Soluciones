package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;
import com.SolucionesParaPlagas.android.Controlador.ControladorCompras;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPedidos;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class MostrarPedidos extends AppCompatActivity {

    private Button btnCatalogo;
    private RecyclerView pedidos;
    private TextView txtPedidosV;
    private Cliente cliente = new Cliente();
    private ImageView btnMenu, btnProductos, btnCerrarSesion;
    private ControladorCompras controladorCompras = ControladorCompras.obtenerInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configurarBotones();
        inicializarCliente();
        inicializarElementos();
        super.onCreate(savedInstanceState);
        cargarPedidos(cliente.getNoCliente());
        setContentView(R.layout.mostrarpedidos);
    }

    private void inicializarElementos() {
        btnCatalogo.setVisibility(View.GONE);
        txtPedidosV.setVisibility(View.GONE);
        btnMenu = findViewById(R.id.iconoMenu);
        pedidos = findViewById(R.id.listaPedidos);
        btnCatalogo = findViewById(R.id.btnPedido);
        txtPedidosV = findViewById(R.id.txtPedidosV);
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        pedidos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void despertarElementos(){
        if(controladorCompras.obtenerRepositorio().isEmpty()){
            btnCatalogo.setVisibility(View.VISIBLE);
            txtPedidosV.setVisibility(View.VISIBLE);
        }
    }

    private void inicializarCliente(){
        // Obtenemos el cliente ya que es el unico que es el unico en el repositorio
        ControladorCliente controladorCliente = ControladorCliente.obtenerInstancia(this);
        cliente = controladorCliente.obtenerObjeto();
    }

    private void cargarPedidos(int idCliente){
        // Hacemos la solicitud a la base de datos
        controladorCompras.obtenerLista("noCliente",idCliente);
        cargarLista();
        despertarElementos();
    }

    private void cargarLista(){
        List<Compra> listaPedidos = controladorCompras.obtenerRepositorio();
        if (listaPedidos != null) {
            AdaptadorPedidos adaptador = new AdaptadorPedidos(this);
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
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

}