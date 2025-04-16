package com.example.myapplication.view.formcadastro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.telas.MainActivity;
import com.example.myapplication.PrimTela;
import com.example.myapplication.R;
import com.example.myapplication.view.formlogin.FormLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormCadastro extends AppCompatActivity {

    private Button btnLoginum;
    private EditText nomeCad,sobrenomeCad,emailCad,senhaCad,confsenhaCad, telCad;
    private Button bt_cadastrar;
    private ImageView bt_Voltar, bt_atualizarfoto;

    private CircleImageView fotoPerfil;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    String[] mensagens = {"É necessário que todos os campos sejam preenchidos!", "Cadastro realizado com sucesso!", "É necessário que o conteúdo 'Confirmar Senha' seja idêntico ao campo 'Senha'"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);


        IniciarComponentes();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        bt_atualizarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        bt_Voltar = findViewById(R.id.btnVoltar);
        bt_Voltar.setOnClickListener(view -> {
            startActivity(new Intent(this, PrimTela.class));
        });


        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = nomeCad.getText().toString();
                String sobrenome = sobrenomeCad.getText().toString();
                String email = emailCad.getText().toString();
                String senha = senhaCad.getText().toString();
                String confsenha = confsenhaCad.getText().toString();
                String telefone = telCad.getText().toString();
                String fotoP = imageUri.toString();

                if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || senha.isEmpty() || confsenha.isEmpty() || telefone.isEmpty() || fotoP.isEmpty() ){
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.GRAY);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }
                else{
                    if(confsenha.equals(senha)){
                        CadastrarUsuario(v);
                    }
                    else{
                        Snackbar snackbar = Snackbar.make(v,mensagens[2],Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.GRAY);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    }
                }

            }
        });

        btnLoginum = findViewById(R.id.btnLogin);
        btnLoginum.setOnClickListener(view ->
                startActivity(new Intent(this, FormLogin.class)));
    }

    private void CadastrarUsuario(View v){

        String email = emailCad.getText().toString();
        String senha = senhaCad.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    SalvarDadosUsuario();
                    Snackbar snackbar = Snackbar.make(v,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.GRAY);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    TelaPrincipal();

                }
                else{
                    String erro;
                    try {
                        throw task.getException();

                    }
                    catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres!";
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        erro = "Este endereço de email já está cadastrado!";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Endereço de email inválido!";
                    }
                    catch (Exception e){
                        erro = "Erro ao cadastrar usuário!";
                    }

                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.GRAY);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();

                }
            }
        });
    }

    private void SalvarDadosUsuario(){
        String nome = nomeCad.getText().toString();
        String sobrenome = sobrenomeCad.getText().toString();
        String fotoP = imageUri.toString();
        String telefone = telCad.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);
        usuarios.put("sobrenome",sobrenome);
        usuarios.put("fotoP", fotoP);
        usuarios.put("telefone",telefone);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db", "Sucesso ao salvar os dados!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salvar os dados!" + e.toString());
                    }
                });
    }
    private void IniciarComponentes(){

        nomeCad = findViewById(R.id.nomeCad);
        sobrenomeCad = findViewById(R.id.sobrenomeCad);
        emailCad = findViewById(R.id.emailCad);
        senhaCad = findViewById(R.id.senhaCad);
        confsenhaCad = findViewById(R.id.confsenhaCad);
        bt_cadastrar = findViewById(R.id.btnEntrar);
        bt_atualizarfoto = findViewById(R.id.bt_atualizarft);
        fotoPerfil = findViewById(R.id.foto_perfil);
        telCad = findViewById(R.id.telCad);

    }

    private void TelaPrincipal(){

        Intent intent = new Intent(FormCadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            fotoPerfil.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Salvando Imagem...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storage.getReference().child("images/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Imagem Salva", Snackbar.LENGTH_LONG).show();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Falha em salvar imagem", Toast.LENGTH_LONG).show();
                    }
                });
    }}