package com.SolucionesParaPlagas.android.Vista.Adaptador;

import java.util.List;

import com.SolucionesParaPlagas.android.Controlador.ControladorCarrito;
import com.example.sol.R;
import java.util.HashMap;
import android.view.View;
import java.util.ArrayList;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Carrito;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.CarritoViewHolder> {

    private Context context;
    private final OnProductoCarritoClickListener listener;
    private Carrito carrito = new Carrito();
    private ControladorCarrito controladorCarrito = ControladorCarrito.obtenerInstancia(this);
    private ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia(this);

    public AdaptadorCarrito(HashMap<String, Integer> carrito, Context context, AdaptadorCarrito.OnProductoCarritoClickListener listener) {
        this.carrito = carrito;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista para cada elemento de la lista
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prod_carrito, parent, false);
        return new CarritoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        // Obtener el ID del producto a partir de la posición
        List<String> keysList = new ArrayList<>(carrito.keySet());
        int idProducto = keysList.get(position).trim();
        // Obtener la cantidad correspondiente al producto
        int cantidad = carrito.get(idProducto);
        // Obtener el producto usando el ID
        Producto producto = controladorProducto.obtenerObjeto(idProducto);
        // Vincular los datos con la vista
        holder.nombreProducto.setText(producto.getNombreProd());
        holder.cantidadProducto.setText("Cantidad: "+String.valueOf(cantidad));
        holder.pesoProducto.setText("Peso: "+producto.getPeso()+" "+producto.getUnidadM());
        String imageUrl = producto.getUrlImagen();
        ControladorImagenes controladorImagenes = new ControladorImagenes(context);
        controladorImagenes.cargarImagenDesdeUrl(imageUrl, holder.imagenCarrito);
        holder.itemView.setOnClickListener(v -> listener.onProductoClick(producto));
        // Manejar clic en el icono de eliminar para eliminar el producto del carrito
        holder.iconoEliminar.setOnClickListener(v -> {
            // Llamar al listener para eliminar el producto
            listener.onProductoEliminarClick(idProducto);
        });
    }

    public interface OnProductoCarritoClickListener {
        void onProductoClick(Producto producto);
        void onProductoEliminarClick(String idProducto);
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los elementos de la vista
        TextView nombreProducto, cantidadProducto, pesoProducto;
        ImageView imagenCarrito, iconoEliminar;
        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            cantidadProducto = itemView.findViewById(R.id.cantidadProducto);
            pesoProducto = itemView.findViewById(R.id.pesoProducto);
            imagenCarrito = itemView.findViewById(R.id.imagenProducto);
            iconoEliminar = itemView.findViewById(R.id.eliminarProducto);
        }
    }

}

