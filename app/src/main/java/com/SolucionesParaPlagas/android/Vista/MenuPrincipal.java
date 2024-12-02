package com.SolucionesParaPlagas.android.Vista;

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
import android.text.SpannableString;
import android.text.style.StyleSpan;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class MenuPrincipal extends AppCompatActivity {

    // Menu lateral
    private Menu menu;
    private DrawerLayout drawerLayout;
    private Cliente cliente = new Cliente();
    private TextView txtBienvenida,txtPerfil;
    private ControladorValidaciones validaciones = new ControladorValidaciones();
    private ImageView btnProductos, btnSitioWeb, btnMiPerfil, btnCerrarMenuL;
    private ControladorCliente controladorCliente = ControladorCliente.obtenerInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuopciones);
        inicializarElementos();
        configurarBotones();
        cargarDatos();
    }

    // Metodo para evitar excepciones de mostrar el cliente cuando se recibe como parametro de otra actividad
    private void cargarDatos(){
        cliente = controladorCliente.obtenerObjeto();
        mostrarDatos();
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconolistaproductos);
        btnSitioWeb = findViewById(R.id.logotipsinfondo);
        btnMiPerfil = findViewById(R.id.iconomiperfil);
        txtBienvenida = findViewById(R.id.bienvenidaNombre);
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
            }
            menu = navigationView.getMenu();
            if (menu != null) {
                // Configura los ítems del menú lateral
                MenuItem btnConsultarPerfil = menu.findItem(R.id.iconoDatosP);
                MenuItem btnDirecciones = menu.findItem(R.id.direcciones);
                MenuItem btnCerrarSesion = menu.findItem(R.id.nav_logout);
                if (btnConsultarPerfil != null) {
                    btnConsultarPerfil.setOnMenuItemClickListener(this::irAMiPerfil);
                }
                if (btnCerrarSesion != null) {
                    btnCerrarSesion.setOnMenuItemClickListener(this::cerrarSesion);
                }
                if (btnDirecciones != null) {
                    btnDirecciones.setOnMenuItemClickListener(this::irADirecciones);
                }
            }
        }
    }

    private boolean irADirecciones(MenuItem item){
        Intent intent = new Intent(MenuPrincipal.this, EditarDirecciones.class);
        startActivity(intent);
        return true;
    }

    private void cerrarMenuL(){
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::irAConsultarProductos);
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
        validaciones.confirmarCerrarSesion(this);
        return true;
    }

    private void irAConsultarProductos(View v) {
        Intent intent = new Intent(MenuPrincipal.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void mostrarDatos() {
        txtPerfil.setText(validaciones.capitalizarLetras(cliente.getNombreC()));
        String nombreCliente = cliente.getNombreC();
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
