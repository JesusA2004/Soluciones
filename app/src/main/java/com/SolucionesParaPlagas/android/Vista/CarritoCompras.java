package com.SolucionesParaPlagas.android.Vista;

import com.SolucionesParaPlagas.android.Controlador.ControladorVentaProducto;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCarrito;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorCarrito;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class CarritoCompras extends AppCompatActivity implements AdaptadorCarrito.OnProductoCarritoClickListener{

    private View modificarCantidad;
    private RecyclerView recyclerViewCarrito;
    private Button btnCotizacion, btnCatalogo;
    private AdaptadorCarrito adaptadorCarrito;
    private ControladorImagenes controladorImagenes;
    private TextView txtCantidad, txtMsjB, txtCarritoV;
    private Producto productoSeleccionado = new Producto();
    private ImageView btnVerProductos, btnMenu, btnCerrarSesion, btnMenos, btnMas, btnBajarCantidad;
    private Controlador<Compra> controladorCarrito = ControladorCarrito.obtenerInstancia(this);
    private ControladorCarrito contC = new ControladorCarrito(this);
    private Compra compra = contC.obtenerCarritoEnCompra();
    private ControladorVentaProducto controladorVentaProducto = new ControladorVentaProducto(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carritocompra);
        inicializarElementos();
        configurarBotones();
        configurarRecyclerView();
    }

    private void inicializarElementos(){
        btnCotizacion = findViewById(R.id.btnCotizar);
        btnVerProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        recyclerViewCarrito = findViewById(R.id.listaCarrito);
        modificarCantidad = findViewById(R.id.vistaModificarCantidad);
        btnMenos = findViewById(R.id.iconomenos_1);
        btnMas = findViewById(R.id.iconomas_1);
        txtMsjB = findViewById(R.id.txtMsjB);
        txtCantidad = findViewById(R.id.editxtCantidad);
        btnBajarCantidad = findViewById(R.id.flechaAbajo);
        btnCatalogo = findViewById(R.id.btnPedido);
        txtCarritoV = findViewById(R.id.txtCarritoVacio);
        despertarElementos();
        // Objetos que despertaremos cuando se toque un objeto del carrito
        ocultarVistaCantidad();
    }

    private void despertarElementos(){
        if(compra != null){
            btnCotizacion.setVisibility(View.GONE);
        }else{
            btnCatalogo.setVisibility(View.GONE);
            txtCarritoV.setVisibility(View.GONE);
        }
    }

    private void actualizarBotones(){
        if(compra != null){
            btnCotizacion.setVisibility(View.GONE);
            btnCatalogo.setVisibility(View.VISIBLE);
            txtCarritoV.setVisibility(View.VISIBLE);
        }else{
            btnCatalogo.setVisibility(View.GONE);
            txtCarritoV.setVisibility(View.GONE);
            btnCotizacion.setVisibility(View.VISIBLE);
        }
    }

    private void configurarBotones() {
        btnVerProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
        btnCotizacion.setOnClickListener(this::cotizacion);
        btnCatalogo.setOnClickListener(this::regresarAProductos);
    }

    private void configurarRecyclerView() {
        adaptadorCarrito = new AdaptadorCarrito(controladorCarrito.obtenerObjetoBD(compra.getIdNotaVenta()), this,this);
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(CarritoCompras.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void cotizacion(View v) {
        ControladorCarrito contC = new ControladorCarrito(this);
        contC.finalizarCompra(compra.getIdNotaVenta());
        compra = new Compra();
        controladorCarrito.limpiarRepositorio();
    }

    private void irAMenu(View v){
        Intent intent = new Intent(CarritoCompras.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    @Override
    public void onProductoClick(Producto producto){
        productoSeleccionado = producto;
        modificarCantidad.setVisibility(View.VISIBLE);
        btnMenos.setVisibility(View.VISIBLE);
        btnMas.setVisibility(View.VISIBLE);
        txtMsjB.setVisibility(View.VISIBLE);
        txtCantidad.setVisibility(View.VISIBLE);
        btnBajarCantidad.setVisibility(View.VISIBLE);
        txtCantidad.setText(String.valueOf(controladorVentaProducto.obtenerCantidadProducto(compra.getIdNotaVenta(),producto.getFolio())));
        // Listener para modificar cantidad
        btnMas.setOnClickListener(this::incrementarCantidad);
        btnMenos.setOnClickListener(this::decrementarCantidad);
        btnBajarCantidad.setOnClickListener(this::ocultarModificarCantidad);
    }

    @Override
    public void onProductoEliminarClick(int idProducto) {
        controladorVentaProducto.eliminarProducto(compra.getIdNotaVenta(),idProducto);
        // Actualizamos el recyclerview con el producto ya eliminado
        adaptadorCarrito = new AdaptadorCarrito(controladorCarrito.obtenerObjetoBD(compra.getIdNotaVenta()), this,this);
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
        modificarCantidad.setVisibility(View.GONE);
        btnMenos.setVisibility(View.GONE);
        btnMas.setVisibility(View.GONE);
        txtMsjB.setVisibility(View.GONE);
        txtCantidad.setVisibility(View.GONE);
        actualizarBotones();
    }

    private void ocultarModificarCantidad(View v){
        ocultarVistaCantidad();
    }

    private void ocultarVistaCantidad(){
        modificarCantidad.setVisibility(View.GONE);
        btnMenos.setVisibility(View.GONE);
        btnMas.setVisibility(View.GONE);
        txtMsjB.setVisibility(View.GONE);
        txtCantidad.setVisibility(View.GONE);
        btnBajarCantidad.setVisibility(View.GONE);
    }

    private void incrementarCantidad(View v){
        int cantidad = Integer.parseInt(txtCantidad.getText().toString());
        cantidad += 1;
        if(cantidad < 10000){
            txtCantidad.setText(String.valueOf(cantidad));
            modificarCantidad(productoSeleccionado.getFolio(), cantidad);
        }else{
            Toast.makeText(getApplicationContext(), "No puedes agregar más productos al carrito", Toast.LENGTH_LONG).show();
        }
    }

    private void decrementarCantidad(View v){
        int cantidad = Integer.parseInt(txtCantidad.getText().toString());
        cantidad -= 1;
        if(cantidad >= 1){
            txtCantidad.setText(String.valueOf(cantidad));
            modificarCantidad(productoSeleccionado.getFolio(), cantidad);
        }
    }

    private void modificarCantidad(int idProducto, int nuevaCantidad){
        controladorVentaProducto.actualizarCantidad(compra.getIdNotaVenta(),nuevaCantidad, idProducto);
        adaptadorCarrito = new AdaptadorCarrito(controladorCarrito.obtenerObjetoBD(compra.getIdNotaVenta()), this,this);
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
    }

}
