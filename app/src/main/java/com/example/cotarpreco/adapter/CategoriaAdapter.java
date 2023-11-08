package com.example.cotarpreco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cotarpreco.R;
import com.example.cotarpreco.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {

    private List<Categoria> categoriaList;
    private OnClickListener onClickListener;

    public CategoriaAdapter(List<Categoria> categoriaList, OnClickListener onClickListener) {
        this.categoriaList = categoriaList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = categoriaList.get(position);

        //holder.img_categoria.setImageResource(Integer.parseInt(categoria.getId()));

        holder.text_categoria.setText(categoria.getNome());

        holder.itemView.setOnClickListener(v -> onClickListener.OnClick(categoria, position));

    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public interface OnClickListener {
        void OnClick(Categoria categoria, int position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        //ImageView img_categoria;
        TextView text_categoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //img_categoria = itemView.findViewById(R.id.img_categoria);
            text_categoria = itemView.findViewById(R.id.text_categoria);
        }
    }
}
