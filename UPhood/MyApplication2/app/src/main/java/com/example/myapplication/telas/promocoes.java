package com.example.myapplication.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.myapplication.PeerfilTeela;
import com.example.myapplication.R;
import com.example.myapplication.telas.MainActivity;
import com.example.myapplication.telas.notificacao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class promocoes extends AppCompatActivity {

    BottomNavigationView BottomNavigationView;
    private ImageView perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes);

        initNavigation();

        perfil = findViewById(R.id.profileImgg);
        perfil.setOnClickListener(view ->
                startActivity(new Intent(this, PeerfilTeela.class)));
    }

    private void initNavigation(){
        /*navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);*/

        BottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationView.setSelectedItemId(R.id.menu_estoque);
        BottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu_notificacao:
                        startActivity(new Intent(getApplicationContext(), notificacao.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menu_estoque:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menu_promocao:
                        return true;
                }
                return false;
            }
        });


    }
}