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
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

public class EditarDirecciones extends AppCompatActivity {

    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private DetalleCliente clienteCompleto = new DetalleCliente();
    private ExpandableListView direccionFiscal, direccionEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direccioncliente);
        inicializarElementos();
        configurarBotones();
        inicializarCliente();
        configurarExpandableListViews();
    }

    private void inicializarElementos(){
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
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

    private void configurarExpandableListViews() {
        configurarFiscal();
        configurarEnvio();
    }

    private void configurarEnvio(){
        // Datos para el ExpandableListView de direccion de Envio
        List<String> direccionEnvioTitles = new ArrayList<>();
        HashMap<String, List<String>> direccionEnvioData = new HashMap<>();

        // Ejemplo de datos
        direccionEnvioTitles.add("Direccion de Envio");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Agregar direccion para el envio de su pedido");
        direccionEnvioData.put(direccionEnvioTitles.get(0), basicInfo);

        // Configurar el segundo ExpandableListView
        AdaptadorPerfil adaptadorEnvio = new AdaptadorPerfil(this, direccionEnvioTitles,direccionEnvioData);
        direccionEnvio.setAdapter(adaptadorEnvio);
    }

    private void configurarFiscal() {
        // Datos para el ExpandableListView de direccion Fiscal
        List<String> direccionFiscalTitles = new ArrayList<>();
        HashMap<String, List<String>> direccionFiscalData = new HashMap<>();
        // Título para la dirección fiscal
        direccionFiscalTitles.add("Direccion Fiscal");
        // Lista que contendrá la información básica de la dirección fiscal
        List<String> basicInfo = new ArrayList<>();
        // Agregar más información relacionada con la dirección fiscal
        basicInfo.add("Estado: " + clienteCompleto.getState());
        basicInfo.add("Ciudad: " + clienteCompleto.getCity());
        basicInfo.add("Agregar datos de la dirección fiscal");
        // Asociar los datos con el título
        direccionFiscalData.put(direccionFiscalTitles.get(0), basicInfo);
        // Configurar el primer ExpandableListView
        AdaptadorPerfil adaptadorFiscal = new AdaptadorPerfil(this, direccionFiscalTitles, direccionFiscalData);
        direccionFiscal.setAdapter(adaptadorFiscal);
    }

}