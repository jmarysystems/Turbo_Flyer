/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmarysystems.turbo_flyer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import jm.Arquivo_Ou_Pasta;
import jm.Contato;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 *
 * @author JMarySystems
 */
public class Turbo_Flyer_Auto_Leitura_Online {

    String s = System.getProperty("file.separator");
    //String internalStorageDir = System.getProperty("user.dir");
    String internalStorageDir = null;

    OO12_Servico OO12_Servico3;
    int contadorXXX333 = 0;

    Context HomeAndroid;

    String de_nome_remetente23 = "";
    String de_email_remetente23 = "";
    
    public Turbo_Flyer_Auto_Leitura_Online(
            int contadorXXX2,
            OO12_Servico OO12_Servico2,
            Context HomeAndroid2,
            String de_nome_remetente,
            String de_email_remetente) {

        internalStorageDir = HomeAndroid2.getFilesDir().getPath();

        OO12_Servico3 = OO12_Servico2;
        contadorXXX333 = contadorXXX2;

        HomeAndroid = HomeAndroid2;
        de_nome_remetente23 = de_nome_remetente;
        de_email_remetente23 = de_email_remetente;
                
        try {                  
            oo_alterar_numero_ultimo_lido(
                    de_email_remetente23,
                1
            );
        } catch( Exception e ){}

        //System.out.println("\n** Início - Turbo_Flyer_Auto_Leitura_Online - *****************************************************************");
        //System.out.println("\n de_nome_remetente23: " + de_nome_remetente23 );
        //System.out.println("\n de_email_remetente23: " + de_email_remetente23 );
        //System.out.println("\n** Fim - Turbo_Flyer_Auto_Leitura_Online - *****************************************************************");

        oo_0_criar_usuario_logado(contadorXXX333);
    }

    int servico_proxima_mensagem_a_ler = 0;
    private void oo_0_criar_usuario_logado(int contadorXXX) {

        contadorXXX333 = contadorXXX;
        
        new Thread() {   @Override public void run() { try { 
                                    
            String nome_Usuario_Logado = ""; 
            try{ nome_Usuario_Logado = de_nome_remetente23; }catch( Exception e ){}
            
            String email_Usuario_Logado = ""; 
            try{ email_Usuario_Logado = de_email_remetente23; }catch( Exception e ){}
            
            try{
                String email_Usuario_Logado1 = de_email_remetente23;
                String email_Usuario_Logado2 = email_Usuario_Logado1.trim().toUpperCase().replace("@", "_");
                String email_Usuario_Logado3 = email_Usuario_Logado2.trim().toUpperCase().replace(".", "_");
                email_Usuario_Logado = email_Usuario_Logado3;
            }catch( Exception e ){}
            
            try {
            
                if( contadorXXX333 > 10 ){
                    contadorXXX333 = 0;
                    
                    oo_alterar_numero_ultimo_lido( email_Usuario_Logado, 0 );
                }
            } catch (Exception e) { e.printStackTrace(); }
            
            try {
            
                oo_get_numero_ultimo_lido(
                    nome_Usuario_Logado,
                    email_Usuario_Logado
                );
            } catch (Exception e) { e.printStackTrace(); }

            //System.out.println("\n** internalStorageDir *****************************************************************");
            //System.out.println("internalStorageDir: " + internalStorageDir );
            //System.out.println("\n** internalStorageDir *****************************************************************");

            //System.out.println("\n\n\n");
            //System.out.println("\nINÍCIO @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            //System.out.println("\n** oo_0_Turbo_Flyer_Auto_Leitura_Online *****************************************************************");
            //System.out.println("servico_proxima_mensagem_a_ler - " + servico_proxima_mensagem_a_ler);
            //System.out.println("Usuário Logado - " + email_Usuario_Logado);
            //System.out.println("** oo_0_Turbo_Flyer_Auto_Leitura_Online *****************************************************************\n");
        
            try{ oo_1_setar_Contatos_do_Properties(nome_Usuario_Logado, email_Usuario_Logado); }catch( Exception e ){}
        
        } catch( Exception e ){  } } }.start();        
    }
    
    public void oo_get_numero_ultimo_lido( 
            String de_nome_remetente,
            String de_email_remetente) {
        
        String arquivo_properties2 = de_email_remetente.trim().toUpperCase().replace("@", "_");
        String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "LEITURAS_M" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "LEITURAS_M", email_REMETENTE );
        
