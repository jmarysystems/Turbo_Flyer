package com.jmarysystems.turbo_flyer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import jm.Arquivo_Ou_Pasta;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;

public class OO12_Ler_Mensagens_Auto extends Service {

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

        oo_1_verificar_pastas();
        // START_STICKY serve para executar seu serviço até que você pare ele, é reiniciado automaticamente sempre que termina
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void oo_1_verificar_pastas() {

        String s = System.getProperty("file.separator");
        File internalStorageDir = getFilesDir();

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "CONTATOS" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "USUARIO_CADASTRADO" );

        File criar = new File( internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO");
        if ( !criar.exists() ) {

        }
        else if ( criar.exists() ) {

            System.out.println("\n*******************************************************************");
            System.out.println("oo_1_verificar_pastas()");
            System.out.println("*******************************************************************\n");

            //Se a pasta: USUARIO_CADASTRADO existir segue
            oo_2_verificar_properties();
        }
    }

    private void oo_2_verificar_properties() {
        String s = System.getProperty("file.separator");
        File internalStorageDir = getFilesDir();

        File criar = new File( internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO" + s + "USER" + ".properties");
        if ( !criar.exists() ) {

            // Se não existir USUÁRIO cadastrado abre a Activity para cadastrar um Usuário
            //abrir_Activity(OO2_Cadastrar_Usuario_Sistema.class);
        }
        else if ( criar.exists() ) {

            System.out.println("\n*******************************************************************");
            System.out.println("oo_2_verificar_properties()");
            System.out.println("*******************************************************************\n");

            // Se existir USUÁRIO então abrir
            oo_3_abrir_properties();

        }
    }

    private void oo_3_abrir_properties() {
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

                System.out.println("\n*******************************************************************");
                System.out.println("oo_3_abrir_properties()");
                System.out.println("*******************************************************************\n");

                //abrir_Activity(OO3_Controle_Mensagem.class, nome, email);
                oo_4_setar_Contatos_do_Properties(nome, email);
            }

        } catch( Exception e ){}
    }

    private void oo_4_setar_Contatos_do_Properties(String de_nome_remetente, String de_email_remetente) {
        /*new Thread() {   @Override public void run() {*/
        try {
            List<Contato> listaDeContatos = new ArrayList<Contato>();

            String esp = System.getProperty("file.separator");
            java.io.File userdir = this.getFilesDir();

            String email_Usuario_Logado = de_email_remetente;

            if (!email_Usuario_Logado.equals("")) {

                String arquivo_properties = email_Usuario_Logado.trim().toUpperCase().replace("@", "_");
                String email_Logado = arquivo_properties.trim().toUpperCase().replace(".", "_");
                jm.Arquivo_Ou_Pasta.criarPasta(userdir + esp, "00_Externo");
                jm.Arquivo_Ou_Pasta.criarPasta(userdir + esp + "00_Externo", "CONTATOS");
                jm.Arquivo_Ou_Pasta.criarPasta(userdir + esp + "00_Externo" + esp + "CONTATOS", email_Logado);

                String pasta = userdir + esp + "00_Externo" + esp + "CONTATOS" + esp + email_Logado;
                java.io.File diretorio = new java.io.File(pasta);

                java.io.File lista_de_arquivos[] = null;
                try {
                    lista_de_arquivos = diretorio.listFiles();
                } catch (Exception e) {
                }
                if (lista_de_arquivos != null) {

                    for (int j = 0; j < lista_de_arquivos.length; j++) {

                        String arquivoASerCriado = userdir + esp + "00_Externo" + esp + "CONTATOS" + esp + email_Logado + esp + lista_de_arquivos[j].getName();
                        System.out.println("arquivoASerCriado: " + arquivoASerCriado);

                        Properties properties = new Properties();
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(arquivoASerCriado);
                            properties.loadFromXML(in);
                            in.close();

                            String nome = "";
                            String email = "";

                            for (Enumeration elms = properties.propertyNames(); elms.hasMoreElements(); ) {

                                String prop = (String) elms.nextElement();
                                String value = properties.getProperty(prop).trim();

                                switch (prop) {
                                    case "nome":
                                        nome = value;
                                        System.out.println(prop + " - " + value);
                                        break;
                                    case "email":
                                        email = value;
                                        System.out.println(prop + " - " + value);
                                        break;
                                }
                            }

                            //pergar_lista_de_contatos_do_properties(nome, email);
                            Contato Contato = new Contato();
                            Contato.setNome_remetente(nome);
                            Contato.setEmail_remetente(email);
                            listaDeContatos.add(Contato);

                        } catch (Exception e) {
                        }

                    }

                }
            }

            System.out.println("\n*******************************************************************");
            System.out.println("oo_4_setar_Contatos_do_Properties()");
            System.out.println("*******************************************************************\n");

            oo_5_listar_contatos( de_nome_remetente, de_email_remetente, listaDeContatos );

        } catch (Exception e) { } //} }.start();
    }

    public void oo_5_listar_contatos(
            String de_nome_remetente,
            String de_email_remetente,
            List<Contato> listaDeContatos){

        try{

            for (int j = 0; j < listaDeContatos.size(); j++) {

                try {
                    if( this.servico_executarUmaVez == false ) {

                        this.servico_executarUmaVez = true;

                        oo_o_numero_ultimo_lido(
                                de_nome_remetente,
                                de_email_remetente,
                                listaDeContatos.get(j).getNome_remetente(),
                                listaDeContatos.get(j).getEmail_remetente()
                        );
                    }
                } catch (Exception e) { e.printStackTrace(); }

                System.out.println("Contato: " + listaDeContatos.get(j).getEmail_remetente()
                        + " - servico_proxima_mensagem_a_ler: " + servico_proxima_mensagem_a_ler);

                OO12_Ler_Mensagens_Auto2 OO12_Ler_Mensagens_Auto2 = new OO12_Ler_Mensagens_Auto2(
                        servico_proxima_mensagem_a_ler,
                        this,
                        de_nome_remetente,
                        de_email_remetente,
                        listaDeContatos.get(j).getNome_remetente(),
                        listaDeContatos.get(j).getEmail_remetente()
                );
                OO12_Ler_Mensagens_Auto2.execute("");
            }

        } catch( Exception e ){ e.printStackTrace(); }

////////////////////////////////////////////////////////////////////////////////////////////////////
        try{

            System.out.println("\n*******************************************************************");
            System.out.println("oo_5_listar_contatos()");
            System.out.println("*******************************************************************\n");

            //Thread.sleep(1000);
            //oo_1_verificar_pastas();

        } catch( Exception e ){ e.printStackTrace(); }
////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void oo_o_numero_ultimo_lido(
            String de_nome_remetente,
            String de_email_remetente,
            String para_nome_destinatario,
            String para_email_destinatario ) {

        String s = System.getProperty("file.separator");
        File internalStorageDir = this.getFilesDir();

        String arquivo_properties2 = de_email_remetente.trim().toUpperCase().replace("@", "_");
        String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

        String nome_email_destinatario = para_email_destinatario.trim().toUpperCase().replace("@", "_");
        String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "LEITURAS_M" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "LEITURAS_M", email_REMETENTE );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "LEITURAS_M" + s + email_REMETENTE, email_DESTINATARIO );

        String properties001 = internalStorageDir + s + "00_Externo" + s + "LEITURAS_M" + s + email_REMETENTE + s + email_DESTINATARIO + ".properties";

        String numero_ultimo_lido = "";

        Properties properties = new Properties();
        FileInputStream in = null;
        try{
            in = new FileInputStream( properties001 );
            properties.loadFromXML(in);
            in.close();

            for(Enumeration elms = properties.propertyNames(); elms.hasMoreElements();){

                String prop = (String)elms.nextElement();
                String value = properties.getProperty(prop);

                if( prop.equalsIgnoreCase("ultimo") ){

                    numero_ultimo_lido = value;

                    try{
                        int v = Integer.parseInt( numero_ultimo_lido.trim() );
                        servico_proxima_mensagem_a_ler = v;
                    } catch( Exception e ){}
                }
            }

        } catch( Exception e ){}
    }

}

