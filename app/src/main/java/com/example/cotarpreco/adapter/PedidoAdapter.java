package com.example.cotarpreco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cotarpreco.R;
import com.example.cotarpreco.model.ItemPedido;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {

    private final List<ItemPedido> itemPedidoList;
    private final Context context;
    private final OnClickListener onClickListener;

    public PedidoAdapter(List<ItemPedido> itemPedidoList, Context context, OnClickListener onClickListener) {
        this.itemPedidoList = itemPedidoList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pedido, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemPedido itemPedido = itemPedidoList.get(position);

        holder.textNome.setText(itemPedido.getItem());
        holder.textMarca.setText(itemPedido.getMarca());

        holder.itemView.setOnClickListener(v -> onClickListener.OnClick(itemPedido));

    }

    @Override
    public int getItemCount() {
        return itemPedidoList.size();
    }

    public interface OnClickListener {
        void OnClick(ItemPedido itemPedido);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textNome, textMarca;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textNome = itemView.findViewById(R.id.textNome);
            textMarca = itemView.findViewById(R.id.textMarca);
        }
    }

}
