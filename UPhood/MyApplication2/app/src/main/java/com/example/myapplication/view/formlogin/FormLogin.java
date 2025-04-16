package com.example.myapplication.view.formlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.PrimTela;
import com.example.myapplication.view.formcadastro.FormCadastro;
import com.example.myapplication.telas.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FormLogin extends AppCompatActivity {

    //private Button btnTelaPrincipaldois;
    private Button btnTelaCadastrodois;
    private EditText emailLog, senhaLog;
    private Button bt_entrar;
    private CheckBox checkLembrar;
    private ImageView bt_Voltar;
    String[] mensagens = {"É necessário que todos os campos sejam preenchidos!", "Login efetuado com sucesso!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);


        btnTelaCadastrodois = findViewById(R.id.btnCadastro);
        btnTelaCadastrodois.setOnClickListener(view ->
                startActivity(new Intent(this, FormCadastro.class)));


        IniciarComponentes();

        bt_Voltar = findViewById(R.id.btnVoltar);
        bt_Voltar.setOnClickListener(view -> {
            startActivity(new Intent(this, PrimTela.class));
        });

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailLog.getText().toString();
                String senha = senhaLog.getText().toString();

                if (email.isEmpty() || senha.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.RED);
                    snackbar.show();
                }
                else{
                    AutenticarUsuario(v);
                }
            }
        });


    }
    private void AutenticarUsuario(View v){

        String email = emailLog.getText().toString();
        String senha = senhaLog.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           TelaPrincipal();
                        }
                    }, 1000);
                }
                else{
                   String erro;
                   try{
                       throw task.getException();
                   }
                   catch (Exception e){
                       erro = "Erro ao logar usuário!";
                   }
                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.RED);
                    snackbar.show();
                }
            }
        });

    }


    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(usuarioAtual != null){
            TelaPrincipal();
        }
    }

    private void TelaPrincipal(){

        Intent intent = new Intent(FormLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void IniciarComponentes(){

        emailLog = findViewById(R.id.emailLog);
        senhaLog = findViewById(R.id.senhaLog);
        bt_entrar = findViewById(R.id.btnEntrarMain);
        checkLembrar = findViewById(R.id.checkLembrar);

        checkLembrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkLembrar.isChecked()){
                    onStart();
                }
            }
        });
    }
}