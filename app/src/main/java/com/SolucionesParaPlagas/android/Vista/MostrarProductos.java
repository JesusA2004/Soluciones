package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import java.util.HashMap;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;

public class MostrarProductos extends AppCompatActivity {

    ImageView btnCerrarSesion,btnMenu, btnCarrito;
    RecyclerView productos;
    HashMap<String, Integer> carrito = new HashMap<>();
    Sesion sesion = new Sesion();
    private ProgressBar iconoCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarproducto);
        cargarProductos();
        inicializarElementos();
        configurarBotones();
    }

    private void cargarProductos() {
        Controlador<JsonProducto> controladorJsonProducto = new ControladorJsonProducto();
        iconoCarga.setVisibility(View.VISIBLE); // Mostrar ProgressBar
        new Thread(() -> {
            controladorJsonProducto.realizarSolicitud();
            int tiempoEspera = obtenerTiempoEspera();
            try {
                Thread.sleep(tiempoEspera); // Ajusta el tiempo según la red
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
            });
        }).start();
    }

    private int obtenerTiempoEspera() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        if (network != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return 6000; // 6 segundos si está en Wi-Fi
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return 8000; // 8 segundos si está en datos móviles
                }
            }
        }
        return 10000; // 10 segundos si no hay red o red desconocida
    }

    private void inicializarElementos() {
        btnCarrito = findViewById(R.id.iconocompra);
        btnMenu = findViewById(R.id.iconomenu);
        btnCerrarSesion = findViewById(R.id.iconocerrarsesion);
        productos = findViewById(R.id.listaProductos);
        // Configurar el LayoutManager del RecyclerView
        productos.setLayoutManager(new LinearLayoutManager(this));
        iconoCarga = findViewById(R.id.progressBar);
        iconoCarga.setVisibility(View.GONE); // Inicialmente oculto
    }

    private void obtenerElementos(){
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("carrito")){
            carrito = (HashMap<String, Integer>) intent.getSerializableExtra("carrito");
        }
    }

    private void configurarBotones() {
        btnCarrito.setOnClickListener(this::irACarrito);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(MostrarProductos.this,Menu.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        carrito = sesion.limpiarSesion();
        Intent intent = new Intent(MostrarProductos.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void irACarrito(View v){
        Intent intent = new Intent(MostrarProductos.this,CarritoCompras.class);
        intent.putExtra("carrito",carrito);
        startActivity(intent);
    }

}