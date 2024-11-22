package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import android.os.Bundle;
import android.view.View;
import com.example.sol.R;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorListas;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorProductos;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class MostrarProductos extends AppCompatActivity implements AdaptadorProductos.OnProductoClickListener {

    private SearchView searchView;
    private RecyclerView productos;
    private AdaptadorProductos adaptador;
    private List<Producto> listaProductosOriginal;
    private ImageView btnCerrarSesion, btnMenu, btnCarrito;
    private ControladorListas<Producto> controladorProducto = ControladorProducto.obtenerInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscarproducto);
        inicializarElementos();
        cargarProductos();
        configurarSearchView();
        configurarBotones();
    }

    private void cargarProductos() {
        listaProductosOriginal = controladorProducto.obtenerLista();
        adaptador = new AdaptadorProductos(listaProductosOriginal, this,this);
        productos.setAdapter(adaptador);
    }

    private void inicializarElementos() {
        btnCarrito = findViewById(R.id.iconocompra);
        btnMenu = findViewById(R.id.iconomenu);
        btnCerrarSesion = findViewById(R.id.iconocerrarsesion);
        productos = findViewById(R.id.listaProductos);
        productos.setLayoutManager(new LinearLayoutManager(this)); // Configura el LayoutManager
        searchView = findViewById(R.id.searchView3); // Referencia al SearchView
        searchView.setQueryHint("Buscar producto");
        searchView.setIconified(false);
    }

    private void configurarBotones() {
        btnCarrito.setOnClickListener(this::irACarrito);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void configurarSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Realiza la búsqueda cuando el usuario presione "buscar" en el teclado
                filtrarProductos(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra los productos a medida que el usuario escribe
                filtrarProductos(newText);
                return false;
            }
        });
    }

    private void filtrarProductos(String query) {
        // Utiliza el método productosParcial del controlador para realizar la búsqueda
        ControladorProducto contP = new ControladorProducto(this);
        List<Producto> listaFiltrada = contP.filtrarRepositorio(query);
        adaptador = new AdaptadorProductos(listaFiltrada, this,this);
        productos.setAdapter(adaptador);
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(MostrarProductos.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    private void irACarrito(View v) {
        Intent intent = new Intent(MostrarProductos.this, CarritoCompras.class);
        startActivity(intent);
    }

    private void irAProducto(View v, Producto producto) {
        Intent intent = new Intent(MostrarProductos.this, MostrarProducto.class);
        intent.putExtra("producto", producto);
        startActivity(intent);
    }

    @Override
    public void onProductoClick(Producto producto) {
        irAProducto(null, producto);
    }

}
