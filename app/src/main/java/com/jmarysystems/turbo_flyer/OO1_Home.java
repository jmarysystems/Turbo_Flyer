package com.jmarysystems.turbo_flyer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import jm.Arquivo_Ou_Pasta;

public class OO1_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo1__home);

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.hide();
        actionbar.setTitle("Bem Vindo a JMarySystems");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verificar_pastas();
            }
        }, 2000);
    }

    private void verificar_pastas() {

        String s = System.getProperty("file.separator");
        File internalStorageDir = getFilesDir();

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "CONTATOS" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "USUARIO_CADASTRADO" );

        File criar = new File( internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO");
        if ( !criar.exists() ) {

        }
        else if ( criar.exists() ) {

            //Se a pasta: USUARIO_CADASTRADO existir segue
            verificar_properties();
        }
    }

    private void verificar_properties() {
        String s = System.getProperty("file.separator");
        File internalStorageDir = getFilesDir();

        File criar = new File( internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO" + s + "USER" + ".properties");
        if ( !criar.exists() ) {

            // Se não existir USUÁRIO cadastrado abre a Activity para cadastrar um Usuário
            abrir_Activity(OO2_Cadastrar_Usuario_Sistema.class);
        }
        else if ( criar.exists() ) {

            // Se existir USUÁRIO então abrir
            //abrir_Activity(Home.class);
            abrir_properties();

        }
    }

    private void abrir_properties() {
        String s = System.getProperty("file.separator");
        File internalStorageDir = getFilesDir();

        String arquivoASerCriado = internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO" + s + "USER" + ".properties";

        String nome = "";
        String email = "";

        Properties properties = new Properties();
        FileInputStream in = null;
        try{
            in = new FileInputStream( arquivoASerCriado );
            properties.loadFromXML(in);
            in.close();

            for(Enumeration elms = properties.propertyNames(); elms.hasMoreElements();){

                String prop = (String)elms.nextElement();
                String value = properties.getProperty(prop);

                if( prop.equalsIgnoreCase("nome") ){

                    nome = value;
                }
                else if( prop.equalsIgnoreCase("email") ){

                    email = value;
                }
            }

            if( !email.equals("") ) {

                abrir_Activity(OO3_Controle_Mensagem.class, nome, email);
            }

        } catch( Exception e ){}
    }

    private void abrir_Activity(Class Classe_a_Abrir) {
        Intent intent = new Intent( this, Classe_a_Abrir  );
        startActivity(intent);
        finish();
    }

    private void abrir_Activity(Class Classe_a_Abrir, String nome, String email) {
        Intent intent = new Intent( this, Classe_a_Abrir  );

        Bundle bundle = new Bundle();
        bundle.putString("nome", nome);
        bundle.putString("email", email);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }
}
