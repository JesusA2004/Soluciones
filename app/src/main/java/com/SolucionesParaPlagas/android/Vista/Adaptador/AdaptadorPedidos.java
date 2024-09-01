package com.SolucionesParaPlagas.android.Vista.Adaptador;

import java.util.List;
import android.view.View;
import com.example.sol.R;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido.PedidoIndividual;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.PedidoViewHolder> {

    private List<PedidoIndividual> listaPedidos;

    public AdaptadorPedidos(List<PedidoIndividual> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista para cada elemento de la lista
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        // Vincular los datos con la vista
        PedidoIndividual pedido = listaPedidos.get(position);
        holder.idPedido.setText(pedido.getID());
        holder.fechaPedido.setText(pedido.getOrderDate());
        holder.totalPedido.setText(""+pedido.getTotal());
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los elementos de la vista
        TextView idPedido, fechaPedido, totalPedido;
        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            idPedido = itemView.findViewById(R.id.idPedido);
            fechaPedido = itemView.findViewById(R.id.fechaPedido);
            totalPedido = itemView.findViewById(R.id.totalPedido);
        }
    }

}

