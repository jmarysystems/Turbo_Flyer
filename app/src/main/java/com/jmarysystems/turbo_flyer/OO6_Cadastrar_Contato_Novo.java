package com.jmarysystems.turbo_flyer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class OO6_Cadastrar_Contato_Novo extends AppCompatActivity {

    String nome_user_sis;
    String email_user_sis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo6__cadastrar__contato__novo);

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.hide();
        actionbar.setTitle("Cadastrar Novo Contato");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nome_user_sis = bundle.getString("nome");
        email_user_sis = bundle.getString("email");

        Button mEmailSignInButton = (Button) findViewById(R.id.bt_cad_contato);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    private EditText mLoginView;
    private EditText mEmailView;
    private void cadastrar() {

        View focusView = null;

        mLoginView    = (EditText) findViewById(R.id.nome_contato);
        mEmailView    = (EditText) findViewById(R.id.email_contato);

        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String nome = mLoginView.getText().toString();
        String email = mEmailView.getText().toString();

        if (TextUtils.isEmpty(nome)) {

            mLoginView.setError("Informe um Login");
            focusView = mLoginView;

            /*
            Toast toast = Toast.makeText(this, "Informe um Login", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            */

            /*
            AlertDialog.Builder builder;
            AlertDialog alertDialog;
            builder = new AlertDialog.Builder(mContext);
            builder.setView(layout);
            alertDialog = builder.create();
            */
        }
        else if (TextUtils.isEmpty(email)) {

            mEmailView.setError("Informe um Email");
            focusView = mEmailView;
        } else if (!isEmailValid(email)) {

            mEmailView.setError("Email Inválido");
            focusView = mEmailView;
        }
        else{

            criar_arquivo_properties( nome, email);
        }
    }

    private void criar_arquivo_properties( String nome, String email){
        /*new Thread() {   @Override public void run() {*/ try {

            String esp = System.getProperty("file.separator");
            File userdir = getFilesDir();

            String email_Usuario_Logado = email_user_sis;

            if( !email_Usuario_Logado.equals("") ){

                String arquivo_properties = email_Usuario_Logado.trim().toUpperCase().replace("@", "_");
                String email_Logado = arquivo_properties.trim().toUpperCase().replace(".", "_");
                jm.Arquivo_Ou_Pasta.criarPasta( userdir + esp, "00_Externo" );
                jm.Arquivo_Ou_Pasta.criarPasta( userdir + esp + "00_Externo", "CONTATOS" );
                jm.Arquivo_Ou_Pasta.criarPasta( userdir + esp + "00_Externo" + esp + "CONTATOS", email_Logado );

                String arquivo_properties22 = email.trim().toUpperCase().replace("@", "_");
                String email_contato = arquivo_properties22.trim().toUpperCase().replace(".", "_");
                String arquivoASerCriado = userdir + esp + "00_Externo" + esp + "CONTATOS" + esp + email_Logado + esp + email_contato + ".properties";

                Properties properties = new Properties();
                FileInputStream in = null;
                try{
                    in = new FileInputStream( arquivoASerCriado );
                    properties.loadFromXML(in);
                    in.close();

                    jm.Arquivo_Ou_Pasta.deletar( new File( arquivoASerCriado ) );

                    Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    propertiesX.put("nome", nome);
                    propertiesX.put("email", email);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    FileOutputStream out = new FileOutputStream( arquivoASerCriado );
                    propertiesX.storeToXML(out, "updated", "UTF-8");
                    out.flush();
                    out.close();

                } catch( Exception e ){

                    Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    propertiesX.put("nome", nome);
                    propertiesX.put("email", email);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    FileOutputStream out = new FileOutputStream( arquivoASerCriado );
                    propertiesX.storeToXML(out, "updated", "UTF-8");
                    out.flush();
                    out.close();

                    abrir_Activity(OO3_Controle_Mensagem.class, nome_user_sis, email_user_sis  );
                }

                abrir_Activity(OO3_Controle_Mensagem.class, nome_user_sis, email_user_sis  );
            }
        } catch( Exception e ){  } //} }.start();
    }

    private void abrir_Activity(Class Classe_a_Abrir, String nome22, String email22) {
        Intent intent = new Intent( this, Classe_a_Abrir  );

        Bundle bundle = new Bundle();
        bundle.putString("nome", nome22);
        bundle.putString("email", email22);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    /*
    private void abrir_Activity(Class Classe_a_Abrir, String nome, String email) {
        Intent intent = new Intent( this, Classe_a_Abrir  );
        startActivity(intent);
        finish();
    }
    */

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
