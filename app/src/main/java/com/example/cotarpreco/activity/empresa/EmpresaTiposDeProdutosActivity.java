package com.example.cotarpreco.activity.empresa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityEmpresaTiposDeProdutosBinding;

public class EmpresaTiposDeProdutosActivity extends AppCompatActivity {

    private ActivityEmpresaTiposDeProdutosBinding binding;

    private RecyclerView rv_tipos_produtos;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        configCliques();

        binding.include.textToolbar.setText("Tipos de produtos");

    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibAdd.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_tipo_produto, null);
        builder.setView(view);

        EditText edt_tipo_produto = view.findViewById(R.id.edt_tipo_produto);
        Button btn_fechar = view.findViewById(R.id.btn_fechar);
        Button btn_salvar = view.findViewById(R.id.btn_salvar);

        String nomeTipoProduto = edt_tipo_produto.getText().toString().trim();

        btn_salvar.setOnClickListener(v -> {
            if (!nomeTipoProduto.isEmpty()) {

            } else {
                edt_tipo_produto.requestFocus();
                edt_tipo_produto.setError("Informe um tipo.");
            }
        });

        btn_fechar.setOnClickListener(v -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }

}