package com.sigcar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sigcar.R;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


//
//        tipoUsuario = (TextView) findViewById(R.id.txtTipoUsuario);
//
        autenticacao = FirebaseAuth.getInstance();
//
//        referenciaFirebase = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_usuario){

            abrirTelaCadastroUsuario();

        }else if (id == R.id.action_sair_admin){

            deslogarUsuario();

        }

        return  super.onOptionsItemSelected(item);
    }

    private void abrirTelaCadastroUsuario() {
        Intent intent = new Intent(PrincipalActivity.this, CadastroUsuarioActivity.class);

        startActivity(intent);
    }

    private void deslogarUsuario() {

        autenticacao.signOut();

        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}

