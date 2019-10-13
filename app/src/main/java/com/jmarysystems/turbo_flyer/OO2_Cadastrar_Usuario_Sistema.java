package com.jmarysystems.turbo_flyer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class OO2_Cadastrar_Usuario_Sistema extends AppCompatActivity {

    private EditText mLoginView;
    private EditText mEmailView;
    private EditText mPasswordView;

    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo2__cadastrar__usuario__sistema);

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.hide();
        actionbar.setTitle("Cadastrar Usuário Sistema");

        mLoginView = (EditText) findViewById(R.id.nome_sis);
        mEmailView = (EditText) findViewById(R.id.email_sis);
        mPasswordView = (EditText) findViewById(R.id.senha_sis);

        Button bCadastrar = (Button) findViewById(R.id.bt_cad_sis);
        bCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    private void eventos2() {

        mLoginView.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String nome = null; try{ nome = mLoginView.getText().toString(); } catch( Exception e ){}

                if( nome.equalsIgnoreCase("Login") ){
                    mLoginView.setText("");
                    mLoginView.setError("Informe um Login");
                    focusView = mLoginView;
                }

                return false;
            }
        } );
    }

    private void cadastrar() {

        /*
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.layout_root));
        */

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String nome = mLoginView.getText().toString();
        String email = mEmailView.getText().toString();
        String senha = mPasswordView.getText().toString();

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
        else if (!TextUtils.isEmpty(senha) && !isPasswordValid(senha)) {

            mPasswordView.setError("Senha Inválida");
            focusView = mPasswordView;

            Toast.makeText(this, "Senha Inválida", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(email)) {

            mEmailView.setError("Informe um Email");
            focusView = mEmailView;
        } else if (!isEmailValid(email)) {

            mEmailView.setError("Email Inválido");
            focusView = mEmailView;
        }
        else{

            criar_arquivo_properties( nome, email, senha);
        }
    }

    private void criar_arquivo_properties(String nome, String email, String senha) {
        String s = System.getProperty("file.separator");
        File internalStorageDir = getFilesDir();

        String arquivoASerCriado = internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO" + s + "USER" + ".properties";

        Properties properties = new Properties();
        FileInputStream in = null;
        try{
            in = new FileInputStream( arquivoASerCriado );
            properties.loadFromXML(in);
            in.close();

            Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            propertiesX.put("nome", nome);
            propertiesX.put("email", email);
            propertiesX.put("senha", senha);

            FileOutputStream out = new FileOutputStream( arquivoASerCriado );
            propertiesX.storeToXML(out, "updated", "UTF-8");
            out.flush();
            out.close();

            abrir_Activity(OO3_Controle_Mensagem.class, nome, email  );

        } catch( Exception e ){

            try{
                Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                propertiesX.put("nome", nome);
                propertiesX.put("email", email);
                propertiesX.put("senha", senha);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                FileOutputStream out = new FileOutputStream( arquivoASerCriado );
                propertiesX.storeToXML(out, "updated", "UTF-8");
                out.flush();
                out.close();

                abrir_Activity(OO3_Controle_Mensagem.class, nome, email  );

            } catch( Exception ee ){}
        }
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
