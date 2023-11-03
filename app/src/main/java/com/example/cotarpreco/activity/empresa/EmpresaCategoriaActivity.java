package com.example.cotarpreco.activity.empresa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.adapter.AdapterCategoria;
import com.example.cotarpreco.databinding.ActivityEmpresaCategoriaBinding;
import com.example.cotarpreco.helper.CategoriaList;
import com.example.cotarpreco.model.Categoria;

public class EmpresaCategoriaActivity extends AppCompatActivity implements AdapterCategoria.OnClickListener {

    private ActivityEmpresaCategoriaBinding binding;

    private RecyclerView rv_categorias;
    private AdapterCategoria adapterCategoria;
    //private ProgressBar progressBar;
    //private TextView text_info;

    //private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaCategoriaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaComponentes();

        configCliques();

        iniciaRV();

        binding.include.textTitulo.setText("Categorias");

    }

    private void iniciaRV(){
        rv_categorias.setLayoutManager(new LinearLayoutManager(this));
        rv_categorias.setHasFixedSize(true);
        adapterCategoria = new AdapterCategoria(CategoriaList.getList(false), this);
        rv_categorias.setAdapter(adapterCategoria);
    }

    private void configCliques(){
        binding.include.ibVoltar.setOnClickListener(view -> finish());
        //findViewById(R.id.ib_add).setOnClickListener(v -> showDialog());
    }

    /*private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_categoria, null);
        builder.setView(view);

        EditText edt_categoria = view.findViewById(R.id.edt_categoria);
        Button btn_fechar = view.findViewById(R.id.btn_fechar);
        Button btn_salvar = view.findViewById(R.id.btn_salvar);

        btn_salvar.setOnClickListener(v -> {

            String nomeCategoria = edt_categoria.getText().toString().trim();

            if(!nomeCategoria.isEmpty()){

                Categoria categoria = new Categoria();
                categoria.setNome(nomeCategoria);
                categoria.salvar();

                dialog.dismiss();

            }else {
                edt_categoria.requestFocus();
                edt_categoria.setError("Informe um nome.");
            }
        });

        btn_fechar.setOnClickListener(v -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }*/

    private void iniciaComponentes(){


        rv_categorias = findViewById(R.id.rv_categorias);
        //progressBar = findViewById(R.id.progressBar);
        //text_info = findViewById(R.id.text_info);
    }

    @Override
    public void OnClick(Categoria categoria) {
        Intent intent = new Intent();
        intent.putExtra("categoriaSelecionada", categoria);
        setResult(RESULT_OK, intent);
        finish();
    }
}