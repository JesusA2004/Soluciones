package com.SolucionesParaPlagas.android.Vista;

import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import com.example.sol.R;
import android.view.View;
import android.text.Spanned;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Typeface;
import android.widget.ProgressBar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.google.android.material.navigation.NavigationView;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.Validaciones;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;

public class MenuPrincipal extends AppCompatActivity {

    // Botones menu principal
    TextView txtBienvenida,txtPerfil;
    ImageView btnProductos, btnEstadoCuenta, btnSitioWeb, btnMiPerfil, btnCerrarMenuL;
    // Menu lateral
    Menu menu;
    private DetalleCliente clienteCompleto = new DetalleCliente();
    private ClienteIndividual clienteIndividual = new ClienteIndividual();
    private Controlador<JsonCliente> controladorJsonCliente;
    private ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
    private Sesion sesion = new Sesion();
    private ProgressBar iconoCarga;
    private DrawerLayout drawerLayout;
    Validaciones validaciones = new Validaciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuopciones);
        inicializarElementos();
        configurarBotones();
        segundoHilo();
    }

    // Metodo para evitar excepciones de mostrar el cliente cuando se recibe como parametro de otra actividad
    private void segundoHilo(){
        inicializarCliente();
        new Thread(() -> {
            // Realizar la solicitud http en un segundo hilo para mejorar la experiencia del usuario
            validarExistenciaClienteD();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                mostrarDatos();
            });
        }).start();
    }

    private void inicializarCliente(){
        // Obtenemos el cliente ya que es el unico que es el unico en el repositorio
        ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
        clienteIndividual = controladorClienteIndividual.obtenerCliente();
    }

    private void validarExistenciaClienteD(){
        // Validamos si el repositorio esta vacio entonces realizamos la solicitud
        if (controladorDetalleCliente.obtenerRepositorio().isEmpty()) {
            cargarClienteD();
        }else{
            // Si el repositorio tiene datos entonces cargarlos al cliente
            clienteCompleto = controladorDetalleCliente.obtenerCliente();
        }
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconolistaproductos);
        btnEstadoCuenta = findViewById(R.id.iconoestadocuenta);
        btnSitioWeb = findViewById(R.id.logotipsinfondo);
        btnMiPerfil = findViewById(R.id.iconomiperfil);
        txtBienvenida = findViewById(R.id.bienvenidaNombre);
        iconoCarga = findViewById(R.id.cargaIcono);
        iconoCarga.setVisibility(View.GONE); // Inicialmente oculto
        drawerLayout = findViewById(R.id.menuDeslizable);
        // Obtener la referencia del NavigationView
        NavigationView navigationView = findViewById(R.id.menuLateral);
        if (navigationView != null) {
            // Inflar la cabecera
            View headerView = navigationView.getHeaderView(0); // 0 es el índice de la cabecera
            // Obtener la referencia al ImageView en la cabecera
            btnCerrarMenuL = headerView.findViewById(R.id.iconoCerrar);
            txtPerfil = headerView.findViewById(R.id.txtNombreCliente);
            if (btnCerrarMenuL != null) {
                btnCerrarMenuL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cerrarMenuL();
                    }
                });
            } else {
                Log.e("MenuPrincipal", "IconoCerrar no encontrado en la cabecera del menú lateral.");
            }
            menu = navigationView.getMenu();
            if (menu != null) {
                // Configura los ítems del menú lateral
                MenuItem btnConsultarPerfil = menu.findItem(R.id.iconoDatosP);
                MenuItem btnPedidos = menu.findItem(R.id.nav_orders);
                MenuItem btnCerrarSesion = menu.findItem(R.id.nav_logout);
                if (btnConsultarPerfil != null) {
                    btnConsultarPerfil.setOnMenuItemClickListener(this::irAMiPerfil);
                }
                if (btnPedidos != null) {
                    btnPedidos.setOnMenuItemClickListener(this::irAPedidos);
                }
                if (btnCerrarSesion != null) {
                    btnCerrarSesion.setOnMenuItemClickListener(this::cerrarSesion);
                }
            }
        }
    }

    private void cerrarMenuL(){
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::irAConsultarProductos);
        btnEstadoCuenta.setOnClickListener(this::irAEc);
        btnSitioWeb.setOnClickListener(this::irASitio);
        btnMiPerfil.setOnClickListener(this::irAMenuLateral);
        btnCerrarMenuL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarMenuL();
            }
        });
    }

    private void irASitio(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://solucionesparaplagas.com"));
        startActivity(intent);
    }

    private void irAMenuLateral(View v) {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    private void irAEc(View v) {
        Intent intent = new Intent(MenuPrincipal.this, EstadoCuenta.class);
        startActivity(intent);
    }

    private boolean irAMiPerfil(MenuItem item) {
        Intent intent = new Intent(MenuPrincipal.this, ConsultarPerfil.class);
        startActivity(intent);
        return true;
    }

    private boolean irAPedidos(MenuItem item) {
        Intent intent = new Intent(MenuPrincipal.this, MostrarPedidos.class);
        startActivity(intent);
        return true;
    }

    private boolean cerrarSesion(MenuItem item) {
        Intent intent = new Intent(MenuPrincipal.this, PaginaInicio.class);
        if (sesion != null) {
            sesion.limpiarSesion();
        }
        startActivity(intent);
        return true;
    }

    private void cargarClienteD() {
        new Thread(() -> {
            controladorJsonCliente = new ControladorJsonCliente(clienteIndividual.getID(), "sobrecarga");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                clienteCompleto = controladorDetalleCliente.obtenerCliente();
            });
        }).start();
    }

    private void irAConsultarProductos(View v) {
        Intent intent = new Intent(MenuPrincipal.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void mostrarDatos() {
        ControladorClienteIndividual controladorClienteIndividual = ControladorClienteIndividual.obtenerInstancia();
        clienteIndividual = controladorClienteIndividual.obtenerCliente();
        txtPerfil.setText(validaciones.capitalizarLetras(clienteIndividual.getClientName()));
        String nombreCliente = clienteIndividual.getClientName();
        // Texto principal
        String textoBienvenida = "¡Hola " + validaciones.capitalizarLetras(nombreCliente) + "!\n\n";
        String textoExplorar = "¿Qué te gustaría explorar hoy?";
        // Crear SpannableString para manejar diferentes estilos
        SpannableString spannableString = new SpannableString(textoBienvenida + textoExplorar);
        // Aplicar negritas al texto de bienvenida
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, textoBienvenida.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), textoBienvenida.length(), textoBienvenida.length() + textoExplorar.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Establecer el texto en el TextView
        txtBienvenida.setText(spannableString);
    }

}
