package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import java.util.HashMap;
import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;

public class ConsultarPerfil extends AppCompatActivity {

    private Button btnGuardarCambios;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private DetalleCliente clienteCompleto = new DetalleCliente();
    private ExpandableListView datosPersonales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datoscliente);
        inicializarElementos();
        configurarBotones();
        inicializarCliente();
        configurarExpandableListViews();
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnGuardarCambios = findViewById(R.id.btnSubirCambios);
        datosPersonales = findViewById(R.id.datosPersonales);
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
        Intent intent = new Intent(ConsultarPerfil.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(ConsultarPerfil.this, MenuPrincipal.class);
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
        datosPersonalesTitles.add("Datos personales");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Nombre Comercial: " + clienteCompleto.getCommercialName());
        basicInfo.add("Nombre Legal: " + clienteCompleto.getLegalName());
        basicInfo.add("Email: " + clienteCompleto.getEmail());
        basicInfo.add("RFC: " + clienteCompleto.getRFC());
        if(clienteCompleto.getTelephones() != null){
            basicInfo.add("Telefono: " + clienteCompleto.getTelephones());
        }else{
            basicInfo.add("Telefono: Añadir telefono");
        }
        datosPersonalesData.put(datosPersonalesTitles.get(0), basicInfo);
        // Configurar el primer ExpandableListView
        AdaptadorPerfil adaptadorDatosPersonales = new AdaptadorPerfil(this, datosPersonalesTitles, datosPersonalesData);
        datosPersonales.setAdapter(adaptadorDatosPersonales);
    }

}