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
import com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;
import com.SolucionesParaPlagas.android.Controlador.Controlador;
import com.SolucionesParaPlagas.android.Controlador.ControladorCliente;
import com.SolucionesParaPlagas.android.Vista.Adaptador.AdaptadorPerfil;
import com.SolucionesParaPlagas.android.Controlador.ControladorValidaciones;

public class EditarDirecciones extends AppCompatActivity {

    private Cliente cliente = new Cliente();
    private AdaptadorPerfil adaptadorFiscal;
    private ImageView btnProductos, btnMenu, btnCerrarSesion;
    private ExpandableListView direccionFiscal;
    Controlador<Cliente> controladorCliente = ControladorCliente.obtenerInstancia(this);

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
        btnMenu = findViewById(R.id.iconoMenu);
        btnProductos = findViewById(R.id.iconoVerProductos);
        direccionFiscal = findViewById(R.id.direccionFiscal);
        btnCerrarSesion = findViewById(R.id.iconoCerrarSesion);
    }

    private void inicializarCliente() {
        cliente = controladorCliente.obtenerObjeto();
        Cliente clienteE = new Cliente();
        clienteE.setClienteRFC(cliente.getClienteRFC());
        clienteE.setTelefonoC(cliente.getTelefonoC());
        controladorCliente.limpiarRepositorio();
        controladorCliente.objetoToRepositorio(clienteE.getClienteRFC(), clienteE.getTelefonoC());
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
    }

    private void configurarFiscal() {
        List<String> direccionFiscalTitles = new ArrayList<>();
        HashMap<String, List<String>> direccionFiscalData = new HashMap<>();

        direccionFiscalTitles.add("Dirección Fiscal");
        List<String> basicInfo = new ArrayList<>();
        basicInfo.add("Estado: " + cliente.getEstado());
        basicInfo.add("Municipio: " + cliente.getMunicipio());
        basicInfo.add("Código Postal: " + cliente.getClienteCP()); // Asegúrate de tener un método getClienteCP()
        basicInfo.add("Calle: " + cliente.getCalle());
        basicInfo.add("Colonia: " + cliente.getColonia());
        basicInfo.add("Localidad: " + cliente.getLocalidad());
        basicInfo.add("Editar un dato específico de la dirección fiscal");
        direccionFiscalData.put(direccionFiscalTitles.get(0), basicInfo);

        adaptadorFiscal = new AdaptadorPerfil(this, direccionFiscalTitles, direccionFiscalData, new AdaptadorPerfil.OnChildClickListener() {
            @Override
            public void onChildClick(int groupPosition, int childPosition) {
                String campo = "", dato = "";
                switch (childPosition) {
                    case 0:
                        campo = "estado";
                        dato = cliente.getEstado();
                        break;
                    case 1:
                        campo = "municipio";
                        dato = cliente.getMunicipio();
                        break;
                    case 2:
                        campo = "cp";
                        dato = ""+cliente.getClienteCP();
                        break;
                    case 3:
                        campo = "calle";
                        dato = cliente.getCalle();
                        break;
                    case 4:
                        campo = "colonia";
                        dato = cliente.getColonia();
                        break;
                    case 5:
                        campo = "localidad";
                        dato = cliente.getLocalidad();
                        break;
                    default: return;
                }
                agregarDireccionFiscal(campo, dato);
            }
        });
        direccionFiscal.setAdapter(adaptadorFiscal);
    }

    private void agregarDireccionFiscal(String campo, String dato) {
        Intent intent = new Intent(EditarDirecciones.this, EditarDireccion.class);
        intent.putExtra("campo", campo); // Pasa el nombre del campo a editar
        intent.putExtra("datoDireccion", dato); // Pasa el valor actual del campo
        startActivity(intent);
    }

}
