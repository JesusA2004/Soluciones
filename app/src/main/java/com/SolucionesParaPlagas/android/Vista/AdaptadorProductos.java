package com.SolucionesParaPlagas.android.Vista;

import java.util.List;
import com.example.sol.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public AdaptadorProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
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
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los elementos de la vista
        TextView nombreProducto;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
        }
    }

}

