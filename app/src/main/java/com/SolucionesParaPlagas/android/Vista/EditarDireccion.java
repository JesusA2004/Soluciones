package com.SolucionesParaPlagas.android.Vista;

import com.example.sol.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class EditarDireccion extends AppCompatActivity {

    private TextView tit;
    private EditText campo;
    private String dato, titulo;
    private Button btnConfirmar;
    private Cliente cliente = new Cliente();
    private ImageView btnMenu, btnCerrarSesion, btnProductos, btnAtras;
    private Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editardatodireccion);
        inicializarElementos();
        recibirElementos();
        configurarBotones();
        inicializarCliente();
    }

    private void inicializarElementos() {
        btnConfirmar = findViewById(R.id.btnConfirmacion);
        btnMenu = findViewById(R.id.iconoMenu);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
        btnProductos = findViewById(R.id.iconoVerProductos);
        btnAtras = findViewById(R.id.iconoAtras);
        campo = findViewById(R.id.entrada);
        tit = findViewById(R.id.textView);
    }

    private void recibirElementos() {
        Intent intent = getIntent();
        if (intent != null) {
            titulo = intent.getStringExtra("campo");
            dato = intent.getStringExtra("datoDireccion");
            campo.setText(dato);
            tit.setText(titulo + ": ");
        }
    }

    private void configurarBotones() {
        btnMenu.setOnClickListener(this::irAMenu);
        btnConfirmar.setOnClickListener(this::guardarCambios);
        btnAtras.setOnClickListener(this::regresarAEditarPerfil);
        btnCerrarSesion.setOnClickListener(this::irACerrarSesion);
        btnProductos.setOnClickListener(this::regresarAProductos);
    }

    private void inicializarCliente() {
        cliente = controladorCliente.obtenerObjeto();
    }

    private void guardarCambios(View v) {

        // Validar el campo de entrada y actualizar el objeto Cliente
        String nuevoValor = campo.getText().toString();
        if (nuevoValor.isEmpty()) {
            avisoUsuario("El campo no puede estar vacío");
            return;
        }

        try {
            switch (titulo) {
                case "estado":
                    cliente.setEstado(nuevoValor);
                    break;
                case "municipio":
                    cliente.setMunicipio(nuevoValor);
                    break;
                case "cp":
                    cliente.setClienteCP(Integer.parseInt(nuevoValor));
                    break;
                case "calle":
                    cliente.setCalle(nuevoValor);
                    break;
                case "colonia":
                    cliente.setColonia(nuevoValor);
                    break;
                case "localidad":
                    cliente.setLocalidad(nuevoValor);
                    break;
                default:
                    avisoUsuario("Campo no válido");
                    return;
            }

            // Actualizar en la base de datos y redirigir a la vista de perfil
            controladorCliente.actualizarObjeto(cliente);
            Intent intent = new Intent(EditarDireccion.this, EditarDirecciones.class);
            startActivity(intent);

        } catch (NumberFormatException e) {
            avisoUsuario("Formato de código postal no válido");
        }
    }

    private void avisoUsuario(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void regresarAProductos(View v) {
        Intent intent = new Intent(EditarDireccion.this, MostrarProductos.class);
        startActivity(intent);
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(EditarDireccion.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void irACerrarSesion(View v) {
        ControladorValidaciones sesion = new ControladorValidaciones();
        sesion.confirmarCerrarSesion(this);
    }

    private void regresarAEditarPerfil(View v) {
        Intent intent = new Intent(EditarDireccion.this, EditarDirecciones.class);
        intent.putExtra("campo", campo.getText().toString());
        intent.putExtra("titulo", tit.getText().toString());
        startActivity(intent);
    }

}
