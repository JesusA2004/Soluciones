package com.SolucionesParaPlagas.android.Vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.sol.R;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class SubirDocumento extends AppCompatActivity {

    private static final int respuestaPermiso = 1;
    private static final int permisoParaAccederAlmacenamiento = 100;
    private ImageView botonMenu;
    private ImageView botonRegresar;
    private ImageView botonSiguiente;
    private ImageView botonSubirDocumento;

    // Variables para almacenar los datos recibidos de la actividad anterior
    private String rfc, razonSocial, telefono, correo;
    private String calle, colonia, localidad, noInterior, noExterior, cp, municipio, estado;

    // Variable global para almacenar el Uri del archivo seleccionado
    private Uri uriArchivoSeleccionado;
    /*
    Un Uri (Uniform Resource Identifier) es un identificador
    que se utiliza para referirse a recursos, como archivos,
    imágenes, videos, etc., en diferentes ubicaciones
    (por ejemplo, en el almacenamiento local del dispositivo,
    en una red, en la web, etc.).
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro2);
        inicializarElementos();
        recibirDatosDeRegistroDireccion(); // Llamamos al método para recibir los datos
        configurarBotones();
    }

    private void inicializarElementos() {
        botonMenu = findViewById(R.id.flechaatras);
        botonSiguiente = findViewById(R.id.iconosiguiente);
        botonRegresar = findViewById(R.id.iconoatras); // Esto probablemente es un error, debería ser otro ID
        botonSubirDocumento = findViewById(R.id.iconosubirdocumento);
    }

    private void configurarBotones() {
        botonMenu.setOnClickListener(this::regresarAPaginaInicio);
        botonRegresar.setOnClickListener(this::regresarAPaginaDireccion); // Corregido para regresar a la actividad de dirección
        botonSiguiente.setOnClickListener(this::irAMenu); // Esto parece un poco confuso, asegúrate de que sea la acción correcta
        botonSubirDocumento.setOnClickListener(this::subirDocumento);
    }

    // Método para recibir los datos de la actividad anterior
    private void recibirDatosDeRegistroDireccion() {
        Intent intent = getIntent();
        rfc = intent.getStringExtra("RFC");
        razonSocial = intent.getStringExtra("RazonSocial");
        telefono = intent.getStringExtra("Telefono");
        correo = intent.getStringExtra("Correo");
        calle = intent.getStringExtra("Calle");
        colonia = intent.getStringExtra("Colonia");
        localidad = intent.getStringExtra("Localidad");
        noInterior = intent.getStringExtra("NoInterior");
        noExterior = intent.getStringExtra("NoExterior");
        cp = intent.getStringExtra("CP");
        municipio = intent.getStringExtra("Municipio");
        estado = intent.getStringExtra("Estado");
    }

    // Métodos para subir el documento
    private void subirDocumento(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permiso para leer el almacenamiento externo
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    permisoParaAccederAlmacenamiento);
        } else {
            // Abrir el explorador de archivos para seleccionar un PDF
            abrirExploradorDeArchivos();
        }
    }

    // Método para abrir el explorador de archivos del teléfono
    private void abrirExploradorDeArchivos() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar PDF"), respuestaPermiso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == respuestaPermiso && resultCode == RESULT_OK) {
            if (data != null) {
                // Almacenar el Uri del archivo seleccionado en la variable global
                uriArchivoSeleccionado = data.getData();
                Toast.makeText(this, "Archivo seleccionado: " + uriArchivoSeleccionado.getPath(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para solicitar permiso de acceder al almacenamiento del teléfono
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permisoParaAccederAlmacenamiento) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirExploradorDeArchivos();
            } else {
                Toast.makeText(this, "Permiso denegado para leer el almacenamiento externo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Métodos para navegación entre layouts
    private void regresarAPaginaInicio(View v) {
        Intent intent = new Intent(SubirDocumento.this, PaginaInicio.class);
        startActivity(intent);
        finish();
    }

    private void regresarAPaginaDireccion(View v) {
        Intent intent = new Intent(SubirDocumento.this, RegistroDireccion.class);
        startActivity(intent);
        finish();
    }

    private void irAMenu(View v) {
        Intent intent = new Intent(SubirDocumento.this, Menu.class);
        startActivity(intent);
        finish();
    }

}
