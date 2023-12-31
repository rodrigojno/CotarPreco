package com.example.cotarpreco.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cotarpreco.R;
import com.example.cotarpreco.adapter.CategoriaAdapter;
import com.example.cotarpreco.adapter.EnderecoAdapter;
import com.example.cotarpreco.databinding.ActivityUsuarioEnderecosBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Categoria;
import com.example.cotarpreco.model.Endereco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioEnderecosActivity extends AppCompatActivity implements EnderecoAdapter.OnClickListener {

    private EnderecoAdapter enderecoAdapter;
    private List<Endereco> enderecoList = new ArrayList<>();

    private ActivityUsuarioEnderecosBinding binding;

    private SwipeableRecyclerView rv_enderecos;

    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioEnderecosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rv_enderecos = binding.rvEnderecos;

        configCliques();

        configRV();

        binding.include.textToolbar.setText("Endereços");

    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperaEnderecos();
    }

    private void configRV() {
        rv_enderecos.setLayoutManager(new LinearLayoutManager(this));
        rv_enderecos.setHasFixedSize(true);
        enderecoAdapter = new EnderecoAdapter(enderecoList, this);
        rv_enderecos.setAdapter(enderecoAdapter);

        rv_enderecos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
            }

            @Override
            public void onSwipedRight(int position) {
                removerEnderecoUsuario(enderecoList.get(position));
            }
        });
    }

    private void recuperaEnderecos(){
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(FirebaseHelper.getIdFirebase());
        enderecoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    enderecoList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        endereco = ds.getValue(Endereco.class);
                        enderecoList.add(endereco);
                    }
                    binding.textInfo.setText("");
                }else {
                    binding.textInfo.setText("Nenhum endereço cadastrado.");
                }

                binding.progressBar.setVisibility(View.GONE);
                Collections.reverse(enderecoList);
                enderecoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void removerEnderecoUsuario(Endereco endereco) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover endereço");
        builder.setMessage("Deseja remover o endereço selecionado?");
        builder.setNegativeButton("Não", ((dialog, which) -> {
            dialog.dismiss();
            enderecoAdapter.notifyDataSetChanged();
        }));
        builder.setPositiveButton("Sim", ((dialog, which) -> {
            endereco.remover();
            enderecoList.remove(endereco);

            if (enderecoList.isEmpty()) {
                binding.textInfo.setText("Nenhum endereço cadastrado.");
            }

            enderecoAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibAdd.setOnClickListener(v ->
                startActivity(new Intent(this, UsuarioFormEnderecoActivity.class)));
    }

    @Override
    public void OnClick(Endereco endereco) {
        Intent intent = new Intent(this, UsuarioFormEnderecoActivity.class);
        intent.putExtra("enderecoSelecionado", endereco);
        startActivity(intent);
    }
}