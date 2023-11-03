package com.example.cotarpreco.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.autenticacao.CriarContaActivity;
import com.example.cotarpreco.activity.autenticacao.LoginActivity;
import com.example.cotarpreco.activity.usuario.UsuarioEnderecosActivity;
import com.example.cotarpreco.activity.usuario.UsuarioPerfilActivity;
import com.example.cotarpreco.helper.FirebaseHelper;

public class UsuarioPerfilFragment extends Fragment {

    private ConstraintLayout l_logado;
    private ConstraintLayout l_deslogado;
    private LinearLayout menu_perfil;
    private LinearLayout menu_endereco;
    private LinearLayout menu_deslogar;
    private Button btn_cadastrar;
    private Button btn_entrar;
    private TextView text_usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuario_perfil, container, false);

        iniciaComponentes(view);

        configCliques();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        verificaAcesso();
    }

    private void verificaAcesso(){
        if(FirebaseHelper.getAutenticado()){
            l_deslogado.setVisibility(View.GONE);
            l_logado.setVisibility(View.VISIBLE);
            menu_deslogar.setVisibility(View.VISIBLE);
            text_usuario.setText(FirebaseHelper.getAuth().getCurrentUser().getDisplayName());
        }else {
            l_deslogado.setVisibility(View.VISIBLE);
            l_logado.setVisibility(View.GONE);
            menu_deslogar.setVisibility(View.GONE);
        }
    }

    private void configCliques(){
        btn_entrar.setOnClickListener(v -> startActivity(new Intent(requireActivity(), LoginActivity.class)));
        btn_cadastrar.setOnClickListener(v -> startActivity(new Intent(requireActivity(), CriarContaActivity.class)));
        menu_deslogar.setOnClickListener(v -> deslogar());
        menu_perfil.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioPerfilActivity.class)));
        menu_endereco.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UsuarioEnderecosActivity.class)));
    }

    private void deslogar(){
        FirebaseHelper.getAuth().signOut();
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.menu_home);
    }

    private void iniciaComponentes(View view){
        l_logado = view.findViewById(R.id.l_logado);
        l_deslogado = view.findViewById(R.id.l_deslogado);
        menu_perfil = view.findViewById(R.id.menu_perfil);
        menu_endereco = view.findViewById(R.id.menu_enderecos);
        menu_deslogar = view.findViewById(R.id.menu_deslogar);
        btn_cadastrar = view.findViewById(R.id.btn_cadastrar);
        btn_entrar = view.findViewById(R.id.btn_entrar);
        text_usuario = view.findViewById(R.id.text_usuario);
    }

}