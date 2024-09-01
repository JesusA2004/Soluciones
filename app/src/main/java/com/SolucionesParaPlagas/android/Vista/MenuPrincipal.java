package com.SolucionesParaPlagas.android.Vista;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Locale;

import com.SolucionesParaPlagas.android.Controlador.ControladorClienteIndividual;
import com.example.sol.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorJsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.JsonCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.ClienteIndividual;
import com.google.android.material.navigation.NavigationView;

public class MenuPrincipal extends AppCompatActivity {

    // Botones menu principal
    TextView txtBienvenida;
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
                if (clienteCompleto != null) {
                    Log.d("PruebaExito", "Cliente completo: " + clienteCompleto.toString());
                }
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
        String nombreCliente = clienteIndividual.getClientName();
        txtBienvenida.setText(capitalizarPrimeraLetra(nombreCliente));
    }

    private String capitalizarPrimeraLetra(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return texto.substring(0, 1).toUpperCase(Locale.ROOT) + texto.substring(1).toLowerCase(Locale.ROOT);
    }

}
