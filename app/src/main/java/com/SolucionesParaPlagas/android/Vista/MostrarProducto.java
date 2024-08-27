package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import java.util.HashMap;
import com.example.sol.R;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;

public class MostrarProducto extends AppCompatActivity {

    ImageView imagenProducto;
    TextView nombreProducto;
    EditText cantidadProducto;
    TextView descripcionProducto;
    Button botonAnadirCarrito;
    TextView pesoProducto;
    ImageView botonProductos;
    ImageView botonCarritoCompras;
    ImageView botonMenu;
    ImageView cerrarSesion;
    ImageView botonRegresar;
    Producto producto = new Producto();
    private int cantidadPro;
    private String idProducto;
    private String nomProducto;
    HashMap<String, Producto> carrito = new HashMap<>();
    private ControladorImagenes controladorImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infounproducto);
        // Hacemos referencia con objetos a los elementos de la interfaz
        inicializarElementos();
        // Obtenemos el producto elegido
        obtenerElementos();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cargamos los datos del producto en la interfaz
        cargarDatosProducto();
        // Preparamos los botones para las acciones que se van a realizar
        configurarBotones();
    }

    private void cargarImagenProducto(){
        String imageUrl = producto.getImageUrl();
        controladorImagenes = new ControladorImagenes(this);
        controladorImagenes.cargarImagenDesdeUrl(imageUrl, imagenProducto);
    }

    private void cargarDatosProducto(){
        cargarImagenProducto();
        nombreProducto.setText(producto.getTitle());
        descripcionProducto.setText(producto.getDescription());
    }

    private void obtenerElementos(){
        // Obtener el Intent que inició esta Activity
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("producto")){
                producto = intent.getParcelableExtra("producto");
            }
            if(intent.hasExtra("carrito")){
                carrito = (HashMap<String, Producto>) intent.getSerializableExtra("carrito");
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
        botonProductos = findViewById(R.id.iconolistaproductos_1);
        botonCarritoCompras = findViewById(R.id.iconocompra_1);
        botonMenu = findViewById(R.id.iconomenu_1);
        cerrarSesion = findViewById(R.id.iconocerrarsesion__1);
        botonRegresar = findViewById(R.id.flecharegreso_1);
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
        /*
        Intent intent = new Intent(MostrarProducto.this, .class);
        startActivity(intent);
        */
    }

    private void irAMenu(View v){

    }

    private void irACerrarSesion(View v){

    }

    // Método para añadir el producto al carrito
    private void anadirAlCarrito(View v) {
        // Obtener la cantidad ingresada
        String cantidadStr = cantidadProducto.getText().toString();
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese una cantidad", Toast.LENGTH_SHORT).show();
            return;
        }
        cantidadPro = Integer.parseInt(cantidadStr);
        idProducto = producto.getID();
        nomProducto = producto.getTitle();
        // Añadir estos valores a un hashmap que sera enviado a el carrito
        Toast.makeText(this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show();
    }

}