package com.SolucionesParaPlagas.android.Vista.Adaptador;

import java.util.List;
import android.view.View;
import com.example.sol.R;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Controlador.ControladorImagenes;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductoViewHolder> {

    private List<Producto> listaProductos;
    private final OnProductoClickListener listener;
    private Context context;

    public AdaptadorProductos(List<Producto> listaProductos, OnProductoClickListener listener, Context context) {
        this.listaProductos = listaProductos;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista para cada elemento de la lista
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prod_lista, parent, false);
        return new ProductoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        // Vincular los datos con la vista
        Producto producto = listaProductos.get(position);
        holder.nombreProducto.setText(producto.getTitle());
        String imageUrl = producto.getImageUrl();
        ControladorImagenes controladorImagenes = new ControladorImagenes(context);
        controladorImagenes.cargarImagenDesdeUrl(imageUrl, holder.imagenProducto);
        // Manejo de clics en el ítem
        holder.itemView.setOnClickListener(v -> listener.onProductoClick(producto));
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los elementos de la vista
        TextView nombreProducto;
        ImageView imagenProducto;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            imagenProducto = itemView.findViewById(R.id.imagenProducto);
        }
    }

    public interface OnProductoClickListener {
        void onProductoClick(Producto producto);
    }

}
