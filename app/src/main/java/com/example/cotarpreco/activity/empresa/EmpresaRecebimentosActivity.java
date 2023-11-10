package com.example.cotarpreco.activity.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityEmpresaRecebimentosBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Pagamento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmpresaRecebimentosActivity extends AppCompatActivity {

    private ActivityEmpresaRecebimentosBinding binding;

    private List<Pagamento> pagamentoList = new ArrayList<>();

    private Pagamento dinheiro = new Pagamento();
    private Pagamento pix = new Pagamento();
    private Pagamento cartaoCredito = new Pagamento();
    private Pagamento cartaoDebito = new Pagamento();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaRecebimentosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configCliques();

        recuperaPagamentos();

        binding.include.textToolbar.setText("Recebimentos");
    }

    private void configCliques(){
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibSalvar.setOnClickListener(v -> salvarPagamentos());

        binding.cbDinheiro.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            dinheiro.setDescricao("Dinheiro");
            dinheiro.setStatus(isChecked);
        });

        binding.cbPix.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            pix.setDescricao("PIX");
            pix.setStatus(isChecked);
        });

        binding.cbCartaoCredito.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            cartaoCredito.setDescricao("Cartão de crédito");
            cartaoCredito.setStatus(isChecked);
        });

        binding.cbCartaoDebito.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            cartaoDebito.setDescricao("Cartão de débito");
            cartaoDebito.setStatus(isChecked);
        });

    }

    private void salvarPagamentos() {

        if(binding.cbDinheiro.isChecked()){
            if(!pagamentoList.contains(dinheiro)) pagamentoList.add(dinheiro);
        }

        if(binding.cbPix.isChecked()){
            if(!pagamentoList.contains(pix)) pagamentoList.add(pix);
        }

        if(binding.cbCartaoCredito.isChecked()){
            if(!pagamentoList.contains(cartaoCredito)) pagamentoList.add(cartaoCredito);
        }

        if(binding.cbCartaoDebito.isChecked()){
            if(!pagamentoList.contains(cartaoDebito)) pagamentoList.add(cartaoDebito);
        }


        Pagamento.salvar(pagamentoList);
        Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void recuperaPagamentos(){
        DatabaseReference pagamentoRef = FirebaseHelper.getDatabaseReference()
                .child("recebimentos")
                .child(FirebaseHelper.getIdFirebase());
        pagamentoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Pagamento pagamento = ds.getValue(Pagamento.class);
                        pagamentoList.add(pagamento);
                    }

                    configPagamentos();

                }else {
                    configSalvar(false);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    private void configPagamentos() {
        for (Pagamento pagamento : pagamentoList) {
            switch (pagamento.getDescricao()){
                case "Dinheiro":
                    dinheiro = pagamento;
                    binding.cbDinheiro.setChecked(dinheiro.getStatus());
                    break;
                case "PIX":
                    pix = pagamento;
                    binding.cbPix.setChecked(pix.getStatus());
                    break;
                case "Cartão de crédito":
                    cartaoCredito = pagamento;
                    binding.cbCartaoCredito.setChecked(cartaoCredito.getStatus());
                    break;
                case "Cartão de débito":
                    cartaoDebito = pagamento;
                    binding.cbCartaoDebito.setChecked(cartaoDebito.getStatus());
                    break;
            }
        }
        configSalvar(false);
    }

}