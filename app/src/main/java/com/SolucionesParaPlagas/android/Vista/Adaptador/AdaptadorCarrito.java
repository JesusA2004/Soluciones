package com.SolucionesParaPlagas.android.Vista.Adaptador;

import java.util.List;
import com.example.sol.R;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;
import com.SolucionesParaPlagas.android.Controlador.ControladorCarrito;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Controlador.ControladorProducto;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.CarritoViewHolder> {

    private Context context;
    private final OnProductoCarritoClickListener listener;
    private Compra carrito; // Objeto de la clase Compra que representa la nota de venta
    private ControladorProducto controladorProducto;

    public AdaptadorCarrito(Compra carrito, Context context, AdaptadorCarrito.OnProductoCarritoClickListener listener) {
        this.carrito = carrito;
        this.context = context;
        this.listener = listener;
        controladorProducto = ControladorProducto.obtenerInstancia(context);
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
        // Convertir el mapa de productos a una lista para obtener las claves (IDs de productos)
        List<Map.Entry<Integer, Integer>> productosList = new ArrayList<>(carrito.getProductos().entrySet());
        Map.Entry<Integer, Integer> productoEntry = productosList.get(position);
        int idProducto = productoEntry.getKey();
        int cantidad = productoEntry.getValue();

        // Obtener el producto usando el ID
        Producto producto = controladorProducto.obtenerObjeto(idProducto);

        // Verificar que el producto exista
        if (producto != null) {
            // Vincular los datos con la vista
            holder.nombreProducto.setText(producto.getNombreProd());
            holder.cantidadProducto.setText("Cantidad: " + cantidad);
            holder.pesoProducto.setText("Peso: " + producto.getPeso() + " " + producto.getUnidadM());

            String imageUrl = producto.getUrlImagen();
            ControladorImagenes controladorImagenes = new ControladorImagenes(context);
            controladorImagenes.cargarImagenDesdeUrl(imageUrl, holder.imagenCarrito);
        }

        holder.itemView.setOnClickListener(v -> listener.onProductoClick(producto));

        // Manejar clic en el icono de eliminar para eliminar el producto del carrito
        holder.iconoEliminar.setOnClickListener(v -> listener.onProductoEliminarClick(idProducto));
    }

    @Override
    public int getItemCount() {
        return carrito.getProductos().size(); // Tamaño del mapa de productos en la compra
    }

    public interface OnProductoCarritoClickListener {
        void onProductoClick(Producto producto);
        void onProductoEliminarClick(int idProducto);
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
