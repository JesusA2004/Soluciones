package com.SolucionesParaPlagas.android.Vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.sol.R;

public class SubirDocumento extends AppCompatActivity {

    private static final int RESPUESTA_PERMISO = 2;
    private static final int PERMISO_ACCESO_ALMACENAMIENTO = 100;
    private ImageView botonMenu;
    private ImageView botonRegresar;
    private ImageView botonSiguiente;
    private ImageView botonSubirDocumento;

    // Variable para almacenar de manera local el archivo seleccionado y mandarlo a gmail
    private Uri archivoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subirdocumento); // Asegúrate de que 'subirdocumento' sea el layout correcto
        inicializarElementos();
        configurarBotones();
    }

    private void inicializarElementos() {
        botonMenu = findViewById(R.id.flechaatras);
        botonSiguiente = findViewById(R.id.iconosiguiente);
        botonRegresar = findViewById(R.id.iconoatras);
        botonSubirDocumento = findViewById(R.id.iconosubirdocumento);
    }

    private void configurarBotones() {
        botonMenu.setOnClickListener(this::regresarAPaginaInicio);
        botonRegresar.setOnClickListener(this::regresarAPaginaDireccion);
        botonSiguiente.setOnClickListener(this::irAMenu);
        botonSubirDocumento.setOnClickListener(this::subirDocumento);
    }

    private void subirDocumento(View v) {
        if (ContextCompat.checkSelfPermission
        (this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISO_ACCESO_ALMACENAMIENTO);
        } else {
            abrirExploradorDeArchivos();
        }
    }

    private void abrirExploradorDeArchivos() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Para permitir todo tipo de archivos
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), RESPUESTA_PERMISO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESPUESTA_PERMISO && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                archivoSeleccionado = data.getData();
                mostrarRutaDelArchivo(archivoSeleccionado);
            } else {
                Toast.makeText(this, "No se seleccionó ningún archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mostrarRutaDelArchivo(Uri uri) {
        if (uri != null) {
            Toast.makeText(this, "Archivo seleccionado: " + uri.getPath(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo obtener la ruta del archivo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_ACCESO_ALMACENAMIENTO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirExploradorDeArchivos();
            } else {
                Toast.makeText(this, "Permiso denegado para leer el almacenamiento externo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean mandarCorreo() {
        if (archivoSeleccionado != null) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("vnd.android.cursor.dir/email");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"destinatario@ejemplo.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Mensaje del correo");
            emailIntent.putExtra(Intent.EXTRA_STREAM, archivoSeleccionado);
            try {
                startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            Toast.makeText(this, "Por favor selecciona un archivo primero.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void regresarAPaginaInicio(View v) {
        Intent intent = new Intent(SubirDocumento.this, PaginaInicio.class);
        startActivity(intent);
    }

    private void regresarAPaginaDireccion(View v) {
        Intent intent = new Intent(SubirDocumento.this, RegistroDireccion.class);
        startActivity(intent);
    }

    private void irAMenu(View v) {
        if (mandarCorreo()) {
            Intent intent = new Intent(SubirDocumento.this, Menu.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Ocurrió un error al enviar el correo. Por favor, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

}
