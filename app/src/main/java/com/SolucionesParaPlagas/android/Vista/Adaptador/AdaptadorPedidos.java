package com.SolucionesParaPlagas.android.Vista.Adaptador;

import java.util.List;
import android.view.View;

import com.example.sol.R;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.SolucionesParaPlagas.android.Modelo.Entidad.Compra;
import com.SolucionesParaPlagas.android.Controlador.ControladorListas;
import com.SolucionesParaPlagas.android.Controlador.ControladorCompras;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.PedidoViewHolder> {

    private List<Compra> listaPedidos;
    private Context context;
    private ControladorListas<Compra> controladorCompras;

    public AdaptadorPedidos(Context context) {
        this.context = context;
        controladorCompras = ControladorCompras.obtenerInstancia(context);
        // Obtener la lista de pedidos del controlador
        this.listaPedidos = controladorCompras.obtenerLista(); // Método para obtener todas las compras
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
        Compra pedido = listaPedidos.get(position);
        holder.idPedido.setText("ID Pedido: " + pedido.getIdNotaVenta());
        String fecha = pedido.getFecha().toString(); // Formatea la fecha según sea necesario
        holder.fechaPedido.setText("Fecha: " + fecha);
        holder.totalPedido.setText("Monto total del pedido: $" + pedido.getPagoTotal());
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
