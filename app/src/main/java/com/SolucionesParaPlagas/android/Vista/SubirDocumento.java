package com.SolucionesParaPlagas.android.Vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.example.sol.R;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ImageView;
import com.itextpdf.text.pdf.PdfReader;
import androidx.appcompat.app.AppCompatActivity;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SubirDocumento extends AppCompatActivity {

    private ImageView botonMenu;
    private ImageView botonRegresar;
    private ImageView botonSiguiente;
    private ImageView botonSubirDocumento;
    private EditText txt_contenido_pdf;

    // Variable para almacenar de manera local el archivo seleccionado y mandarlo a gmail
    private Uri archivoSeleccionado;

    // Datos recibidos de la actividad anterior
    private String rfc, razonSocial, telefono, correo, calle, colonia, localidad, noInterior, noExterior, cp, municipio, estado;

    // ActivityResultLauncher para seleccionar archivos
    private final ActivityResultLauncher<Intent> seleccionarArchivoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    Log.d("Archivo", "Archivo seleccionado: " + uri);
                    extractTextPdfFile(uri);
                    archivoSeleccionado = uri;
                    Log.d("ArchivoSeleccionado", "Uri del archivo: " + archivoSeleccionado.toString());
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subirdocumento);
        // Recibir los datos de la actividad anterior
        recibirDatosDeRegistroDatos();
        // Inicializar elementos de la UI
        inicializarElementos();
        configurarBotones();
        // Verificar permisos para almacenamiento
        solicitarPermisos();
    }

    private void recibirDatosDeRegistroDatos() {
        // Recibe los datos enviados desde la actividad anterior
        Intent intent = getIntent();
        if(intent != null){
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
    }

    private void inicializarElementos() {
        botonMenu = findViewById(R.id.flechaatras);
        botonSiguiente = findViewById(R.id.iconosiguiente);
        botonRegresar = findViewById(R.id.iconoatras);
        botonSubirDocumento = findViewById(R.id.iconosubirdocumento);
        txt_contenido_pdf = findViewById(R.id.textoPDF);
    }

    private void configurarBotones() {
        botonMenu.setOnClickListener(this::regresarAPaginaInicio);
        botonRegresar.setOnClickListener(this::regresarAPaginaDireccion);
        botonSiguiente.setOnClickListener(this::irAPaginaInicio);
        botonSubirDocumento.setOnClickListener(this::subirDocumento);
    }

    private void subirDocumento(View v) {
        cargarPdf();
    }

    private void extractTextPdfFile(Uri uri) {
        InputStream inputStream = null;
        PdfReader reader = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            reader = new PdfReader(inputStream);
            StringBuilder builder = new StringBuilder();
            int pages = reader.getNumberOfPages();
            for (int i = 1; i <= pages; i++) {
                String fileContent = PdfTextExtractor.getTextFromPage(reader, i);
                builder.append(fileContent);
            }
            final String textContent = builder.toString();
            runOnUiThread(() -> txt_contenido_pdf.setText(textContent));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierra el PdfReader manualmente
            if (reader != null) {
                reader.close();
            }
            // Cierra el InputStream manualmente
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cargarPdf() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        seleccionarArchivoLauncher.launch(intent);
    }

    private boolean mandarCorreo() {
        if (archivoSeleccionado != null) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "amjo220898@upemor.edu.mx", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android APP - ");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Mensaje del correo");
            emailIntent.putExtra(Intent.EXTRA_STREAM, archivoSeleccionado);
            Intent chooser = Intent.createChooser(emailIntent, getString(R.string.enviar_mail));
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
                return true;
            } else {
                Toast.makeText(this, "No hay aplicaciones de correo electrónico instaladas.", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Por favor selecciona un archivo primero.", Toast.LENGTH_LONG).show();
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

    private void irAPaginaInicio(View v) {
        if (mandarCorreo()) {
            Toast.makeText(this, "Correo electrónico enviado con éxito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SubirDocumento.this, PaginaInicio.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Ocurrió un error al enviar el correo. Por favor, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void solicitarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

}
