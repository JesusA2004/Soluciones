package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class EditarDirecciones extends AppCompatActivity {

    private Cliente cliente = new Cliente();
    private AdaptadorPerfil adaptadorFiscal;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private ExpandableListView direccionFiscal;
    Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direccioncliente);
        inicializarElementos();
        configurarBotones();
        inicializarCliente();
        configurarExpandableListViews();
    }

    private void inicializarElementos() {
        btnMenu = findViewById(R.id.iconoMenu);
        btnProductos = findViewById(R.id.iconoVerProductos);
        direccionFiscal = findViewById(R.id.direccionFiscal);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
    }

    private void inicializarCliente() {
        cliente = controladorCliente.obtenerObjeto();
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void regresarAProductos(View v) {
        Intent intent = new Intent(EditarDirecciones.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(EditarDirecciones.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    private void configurarExpandableListViews() {
        configurarFiscal();
    }

    private void configurarFiscal() {
        // Datos para el ExpandableListView de dirección Fiscal
        List<String> direccionFiscalTitles = new ArrayList<>();
        HashMap<String, List<String>> direccionFiscalData = new HashMap<>();

        direccionFiscalTitles.add("Dirección Fiscal");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Estado: " + cliente.getEstado());
        basicInfo.add("Ciudad: " + cliente.getMunicipio());
        basicInfo.add("Agregar datos de la dirección fiscal");
        direccionFiscalData.put(direccionFiscalTitles.get(0), basicInfo);

        // Configurar el primer ExpandableListView con el listener personalizado
        adaptadorFiscal = new AdaptadorPerfil(this, direccionFiscalTitles, direccionFiscalData, new AdaptadorPerfil.OnChildClickListener() {
            @Override
            public void onChildClick(int groupPosition, int childPosition) {
                if (childPosition == 2) {
                    agregarDireccionFiscal();
                }
            }
        });
        direccionFiscal.setAdapter(adaptadorFiscal);
    }

    // Acción para "Agregar dirección fiscal"
    private void agregarDireccionFiscal() {
        Intent intent = new Intent(EditarDirecciones.this, EditarDireccion.class);
        intent.putExtra("tipoDireccion", "fiscal");
        startActivity(intent);
    }

}
