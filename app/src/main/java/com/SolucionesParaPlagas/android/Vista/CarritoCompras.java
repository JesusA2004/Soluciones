package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import java.util.HashMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.ControladorCarrito;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorCarrito;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class CarritoCompras extends AppCompatActivity implements AdaptadorCarrito.OnProductoCarritoClickListener{

    private Button btnCotizacion, btnCatalogo;
    private Producto productoSeleccionado = new Producto();
    private TextView txtCantidad, txtMsjB, txtCarritoV;
    private ImageView btnVerProductos, btnMenu, btnCerrarSesion, btnMenos, btnMas, btnBajarCantidad;
    private View modificarCantidad;
    private ControladorImagenes controladorImagenes;
    private Sesion sesion = new Sesion();
    private RecyclerView recyclerViewCarrito;
    private AdaptadorCarrito adaptadorCarrito;
    private ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia();
    private ControladorCarrito controladorCarrito = ControladorCarrito.obtenerInstancia();

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
        if(controladorCarrito.obtenerCarrito().isEmpty()){
            btnCotizacion.setVisibility(View.GONE);
        }else{
            btnCatalogo.setVisibility(View.GONE);
            txtCarritoV.setVisibility(View.GONE);
        }
    }

    private void actualizarBotones(){
        if(controladorCarrito.obtenerCarrito().isEmpty()){
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
        adaptadorCarrito = new AdaptadorCarrito(controladorCarrito.obtenerCarrito(), this,this);
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(CarritoCompras.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void cotizacion(View v) {
        new Thread(() -> {
            try {
                if (mandarCorreo()) {
                    Thread.sleep(1500);
                    runOnUiThread(() -> {
                        controladorCarrito.vaciarCarrito();
                        notificarUsuario();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "No se pudo enviar el correo. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Ocurrió un error durante la espera. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private boolean mandarCorreo() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amjo220898@upemor.edu.mx"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cotización de Productos");
        emailIntent.putExtra(Intent.EXTRA_TEXT, construirMensaje());
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error al enviar el correo. Por favor, inténtalo nuevamente.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private String construirMensaje(){
        ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
        ClienteIndividual clienteIndividual = controladorClienteIndividual.obtenerCliente();
        StringBuilder mensajeCorreo = new StringBuilder();
        mensajeCorreo.append("Cotizacion para: "+clienteIndividual.getClientName()+",\n\n");
        mensajeCorreo.append("A continuación se muestran los productos seleccionados:\n\n");
        HashMap<String, Integer> carrito = controladorCarrito.obtenerCarrito();
        for (String idProducto : carrito.keySet()) {
            Producto producto = controladorProducto.obtenerProducto(idProducto);
            if (producto != null) {
                mensajeCorreo.append("Producto: ").append(producto.getTitle()).append("\n")
                        .append("Cantidad: ").append(carrito.get(idProducto)).append("\n")
                        .append("Peso: ").append(producto.getWeight()).append("\n")
                        .append("Unidad: ").append(producto.getUnit()).append("\n")
                        .append("-------------------------\n");
            }
        }
        if(clienteIndividual.getPhone() != null && clienteIndividual.getEmail() != null){
            mensajeCorreo.append("\nConfirmar cotizacion al correo: "+clienteIndividual.getEmail()+
                    "\n\no al telefono: "+clienteIndividual.getPhone()+"\n\nSoluciones Para Plagas");
        }else if(clienteIndividual.getEmail() != null && clienteIndividual.getPhone() == null){
            mensajeCorreo.append("\nConfirmar cotizacion al correo: "
                    +clienteIndividual.getEmail()+"\n\nSoluciones Para Plagas");
        }else if(clienteIndividual.getEmail() == null && clienteIndividual.getPhone() != null){
            mensajeCorreo.append("\nConfirmar cotizacion al telefono: "
                    +clienteIndividual.getPhone()+"\n\nSoluciones Para Plagas");
        }else{
            mensajeCorreo.append("\nSe le confirmara la cotizacion al remitente de este correo: "
                    +"\n\nSoluciones Para Plagas");
        }
        return mensajeCorreo.toString();
    }

    private void irAMenu(View v){
        Intent intent = new Intent(CarritoCompras.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        limpiarSesion();
        Intent intent = new Intent(CarritoCompras.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void limpiarSesion(){
        sesion.limpiarSesion();
    }

    private void notificarUsuario(){
        new AlertDialog.Builder(this)
            .setTitle("Aviso")
            .setMessage("¡Gracias por tu preferencia! Pronto, uno de nuestros asistentes te contactará con la cotización de tus productos solicitados.")
            .setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                Intent intent = new Intent(CarritoCompras.this, MenuPrincipal.class);
                startActivity(intent);
            }
            })
            .show();
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
        txtCantidad.setText(String.valueOf(controladorCarrito.obtenerCantidadProducto(producto.getID())));
        // Listener para modificar cantidad
        btnMas.setOnClickListener(this::incrementarCantidad);
        btnMenos.setOnClickListener(this::decrementarCantidad);
        btnBajarCantidad.setOnClickListener(this::ocultarModificarCantidad);
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
            modificarCantidad(productoSeleccionado.getID(), cantidad);
        }else{
            Toast.makeText(getApplicationContext(), "No puedes agregar más productos al carrito", Toast.LENGTH_LONG).show();
        }
    }

    private void decrementarCantidad(View v){
        int cantidad = Integer.parseInt(txtCantidad.getText().toString());
        cantidad -= 1;
        if(cantidad >= 1){
            txtCantidad.setText(String.valueOf(cantidad));
            modificarCantidad(productoSeleccionado.getID(), cantidad);
        }
    }

    private void modificarCantidad(String idProducto, int nuevaCantidad){
        controladorCarrito.actualizarCantidad(idProducto, nuevaCantidad);
        adaptadorCarrito = new AdaptadorCarrito(controladorCarrito.obtenerCarrito(), this,this);
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
    }

    @Override
    public void onProductoEliminarClick(String idProducto) {
        controladorCarrito.eliminarProducto(idProducto);
        // Actualizamos el recyclerview con el producto ya eliminado
        adaptadorCarrito = new AdaptadorCarrito(controladorCarrito.obtenerCarrito(), this,this);
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
        modificarCantidad.setVisibility(View.GONE);
        btnMenos.setVisibility(View.GONE);
        btnMas.setVisibility(View.GONE);
        txtMsjB.setVisibility(View.GONE);
        txtCantidad.setVisibility(View.GONE);
        actualizarBotones();
    }

}
