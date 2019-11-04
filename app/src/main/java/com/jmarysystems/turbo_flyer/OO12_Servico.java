package com.jmarysystems.turbo_flyer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import jm.Arquivo_Ou_Pasta;

import static android.content.ContentValues.TAG;

public class OO12_Servico extends Service {

    String s = null;
    String internalStorageDir = null;

    public int servico_proxima_mensagem_a_ler = 1;
    public boolean servico_executarUmaVez = false;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        s = System.getProperty("file.separator");
        internalStorageDir = this.getFilesDir().getPath();

        iniciar_servico(0);
        // START_STICKY serve para executar seu serviço até que você pare ele, é reiniciado automaticamente sempre que termina
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void iniciar_servico(int contadorXXX333) {

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo");
        Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo", "CONTATOS");
        Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo", "USUARIO_CADASTRADO");

        File criar = new File(internalStorageDir + s + "00_Externo" + s + "USUARIO_CADASTRADO");
        if (!criar.exists()) {

        } else if (criar.exists()) {

            //System.out.println("\n\n** OO12_Servico ***************************************************************");
            //System.out.println("oo_1_verificar_pastas() - OO12_Servico extends Service - internalStorageDir = " + internalStorageDir);
            //System.out.println("** OO12_Servico *****************************************************************\n\n");

            //Se a pasta: USUARIO_CADASTRADO existir segue
            oo_2_verificar_properties(contadorXXX333);
        }
    }

    private void oo_2_verificar_properties(int contadorXXX333) {

        File criar = new File(internalStorageDir + s + "00_Externo" + s + "USUARIO_CADASTRADO" + s + "USER" + ".properties");
        if (!criar.exists()) {

            // Se não existir USUÁRIO cadastrado abre a Activity para cadastrar um Usuário
            //abrir_Activity(OO2_Cadastrar_Usuario_Sistema.class);

            OO12_Time OO12_Time = new OO12_Time(this);
            OO12_Time.execute("");

        } else if (criar.exists()) {

            //System.out.println("\n*******************************************************************");
            //System.out.println("oo_2_verificar_properties()");
            //System.out.println("*******************************************************************\n");

            // Se existir USUÁRIO então abrir
            oo_3_abrir_properties(contadorXXX333);

        }
    }

    private void oo_3_abrir_properties(int contadorXXX333) {

        String arquivoASerCriado = internalStorageDir + s + "00_Externo" + s + "USUARIO_CADASTRADO" + s + "USER" + ".properties";

        String nome = "";
        String email = "";

        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(arquivoASerCriado);
            properties.loadFromXML(in);
            in.close();

            for (Enumeration elms = properties.propertyNames(); elms.hasMoreElements(); ) {

                String prop = (String) elms.nextElement();
                String value = properties.getProperty(prop);

                if (prop.equalsIgnoreCase("nome")) {

                    nome = value;
                } else if (prop.equalsIgnoreCase("email")) {

                    email = value;
                }
            }

            if (!email.equals("")) {

                //System.out.println("\n*******************************************************************");
                //System.out.println("oo_3_abrir_properties()");
                //System.out.println("*******************************************************************\n");

                //abrir_Activity(OO3_Controle_Mensagem.class, nome, email);
                //oo_4_setar_Contatos_do_Properties(nome, email);
                //Turbo_Flyer_Auto_Leitura_Online
                //Auto Auto = new Auto( this, nome, email );
                //mostrar_mensagem_parte_1(jsonDeResposta);
                /*
                Turbo_Flyer_Auto_Leitura_Online Turbo_Flyer_Auto_Leitura_Online = new Turbo_Flyer_Auto_Leitura_Online(
                        contadorXXX333, this,this, nome, email
                );
                */
                Leitura_Online_Background Leitura_Online_Background = new Leitura_Online_Background(
                        contadorXXX333, this,this, nome, email
                );
                Leitura_Online_Background.execute("");
            }

        } catch (Exception e) {
        }
    }
}

// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class OO12_Time extends AsyncTask<String, String, Void> {

    OO12_Servico OO12_Servico2;

    public OO12_Time( OO12_Servico OO12_Servico3 ) {

        OO12_Servico2 = OO12_Servico3;
    }

    //É onde acontece o processamento
    //Este método é executado em uma thread a parte,
    //ou seja ele não pode atualizar a interface gráfica,
    //por isso ele chama o método onProgressUpdate,
    // o qual é executado pela
    //UI thread.
    @Override
    protected Void doInBackground(String... params) {
        String jsonDeResposta = null;

        try{

            Thread.sleep( 30000 );

        } catch( Exception e ){}

        //Notifica o Android de que ele precisa
        //fazer atualizações na
        //tela e chama o método onProgressUpdate
        //para fazer a atualização da interface gráfica
        //passando o valor do
        //contador como parâmetro.
        publishProgress(jsonDeResposta);

        return null;
    }

    // É invocado para fazer uma atualização na
    // interface gráfica
    @Override
    protected void onProgressUpdate(String... jsonDeResposta2) {
        //System.out.println( jsonDeResposta[0] );

        String jsonDeResposta = null;
        try {

            jsonDeResposta = jsonDeResposta2[0];

            OO12_Servico2.iniciar_servico( 0 );

        } catch (Exception e) {}
    }
}




// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class Leitura_Online_Background extends AsyncTask<String, String, Void> {

    String s = System.getProperty("file.separator");
    //String internalStorageDir = System.getProperty("user.dir");
    String internalStorageDir = null;

    OO12_Servico OO12_Servico3;
    int contadorXXX333 = 0;

    Context HomeAndroid;

    String de_nome_remetente23 = "";
    String de_email_remetente23 = "";

    public Leitura_Online_Background(
            int contadorXXX2,
            OO12_Servico OO12_Servico2,
            Context HomeAndroid2,
            String de_nome_remetente,
            String de_email_remetente ) {

        internalStorageDir = HomeAndroid2.getFilesDir().getPath();

        OO12_Servico3 = OO12_Servico2;
        contadorXXX333 = contadorXXX2;

        HomeAndroid = HomeAndroid2;
        de_nome_remetente23 = de_nome_remetente;
        de_email_remetente23 = de_email_remetente;
    }

    @Override
    protected Void doInBackground(String... params) {
        String jsonDeResposta = null;

        //
        Turbo_Flyer_Auto_Leitura_Online Turbo_Flyer_Auto_Leitura_Online = new Turbo_Flyer_Auto_Leitura_Online(
                contadorXXX333,
                OO12_Servico3,
                HomeAndroid,
                de_nome_remetente23,
                de_email_remetente23
        );

        publishProgress(jsonDeResposta);

        return null;
    }

    @Override
    protected void onProgressUpdate(String... jsonDeResposta2) {
        //System.out.println( jsonDeResposta[0] );

        try {

            //
        } catch (Exception e) {}
    }
}