        String arquivoASerCriado = internalStorageDir + s + "00_Externo" + s + "LEITURAS_M" + s + email_REMETENTE + s + "ULTIMO_NUMERO_LIDO" + ".properties";
        
        File f = new File(arquivoASerCriado);
        if( f.exists() == true ){
        
            Properties properties = new Properties();
            FileInputStream in = null;
            try{
                in = new FileInputStream( arquivoASerCriado );
                properties.loadFromXML(in);
                in.close();

                for(Enumeration elms = properties.propertyNames(); elms.hasMoreElements();){
 
                    String prop = (String)elms.nextElement();
                    String value = properties.getProperty(prop);

                    if( prop.equalsIgnoreCase("ultimo_numero_lido") ){

                         String numero_ultimo_lido = value;

                        try{
                            int v = Integer.parseInt( numero_ultimo_lido.trim() );
                            servico_proxima_mensagem_a_ler = v;
                            
                            //System.out.println("\n** oo_get_numero_ultimo_lido *****************************************************************");
                            //System.out.println("servico_proxima_mensagem_a_ler: " + servico_proxima_mensagem_a_ler );
                            //System.out.println("\n** oo_get_numero_ultimo_lido *****************************************************************");

                        } catch( Exception e ){}
                    }
                }
            } catch( Exception e ){
        
                try{
                    Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    propertiesX.put("ultimo_numero_lido", "0");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    FileOutputStream out = new FileOutputStream( arquivoASerCriado );
                    propertiesX.storeToXML(out, "updated", "UTF-8");
                    out.flush();
                    out.close();

                } catch( Exception ee ){}
            }
        }
        else{
            
            try{
                Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                propertiesX.put("ultimo_numero_lido", "0");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                FileOutputStream out = new FileOutputStream( arquivoASerCriado );
                propertiesX.storeToXML(out, "updated", "UTF-8");
                out.flush();
                out.close();

            } catch( Exception ee ){}
        }        
    }
    
    public void oo_alterar_numero_ultimo_lido( 
            String de_email_remetente,
            int proxima_mensagem_a_ler) {

        String arquivo_properties2 = de_email_remetente.trim().toUpperCase().replace("@", "_");
        String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "LEITURAS_M" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "LEITURAS_M", email_REMETENTE );
        
        String arquivoASerCriado = internalStorageDir + s + "00_Externo" + s + "LEITURAS_M" + s + email_REMETENTE + s + "ULTIMO_NUMERO_LIDO" + ".properties";

            try{
                
                Arquivo_Ou_Pasta.deletar( new File( arquivoASerCriado ) );  
                
                try{
                    Properties propertiesX = new Properties();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    propertiesX.put("ultimo_numero_lido", String.valueOf( proxima_mensagem_a_ler ) );
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    FileOutputStream out = new FileOutputStream( arquivoASerCriado );
                    propertiesX.storeToXML(out, "updated", "UTF-8");
                    out.flush();
                    out.close();
                    
                    //System.out.println("\n** oo_alterar_numero_ultimo_lido *****************************************************************");
                    //System.out.println("servico_proxima_mensagem_a_ler: " + proxima_mensagem_a_ler );
                    //System.out.println("\n** oo_alterar_numero_ultimo_lido *****************************************************************");

                    
                } catch( Exception ee ){}
                
                servico_proxima_mensagem_a_ler = proxima_mensagem_a_ler;

            } catch( Exception e ){}       
    }
    
    private void oo_1_setar_Contatos_do_Properties(String de_nome_remetente, String email_Usuario_Logado) {
        /*new Thread() {   @Override public void run() {*/
        List<Contato> listaDeContatos = null;
        try {
            listaDeContatos = new ArrayList<Contato>();

            if (!email_Usuario_Logado.equals("")) {

                Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo");
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "CONTATOS");
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "CONTATOS", email_Usuario_Logado);

                String pasta = internalStorageDir + s + "00_Externo" + s + "CONTATOS" + s + email_Usuario_Logado;
                File diretorio = new File(pasta);

                File lista_de_arquivos[] = null;
                try {
                    lista_de_arquivos = diretorio.listFiles();
                } catch (Exception e) {
                }
                if (lista_de_arquivos != null) {

                    for (int j = 0; j < lista_de_arquivos.length; j++) {

                        String arquivoASerCriado = internalStorageDir + s + "00_Externo" + s + "CONTATOS" + s + email_Usuario_Logado + s + lista_de_arquivos[j].getName();

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
                                        //System.out.println(prop + " - " + value);
                                        break;
                                    case "email":
                                        email = value;
                                        //System.out.println(prop + " - " + value);
                                        break;
                                }
                            }

                            //pergar_lista_de_contatos_do_properties(nome, email);
                            Contato Contato = new Contato();
                            Contato.setNome_remetente(nome);
                            Contato.setEmail_remetente(email);
                            listaDeContatos.add(Contato);
                            
                            //System.out.println("\n**CONTATO ADICIONADO*****************************************************************");
                            //System.out.println("Nome: " + nome + " - Email: " + email);
                            //System.out.println("\n**CONTATO ADICIONADO*****************************************************************");

                        } catch (Exception e) {
                        }
                    }
                }
            }                                    
        } catch (Exception e) { } //} }.start();
        
        try{

            OO12_Ler_Mensagens_Auto2 OO12_Ler_Mensagens_Auto2 = new OO12_Ler_Mensagens_Auto2(
                    this,
                    HomeAndroid,
                    OO12_Servico3,
                    contadorXXX333,
                    servico_proxima_mensagem_a_ler,
                    de_nome_remetente,
                    email_Usuario_Logado,
                    listaDeContatos
            );
            OO12_Ler_Mensagens_Auto2.execute("01");
        } catch( Exception e ){ e.printStackTrace(); } 
    }
}


// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class OO12_Ler_Mensagens_Auto2 extends AsyncTask<String, String, Void> {

    String s = System.getProperty("file.separator");
    //String internalStorageDir = System.getProperty("user.dir");
    String internalStorageDir = null;

    Turbo_Flyer_Auto_Leitura_Online Turbo_Flyer_Auto_Leitura_Online3;
    Context HomeAndroid;

    int contadorXXX = 0;

    OO12_Servico OO12_Servico3;
    //

    int proxima_mensagem_a_ler = 0;
    String de_nome_remetente = "";
    String email_Usuario_Logado = "";
    List<Contato> listaDeContatos = null;

    public OO12_Ler_Mensagens_Auto2(
            Turbo_Flyer_Auto_Leitura_Online Turbo_Flyer_Auto_Leitura_Online2,
            Context HomeAndroid2,
            OO12_Servico OO12_Servico2,
            int contadorXXX2,
            int proxima_mensagem_a_ler2,
            String de_nome_remetente2,
            String email_Usuario_Logado2,
            List<Contato> listaDeContatos2) {

        internalStorageDir = HomeAndroid2.getFilesDir().getPath();

        Turbo_Flyer_Auto_Leitura_Online3 = Turbo_Flyer_Auto_Leitura_Online2;
        HomeAndroid = HomeAndroid2;

        contadorXXX = contadorXXX2;
        OO12_Servico3 = OO12_Servico2;

        proxima_mensagem_a_ler = proxima_mensagem_a_ler2;
        de_nome_remetente = de_nome_remetente2;
        email_Usuario_Logado = email_Usuario_Logado2;
        listaDeContatos = listaDeContatos2;
    }

    //É onde acontece o processamento
    //Este método é executado em uma thread a parte,
    //ou seja ele não pode atualizar a interface gráfica,
    //por isso ele chama o método onProgressUpdate,
    // o qual é executado pela
    //UI thread.
    @Override
    protected Void doInBackground(String... params) {

        //System.out.println("\n** OO12_Ler_Mensagens_Auto2 *****************************************************************");
        //System.out.println("Posição a ler - " + proxima_mensagem_a_ler);
        //System.out.println("Usuário Logado - " + de_nome_remetente);
        //System.out.println("Email Usuário Logado - " + de_email_remetente);
        //System.out.println("** OO12_Ler_Mensagens_Auto2 *****************************************************************\n");


        String jsonDeResposta = null;

        try{

            try {

                String id_0_formulario = "1By3w9XL3XDO_Dzi5VomurGnqoMS-_zW552C4-B2SJKo";
                String id_1_Espaco = "A:F";
                try{

                    //String x = String.valueOf( proxima_mensagem_a_ler ).trim();
                    //id_1_Espaco = "A" + x + ":F"+ x;

                    //System.out.println("\n*******************************************************************");
                    //System.out.println("id_1_Espaco - " + id_1_Espaco);
                    //System.out.println("*******************************************************************\n");

                } catch( Exception e ){}
                String key = "AIzaSyBwiMCywJRFQHuuksWdhqwjOrR5mDaWJYs";
                String GET_URL = "https://sheets.googleapis.com/v4/spreadsheets/" + id_0_formulario + "/values/" + id_1_Espaco + "?key=" + key;

                try{

                    //System.out.println("\n*******************************************************************");
                    //System.out.println("GET_URL - " + GET_URL);
                    //System.out.println("*******************************************************************\n");
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

                    //System.out.println("\n*******************************************************************");
                    //System.out.println("jsonDeResposta - " + jsonDeResposta);
                    //System.out.println("*******************************************************************\n");
                } catch( Exception e ){}

            } catch (Exception e) {
                jsonDeResposta = null;
                //e.printStackTrace();
            } finally {}

        } catch( Exception e ){}

        //Notifica o Android de que ele precisa
        //fazer atualizações na
        //tela e chama o método onProgressUpdate
        //para fazer a atualização da interface gráfica
        //passando o valor do
        //contador como parâmetro.
        publishProgress(jsonDeResposta);

        return null;
        //}while(true);
    }
    // É invocado para fazer uma atualização na
    // interface gráfica
    @Override
    protected void onProgressUpdate(String... jsonDeResposta2) {
        //System.out.println( jsonDeResposta[0] );

        String jsonDeResposta = null;
        try{

            jsonDeResposta = jsonDeResposta2[0];
        } catch( Exception e ){}

        try{

            if( jsonDeResposta != null ){

                int quantidade_de_linhas_json = -1;
                try{

                    String json_linha_por_linha[] = jsonDeResposta.split("],");
                    quantidade_de_linhas_json = json_linha_por_linha.length - 1;

                    //System.out.println("\n\nTurbo_Flyer_Auto_Leitura_Online - Para baixar as Mensagens - quantidade_de_linhas_json - " + quantidade_de_linhas_json);
                    //System.out.println("jsonDeResposta - " + jsonDeResposta);
                    /*if( quantidade_de_linhas_json == 1 ){
                        System.out.println("json_linha_por_linha[0] - " + json_linha_por_linha[0] );
                        System.out.println("json_linha_por_linha[1] - " + json_linha_por_linha[1] );
                    }
                    */
                }catch( Exception e ){}

                if( quantidade_de_linhas_json > proxima_mensagem_a_ler ){

                    for (int j = 0; j < listaDeContatos.size(); j++) {

                        String nome_da_lista_dos_contatos;
                        String email_da_lista_dos_contatos = null;

                        try{
                            String email_Contato_Destinatario1 = listaDeContatos.get(j).getEmail_remetente();
                            String email_Contato_Destinatario2 = email_Contato_Destinatario1.trim().toUpperCase().replace("@", "_");
                            String email_Contato_Destinatario3 = email_Contato_Destinatario2.trim().toUpperCase().replace(".", "_");
                            email_da_lista_dos_contatos = email_Contato_Destinatario3;
                        }catch( Exception e ){}

                        nome_da_lista_dos_contatos = listaDeContatos.get(j).getNome_remetente();
/////////////////////////////////////////////////////////////////////////////////////////
                        separar_json_linha_por_linha(
                                proxima_mensagem_a_ler,
                                de_nome_remetente,
                                email_Usuario_Logado,
                                nome_da_lista_dos_contatos,
                                email_da_lista_dos_contatos,
                                jsonDeResposta,
                                quantidade_de_linhas_json
                        );
/////////////////////////////////////////////////////////////////////////////////////////
                    }
                }
            }
            else{

                Thread.sleep( 1000 );
                System.out.println("\nTurbo_Flyer_Auto_Leitura_Online - Para baixar as Mensagens - jsonDeResposta == null - " + proxima_mensagem_a_ler);
            }
        } catch( Exception e ){}

        try{

            //System.out.println("\nReiniando - Turbo_Flyer_Auto_Leitura_Online - Para baixar as Mensagens - " + servico_proxima_mensagem_a_ler);
            Thread.sleep( 1 );
            contadorXXX++;
            //oo_0_criar_usuario_logado(contadorXXX);
            OO12_Servico3.iniciar_servico(contadorXXX);
        } catch( Exception e ){}
    }

    private void separar_json_linha_por_linha(
            int proxima_mensagem_a_ler,
            String de_nome_remetente,
            String email_Usuario_Logado,
            String para_nome_destinatario,
            String email_da_lista_dos_contatos,
            String jsonDeResposta,
            int quantidade_de_linhas_json ) {

        try{
            String json_linha_por_linha[] = jsonDeResposta.split("],");

            //System.out.println("\n*******************************************************************");
            //System.out.println("Quantidade de Linhas: " + quantidade_de_linhas_json + " - proxima_mensagem_a_ler: " + proxima_mensagem_a_ler );
            //System.out.println("*******************************************************************\n");

            for( int i = 1; i < json_linha_por_linha.length; i++ ){

                String dados_da_linha[] = json_linha_por_linha[i].split( "\"," );

                String data1 = dados_da_linha[0].trim().toUpperCase().trim();
                String data = data1.trim();

                String remetente1 = dados_da_linha[1].trim().toUpperCase().replace("@", "_");
                String remetente2 = remetente1.trim().toUpperCase().replace(".", "_");
                String remetente_da_mensagem = remetente2;

                String destinatario1 = dados_da_linha[2].trim().toUpperCase().replace("@", "_");
                String destinatario2 = destinatario1.trim().toUpperCase().replace(".", "_");
                String destinatario_da_mensagem = destinatario2;

                String mensagem1 = dados_da_linha[3].trim();
                String mensagem_a_mostrar = "";
                try{
                    //System.out.println("\n** Mensagem do arquivo a baixar ***" + dados_da_linha[3].trim());
                    //separando a mensagem
                    String mensagem[] = mensagem1.split( "__@jm@inicio@jm@__" );
                    mensagem_a_mostrar = mensagem[1];
                    //String decodedUrl = java.net.URLDecoder.decode(mensagem[1], "UTF-8");
                    //mensagem_a_mostrar = decodedUrl;

                } catch( Exception e ){
                    mensagem_a_mostrar = dados_da_linha[3].trim();
                    System.out.println("\nErro ao ler mensagem - Mensagem - " + dados_da_linha[3].trim());
                }

                mostrar_mensagem_parte_2( data.trim(), remetente_da_mensagem, destinatario_da_mensagem, mensagem_a_mostrar, proxima_mensagem_a_ler, de_nome_remetente, email_Usuario_Logado, email_da_lista_dos_contatos, para_nome_destinatario, quantidade_de_linhas_json);
            }
        } catch( Exception e ){}
    }

    private void mostrar_mensagem_parte_2( String data2, String remetente_da_mensagem2, String destinatario_da_mensagem2, String mensagem_a_mostrar, int proxima_mensagem_a_ler, String de_nome_remetente, String email_Usuario_Logado, String email_da_lista_dos_contatos, String para_nome_destinatario,  int quantidade_de_linhas_json) { try {

        String data = data2.trim().replace("\"", "").replace("[", "").replace("/", "_").replace(":", "_").trim();
        String remetente_da_mensagem = remetente_da_mensagem2.trim().replace("\"", "").toUpperCase();
        String destinatario_da_mensagem = destinatario_da_mensagem2.trim().replace("\"", "").toUpperCase();

        if( mensagem_a_mostrar != null && !mensagem_a_mostrar.equals("") ){

            //A mensagem é para mim
            if( destinatario_da_mensagem.equalsIgnoreCase(email_Usuario_Logado) ){

                if( remetente_da_mensagem.equalsIgnoreCase(email_da_lista_dos_contatos) ){

                    System.out.println("\n** Início - Turbo_Flyer_Auto_Leitura_Online - *****************************************************************");
                    System.out.println("\n A mensagem é para mim: " + email_Usuario_Logado );
                    System.out.println("\n email_Usuario_Logado: " + email_Usuario_Logado );
                    System.out.println("\n email_da_lista_dos_contatos: " + email_da_lista_dos_contatos );
                    System.out.println("\n remetente_da_mensagem: " + remetente_da_mensagem );
                    System.out.println("\n destinatario_da_mensagem: " + destinatario_da_mensagem );
                    System.out.println("\n** Fim - Turbo_Flyer_Auto_Leitura_Online - *****************************************************************");

                    try {

                        String datax = data.trim().replace(" ", "_");
                        String datas[] = datax.trim().split("_");
                        String dataXX = datas[2]+"_"+datas[1]+"_"+datas[0]+"_"+datas[3]+"_"+datas[4]+"_"+datas[5];

                        salvar_arquivo_baixado_destinatario_e_remetente(
                                dataXX,
                                mensagem_a_mostrar,
                                email_da_lista_dos_contatos,
                                email_Usuario_Logado,
                                para_nome_destinatario,
                                quantidade_de_linhas_json);
                    } catch( Exception e ){ e.printStackTrace(); }

                }
            }
            //Eu enviei a mensagem
            else if( remetente_da_mensagem.equalsIgnoreCase(email_Usuario_Logado) ){

                if( email_da_lista_dos_contatos.equalsIgnoreCase(destinatario_da_mensagem) ){

                    System.out.println("\n** Início - Turbo_Flyer_Auto_Leitura_Online - *****************************************************************");
                    System.out.println("\n Eu enviei a mensagemm: " + email_Usuario_Logado );
                    System.out.println("\n email_Usuario_Logado: " + email_Usuario_Logado );
                    System.out.println("\n email_da_lista_dos_contatos: " + email_da_lista_dos_contatos );
                    System.out.println("\n remetente_da_mensagem: " + remetente_da_mensagem );
                    System.out.println("\n destinatario_da_mensagem: " + destinatario_da_mensagem );
                    System.out.println("\n** Fim - Turbo_Flyer_Auto_Leitura_Online - *****************************************************************");

                    try {

                        String datax = data.trim().replace(" ", "_");
                        String datas[] = datax.trim().split("_");
                        String dataXX = datas[2]+"_"+datas[1]+"_"+datas[0]+"_"+datas[3]+"_"+datas[4]+"_"+datas[5];

                        salvar_arquivo_baixado_remetente_e_destinatario(
                                dataXX,
                                mensagem_a_mostrar,
                                email_da_lista_dos_contatos,
                                email_Usuario_Logado,
                                quantidade_de_linhas_json);
                    } catch( Exception e ){ e.printStackTrace(); }
                }
            }
        }

        /*
        if( mensagem_a_mostrar != null && !mensagem_a_mostrar.equals("") ){

            if( remetente_da_mensagem.trim().equalsIgnoreCase( email_Usuario_Logado.toUpperCase().trim() )  ){


            }
            else if( remetente_da_mensagem.trim().equalsIgnoreCase( email_da_lista_dos_contatos.toUpperCase().trim() )  ){


            }
        }
        */
    } catch( Exception e ){ } }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void mensagem_no_SystemTray(String mensagem2, String titulo) { try {

///////////////////////////////////////////////////////////////////////////////
        // codePointAt para String
        StringBuilder sb_final = new StringBuilder();
        String parte_01[] = mensagem2.trim().split("_");
        for (int i = 0; i < parte_01.length; i++) {

            int codePointAt = Integer.parseInt( parte_01[i] );
            // converting to char[] pair
            char[] charPair = Character.toChars(codePointAt);
            // and to String, containing the character we want
            String symbol = new String(charPair);

            sb_final.append(symbol);

            //Transformar unicode para utf
            if (Character.charCount(codePointAt) == 1) {

                /*
                System.out.println( "\n\n filtrarTipoArquivos__iniciais_2 - Início - Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                System.out.println( " String Completa - " + mensagem2 );
                System.out.println( " String parte_01[i] - " + parte_01[i] );
                System.out.println( " codePointAt - " + codePointAt );
                System.out.println( " char[] charPair - " + charPair );
                System.out.println( " Symbol - " + symbol );
                System.out.println( " filtrarTipoArquivos__iniciais_2 - Fim - Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");
                */
            } else {
                //return new String(Character.toChars(codePoint));
            }
        }
////////////////////////////////////////////////////////////////////////////////

        //String mensagemSX = sb.toString().trim();
        String lineSeparator = System.getProperty("line.separator");
        String mensagem = sb_final.toString().trim().replaceAll("__@jm@quebra_linha@jm@__", lineSeparator );

        int id = (int) (System.currentTimeMillis() / 1000);

        Intent intent = new Intent(HomeAndroid, HomeAndroid.getClass());

        // PendingIntent para executar a Activity se o usuário selecionar a notificação
        PendingIntent pendingIntent = PendingIntent.getActivity(
                HomeAndroid,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeAndroid)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(titulo)
                .setContentText(mensagem)
                //.setContentIntent(pendingIntent)
                //.setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(titulo));

        NotificationManager notifyManager = (NotificationManager) HomeAndroid.getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(id, builder.build());

        MediaPlayer mediaPlayer = MediaPlayer.create(HomeAndroid, R.raw.iphone_sms);
        mediaPlayer.start();

    } catch( Exception e ){} }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void salvar_arquivo_baixado_destinatario_e_remetente(
            String segundo,
            String msg_a_enviar2,
            String email_destinatario,
            String email_remetente,
            String para_nome_destinatario,
            int quantidade_de_linhas_json) { /*new Thread() {   @Override public void run() {*/ try {

        try{

            String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
            String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");
            String remetente_e_destinatario = email_REMETENTE + "-" + email_DESTINATARIO + "-";
            String destinatario_e_remetente = email_DESTINATARIO + "-" + email_REMETENTE + "-";

            String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo + s + "RECEBIDO_E_NAO_LIDO";
            String arquivoASerCriadoXX = destino + s + destinatario_e_remetente + ".html";

            if( new File( arquivoASerCriadoXX ).exists() == false ){

                //System.out.println("\n** ARQUIVO NÃO EXISTE *****************************************************************");
                //System.out.println("arquivoASerCriadoXX: " + arquivoASerCriadoXX );
                //System.out.println("\n** ARQUIVO NÃO EXISTE *****************************************************************");

                Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo");
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "MENSAGENS_RECEBIDAS");
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS", email_REMETENTE);
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE, email_DESTINATARIO);
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, segundo);
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo, "RECEBIDO_E_NAO_LIDO");

                try{

                    //Mensagem Enviada
                    File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(arquivoASerCriadoXX, msg_a_enviar2, "UTF-8");

                    try {

                        mensagem_no_SystemTray( msg_a_enviar2, para_nome_destinatario );
                    } catch( Exception e ){ e.printStackTrace(); }

                } catch( Exception e ){e.printStackTrace();}

                try {
                    Turbo_Flyer_Auto_Leitura_Online3.oo_alterar_numero_ultimo_lido(
                            email_REMETENTE,
                            quantidade_de_linhas_json
                    );
                } catch( Exception e ){e.printStackTrace();}
            }
        } catch( Exception e ){e.printStackTrace();}
    } catch( Exception e ){ } /*} }.start();*/ }

    public void salvar_arquivo_baixado_remetente_e_destinatario(
            String segundo,
            String msg_a_enviar2,
            String email_destinatario,
            String email_remetente,
            int quantidade_de_linhas_json) { /*new Thread() {   @Override public void run() {*/ try {

        try{

            String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
            String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");
            String remetente_e_destinatario = email_REMETENTE + "-" + email_DESTINATARIO + "-";
            String destinatario_e_remetente = email_DESTINATARIO + "-" + email_REMETENTE + "-";

            String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo + s + "ENVIADO_ONLINE";
            String arquivoASerCriadoXX = destino + s + remetente_e_destinatario + ".html";

            if( new File( arquivoASerCriadoXX ).exists() == false ){

                Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo");
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "MENSAGENS_RECEBIDAS");
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS", email_REMETENTE);
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE, email_DESTINATARIO);
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, segundo);
                Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo, "ENVIADO_ONLINE");

                try{

                    //Mensagem Enviada
                    File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(arquivoASerCriadoXX, msg_a_enviar2, "UTF-8");

                } catch( Exception e ){e.printStackTrace();}

                try {
                    Turbo_Flyer_Auto_Leitura_Online3.oo_alterar_numero_ultimo_lido(
                            email_REMETENTE,
                            quantidade_de_linhas_json
                    );
                } catch( Exception e ){e.printStackTrace();}
            }
        } catch( Exception e ){e.printStackTrace();}
    } catch( Exception e ){ } /*} }.start();*/ }
}