// AsyncTask<Parâmetros,Progresso,Resultado>
class automato extends AsyncTask<String, String, Void> {

    Context contextX;
    int proxima_mensagem_a_ler = 1;

    public automato(
            int proxima_mensagem_a_ler2,
            Context contextX2) {

        proxima_mensagem_a_ler = proxima_mensagem_a_ler2;
        contextX = contextX2;
    }

    @Override
    protected Void doInBackground(String... params) {
        publishProgress(params[0]);
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        try{
////////////////////////////////////////////////////////////////////////////////////////////////////
            try{

                System.out.println("\n*******************************************************************");
                System.out.println("Reiniciando");
                System.out.println("*******************************************************************\n");

                oo_1_verificar_pastas();

            } catch( Exception e ){ e.printStackTrace(); }
////////////////////////////////////////////////////////////////////////////////////////////////////
        } catch( Exception e ){ e.printStackTrace(); }
    }

    public void oo_1_verificar_pastas() {

        String s = System.getProperty("file.separator");
        File internalStorageDir = contextX.getFilesDir();

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "CONTATOS" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "USUARIO_CADASTRADO" );

        File criar = new File( internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO");
        if ( !criar.exists() ) {

        }
        else if ( criar.exists() ) {

            System.out.println("\n*******************************************************************");
            System.out.println("oo_1_verificar_pastas()");
            System.out.println("*******************************************************************\n");

            //Se a pasta: USUARIO_CADASTRADO existir segue
            oo_2_verificar_properties();
        }
    }

