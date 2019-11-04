/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmarysystems.turbo_flyer;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import jm.Arquivo_Ou_Pasta;

/**
 *
 * @author JMarySystems
 */
public class Turbo_Flyer_Auto_Enviar_Offline {

    String s = null;
    //String internalStorageDir = System.getProperty("user.dir");
    String internalStorageDir = null;

    Context HomeAndroid;
    
    public Turbo_Flyer_Auto_Enviar_Offline(
            Context HomeAndroid2,
            String msg_a_enviar2, 
            String nome_criar_pasta_mensagem,
            String de_email_remetente2, 
            String para_email_destinatario2) {

        HomeAndroid = HomeAndroid2;
        internalStorageDir = HomeAndroid2.getFilesDir().getPath();
        s = System.getProperty("file.separator");

        //Quebra de linha para - __@jm@quebra_linha@jm@__
        StringBuilder sb_turbo_android = new StringBuilder();
        String separador0 = System.getProperty("line.separator");
        StringTokenizer st1 = new StringTokenizer(msg_a_enviar2, separador0 );
        //System.out.println( "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + separador0);
        for (int i = 0; st1.hasMoreTokens(); i++) {

            String rowstring = ""; try{ rowstring = st1.nextToken(); }catch( Exception e ){}
            //System.out.println( "rowstring - " + rowstring );
            if( !rowstring.trim().equals("") ){

                sb_turbo_android.append( rowstring.trim() + "__@jm@quebra_linha@jm@__");
            }
        }
        String b = "__@jm@quebra_linha@jm@__";
        String msg = sb_turbo_android.toString().trim();
        msg = msg.substring (0, msg.length() - b.length());

        String mensagemComQuebraJM = msg.trim();

////////////////////////////////////////////////////////////////////////////////
        StringBuilder sb_turbo_codePointAt = new StringBuilder();
        //String para codePointAt
        String str = mensagemComQuebraJM;
        CharSequence charPair_Array = str;
        for (int i = 0; i < charPair_Array.length(); i++) {

            int codePointAt = Character.codePointAt(charPair_Array, i);
            sb_turbo_codePointAt.append( codePointAt + "_" );

            /////////////////////////////////////////////////////////////////////////
            // converting to char[] pair
            char[] charPair = Character.toChars(codePointAt);
            // and to String, containing the character we want
            String symbol = new String(charPair);
            /////////////////////////////////////////////////////////////////////////

            System.out.println( "\n\n public Turbo_Flyer_Auto_Enviar_Offline - Início - Turbo_Flyer_Auto_Enviar_Offline ***************************************************");
            System.out.println( " String Completa - " + str );
            System.out.println( " int ( i ) - " + i );
            System.out.println( " codePointAt - " + codePointAt );
            System.out.println( " Symbol - " + symbol );
            System.out.println( " public Turbo_Flyer_Auto_Enviar_Offline - Fim - Turbo_Flyer_Auto_Enviar_Offline ***************************************************\n\n");
        }
        String mensagem_codePointAt = sb_turbo_codePointAt.toString().trim();
////////////////////////////////////////////////////////////////////////////////

        Enviar_Mensagens_Auto2 Enviar_Mensagens_Auto2 = new Enviar_Mensagens_Auto2(
                HomeAndroid,
                "__@jm@inicio@jm@__" + mensagem_codePointAt + "__@jm@inicio@jm@__",
                nome_criar_pasta_mensagem,
                de_email_remetente2,
                para_email_destinatario2
        );
        Enviar_Mensagens_Auto2.execute("");
    }

}


// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class Enviar_Mensagens_Auto2 extends AsyncTask<String, String, Void> {

    String s = null;
    //String internalStorageDir = System.getProperty("user.dir");
    String internalStorageDir = null;

    Context HomeAndroid;

    String msg_a_enviar;
    String nome_criar_pasta_mensagem;
    String de_email_remetente;
    String para_email_destinatario;

    public Enviar_Mensagens_Auto2(
            Context HomeAndroid2,
            String msg_a_enviar2,
            String nome_criar_pasta_mensagem2,
            String de_email_remetente2,
            String para_email_destinatario2) {

        HomeAndroid = HomeAndroid2;
        internalStorageDir = HomeAndroid2.getFilesDir().getPath();
        s = System.getProperty("file.separator");

        msg_a_enviar = msg_a_enviar2;
        nome_criar_pasta_mensagem = nome_criar_pasta_mensagem2;
        de_email_remetente = de_email_remetente2;
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

        String jsonDeResposta = null;
        try{

            String id_0_formulario = "1FAIpQLSe26aTcu7OC_OnfIiBk9bkoSsI_cps6EDoI71kX64fZNrW6Hw";
            String id_1_formulario_email_de = "1897386233";
            String id_2_formulario_email_para = "1845166634";
            String id_3_sub_item_formulario_mensagem = "283884279";
            String email_remetente3 = de_email_remetente;
            String email_destinatario3 = para_email_destinatario;
            //String sub_item_formulario_mensagem = URLEncoder.encode(msg_a_enviar, "utf-8");
            String sub_item_formulario_mensagem = msg_a_enviar;
            //String sub_item_formulario_mensagem = java.net.URLEncoder.encode(msg_a_enviar, "UTF-8");

            String GET_URL = "https://docs.google.com/forms/d/e/" + id_0_formulario + "/formResponse?"
                    + "entry." + id_1_formulario_email_de + "=" + email_remetente3
                    + "&entry." + id_2_formulario_email_para + "=" + email_destinatario3
                    + "&entry." + id_3_sub_item_formulario_mensagem + "=" + sub_item_formulario_mensagem;

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
        } finally {}

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
        } catch (Exception e) {}

        try{

            salvar_arquivo_baixado(
                    nome_criar_pasta_mensagem,
                    jsonDeResposta,
                    msg_a_enviar,
                    de_email_remetente,
                    para_email_destinatario
            );

        } catch( Exception e ){}
    }

    public void salvar_arquivo_baixado(
            String nome_criar_pasta_mensagem,
            String jsonDeResposta,
            String msg_a_enviar2,
            String email_remetente,
            String email_destinatario) { /*new Thread() {   @Override public void run() {*/ try {

        try{

            String data = nome_criar_pasta_mensagem.trim().toUpperCase().replace("-", "_");
            String minuto = data.trim().toUpperCase().replace(":", "_");
            String nada = minuto.trim().toUpperCase().replace(" ", "_");
            String segundo = nada.trim().toUpperCase().replace(".", "_");

            String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
            String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");
            String remetente_e_destinatario = email_REMETENTE + "-" + email_DESTINATARIO + "-";
            String destinatario_e_remetente = email_DESTINATARIO + "-" + email_REMETENTE + "-";

            Arquivo_Ou_Pasta.criarPasta( internalStorageDir, "00_Externo");
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "MENSAGENS_RECEBIDAS");
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS", email_REMETENTE);
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE, email_DESTINATARIO);
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, segundo);

            if( jsonDeResposta != null ){
                try{

                    Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo, "ENVIADO");

                    //Mensagem Enviada
                    String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo + s + "ENVIADO";
                    String arquivoASerCriadoXX = destino + s + remetente_e_destinatario + ".html";
                    File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(arquivoASerCriadoXX, msg_a_enviar2, "UTF-8");

                } catch( Exception e ){}
            }
            else{
                try{

                    Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo, "NAO_ENVIADO");

                    //Mensagem Não Enviada
                    String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + segundo + s + "NAO_ENVIADO";
                    String arquivoASerCriadoXX = destino + s + remetente_e_destinatario + ".html";
                    File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(arquivoASerCriadoXX, msg_a_enviar2, "UTF-8");

                } catch( Exception e ){}

                //System.out.println("\n**NULL*****************************************************************");
                //System.out.println("jsonDeResposta = " + "NULL");
                //System.out.println("**NULL*****************************************************************\n");
            }
        } catch( Exception e ){}

    } catch( Exception e ){ } /*} }.start();*/ }

}