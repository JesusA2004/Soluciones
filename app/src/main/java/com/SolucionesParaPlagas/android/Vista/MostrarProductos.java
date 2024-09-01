package com.SolucionesParaPlagas.android.Vista;

import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.JsonProducto;
import com.example.sol.R;
import java.util.HashMap;
import java.util.List;
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
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;

public class MostrarProductos extends AppCompatActivity {

    ImageView btnCerrarSesion, btnMenu, btnCarrito;
    RecyclerView productos;
    HashMap<String, Integer> carrito = new HashMap<>();
    Sesion sesion = new Sesion();
    private ProgressBar iconoCarga;
    private ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarproducto);
        inicializarElementos();
        cargarProductos();
        configurarBotones();
    }

    private void cargarProductos() {
        List<Producto> productosList = controladorProducto.obtenerRepositorio();
        if (productosList != null && !productosList.isEmpty()) {
            AdaptadorProductos adaptador = new AdaptadorProductos(productosList);
            productos.setAdapter(adaptador);
        }
    }

    private void inicializarElementos() {
        btnCarrito = findViewById(R.id.iconocompra);
        btnMenu = findViewById(R.id.iconomenu);
        btnCerrarSesion = findViewById(R.id.iconocerrarsesion);
        productos = findViewById(R.id.listaProductos);
        iconoCarga = findViewById(R.id.cargaIcono);
        iconoCarga.setVisibility(View.GONE); // Inicialmente oculto
        productos.setLayoutManager(new LinearLayoutManager(this)); // Configura el LayoutManager
    }

    private void obtenerElementos() {
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("carrito")) {
            carrito = (HashMap<String, Integer>) intent.getSerializableExtra("carrito");
        }
    }

    private void configurarBotones() {
        btnCarrito.setOnClickListener(this::irACarrito);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(MostrarProductos.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        carrito = sesion.limpiarSesion();
        Intent intent = new Intent(MostrarProductos.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void irACarrito(View v) {
        Intent intent = new Intent(MostrarProductos.this, CarritoCompras.class);
        intent.putExtra("carrito", carrito);
        startActivity(intent);
    }

}
