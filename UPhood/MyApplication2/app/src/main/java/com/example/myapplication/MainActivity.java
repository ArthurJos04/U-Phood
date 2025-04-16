package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myapplication.view.formlogin.FormLogin;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnopcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnopcao = findViewById(R.id.add1);
        btnopcao.setOnClickListener(view ->
                startActivity(new Intent(this, lista.class)));
    }
}