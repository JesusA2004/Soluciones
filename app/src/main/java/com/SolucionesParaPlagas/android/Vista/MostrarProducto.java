package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorVentaProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.VentaProducto;
import com.example.sol.R;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorCarrito;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class MostrarProducto extends AppCompatActivity {

    private int cantidadPro;
    private EditText cantidadProducto;
    private Button botonAnadirCarrito;
    private Producto producto = new Producto();
    private ControladorImagenes controladorImagenes;
    private TextView nombreProducto,descripcionProducto, pesoProducto;
    private ControladorValidaciones validaciones = new ControladorValidaciones();
    private ControladorVentaProducto controladorCarrito = ControladorVentaProducto.obtenerInstancia(this);
    private ImageView imagenProducto, botonProductos, botonCarritoCompras, botonMenu, cerrarSesion, botonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infounproducto);
        // Hacemos referencia con objetos a los elementos de la interfaz
        inicializarElementos();
        // Obtenemos el producto elegido
        obtenerElementos();
        // Cargamos los datos del producto en la interfaz
        cargarDatosProducto();
        // Preparamos los botones para las acciones que se van a realizar
        configurarBotones();
    }

    private void cargarImagenProducto(){
        String imageUrl = producto.getUrlImagen();
        controladorImagenes = new ControladorImagenes(this);
        controladorImagenes.cargarImagenDesdeUrl(imageUrl, imagenProducto);
    }

    private void cargarDatosProducto(){
        cargarImagenProducto();
        nombreProducto.setText(producto.getNombreProd());
        if(validaciones.validarStringNull(producto.getDescripcion())){
            descripcionProducto.setText("Sin información adicional del producto.");
        }else{
            descripcionProducto.setText("Descripcion: "+producto.getDescripcion());
        }
        if(validaciones.validarStringNull(""+producto.getPeso())){
            pesoProducto.setText("");
        }else{
            pesoProducto.setText(""+producto.getPeso()+" "+producto.getUnidadM());
        }
    }

    private void obtenerElementos(){
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("producto")){
                producto = intent.getParcelableExtra("producto");
            }
        }
    }

    private void inicializarElementos(){
        imagenProducto = findViewById(R.id.imagenProducto);
        nombreProducto = findViewById(R.id.txtNombreProducto);
        cantidadProducto = findViewById(R.id.entradaCantidad);
        descripcionProducto = findViewById(R.id.txtDescripcion);
        botonAnadirCarrito = findViewById(R.id.btnAnadirCarrito);
        pesoProducto = findViewById(R.id.txtPeso);
        botonProductos = findViewById(R.id.iconoVerProductos);
        botonCarritoCompras = findViewById(R.id.iconoCarritoCompra);
        botonMenu = findViewById(R.id.iconoMenu);
        cerrarSesion = findViewById(R.id.iconoCerrarSesion);
        botonRegresar = findViewById(R.id.flechaatras);
    }

    private void configurarBotones() {
        botonRegresar.setOnClickListener(this::regresarAProductos);
        botonProductos.setOnClickListener(this::regresarAProductos);
        botonCarritoCompras.setOnClickListener(this::irACarrito);
        botonMenu.setOnClickListener(this::irAMenu);
        cerrarSesion.setOnClickListener(this::irACerrarSesion);
        botonAnadirCarrito.setOnClickListener(this::anadirAlCarrito);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(MostrarProducto.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irACarrito(View v){
        Intent intent = new Intent(MostrarProducto.this, CarritoCompras.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(MostrarProducto.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    // Método para añadir el producto al carrito
    private void anadirAlCarrito(View v) {
        // Obtener la cantidad ingresada
        String cantidadStr = cantidadProducto.getText().toString();
        if (cantidadStr.isEmpty()) {
            avisoUsuario("Por favor, ingrese una cantidad");
            return;
        }
        cantidadPro = Integer.parseInt(cantidadStr);
        controladorCarrito.obtenerUltimaNotaVenta();
        if(controladorCarrito.existeEnCarrito(producto.getID())){
            // Si el producto ya existe en el carrito, actualizar la cantidad
            int cantidadActual = controladorCarrito.obtenerCantidadProducto(producto.getID());
            controladorCarrito.actualizarCantidad(producto.getID(), cantidadActual + cantidadPro);
        }else{
            // Si no existe en el carrito solo se agrega
            controladorCarrito.agregarProducto(producto.getID(),cantidadPro);
        }
        avisoUsuario("Producto añadido al carrito");
    }

    private void avisoUsuario(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}