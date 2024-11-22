package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import com.example.sol.R;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Modelo.Entidad.VentaProducto;
import com.SolucionesParaPlagas.android.Controlador.ControladorCarrito;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;
import com.SolucionesParaPlagas.android.Controlador.ControladorVentaProducto;

public class MostrarProducto extends AppCompatActivity {

    private int cantidadPro;
    private EditText cantidadProducto;
    private Button botonAnadirCarrito;
    private Producto producto = new Producto();
    private ControladorImagenes controladorImagenes;
    private TextView nombreProducto,descripcionProducto, pesoProducto;
    private ControladorValidaciones validaciones = new ControladorValidaciones();
    private Controlador<VentaProducto> controladorVentaProducto = new ControladorVentaProducto(this);
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
        /*String imageUrl = producto.getUrlImagen();*/
        controladorImagenes = new ControladorImagenes(this);
        /*controladorImagenes.cargarImagenDesdeUrl(imageUrl, imagenProducto);*/
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
                producto = (Producto) intent.getSerializableExtra("producto");
                Log.d("Producto", "Producto recibido: " + producto.getNombreProd());
            }
        }
    }

    private void inicializarElementos(){
        botonMenu = findViewById(R.id.iconoMenu);
        pesoProducto = findViewById(R.id.txtPeso);
        botonRegresar = findViewById(R.id.flechaatras);
        imagenProducto = findViewById(R.id.imagenProducto);
        cerrarSesion = findViewById(R.id.iconoCerrarSesion);
        botonProductos = findViewById(R.id.iconoVerProductos);
        nombreProducto = findViewById(R.id.txtNombreProducto);
        cantidadProducto = findViewById(R.id.entradaCantidad);
        descripcionProducto = findViewById(R.id.txtDescripcion);
        botonAnadirCarrito = findViewById(R.id.btnAnadirCarrito);
        botonCarritoCompras = findViewById(R.id.iconoCarritoCompra);
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
        try{
            cantidadPro = Integer.parseInt(cantidadStr);
            // Verificar si ya tenemos un carrito en pendiente o creamos uno nuevo
            Controlador<Compra> contC = ControladorCarrito.obtenerInstancia(this);
            Compra carrito = contC.obtenerCarro();

            VentaProducto venta = new VentaProducto();
            venta.setCantidad(cantidadPro);
            venta.setFolio(producto.getFolio());
            venta.setTotal(producto.getPrecio() * cantidadPro);
            venta.setIdNotaVenta(carrito.getIdNotaVenta());

            if(controladorVentaProducto.registrarObjeto(venta)){
                avisoUsuario("Producto añadido al carrito");
            }
        }catch(NumberFormatException e){
            avisoUsuario("Error, ingresa un numero");
        }
    }

    private void avisoUsuario(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}