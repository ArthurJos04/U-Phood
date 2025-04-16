package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.view.formcadastro.FormCadastro;
import com.example.myapplication.view.formlogin.FormLogin;

public class PrimTela extends AppCompatActivity {

    private Button btnTelaLogin;
    private Button btnTelaCadastro;
    private ImageView ImgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prim_tela);

        btnTelaLogin = findViewById(R.id.btnTelaLogin);
        btnTelaLogin.setOnClickListener(view ->
                startActivity(new Intent(this, FormLogin.class)));


        btnTelaCadastro = findViewById(R.id.btnTelaCadastro);
        btnTelaCadastro.setOnClickListener(view ->
                startActivity(new Intent(this, FormCadastro.class)));

        ImgInfo = findViewById(R.id.ImgInfo);
        ImgInfo.setOnClickListener(view ->
                startActivity(new Intent(this, tela_sobre.class)));


    }
}