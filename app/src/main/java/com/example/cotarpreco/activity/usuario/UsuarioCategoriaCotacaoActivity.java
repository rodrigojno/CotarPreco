package com.example.cotarpreco.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cotarpreco.R;
import com.example.cotarpreco.adapter.CategoriaAdapter;
import com.example.cotarpreco.databinding.ActivityUsuarioCategoriaCotacaoBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Categoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioCategoriaCotacaoActivity extends AppCompatActivity implements CategoriaAdapter.OnClickListener{

    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> categoriaList = new ArrayList<>();

    private @NonNull ActivityUsuarioCategoriaCotacaoBinding binding;

    private SwipeableRecyclerView rv_categorias;

    private AlertDialog dialog;

    private Categoria categoriaSelecionada;
    private int categoriaIndex = 0;
    private Boolean novaCategoria = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioCategoriaCotacaoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        rv_categorias = binding.rvCategorias;

        configCliques();

        recuperaCategorias();

        configRV();

        binding.include.textToolbar.setText("Categorias");

    }

    private void configRV() {
        rv_categorias.setLayoutManager(new LinearLayoutManager(this));
        rv_categorias.setHasFixedSize(true);
        categoriaAdapter = new CategoriaAdapter(categoriaList, this);
        rv_categorias.setAdapter(categoriaAdapter);

        rv_categorias.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                dialogRemoverCategoria(categoriaList.get(position));
            }
        });

    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());
        binding.include.ibAdd.setOnClickListener(v -> {
            novaCategoria = true;
            showDialog();
        });
    }

    private void recuperaCategorias() {
        DatabaseReference categoriasRef = FirebaseHelper.getDatabaseReference()
                .child("categorias")
                .child(FirebaseHelper.getIdFirebase());
        categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Categoria categoria = ds.getValue(Categoria.class);
                        categoriaList.add(categoria);
                    }
                    binding.textInfo.setText("");
                } else {
                    binding.textInfo.setText("Nenhuma categoria cadastrada.");
                }

                binding.progressBar.setVisibility(View.GONE);
                Collections.reverse(categoriaList);
                categoriaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_categoria, null);
        builder.setView(view);

        EditText edt_categoria = view.findViewById(R.id.edt_categoria);
        Button btn_fechar = view.findViewById(R.id.btn_fechar);
        Button btn_salvar = view.findViewById(R.id.btn_salvar);

        if (!novaCategoria) {
            edt_categoria.setText(categoriaSelecionada.getNome());
        }

        btn_salvar.setOnClickListener(v -> {

            String nomeCategoria = edt_categoria.getText().toString().trim();

            if (!nomeCategoria.isEmpty()) {

                if (novaCategoria) {

                    Categoria categoria = new Categoria();
                    categoria.setNome(nomeCategoria);
                    categoria.salvar();

                    categoriaList.add(categoria);
                } else {
                    categoriaSelecionada.setNome(nomeCategoria);
                    categoriaList.set(categoriaIndex, categoriaSelecionada);
                    categoriaSelecionada.salvar();

                }

                if (!categoriaList.isEmpty()) {
                    binding.textInfo.setText("");
                }

                dialog.dismiss();
                categoriaAdapter.notifyDataSetChanged();

            } else {
                edt_categoria.requestFocus();
                edt_categoria.setError("Informe uma categoria.");
            }

        });

        btn_fechar.setOnClickListener(v -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }

    private void dialogRemoverCategoria(Categoria categoria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover categoria");
        builder.setMessage("Deseja remover a categoria selecionada?");
        builder.setNegativeButton("Não", ((dialog, which) -> {
            dialog.dismiss();
            categoriaAdapter.notifyDataSetChanged();
        }));
        builder.setPositiveButton("Sim", ((dialog, which) -> {
            categoria.remover();
            categoriaList.remove(categoria);

            if (categoriaList.isEmpty()) {
                binding.textInfo.setText("Nenhuma categoria cadastrada.");
            }

            categoriaAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void OnClick(Categoria categoria, int position) {
        categoriaSelecionada = categoria;
        categoriaIndex = position;
        novaCategoria = false;

        showDialog();

    }

}
