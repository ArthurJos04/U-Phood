package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class lista extends AppCompatActivity {

    RecyclerView recyclerView;

    String a1[], a2[];
    int images [] = {R.drawable.alfaceeee,R.drawable.tomate,R.drawable.cenoura,R.drawable.cebola,R.drawable.limao};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);

        a1 = getResources().getStringArray(R.array.alimentos);
        a2 = getResources().getStringArray(R.array.descricao);

        classezinha classezinha = new classezinha(this, a1, a2, images);
        recyclerView.setAdapter(classezinha);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));

    }

}