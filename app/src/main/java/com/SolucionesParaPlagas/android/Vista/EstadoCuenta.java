package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import com.example.sol.R;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorDetalleCliente;

public class EstadoCuenta extends AppCompatActivity {

    TextView txtTitulo, txtDiasCredito, txtMontoCredito;
    TextView txtMetodoP;
    ImageView btnMenu, btnCerrarSesion, btnProductos;
    DetalleCliente clienteCompleto = new DetalleCliente();
    Validaciones validaciones = new Validaciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultarestadoc);
        inicializarElementos();
        inicializarCliente();
        mostrarDatos();
        configurarBotones();
    }

    private void inicializarElementos() {
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        txtTitulo = findViewById(R.id.txtMsjB);
        txtDiasCredito = findViewById(R.id.txtDiasCredito);
        txtMontoCredito = findViewById(R.id.txtMontoCredito);
        txtMetodoP = findViewById(R.id.txtMetodoPago);
    }

    private void inicializarCliente() {
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
        Intent intent = new Intent(EstadoCuenta.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v){
        Intent intent = new Intent(EstadoCuenta.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        Sesion sesion = new Sesion();
        sesion.confirmarCerrarSesion(this);
    }

    private void mostrarDatos(){
        if(clienteCompleto != null){
            txtTitulo.setText("¡Bienvenido a tu Estado de\nCuenta "+validaciones.capitalizarLetras(clienteCompleto.getCommercialName())+"!");
            txtDiasCredito.setText("Días de crédito: "+clienteCompleto.getCreditDays()+"");
            txtMontoCredito.setText("Monto de crédito: $"+clienteCompleto.getCreditAmount()+"");
            txtMetodoP.setText("Metodo de pago: "+clienteCompleto.getPaymentMethod()+"");
        }
    }

}