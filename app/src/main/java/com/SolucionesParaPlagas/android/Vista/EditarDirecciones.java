package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

public class EditarDirecciones extends AppCompatActivity {

    private Button btnGuardarCambios;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private DetalleCliente clienteCompleto = new DetalleCliente();
    private ExpandableListView direccionFiscal, direccionEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direccioncliente);
        inicializarElementos();
        inicializarCliente();
        configurarExpandableListViews();
    }

    private void inicializarElementos(){
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnGuardarCambios = findViewById(R.id.btnSubirCambios);
        direccionFiscal = findViewById(R.id.direccionFiscal);
        direccionEnvio = findViewById(R.id.direccionEnvio);
    }

    private void inicializarCliente(){
        // Obtenemos el cliente ya que es el unico que es el unico en el repositorio
        ControladorDetalleCliente controladorDetalleCliente = ControladorDetalleCliente.obtenerInstancia();
        clienteCompleto = controladorDetalleCliente.obtenerCliente();
    }

    private void configurarBotones() {
        btnProductos.setOnClickListener(this::regresarAProductos);
        btnMenu.setOnClickListener(this::irAMenu);
        btnGuardarCambios.setOnClickListener(this::irAGuardarCambios);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
    }

    private void regresarAProductos(View v){
        Intent intent = new Intent(EditarDirecciones.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(EditarDirecciones.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        Sesion sesion = new Sesion();
        sesion.confirmarCerrarSesion(this);
    }

    private void irAGuardarCambios(View v){

    }

    private void configurarExpandableListViews() {
        // Datos para el ExpandableListView de datos personales
        List<String> datosPersonalesTitles = new ArrayList<>();
        HashMap<String, List<String>> datosPersonalesData = new HashMap<>();
        // Ejemplo de datos
        datosPersonalesTitles.add("Información Básica");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Nombre: " + clienteCompleto.getCommercialName());
        basicInfo.add("Email: " + clienteCompleto.getEmail());
        datosPersonalesData.put(datosPersonalesTitles.get(0), basicInfo);
        // Configurar el primer ExpandableListView
        AdaptadorPerfil adaptadorFiscal = new AdaptadorPerfil(this, datosPersonalesTitles, datosPersonalesData);
        direccionFiscal.setAdapter(adaptadorFiscal);
        AdaptadorPerfil adaptadorEnvio = new AdaptadorPerfil(this, datosPersonalesTitles, datosPersonalesData);
        direccionEnvio.setAdapter(adaptadorEnvio);

        // Datos para el ExpandableListView de dirección fiscal
        List<String> dirFiscalTitles = new ArrayList<>();
        HashMap<String, List<String>> dirFiscalData = new HashMap<>();
        dirFiscalTitles.add("Dirección Fiscal");
        List<String> fiscalInfo = new ArrayList<>();
        fiscalInfo.add("Calle: " + clienteCompleto.getAddresses());
        dirFiscalData.put(dirFiscalTitles.get(0), fiscalInfo);

    }

}