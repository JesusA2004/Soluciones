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
import android.widget.ProgressBar;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorCarrito;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class CarritoCompras extends AppCompatActivity {

    Button btnCotizacion;
    ImageView btnVerProductos, btnMenu, btnCerrarSesion;
    TextView textoCargando;
    private ProgressBar iconoCarga;
    HashMap<String, Integer> carrito = new HashMap<>();
    private ControladorImagenes controladorImagenes;
    ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia();
    Sesion sesion = new Sesion();
    private RecyclerView recyclerViewCarrito;
    private AdaptadorCarrito adaptadorCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carritocompra);
        inicializarElementos();
        obtenerElementos();
        configurarBotones();
        configurarRecyclerView();
    }

    private void inicializarElementos(){
        btnCotizacion = findViewById(R.id.btnCotizar);
        btnVerProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        iconoCarga = findViewById(R.id.iconoCargando);
        textoCargando = findViewById(R.id.textoCargando);
        recyclerViewCarrito = findViewById(R.id.listaCarrito);
    }

    private void obtenerElementos(){
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("carrito")){
            carrito = (HashMap<String, Integer>) intent.getSerializableExtra("carrito");
        }
    }

    private void configurarBotones() {
        btnVerProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
        btnCotizacion.setOnClickListener(this::cotizacion);
    }

    private void configurarRecyclerView() {
        adaptadorCarrito = new AdaptadorCarrito(carrito, this);
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrito.setAdapter(adaptadorCarrito);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(CarritoCompras.this, MostrarProductos.class);
        intent.putExtra("carrito", carrito);
        startActivity(intent);
    }

    private void cotizacion(View v) {
        iconoCarga.setVisibility(View.VISIBLE);
        textoCargando.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                if (mandarCorreo()) {
                    Thread.sleep(1500);
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE);
                        textoCargando.setVisibility(View.GONE);
                        carrito.clear();
                        notificarUsuario();
                    });
                } else {
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE);
                        textoCargando.setVisibility(View.GONE);
                        Toast.makeText(this, "No se pudo enviar el correo. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    iconoCarga.setVisibility(View.GONE);
                    textoCargando.setVisibility(View.GONE);
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
        carrito = sesion.limpiarSesion();
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

}
