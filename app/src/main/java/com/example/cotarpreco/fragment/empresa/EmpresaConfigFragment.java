package com.example.cotarpreco.fragment.empresa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.empresa.EmpresaCategoriaActivity;
import com.example.cotarpreco.activity.empresa.EmpresaConfigActivity;
import com.example.cotarpreco.activity.empresa.EmpresaEnderecoActivity;
import com.example.cotarpreco.activity.empresa.EmpresaEntregasActivity;
import com.example.cotarpreco.activity.empresa.EmpresaHomeActivity;
import com.example.cotarpreco.activity.empresa.EmpresaRecebimentosActivity;
import com.example.cotarpreco.activity.usuario.UsuarioHomeActivity;
import com.example.cotarpreco.helper.FirebaseHelper;

public class EmpresaConfigFragment extends Fragment {

    private TextView text_empresa;
    private LinearLayout menu_empresa;
    private LinearLayout menu_categorias;
    private LinearLayout menu_recebimentos;
    private LinearLayout menu_endereco;
    private LinearLayout menu_entregas;
    private LinearLayout menu_deslogar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empresa_config, container, false);

        iniciaComponentes(view);

        configCliques();

        configAcesso();

        return view;
    }

    private void configAcesso(){
        text_empresa.setText(FirebaseHelper.getAuth().getCurrentUser().getDisplayName());
    }

    private void configCliques(){
        menu_empresa.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EmpresaConfigActivity.class)));
        menu_categorias.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EmpresaCategoriaActivity.class)));
        menu_recebimentos.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EmpresaRecebimentosActivity.class)));
        menu_endereco.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EmpresaEnderecoActivity.class)));
        menu_entregas.setOnClickListener(v -> startActivity(new Intent(requireActivity(), EmpresaEntregasActivity.class)));
        menu_deslogar.setOnClickListener(v -> deslogar());
    }

    private void deslogar(){
        FirebaseHelper.getAuth().signOut();
        requireActivity().finish();
        startActivity(new Intent(requireActivity(), UsuarioHomeActivity.class));
    }

    private void iniciaComponentes(View view){
        text_empresa = view.findViewById(R.id.text_empresa);
        menu_empresa = view.findViewById(R.id.menu_empresa);
        menu_categorias = view.findViewById(R.id.menu_categorias);
        menu_recebimentos = view.findViewById(R.id.menu_recebimentos);
        menu_endereco = view.findViewById(R.id.menu_endereco);
        menu_entregas = view.findViewById(R.id.menu_entregas);
        menu_deslogar = view.findViewById(R.id.menu_deslogar);

    }

}