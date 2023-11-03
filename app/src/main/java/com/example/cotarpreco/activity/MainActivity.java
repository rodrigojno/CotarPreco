package com.example.cotarpreco.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.autenticacao.LoginActivity;
import com.example.cotarpreco.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibVerMais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iniciaComponentes();
        ouvinteCliques();

    }

    private void ouvinteCliques() {
        /*ibVerMais.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ibVerMais);
            popupMenu.getMenuInflater().inflate(R.menu.bottom_nav_menu_usuario, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_sobre) {
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.menu_sair) {
                    FirebaseHelper.getAuth().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                return true;
            });

            popupMenu.show();
        });*/
    }


    private void iniciaComponentes() {
        //ibVerMais = findViewById(R.id.ib_verMais);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Consulta Preços");
    }

}