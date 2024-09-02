package com.SolucionesParaPlagas.android.Vista.Adaptador;

import com.example.sol.R;
import java.util.HashMap;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.CarritoViewHolder> {

    HashMap<String, Integer> carrito = new HashMap<>();
    private ControladorProducto controladorProducto = ControladorProducto.obtenerInstancia();
    private Context context;

    public AdaptadorCarrito(HashMap<String, Integer> carrito,Context context) {
        this.carrito = carrito;
        this.context = context;
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
        String idProducto = (String) carrito.keySet().toArray()[position];
        // Obtener la cantidad correspondiente al producto
        int cantidad = carrito.get(idProducto);
        // Obtener el producto usando el ID
        Producto producto = controladorProducto.obtenerProducto(idProducto);
        // Vincular los datos con la vista
        holder.nombreProducto.setText(producto.getTitle());
        holder.cantidadProducto.setText(String.valueOf(cantidad));
        holder.pesoProducto.setText(""+producto.getWeight());
        String imageUrl = producto.getImageUrl();
        ControladorImagenes controladorImagenes = new ControladorImagenes(context);
        controladorImagenes.cargarImagenDesdeUrl(imageUrl, holder.imagenCarrito);
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los elementos de la vista
        TextView nombreProducto, cantidadProducto, pesoProducto;
        ImageView imagenCarrito;
        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            cantidadProducto = itemView.findViewById(R.id.cantidadProducto);
            pesoProducto = itemView.findViewById(R.id.pesoProducto);
            imagenCarrito = itemView.findViewById(R.id.imagenProducto);
        }
    }

}

