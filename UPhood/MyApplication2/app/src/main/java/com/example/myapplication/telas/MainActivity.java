package com.example.myapplication.telas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceDataStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.PeerfilTeela;
import com.example.myapplication.R;
import com.example.myapplication.lista;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private NavHostFragment navHostFragment;
    private NavController navController;
    private ImageView perfil;
    private Button btAddCompart;
    private ImageButton bt_add1, bt_add2, bt_add3, excluir1, excluir2, excluir3;
    private LinearLayout compart1, compart2, compart3;
    private RelativeLayout status1, status2, status3;

    int contador = 0;

    BottomNavigationView BottomNavigationView;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavigation();
        showStatus();
        addCompart();

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
                        return true;

                    case R.id.menu_promocao:
                        startActivity(new Intent(getApplicationContext(), promocoes.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void showStatus(){
        bt_add1 = findViewById(R.id.add1);
        status1 = findViewById(R.id.status1);
        compart1 = findViewById(R.id.compart1);
        excluir1 = findViewById(R.id.excluir1);
        SharedPreferences sharedPreferences;


        sharedPreferences=getPreferences(Context.MODE_PRIVATE);
        status1.setVisibility(sharedPreferences.getInt("visibility1",8));
        bt_add1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (status1.getVisibility() == View.GONE) {
                   status1.setVisibility(View.VISIBLE);
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putInt("visibility1",0).commit();
               }
           }

       });

        compart1.setVisibility(sharedPreferences.getInt("visibilityCompart1",0));
        excluir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compart1.getVisibility() == View.VISIBLE) {
                    compart1.setVisibility(View.GONE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("visibilityCompart1",8).commit();
                }

                contador = 0;
            }
        });

         /*excluir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status1.getVisibility() == View.VISIBLE) {
                    status1.setVisibility(View.GONE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("visibility1",8).commit();
                }
            }
        });*/

        bt_add2 = findViewById(R.id.add2);
        status2 = findViewById(R.id.status2);
        compart2 = findViewById(R.id.compart2);
        excluir2 = findViewById(R.id.excluir2);
        SharedPreferences sharedPreferences2;

        sharedPreferences2=getPreferences(Context.MODE_PRIVATE);
        status2.setVisibility(sharedPreferences2.getInt("visibility2",8));

        bt_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status2.getVisibility() == View.GONE) {
                    status2.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor editor=sharedPreferences2.edit();
                    editor.putInt("visibility2",0).commit();

                }
            }

        });

        compart2.setVisibility(sharedPreferences2.getInt("visibilityCompart2",0));
        excluir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compart2.getVisibility() == View.VISIBLE) {
                    compart2.setVisibility(View.GONE);
                    SharedPreferences.Editor editor=sharedPreferences2.edit();
                    editor.putInt("visibilityCompart2",8).commit();
                }

                contador = 0;
            }
        });

        /*excluir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status2.getVisibility() == View.VISIBLE) {
                    status2.setVisibility(View.GONE);
                    SharedPreferences.Editor editor=sharedPreferences2.edit();
                    editor.putInt("visibility2",8).commit();
                }
            }
        });*/

        bt_add3 = findViewById(R.id.add3);
        status3 = findViewById(R.id.status3);
        compart3 = findViewById(R.id.compart3);
        excluir3 = findViewById(R.id.excluir3);
        SharedPreferences sharedPreferences3;

        sharedPreferences3=getPreferences(Context.MODE_PRIVATE);
        status3.setVisibility(sharedPreferences3.getInt("visibility3",8));

        bt_add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status3.getVisibility() == View.GONE) {
                    status3.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor editor=sharedPreferences3.edit();
                    editor.putInt("visibility3",0).commit();

                }
            }

        });

        compart3.setVisibility(sharedPreferences3.getInt("visibilityCompart3",0));
        excluir3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compart3.getVisibility() == View.VISIBLE) {
                    compart3.setVisibility(View.GONE);
                    SharedPreferences.Editor editor=sharedPreferences3.edit();
                    editor.putInt("visibilityCompart3",8).commit();
                }
                contador = 0;
            }
        });

        /*excluir3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status3.getVisibility() == View.VISIBLE) {
                    status3.setVisibility(View.GONE);
                    SharedPreferences.Editor editor=sharedPreferences3.edit();
                    editor.putInt("visibility3",8).commit();
                }
            }
        });*/

    }

    private void addCompart() {
        btAddCompart = findViewById(R.id.btnAddCompart);
        compart1 = findViewById(R.id.compart1);
        SharedPreferences sharedPreferences4;
        SharedPreferences sharedPreferences5;
        SharedPreferences sharedPreferences6;

        sharedPreferences4=getPreferences(Context.MODE_PRIVATE);
        compart1.setVisibility(sharedPreferences4.getInt("visibilityCompart1",8));

        sharedPreferences5=getPreferences(Context.MODE_PRIVATE);
        compart2.setVisibility(sharedPreferences5.getInt("visibilityCompart2",8));

        sharedPreferences6=getPreferences(Context.MODE_PRIVATE);
        compart3.setVisibility(sharedPreferences6.getInt("visibilityCompart3",8));

        btAddCompart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador++;
                if(contador == 1){

                    if (compart1.getVisibility() == View.GONE) {
                        compart1.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor editor = sharedPreferences4.edit();
                        editor.putInt("visibilityCompart1", 0).commit();
                    }

                }

                if(contador == 2){

                    if (compart2.getVisibility() == View.GONE) {
                        compart2.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor editor = sharedPreferences5.edit();
                        editor.putInt("visibilityCompart2", 0).commit();
                    }

                }

                if(contador == 3){

                    if (compart3.getVisibility() == View.GONE) {
                        compart3.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor editor = sharedPreferences6.edit();
                        editor.putInt("visibilityCompart3", 0).commit();
                    }

                    contador = 0;

                }
            }
        });

    }

}