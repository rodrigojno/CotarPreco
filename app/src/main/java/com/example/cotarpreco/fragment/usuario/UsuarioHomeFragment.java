package com.example.cotarpreco.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.autenticacao.LoginActivity;
import com.example.cotarpreco.activity.usuario.UsuarioFormCotacaoActivity;
import com.example.cotarpreco.helper.FirebaseHelper;

public class UsuarioHomeFragment extends Fragment {

    private Button btn_nova_cotacao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usuario_home, container, false);

        iniciaComponentes(view);

        configClique();

        return view;

    }

    private void configClique() {
        btn_nova_cotacao.setOnClickListener(v -> {
            if (FirebaseHelper.getAutenticado()) {
                startActivity(new Intent(getActivity(), UsuarioFormCotacaoActivity.class));
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    private void iniciaComponentes(View view) {
        btn_nova_cotacao = view.findViewById(R.id.btn_nova_cotacao);
    }
}