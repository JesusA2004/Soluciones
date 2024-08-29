package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import java.util.HashMap;
import com.example.sol.R;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class CarritoCompras extends AppCompatActivity {

    Button btnCotizacion;
    ImageView btnVerProductos, btnMenu, btnCerrarSesion;
    private ProgressBar iconoCarga;
    //       IDP   CantidadP
    HashMap<String, Integer> carrito = new HashMap<>();
    private ControladorImagenes controladorImagenes;
    ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia();
    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carritocompra);
        inicializarElementos();
        configurarBotones();
    }

    private void inicializarElementos(){
        // btnCotizacion = findViewById(R.id.btnCotizar);
        // btnVerProductos = findViewById(R.id.btnVerProductos);
        // btnMenu = findViewById(R.id.btnMenu);
        // btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        // iconoCarga = findViewById(R.id.iconoCarga);
    }

    private void obtenerElementos(){
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("carrito")){
            carrito = (HashMap<String, Integer>) intent.getSerializableExtra("carrito");
        }
    }

    // Realizar esta solicutud para cada producto en un ciclo
    private void cargarImagenProducto(String imagenUrl, ImageView imagenProducto){
        controladorImagenes = new ControladorImagenes(this);
        controladorImagenes.cargarImagenDesdeUrl(imagenUrl, imagenProducto);
    }

    private void configurarBotones() {
        btnVerProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
        btnCotizacion.setOnClickListener(this::cotizacion);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(CarritoCompras.this, MostrarProductos.class);
        intent.putExtra("carrito", carrito); // Pasar el carrito de compras a la siguiente actividad
        startActivity(intent);
    }

    private void cotizacion(View v) {
        iconoCarga.setVisibility(View.VISIBLE); // Mostrar ProgressBar
        new Thread(() -> {
            try {
                // Mandar correo de la cotización
                if (mandarCorreo()) {
                    Thread.sleep(1500); // Ajusta el tiempo según sea necesario
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                        // Limpiamos el carrito de compras
                        carrito.clear();
                        notificarUsuario();
                    });
                } else {
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                        Toast.makeText(this, "No se pudo enviar el correo. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    iconoCarga.setVisibility(View.GONE); // Ocultar ProgressBar
                    Toast.makeText(this, "Ocurrió un error durante la espera. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private boolean mandarCorreo() {
        // Crear un intento para enviar el correo
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");  // Tipo MIME para enviar email
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
        // Construir el cuerpo del correo con la lista de productos en el carrito
        ControladorClienteIndividual controladorClienteIndividual = new ControladorClienteIndividual();
        ClienteIndividual clienteIndividual = controladorClienteIndividual.obtenerCliente();
        StringBuilder mensajeCorreo = new StringBuilder();
        mensajeCorreo.append("Cotizacion de: "+clienteIndividual.getClientName()+",\n\n");
        mensajeCorreo.append("A continuación se muestra la cotización de los productos seleccionados:\n\n");
        for (String idProducto : carrito.keySet()) {
            Producto producto = controladorProducto.obtenerProducto(idProducto);  // Obtener producto por ID
            if (producto != null) {
                mensajeCorreo.append("Producto: ").append(producto.getTitle()).append("\n")
                    .append("Cantidad: ").append(carrito.get(idProducto)).append("\n")
                    .append("Peso: ").append(producto.getWeight()).append("\n")
                    .append("Unidad: ").append(producto.getUnit()).append("\n")
                    .append("-------------------------\n");
            }
        }
        mensajeCorreo.append("\nConfirmar cotizacion al correo: "+clienteIndividual.getEmail()+
                "\n\no al telefono: "+clienteIndividual.getPhone()+"\nSoluciones Para Plagas");
        return mensajeCorreo.toString();
    }

    private void irAMenu(View v){
        Intent intent = new Intent(CarritoCompras.this,Menu.class);
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
            .setMessage("¡Gracias por tu preferencia! Pronto, uno de\nnuestros asistentes te contactará con la\ncotización de tus productos solicitados.")
            .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    Intent intent = new Intent(CarritoCompras.this,Menu.class);
                    startActivity(intent);
                }
            })
            .show();
    }

}