    private void oo_2_verificar_properties() {
        String s = System.getProperty("file.separator");
        File internalStorageDir = contextX.getFilesDir();

        File criar = new File( internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO" + s + "USER" + ".properties");
        if ( !criar.exists() ) {

            // Se não existir USUÁRIO cadastrado abre a Activity para cadastrar um Usuário
            //abrir_Activity(OO2_Cadastrar_Usuario_Sistema.class);
        }
        else if ( criar.exists() ) {

            System.out.println("\n*******************************************************************");
            System.out.println("oo_2_verificar_properties()");
            System.out.println("*******************************************************************\n");

            // Se existir USUÁRIO então abrir
            oo_3_abrir_properties();

        }
    }

    private void oo_3_abrir_properties() {
        String s = System.getProperty("file.separator");
        File internalStorageDir = contextX.getFilesDir();

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

                System.out.println("\n*******************************************************************");
                System.out.println("oo_3_abrir_properties()");
                System.out.println("*******************************************************************\n");

                //abrir_Activity(OO3_Controle_Mensagem.class, nome, email);
                oo_4_setar_Contatos_do_Properties(nome, email);
            }

        } catch( Exception e ){}
    }

    private void oo_4_setar_Contatos_do_Properties(String de_nome_remetente, String de_email_remetente) {
        /*new Thread() {   @Override public void run() {*/
        try {
            List<Contato> listaDeContatos = new ArrayList<Contato>();

            String esp = System.getProperty("file.separator");
            java.io.File userdir = contextX.getFilesDir();

            String email_Usuario_Logado = de_email_remetente;

            if (!email_Usuario_Logado.equals("")) {

                String arquivo_properties = email_Usuario_Logado.trim().toUpperCase().replace("@", "_");
                String email_Logado = arquivo_properties.trim().toUpperCase().replace(".", "_");
                jm.Arquivo_Ou_Pasta.criarPasta(userdir + esp, "00_Externo");
                jm.Arquivo_Ou_Pasta.criarPasta(userdir + esp + "00_Externo", "CONTATOS");
                jm.Arquivo_Ou_Pasta.criarPasta(userdir + esp + "00_Externo" + esp + "CONTATOS", email_Logado);

                String pasta = userdir + esp + "00_Externo" + esp + "CONTATOS" + esp + email_Logado;
                java.io.File diretorio = new java.io.File(pasta);

                java.io.File lista_de_arquivos[] = null;
                try {
                    lista_de_arquivos = diretorio.listFiles();
                } catch (Exception e) {
                }
                if (lista_de_arquivos != null) {

                    for (int j = 0; j < lista_de_arquivos.length; j++) {

                        String arquivoASerCriado = userdir + esp + "00_Externo" + esp + "CONTATOS" + esp + email_Logado + esp + lista_de_arquivos[j].getName();
                        System.out.println("arquivoASerCriado: " + arquivoASerCriado);

                        Properties properties = new Properties();
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(arquivoASerCriado);
                            properties.loadFromXML(in);
                            in.close();

                            String nome = "";
                            String email = "";

                            for (Enumeration elms = properties.propertyNames(); elms.hasMoreElements(); ) {

                                String prop = (String) elms.nextElement();
                                String value = properties.getProperty(prop).trim();

                                switch (prop) {
                                    case "nome":
                                        nome = value;
                                        System.out.println(prop + " - " + value);
                                        break;
                                    case "email":
                                        email = value;
                                        System.out.println(prop + " - " + value);
                                        break;
                                }
                            }

                            //pergar_lista_de_contatos_do_properties(nome, email);
                            Contato Contato = new Contato();
                            Contato.setNome_remetente(nome);
                            Contato.setEmail_remetente(email);
                            listaDeContatos.add(Contato);

                        } catch (Exception e) {
                        }

                    }

                }
            }

            System.out.println("\n*******************************************************************");
            System.out.println("oo_4_setar_Contatos_do_Properties()");
            System.out.println("*******************************************************************\n");

            oo_5_listar_contatos( de_nome_remetente, de_email_remetente, listaDeContatos );

        } catch (Exception e) { } //} }.start();
    }

    public void oo_5_listar_contatos(
            String de_nome_remetente,
            String de_email_remetente,
            List<Contato> listaDeContatos){

        try{

            for (int j = 0; j < listaDeContatos.size(); j++) {

                System.out.println("Contato: " + listaDeContatos.get(j).getEmail_remetente()
                        + " - servico_proxima_mensagem_a_ler: " + proxima_mensagem_a_ler);

                OO12_Ler_Mensagens_Auto2 OO12_Ler_Mensagens_Auto2 = new OO12_Ler_Mensagens_Auto2(
                        proxima_mensagem_a_ler,
                        contextX,
                        de_nome_remetente,
                        de_email_remetente,
                        listaDeContatos.get(j).getNome_remetente(),
                        listaDeContatos.get(j).getEmail_remetente()
                );
                OO12_Ler_Mensagens_Auto2.execute("");
            }

        } catch( Exception e ){ e.printStackTrace(); }

////////////////////////////////////////////////////////////////////////////////////////////////////
        try{

            System.out.println("\n*******************************************************************");
            System.out.println("oo_5_listar_contatos()");
            System.out.println("*******************************************************************\n");

            //Thread.sleep(1000);
            //oo_1_verificar_pastas();

        } catch( Exception e ){ e.printStackTrace(); }
////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}

// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class OO12_Ler_Mensagens_Auto2 extends AsyncTask<String, String, Void> {

    Context contextX;
    String de_nome_remetente;
    String de_email_remetente;
    String para_nome_destinatario;
    String para_email_destinatario;

    int proxima_mensagem_a_ler = 1;

    public OO12_Ler_Mensagens_Auto2(
            int proxima_mensagem_a_ler2,
            Context contextX2,
            String de_nome_remetente2,
            String de_email_remetente2,
            String para_nome_destinatario2,
            String para_email_destinatario2) {

        proxima_mensagem_a_ler = proxima_mensagem_a_ler2;
        contextX = contextX2;
        de_nome_remetente = de_nome_remetente2;
        de_email_remetente = de_email_remetente2;
        para_nome_destinatario = para_nome_destinatario2;
        para_email_destinatario = para_email_destinatario2;

    }

    //É onde acontece o processamento
    //Este método é executado em uma thread a parte,
    //ou seja ele não pode atualizar a interface gráfica,
    //por isso ele chama o método onProgressUpdate,
    // o qual é executado pela
    //UI thread.
    @Override
    protected Void doInBackground(String... params) {
        String endereco = params[0];

        String jsonDeResposta = "";

        HttpURLConnection connection = null;
        try {

            String id_0_formulario = "1By3w9XL3XDO_Dzi5VomurGnqoMS-_zW552C4-B2SJKo";
            String id_1_Espaco = "A:F";
            try{
                /*
                String x = String.valueOf( proxima_mensagem_a_ler ).trim();
                id_1_Espaco = "A"+x+":F";

                System.out.println("\n*******************************************************************");
                System.out.println("id_1_Espaco - " + id_1_Espaco);
                System.out.println("*******************************************************************\n");
                */
            } catch( Exception e ){}
            String key = "AIzaSyBwiMCywJRFQHuuksWdhqwjOrR5mDaWJYs"; //AIzaSyBxe8Sl3WxpmhMenNBeMeBjKHlVjBahsr8
            String GET_URL = "https://sheets.googleapis.com/v4/spreadsheets/" + id_0_formulario + "/values/" + id_1_Espaco + "?key=" + key;

            try{

                System.out.println("\n*******************************************************************");
                System.out.println("GET_URL - " + GET_URL);
                System.out.println("*******************************************************************\n");
            } catch( Exception e ){}

            URL url = new URL(GET_URL);
            URLConnection conn = url.openConnection();

            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();

            String linha = br.readLine();

            while (linha != null) {

                //System.out.println(linha);
                sb.append(linha);
                linha = br.readLine();

            }

            jsonDeResposta = sb.toString();

            try{

                System.out.println("\n*******************************************************************");
                System.out.println("jsonDeResposta - " + jsonDeResposta);
                System.out.println("*******************************************************************\n");
            } catch( Exception e ){}

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.disconnect();
            } catch (Exception e) {
            }
        }


        //Notifica o Android de que ele precisa
        //fazer atualizações na
        //tela e chama o método onProgressUpdate
        //para fazer a atualização da interface gráfica
        //passando o valor do
        //contador como parâmetro.
        publishProgress(jsonDeResposta);

        mostrar_mensagem_parte_1(jsonDeResposta);

        //}while(true);
        return null;
    }

    // É invocado para fazer uma atualização na
    // interface gráfica
    @Override
    protected void onProgressUpdate(String... values) {

        try{
/*
            System.out.println("\n*******************************************************************");
            System.out.println("Conteúdo: " + values[0]);
            System.out.println("*******************************************************************\n");
*/
        } catch( Exception e ){ e.printStackTrace(); }
    }

    private void mostrar_mensagem_parte_1(String json) { try {

        try{

            System.out.println("\n*******************************************************************");
            System.out.println("Conteúdo: " + json);
            System.out.println("*******************************************************************\n");

        } catch( Exception e ){ e.printStackTrace(); }

        String separacao_1[] = json.split("],");

        try{
            int x = separacao_1.length - 1;

            System.out.println("\n*******************************************************************");
            System.out.println("XXXX - i_R: " + x + " - proxima_mensagem_a_ler: " + proxima_mensagem_a_ler );
            System.out.println("*******************************************************************\n");

        } catch( Exception e ){ e.printStackTrace(); }

        for( int i = 1; i < separacao_1.length; i++ ){

            if( i >= proxima_mensagem_a_ler ) {

                mostrar_mensagem_parte_2(separacao_1[i], i);
                //System.out.println( separacao_1[i] );
            }
        }

        try{

            Thread.sleep(5000);

            automato automato = new automato( proxima_mensagem_a_ler, contextX);
            automato.execute("");

        } catch( Exception e ){ e.printStackTrace(); }

    } catch( Exception e ){} }

    private void mostrar_mensagem_parte_2( String json, int i_R ) { try {
        //System.out.println( json );
        try{

            //System.out.println("\n*******************************************************************");
            //System.out.println("mostrar_mensagem_parte_2 : " + json);
            //System.out.println("*******************************************************************\n");

        } catch( Exception e ){ e.printStackTrace(); }

        String separacao_1[] = json.split(",");

        String data = separacao_1[0].replace("\"", "").replace("[", "");
        String de_email = separacao_1[1].replace("\"", "").toUpperCase();
        String para_email = separacao_1[2].replace("\"", "").toUpperCase();
        String mensagem2 = separacao_1[3].replaceAll("]", "");
        String mensagem3 = mensagem2.replace("}", "");

        String str[] = null; try{ str = mensagem3.split("\""); } catch( Exception e ){}

        if( str != null ){

            String mensagem_a_mostrar = mensagem3.replaceAll("\"", "").trim();

            try{

                //System.out.println("\n*******************************************************************");
                //System.out.println("i_R: " + i_R + " - proxima_mensagem_a_ler: " + proxima_mensagem_a_ler + " - de on: " + de_email.trim() + " - para on: " + para_email.trim() + " - mensagem on: "+mensagem_a_mostrar.trim());
                //System.out.println("*******************************************************************\n");

                //System.out.println("\n*******************************************************************");
                //System.out.println("i_R: " + i_R + " - proxima_mensagem_a_ler: " + proxima_mensagem_a_ler + " - de off: " + de_email_remetente.toUpperCase().trim() + " - para off: " + para_email_destinatario.toUpperCase().trim() + " - mensagem off: "+mensagem_a_mostrar.trim());
                //System.out.println("*******************************************************************\n");

                System.out.println("\n*******************************************************************");
                System.out.println( " de_email " + de_email.toUpperCase().trim() + " - para_email_destinatario: " + para_email_destinatario.toUpperCase().trim() );
                System.out.println("*******************************************************************\n");


            } catch( Exception e ){ e.printStackTrace(); }

            /*
            if( de_email.toUpperCase().trim().equalsIgnoreCase( para_email_destinatario.toUpperCase().trim() )  ){

                try {

                    System.out.println("\n*******************************************************************");
                    System.out.println( " OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK OK " );
                    System.out.println("*******************************************************************\n");


                    notificacao("Nova Mensagem de: "+  this.para_nome_destinatario, mensagem_a_mostrar );

                } catch( Exception e ){ e.printStackTrace(); }
            }
            */

            if( de_email.trim().equalsIgnoreCase( de_email_remetente.toUpperCase().trim() )  ){

                if( i_R >= proxima_mensagem_a_ler ){

                    System.out.println("\n**if( de_email.trim().equalsIgnoreCase( de_email_remetente******************");
                    System.out.println("de_email: " + de_email + " - de_email_remetente: " + de_email_remetente.toUpperCase() );
                    System.out.println("**if( de_email.trim().equalsIgnoreCase( de_email_remetente********\n");

                    try {

                        int x = proxima_mensagem_a_ler += 1;
                        int anterior = x;

                        proxima_mensagem_a_ler = anterior;

                        try{

                            System.out.println("\n*******************************************************************");
                            System.out.println("mensagem_a_mostrar 1 : " + mensagem_a_mostrar + " - proxima_mensagem_a_ler - " + proxima_mensagem_a_ler);
                            System.out.println("*******************************************************************\n");

                        } catch( Exception e ){ e.printStackTrace(); }

                    } catch( Exception e ){ }
                }
            }
            else if( de_email.trim().equalsIgnoreCase( para_email_destinatario.toUpperCase().trim() )  ){

                if( i_R >= proxima_mensagem_a_ler ){

                    System.out.println("\n**else if( de_email.trim().equalsIgnoreCase(*****************");
                    System.out.println("de_email: " + de_email + " - para_email_destinatario: " + para_email_destinatario.toUpperCase() );
                    System.out.println("**else if( de_email.trim().equalsIgnoreCase( ********************\n");

                    try {
                        notificacao(
                                "Nova Mensagem de: "+
                                        this.para_nome_destinatario,
                                mensagem_a_mostrar
                        );

                    } catch( Exception e ){ e.printStackTrace(); }

                    try {
                        int x = proxima_mensagem_a_ler += 1;
                        int anterior = x;

                        proxima_mensagem_a_ler = anterior;

                        try{

                            System.out.println("\n*******************************************************************");
                            System.out.println("mensagem_a_mostrar 2: " + mensagem_a_mostrar + " - proxima_mensagem_a_ler - " + proxima_mensagem_a_ler);
                            System.out.println("*******************************************************************\n");

                        } catch( Exception e ){ e.printStackTrace(); }

                    } catch( Exception e ){ }
                }
            }
        }
    } catch( Exception e ){ } }

    private void notificacao(String titulo, String mensagem) { try {

        int id = (int) (System.currentTimeMillis() / 1000);

        Intent intent = new Intent(contextX, contextX.getClass());

        // PendingIntent para executar a Activity se o usuário selecionar a notificação
        PendingIntent pendingIntent = PendingIntent.getActivity(
                contextX,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(contextX)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(titulo)
                .setContentText(mensagem)
                //.setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mensagem));

        NotificationManager notifyManager = (NotificationManager) contextX.getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(id, builder.build());

    } catch( Exception e ){} }

}


class Contato {

    private String nome_remetente = "";
    private String email_remetente = "";

    public String getNome_remetente() {
        return nome_remetente;
    }

    public void setNome_remetente(String nome_remetente) {
        this.nome_remetente = nome_remetente;
    }

    public String getEmail_remetente() {
        return email_remetente;
    }

    public void setEmail_remetente(String email_remetente) {
        this.email_remetente = email_remetente;
    }
}

