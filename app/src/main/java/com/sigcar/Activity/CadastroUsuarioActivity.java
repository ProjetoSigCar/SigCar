package com.sigcar.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sigcar.Classes.Usuario;
import com.sigcar.DAO.ConfiguracaoFirebase;
import com.sigcar.R;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private BootstrapEditText email;
    private BootstrapEditText senha1;
    private BootstrapEditText senha2;
    private BootstrapEditText nome;
    private RadioButton rbAdmin;
    private RadioButton rbAtend;
    private BootstrapButton btnCadastrar;
    private BootstrapButton btnCancelar;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);


        email = (BootstrapEditText) findViewById(R.id.edtCadEmail);
        senha1 = (BootstrapEditText) findViewById(R.id.edtCadSenha1);
        senha2 = (BootstrapEditText) findViewById(R.id.edtCadSenha2);
        nome = (BootstrapEditText) findViewById(R.id.edtCadNome);
        rbAdmin = (RadioButton) findViewById(R.id.rbAdmin);
        rbAtend = (RadioButton) findViewById(R.id.rbAtend);
        btnCadastrar = (BootstrapButton) findViewById(R.id.btnCadastrar);
        btnCancelar = (BootstrapButton) findViewById(R.id.btnCancelar);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (senha1.getText().toString().equals(senha2.getText().toString())) {

                    usuario = new Usuario();

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha1.getText().toString());
                    usuario.setNome(nome.getText().toString());

                    if (rbAdmin.isChecked()) {

                        usuario.setTipoUsuario("Adminstrador");

                    } else if (rbAtend.isChecked()) {

                        usuario.setTipoUsuario("Atendente");
                    }

                    cadastrarUsuario();

                } else {

                    Toast.makeText(CadastroUsuarioActivity.this, "As senhas estão diferentes", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(

                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    insereUsuario(usuario);


                } else {

                    String erroExcecao = "";

                    try {

                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma Senha mais forte contendo no mínimo 8 caracteres que contenha letras e números";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é invalido digite o novo e-mail";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e=mail já está cadastrado";

                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar cadastro";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro" + erroExcecao, Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    private boolean insereUsuario(Usuario usuario) {

        try {

            reference = ConfiguracaoFirebase.getFirebase().child("usuarios");
            reference.push().setValue(usuario);
            Toast.makeText(CadastroUsuarioActivity.this, "Usuario Cadastado com Sucesso", Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao gravar usuario", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;


        }

    }
}
