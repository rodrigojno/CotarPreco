package com.example.cotarpreco.activity.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityEmpresaCategoriaBinding;
import com.example.cotarpreco.databinding.ActivityEmpresaEntregasBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Entrega;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmpresaEntregasActivity extends AppCompatActivity {

    private ActivityEmpresaEntregasBinding binding;

    private List<Entrega> entregaList = new ArrayList<>();

    private Entrega domicilio = new Entrega();
    private Entrega retirada = new Entrega();
    private Entrega outra = new Entrega();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaEntregasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperaEntregas();

        configCliques();

        binding.include.textToolbar.setText("Entregas");
    }

    private void configSalvar(boolean progress){
        if(progress){
            binding.include.progressBar.setVisibility(View.VISIBLE);
            binding.include.ibSalvar.setVisibility(View.GONE);
        }else {
            binding.include.progressBar.setVisibility(View.GONE);
            binding.include.ibSalvar.setVisibility(View.VISIBLE);
        }
    }

    private void recuperaEntregas(){
        DatabaseReference entregasRef = FirebaseHelper.getDatabaseReference()
                .child("entregas")
                .child(FirebaseHelper.getIdFirebase());
        entregasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    entregaList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Entrega entrega = ds.getValue(Entrega.class);
                        configEntregas(entrega);
                    }
                }else {
                    configSalvar(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configCliques(){
        binding.include.ibSalvar.setOnClickListener(v -> validaEntregas());
        binding.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void configEntregas(Entrega entrega){
        switch (entrega.getDescricao()){
            case "Domicílio":
                domicilio = entrega;
                binding.edtDomicilio.setText(String.valueOf(domicilio.getTaxa() * 10));
                binding.cbDomicilio.setChecked(domicilio.getStatus());
                break;
            case "Retirada":
                retirada = entrega;
                binding.edtRetirada.setText(String.valueOf(retirada.getTaxa() * 10));
                binding.cbRetirada.setChecked(retirada.getStatus());
                break;
            case "Outra":
                outra = entrega;
                binding.edtOutra.setText(String.valueOf(outra.getTaxa() * 10));
                binding.cbOutra.setChecked(outra.getStatus());
                break;
        }

        configSalvar(false);
    }

    private void validaEntregas(){

        entregaList.clear();
        configSalvar(true);

        domicilio.setStatus(binding.cbDomicilio.isChecked());
        domicilio.setTaxa((double) binding.edtDomicilio.getRawValue() / 100);
        domicilio.setDescricao("Domicílio");

        retirada.setStatus(binding.cbRetirada.isChecked());
        retirada.setTaxa((double) binding.edtRetirada.getRawValue() / 100);
        retirada.setDescricao("Retirada");

        outra.setStatus(binding.cbOutra.isChecked());
        outra.setTaxa((double) binding.edtOutra.getRawValue() / 100);
        outra.setDescricao("Outra");

        entregaList.add(domicilio);
        entregaList.add(retirada);
        entregaList.add(outra);

        Entrega.salvar(entregaList);
        configSalvar(false);
        Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

    }

}