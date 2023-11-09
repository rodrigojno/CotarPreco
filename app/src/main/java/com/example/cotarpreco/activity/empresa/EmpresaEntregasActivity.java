package com.example.cotarpreco.activity.empresa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityEmpresaCategoriaBinding;
import com.example.cotarpreco.databinding.ActivityEmpresaEntregasBinding;
import com.example.cotarpreco.model.Entrega;

import java.util.ArrayList;
import java.util.List;

public class EmpresaEntregasActivity extends AppCompatActivity {

    private ActivityEmpresaEntregasBinding binding;

    private List<Entrega> entregaList = new ArrayList<>();

    private Entrega domicilio;
    private Entrega retirada;
    private Entrega outra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaEntregasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.include.textToolbar.setText("Entregas");
    }

    private void configCliques(){
        binding.include.ibSalvar.setOnClickListener(v -> validaEntregas());
    }

    private void configEntregas(){

    }

    private void validaEntregas(){

        entregaList.clear();

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



    }

}