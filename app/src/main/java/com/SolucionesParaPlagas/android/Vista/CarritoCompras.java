package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import java.util.HashMap;
import com.example.sol.R;
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
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;

public class CarritoCompras extends AppCompatActivity {

    Button btnCotizacion;
    ImageView btnVerProductos, btnMenu, btnCerrarSesion, btnMas, btnMenos;
    TextView textoCargando;

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
        btnCotizacion = findViewById(R.id.btnCotizar);
        btnVerProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        iconoCarga = findViewById(R.id.iconoCargando);
        textoCargando = findViewById(R.id.textoCargando);
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
        iconoCarga.setVisibility(View.VISIBLE);  // Mostrar ProgressBar
        textoCargando.setVisibility(View.VISIBLE); // Mostrar Texto
        new Thread(() -> {
            try {
                if (mandarCorreo()) {
                    Thread.sleep(1500); // Ajusta el tiempo según sea necesario
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE);  // Ocultar ProgressBar
                        textoCargando.setVisibility(View.GONE); // Ocultar Texto
                        carrito.clear(); // Limpiamos el carrito de compras
                        notificarUsuario();
                    });
                } else {
                    runOnUiThread(() -> {
                        iconoCarga.setVisibility(View.GONE);  // Ocultar ProgressBar
                        textoCargando.setVisibility(View.GONE); // Ocultar Texto
                        Toast.makeText(this, "No se pudo enviar el correo. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    iconoCarga.setVisibility(View.GONE);  // Ocultar ProgressBar
                    textoCargando.setVisibility(View.GONE); // Ocultar Texto
                    Toast.makeText(this, "Ocurrió un error durante la espera. Inténtalo de nuevo.", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    // Este metodo de enviar correo ya sirve
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
        ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
        ClienteIndividual clienteIndividual = controladorClienteIndividual.obtenerCliente();
        StringBuilder mensajeCorreo = new StringBuilder();
        mensajeCorreo.append("Cotizacion para: "+clienteIndividual.getClientName()+",\n\n");
        mensajeCorreo.append("A continuación se muestran los productos seleccionados:\n\n");
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
        if(clienteIndividual.getPhone() != null && clienteIndividual.getEmail() != null){
            // Caso para cuando no es nulo ni el correo ni el telefono
            mensajeCorreo.append("\nConfirmar cotizacion al correo: "+clienteIndividual.getEmail()+
                    "\n\no al telefono: "+clienteIndividual.getPhone()+"\n\nSoluciones Para Plagas");
        }else if(clienteIndividual.getEmail() != null && clienteIndividual.getPhone() == null){
            // Caso para cuando el correo existe pero el telefono no
            mensajeCorreo.append("\nConfirmar cotizacion al correo: "
                    +clienteIndividual.getEmail()+"\n\nSoluciones Para Plagas");
        }else if(clienteIndividual.getEmail() == null && clienteIndividual.getPhone() != null){
            // Caso para cuando existe el telefono pero el correo no
            mensajeCorreo.append("\nConfirmar cotizacion al telefono: "
                    +clienteIndividual.getPhone()+"\n\nSoluciones Para Plagas");
        }else{
            // Caso cuando el cliente no cuenta ni con telefono ni correo
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