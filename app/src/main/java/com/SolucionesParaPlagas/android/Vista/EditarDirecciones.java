package com.SolucionesParaPlagas.android.Vista;

import java.util.List;

import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AppCompatActivity;

import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

public class EditarDirecciones extends AppCompatActivity {

    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private Cliente clienteCompleto = new Cliente();
    private ExpandableListView direccionFiscal, direccionEnvio;
    private AdaptadorPerfil adaptadorFiscal, adaptadorEnvio;

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
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        direccionFiscal = findViewById(R.id.direccionFiscal);
        direccionEnvio = findViewById(R.id.direccionEnvio);
    }

    private void inicializarCliente() {
        // Obtenemos el cliente ya que es el único en el repositorio
        ControladorCliente controladorDetalleCliente = ControladorCliente.obtenerInstancia(this);
        clienteCompleto = controladorDetalleCliente.obtenerObjeto();
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
        configurarEnvio();
    }

    private void configurarEnvio() {
        // Datos para el ExpandableListView de dirección de Envío
        List<String> direccionEnvioTitles = new ArrayList<>();
        HashMap<String, List<String>> direccionEnvioData = new HashMap<>();

        direccionEnvioTitles.add("Dirección de Envío");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Agregar dirección para el envío de su pedido");
        direccionEnvioData.put(direccionEnvioTitles.get(0), basicInfo);

        // Configurar el segundo ExpandableListView con el listener personalizado
        adaptadorEnvio = new AdaptadorPerfil(this, direccionEnvioTitles, direccionEnvioData, new AdaptadorPerfil.OnChildClickListener() {
            @Override
            public void onChildClick(int groupPosition, int childPosition) {
                if (childPosition == 0) {
                    agregarDireccionEnvio();
                }
            }
        });
        direccionEnvio.setAdapter(adaptadorEnvio);
    }

    private void configurarFiscal() {
        // Datos para el ExpandableListView de dirección Fiscal
        List<String> direccionFiscalTitles = new ArrayList<>();
        HashMap<String, List<String>> direccionFiscalData = new HashMap<>();

        direccionFiscalTitles.add("Dirección Fiscal");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Estado: " + clienteCompleto.getEstado());
        basicInfo.add("Ciudad: " + clienteCompleto.getMunicipio());
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
        Intent intent = new Intent(EditarDirecciones.this, AgregarDireccion.class);
        intent.putExtra("tipoDireccion", "fiscal");
        startActivity(intent);
    }

    // Acción para "Agregar dirección de envío"
    private void agregarDireccionEnvio() {
        Intent intent = new Intent(EditarDirecciones.this, AgregarDireccion.class);
        intent.putExtra("tipoDireccion", "envio");
        startActivity(intent);
    }

}
