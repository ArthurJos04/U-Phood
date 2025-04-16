package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class tela_sobre extends AppCompatActivity {

    private ImageView bt_Voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_sobre);


        bt_Voltar = findViewById(R.id.btnVoltar);
        bt_Voltar.setOnClickListener(view -> {
            startActivity(new Intent(this, PrimTela.class));
        });
    }
}