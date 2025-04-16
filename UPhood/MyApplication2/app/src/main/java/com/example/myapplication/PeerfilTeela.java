package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.telas.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeerfilTeela extends AppCompatActivity {

    private TextView nomeUser, sobrenomeUser;
    private EditText nomeUser2, sobrenomeUser2, emailUser, senhaUser, telUser;
    private Button bt_deslogar, bt_atualizar, bt_EditInfos;
    private ImageView bt_Voltar, bt_atualizarfoto;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    private CircleImageView fotoPerfil;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peerfil_teela);


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
            startActivity(new Intent(this, MainActivity.class));
        });



        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PeerfilTeela.this,PrimTela.class);
                startActivity(intent);
                finish();
            }
        });

        bt_EditInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeUser2.setEnabled(true);
                sobrenomeUser2.setEnabled(true);
                telUser.setEnabled(true);
                //emailUser.setEnabled(true);
                //senhaUser.setEnabled(true);
                nomeUser2.requestFocus();

                nomeUser2.requestFocus();

                if (nomeUser2.requestFocus()){

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(nomeUser2, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senha = FirebaseAuth.getInstance().getCurrentUser().getProviderId().toString();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if(documentSnapshot != null){
                    nomeUser.setText(documentSnapshot.getString("nome"));
                    sobrenomeUser.setText(documentSnapshot.getString("sobrenome"));
                    nomeUser2.setText(documentSnapshot.getString("nome"));
                    sobrenomeUser2.setText(documentSnapshot.getString("sobrenome"));
                    emailUser.setHint(email);
                    senhaUser.setHint(senha);
                    //fotoPerfil.setImageURI(Uri.parse(documentSnapshot.getString("fotoP")));
                    telUser.setText(documentSnapshot.getString("telefone"));
                }


                bt_atualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            db.collection("Usuarios").document(userID).update("nome", nomeUser2.getText().toString());
                            nomeUser2.setHint(documentSnapshot.getString("nome"));

                            db.collection("Usuarios").document(userID).update("sobrenome", sobrenomeUser2.getText().toString());
                            sobrenomeUser.setText(documentSnapshot.getString("sobrenome"));

                            db.collection("Usuarios").document(userID).update("telefone", telUser.getText().toString());
                            telUser.setHint(documentSnapshot.getString("telefone"));

                            db.collection("Usuarios").document(userID).update("fotoP", imageUri.toString());
                            fotoPerfil.setImageURI(Uri.parse(documentSnapshot.getString("fotoP")));


                        /*emailUser.setHint(email);
                        senhaUser.setHint(senha);*/

                    }
                });

            }


        });

    }


    private void IniciarComponentes(){

        nomeUser = findViewById(R.id.nomeExib);
        nomeUser2 = findViewById(R.id.nomeAlterar);
        sobrenomeUser2 = findViewById(R.id.sobrenomeAlterar);
        sobrenomeUser = findViewById(R.id.sobrenomeExib);
        emailUser = findViewById(R.id.textEmailUsuario);
        senhaUser = findViewById(R.id.textSenhaUsuario);
        bt_deslogar = findViewById(R.id.btnLogout);
        bt_atualizar = findViewById(R.id.btnAtualizar);
        fotoPerfil = findViewById(R.id.foto_perfil);
        bt_atualizarfoto = findViewById(R.id.bt_atualizarft);
        telUser = findViewById(R.id.telAlterar);
        bt_EditInfos = findViewById(R.id.btnEditInfos);

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
                /*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + "%");

                    }
                })*/

    }
}