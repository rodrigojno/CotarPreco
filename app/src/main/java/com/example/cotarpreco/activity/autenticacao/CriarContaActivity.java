package com.example.cotarpreco.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cotarpreco.R;
import com.example.cotarpreco.adapter.ViewPagerAdapter;
import com.example.cotarpreco.databinding.ActivityCriarContaBinding;
import com.example.cotarpreco.fragment.empresa.EmpresaFragment;
import com.example.cotarpreco.fragment.usuario.UsuarioFragment;
import com.google.android.material.tabs.TabLayout;

public class CriarContaActivity extends AppCompatActivity {

    private ActivityCriarContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configTabsLayout();

        configCliques();

        binding.include.textTitulo.setText("Cadastre-se");

    }

    private void configCliques(){
        binding.include.ibVoltar.setOnClickListener(v -> finish());
    }

    private void configTabsLayout(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new UsuarioFragment(), "Usuário");
        viewPagerAdapter.addFragment(new EmpresaFragment(), "Empresa");

        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.tabLayout.setElevation(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }


}