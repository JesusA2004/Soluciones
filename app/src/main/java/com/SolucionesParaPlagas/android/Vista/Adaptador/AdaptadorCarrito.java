package com.SolucionesParaPlagas.android.Vista.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SolucionesParaPlagas.android.Modelo.Entidad.Producto.Producto;
import com.example.sol.R;

import java.util.List;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public AdaptadorCarrito(List<Producto> listaProductos) {
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

