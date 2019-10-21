package com.jmarysystems.turbo_flyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import jm.Arquivo_Ou_Pasta;

public class OO13_Resete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo13__resete);

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.hide();
        actionbar.setTitle("Resetar Todos Os Dados");

        Button bCadastrar = (Button) findViewById(R.id.bt_resete_sis);
        bCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetar();
            }
        });
    }

    private void resetar() {
        try{

            String s = System.getProperty("file.separator");
            File internalStorageDir = getFilesDir();

            Arquivo_Ou_Pasta.deletar_Todos_Arquivos_da_Pasta( new File(internalStorageDir +s+ "00_Externo") );

            abrir_Activity(OO1_Home.class);

        } catch( Exception ee ){}
    }

    private void abrir_Activity(Class Classe_a_Abrir) {
        Intent intent = new Intent( this, Classe_a_Abrir  );
        startActivity(intent);
        finish();
    }
}
