package com.SolucionesParaPlagas.android.Vista;

import android.os.Bundle;
import com.example.sol.R;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Controlador.Sesion;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente.DetalleCliente;

public class EstadoCuenta extends AppCompatActivity {

    TextView txtTitulo, txtDiasCredito, txtMontoCredito;
    TextView txtMetodoP;
    ImageView btnMenu, btnCerrarSesion, btnProductos;
    DetalleCliente cliente = new DetalleCliente();
    Sesion sesion = new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultarestadoc);
        inicializarElementos();
        obtenerElementos();
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

    private void obtenerElementos(){
        Intent intent = getIntent();
        if(intent.hasExtra("ClienteC")){
            cliente = intent.getParcelableExtra("ClienteC");
        }
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
        Intent intent = new Intent(EstadoCuenta.this,Menu.class);
        intent.putExtra("ClienteC", cliente);
        startActivity(intent);
    }

    private void irACerrarSesion(View v){
        limpiarSesion();
        Intent intent = new Intent(EstadoCuenta.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void limpiarSesion(){
        sesion.limpiarSesion();
    }

    private void mostrarDatos(){
        if(cliente != null){
            txtTitulo.setText("¡Bienvenido a tu Estado de\nCuenta "+cliente.getLegalName()+"!");
            txtDiasCredito.setText("Días de crédito: "+cliente.getCreditDays()+"");
            txtMontoCredito.setText("Monto de crédito: $"+cliente.getCreditAmount()+"");
            txtMetodoP.setText("Metodo de pago: "+cliente.getPaymentMethod()+"");
        }
    }

}