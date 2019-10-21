package com.jmarysystems.turbo_flyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;

import jm.Arquivo_Ou_Pasta;

import static android.graphics.Color.parseColor;

public class OO8_Conversa extends AppCompatActivity {

    public String nome = "";
    String de_email_remetente = "";
    String para_email_destinatario = "";
    public android.app.Activity Activity;

    View focusView = null;
    private ScrollView scrollView22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo8__conversa);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nome = bundle.getString("nome");
        de_email_remetente = bundle.getString("email");
        para_email_destinatario = bundle.getString("emaildestinatario");

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.hide();

        Activity = this;

        scrollView22 = (ScrollView) findViewById(R.id.scrollcentro);

        preencher_layout_topo();
        linha_do_menu_add();
        linha_do_footer_add();
        //mensagem_direita("mensagem_direita");
        //mensagem_esquerda("mensagem_esquerda");

        _ler_da_mensagens(true);
    }

    public void _ler_da_mensagens(boolean executarUmaVez){
        try{

            String ip = "http://192.168.0.100:8000/";

            LerMensagem LerMensagem = new LerMensagem(this, executarUmaVez, ip, nome, de_email_remetente, para_email_destinatario);
            LerMensagem.execute(ip);

        } catch( Exception e ){
            e.printStackTrace();
            //JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
            //+ e.getMessage() + "\n", "Home" );
        }
    }

    public void _envio_da_mensagem(String msg_a_enviar, String pasta, String file, String segundo){
        try{

            EnviarMensagem EnviarMensagem = new EnviarMensagem(this, msg_a_enviar, nome, de_email_remetente, para_email_destinatario, pasta, file, segundo);
            EnviarMensagem.execute(msg_a_enviar);

        } catch( Exception e ){
            e.printStackTrace();
            //JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
            //+ e.getMessage() + "\n", "Home" );
        }
    }

    RelativeLayout relative_layout_topo;
    RelativeLayout relative_layout_footer;

    RelativeLayout relative_layout_center2;
    LinearLayout relative_layout_center;
    //android.widget.ScrollView ScrollViewXML33;
    private void preencher_layout_topo() {
        try {

///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            relative_layout_topo = (RelativeLayout) Activity.findViewById(R.id.header);
            relative_layout_footer = (RelativeLayout) Activity.findViewById(R.id.footer);
            EditText deletar = (EditText) Activity.findViewById(R.id.footer_editText);
            try{ relative_layout_footer.removeView(deletar); } catch( Exception e ){}
            // LAYOUT CENTRAL PRINCIPAL FIXO
            relative_layout_center2 = (RelativeLayout) Activity.findViewById(R.id.content);

            // LAYOUT CENTRAL DINÂMICO
            relative_layout_center = new LinearLayout(Activity);
            relative_layout_center.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            relative_layout_center.setOrientation(LinearLayout.VERTICAL);

            // ADICIONAR LAYOUT DINÂMICO AO LAYOUT FIXO
            relative_layout_center2.addView(relative_layout_center);

            // ADICIONAR LAYOUT DINÂMICO AO ScrollView
            //ScrollViewXML33 = new ScrollView(Activity);
            //define o layout como a view principal
            //ScrollViewXML33.addView(relative_layout_center2);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void linha_do_menu_add() {
//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout3 = new LinearLayout(Activity);
        LinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout3.setGravity(Gravity.RIGHT);

        // CRIA O BOTÃO PARA ADICIONAR NOVO CONTATO
        Button bt_novo_Contato = new Button(Activity);
        bt_novo_Contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir_Activity( OO1_Home.class, nome, email  );
                abrir_Activity(OO9_Abrir_Contatos.class, nome, de_email_remetente);
            }
        });
        bt_novo_Contato.setText("Voltar");
        LinearLayout3.addView(bt_novo_Contato);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 2 Nome e Email///////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout2 = new LinearLayout(Activity);
        LinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        LinearLayout2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout2.setGravity(Gravity.CENTER_VERTICAL);

        TextView textView = new TextView(Activity);
        textView.setText("    " + nome );
        textView.setTextColor(parseColor("#ffffff"));
        LinearLayout2.addView(textView);
//PREENCHER Layout 2////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout1 = new LinearLayout(Activity);
        LinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 100
        ));
        LinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout1.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout1.setPadding(1, 2, 0, 0); //Padding( left, top, right, bottom);
        LinearLayout1.setBackgroundColor(parseColor("#008577"));
        //define a cor de fundo do layout
//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
        //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
        ImageView imagem = new ImageView(Activity);
        imagem.setImageResource(R.mipmap.ic_launcher_round);
        imagem.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imagem.setLayoutParams(new LinearLayout.LayoutParams(
                100, LinearLayout.LayoutParams.MATCH_PARENT
        ));

        LinearLayout1.addView(imagem);
        //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////
        LinearLayout1.addView(LinearLayout2);
        LinearLayout1.addView(LinearLayout3);
//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////

//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        relative_layout_topo.addView(LinearLayout1  );
        //LinearLayoutXML.addView(LinearLayout1, contador);
        //contador++;
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
    }

    public EditText digitar_mensagem;
    private void linha_do_footer_add() {
    //PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
    LinearLayout LinearLayout3 = new LinearLayout(Activity);
        LinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout3.setGravity(Gravity.RIGHT);

    // CRIA O BOTÃO PARA ADICIONAR NOVO CONTATO
        //EditText
        digitar_mensagem =  new EditText(Activity);
    Button bt_novo_Contato = new Button(Activity);
        bt_novo_Contato.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String msg_a_enviar = "";
            try{ msg_a_enviar = digitar_mensagem.getText().toString().trim(); } catch( Exception e ){}
            if( msg_a_enviar != null ){

                if( !msg_a_enviar.equals("") ) {
                    //mensagem_direita(msg22);
                    digitar_mensagem.setText("");

                    try {

                        String nome_criar_pasta_mensagem = null;

                        //System.out.println( "ID = criar_pasta_deste_destinatario_no_remetente - " + id_criar_pasta_deste_destinatario_no_remetente );
                        ////////dd.MM.yyyy HH:mm:ss/////////////EEEE, d MMMM yyyy HH:mm:ss
                        Calendar calendar = Calendar.getInstance();
                        java.util.Date now = calendar.getTime();
                        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

                        DateFormat dfmt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        try{ nome_criar_pasta_mensagem = dfmt.format(currentTimestamp);  }catch(Exception e){}
                        ////////dd.MM.yyyy HH:mm:ss/////////////EEEE, d MMMM yyyy HH:mm:ss

                        mensagem_direita(msg_a_enviar, nome_criar_pasta_mensagem);

                        //_envio_da_mensagem(msg_a_enviar);

                        criar_arquivo( msg_a_enviar, de_email_remetente, para_email_destinatario, "ENVIAR", "");

                    } catch (Exception e) {
                        e.printStackTrace();
                        //JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
                        //+ e.getMessage() + "\n", "Home" );
                    }
                }
            }
            //abrir_Activity( OO1_Home.class, nome, email  );
            //abrir_Activity(OO9_Abrir_Contatos.class, nome, email);
        }
    });
        bt_novo_Contato.setText("Enviar");
        LinearLayout3.addView(bt_novo_Contato);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

    //PREENCHER Layout 2 Nome e Email///////////////////////////////////////////////////////////////////
    LinearLayout LinearLayout2 = new LinearLayout(Activity);
        LinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        LinearLayout2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout2.setGravity(Gravity.CENTER_VERTICAL);

        //EditText
        digitar_mensagem.setHint("Digite aqui");
        LinearLayout2.addView(digitar_mensagem);
//PREENCHER Layout 2////////////////////////////////////////////////////////////////////////////////

    //PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////
    LinearLayout LinearLayout1 = new LinearLayout(Activity);
        LinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT
            ));
        LinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout1.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout1.setPadding(1, 2, 0, 0); //Padding( left, top, right, bottom);
        //LinearLayout1.setBackgroundColor(parseColor("#008577"));
    //define a cor de fundo do layout
//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
    ImageView imagem = new ImageView(Activity);
        imagem.setImageResource(R.mipmap.ic_launcher_round);
        imagem.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imagem.setLayoutParams(new LinearLayout.LayoutParams(
                100, LinearLayout.LayoutParams.MATCH_PARENT
        ));

        LinearLayout1.addView(imagem);
    //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////
        LinearLayout1.addView(LinearLayout2);
        LinearLayout1.addView(LinearLayout3);
//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////

//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
    relative_layout_footer.addView(LinearLayout1);
    //LinearLayoutXML.addView(LinearLayout1, contador);
    //contador++
    //focusView = bt_novo_Contato;
        //View currentFocus = getWindow().getCurrentFocus();
        //currentFocus.clearFocus();
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
}

    public void mostrar_mensagens_iniciais(String email_remetente, String email_destinatario) { try {

        String s = System.getProperty("file.separator");
        File internalStorageDir = Activity.getFilesDir();

        String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
        String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
        String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
        String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
        if ( new File(internalStorageDir + s + "00_Externo").exists() == true ) {

            System.out.println("00_Externo -- Existe --- " + internalStorageDir + s + "00_Externo" );
        }
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "MENSAGENS_RECEBIDAS" );
        if ( new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS").exists() == true ) {

            System.out.println("MENSAGENS_RECEBIDAS -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" );
        }
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS", email_REMETENTE );
        if ( new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE).exists() == true ) {

            System.out.println(email_REMETENTE+" -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE );
        }
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE, email_DESTINATARIO );
        if ( new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO).exists() == true ) {

            System.out.println(email_DESTINATARIO+" -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO );
        }
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, "NAO_ENVIADO" );
        if ( new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO").exists() == true ) {

            System.out.println("NAO_ENVIADO -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO" );
        }
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, "ENVIADO" );
        if ( new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO").exists() == true ) {

            System.out.println("ENVIADO -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO" );
        }

        String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO;
        File diretorio = new File(destino);

        if ( diretorio.exists() == true ) {
            System.out.println("******************************************************************** -- " + diretorio.getPath());

            File pasta_da_msg_recebida[] = diretorio.listFiles();

            //File[] pasta_da_msg_recebida = null;
            try {

                //pasta_da_msg_recebida = diretorio.listFiles();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (pasta_da_msg_recebida != null) {

                System.out.println("if (pasta_da_msg_recebida != null) { -- " + diretorio.getPath());

                for (int j = 0; j < pasta_da_msg_recebida.length; j++) {

                    if (pasta_da_msg_recebida[j].isDirectory()) {

                        String namePasta = pasta_da_msg_recebida[j].getName().trim().toUpperCase();

                        if (namePasta.equalsIgnoreCase("EMOJIS")) {

                        } else if (namePasta.equalsIgnoreCase("NAO_ENVIADO")) {
                            System.out.println("namePasta - " + namePasta);
                            System.out.println("pasta_da_msg_recebida[j] - " + pasta_da_msg_recebida[j].getPath());
                        /*
                        Arquivo_Ou_Pasta.criarPasta( System.getProperty("user.dir") + "\\00_Externo", "MENSAGENS_RECEBIDAS" );
                        Arquivo_Ou_Pasta.criarPasta( System.getProperty("user.dir") + "\\00_Externo\\MENSAGENS_RECEBIDAS", namePasta );
                        String destino2 = System.getProperty("user.dir") + "\\00_Externo\\MENSAGENS_RECEBIDAS\\" + namePasta; // NAO_ENVIADO\\

                        File[] listaDosArquivos2 = new File( destino2 ).listFiles();
                        if ( listaDosArquivos2 != null ) {

                            Arquivo_Ou_Pasta.copiarArquivoNovoNome( listaDosArquivos2[j], destino2, "eu" + ".html" );
                            Arquivo_Ou_Pasta.deletar(listaDosArquivos2[j]);
                        }

                        Arquivo_Ou_Pasta.deletar( pasta_da_msg_recebida[j] );
                        */
                        } else {
                            System.out.println("namePasta - " + namePasta);
                            System.out.println("pasta_da_msg_recebida[j] - " + pasta_da_msg_recebida[j].getPath());

                            String msg_local = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + pasta_da_msg_recebida[j].getName().trim();

                            File[] listaDosArquivos = new File(msg_local).listFiles();
                            for (int i=0; i < listaDosArquivos.length; i++) {

                                System.out.println("\n**************************************************************" );
                                System.out.println("Pasta enviada - " + listaDosArquivos[i].getPath());
                                System.out.println("**************************************************************\n" );

                                mostrar_arquivo_html__iniciais_1( listaDosArquivos[i].getPath() );
                            }
                        /*
                        File[] listaDosArquivos2 = pasta_da_msg_recebida[j].listFiles();
                        for (int f=0; f < listaDosArquivos2.length; f++) {

                            File fileS = listaDosArquivos2[f];
                            String nome_Arquivo = fileS.getName().trim().toUpperCase();
                            System.out.println( "nome_Arquivo - " + nome_Arquivo );

                            if ( nome_Arquivo.equalsIgnoreCase( "EU.HTML" ) ) {
                                System.out.println( "nome_Arquivo - " + "EU" );

                                String ff = pasta_da_msg_recebida[j].getPath() + System.getProperty("file.separator") + fileS.getPath();
                                String extencao = ff.substring( ff.lastIndexOf('.') + 1 );

                                if ( extencao.equalsIgnoreCase( "html" ) ) {

                                    StringBuilder sb = new StringBuilder();
                                    BufferedReader bR = new BufferedReader( new FileReader( fileS ) );
                                    String linha;
		                    while( (linha = bR.readLine()) != null){
                                        sb.append( linha );
		                    }

                                    System.out.println( sb.toString() );

                                    String list_str_1[] = null; try { list_str_1 = pasta_da_msg_recebida[j].getName().split("_"); } catch (Exception e) {}
                                    String nome_data_hora_mensagem = list_str_1[1]+"/"+list_str_1[2]+"/"+list_str_1[2]+" " +
                                                     list_str_1[3]+"/"+list_str_1[4]+"/"+list_str_1[5];

                                    mensagem2("      Você - " + nome_data_hora_mensagem, sb.toString(), true, false);
                                }
                            }
                        }
                        */
                        }
                    }
                }
            } else {

                System.out.println("if (pasta_da_msg_recebida == null) { -- " + diretorio.getPath());
            }
       }

    } catch( Exception e ){ e.printStackTrace();} }

    private void mostrar_arquivo_html__iniciais_1( String pasta_criada_local ){
        try {
            System.out.println("mostrar_arquivo_html__iniciais_1( String pasta_criada_local ){ -- " );

            File diretorio = new File( pasta_criada_local );

            if ( diretorio.isDirectory()  ) {
                System.out.println("if ( diretorio.isDirectory()  ) { -- " + diretorio.getPath());

                File[] listaDosArquivos = diretorio.listFiles();

                if ( listaDosArquivos != null ) {
                    System.out.println("if ( listaDosArquivos != null ) { -- " + diretorio.getPath());

                    for (int i=0; i < listaDosArquivos.length; i++) {

                        File f = new File( listaDosArquivos[i].getName() );
                        System.out.println("File f = new File( listaDosArquivos[i].getName() ); -- " + f.getPath());

                        filtrarTipoArquivos__iniciais_2( diretorio, f );
                    }
                }
            }
            else{ /*System.out.println( pasta );*/ }

        } catch( Exception e ){}
    }

    private void filtrarTipoArquivos__iniciais_2( File diretorio, File arquivo ){

        try{

            String f = diretorio.getPath() + System.getProperty("file.separator") + arquivo.getPath();

            System.out.println( "filtrarTipoArquivos - HTML - " + f );

            //mensagemHTML( diretorio.getName(), f, false, true );

            File file = new File( f );

            if ( file.isFile() == true ) {

                String extencao = f.substring( f.lastIndexOf('.') + 1 );

                if( extencao.equalsIgnoreCase( "html" ) ){

                    System.out.println( "filtrarTipoArquivos - HTML - " + arquivo.getName() );

                    StringBuilder sb = new StringBuilder();
                    BufferedReader bR = new BufferedReader( new FileReader( file ) );
                    String linha;
                    while( (linha = bR.readLine()) != null){

                        //System.out.println( linha );
                        sb.append( linha );
                    }

                    System.out.println("\n**************************************************************" );
                    System.out.println("Texto - " + sb.toString());
                    System.out.println("**************************************************************\n" );


                    String data1 = diretorio.getName();
                    String data2 = data1.replaceAll("__", ":");
                    String data3 = data2.replaceAll("_", "/");
                    //String data4 = data3.replace("T", "_");
                    //String data5 = data4.replace("Z", "");
/*
                    String list_str_1[] = null; try { list_str_1 = data5.split("_"); } catch (Exception e) {}
                    for( int j = 0; j < list_str_1.length; j++ ){
                        System.out.println( "String - " + j + " - " + list_str_1[j] );
                    }
                    String nome_data_hora_mensagem = list_str_1[2]+"/"+list_str_1[1]+"/"+list_str_1[0]+" " +
                            list_str_1[3]+":"+list_str_1[4]+":"+list_str_1[5];
                    */

                    String nome_email_destinatario = para_email_destinatario.trim().toUpperCase().replace("@", "_");
                    String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
                    String arquivo_properties2 = de_email_remetente.trim().toUpperCase().replace("@", "_");
                    String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

                    String remetente_e_destinatario = email_REMETENTE    + "-" + email_DESTINATARIO + "-";
                    String destinatario_e_remetente = email_DESTINATARIO + "-" + email_REMETENTE + "-";

                    System.out.println("\n**************************************************************" );
                    System.out.println("arquivo.getName() - " + arquivo.getName());
                    System.out.println("**************************************************************\n" );

                    if ( arquivo.getName().equalsIgnoreCase( remetente_e_destinatario + ".html" ) ) {

                        //mensagem2("      Você - " + nome_data_hora_mensagem, sb.toString(), true, false);
                        mensagem_direita( sb.toString(),  " "+data3);
                    }
                    else if ( arquivo.getName().equalsIgnoreCase( destinatario_e_remetente + ".html" ) ) {

                        //mensagem2( nome_data_hora_mensagem, sb.toString(), false, false );
                        mensagem_esquerda( sb.toString(), " "+data3);
                    }
                }
            }

        } catch( Exception e ){ System.out.println( "Zero - Diretórios - Home - filtrarTipoArquivos( File diretório, File arquivo )" ); e.printStackTrace();}
    }

    public void criar_arquivo(String html, String email_remetente, String email_destinatario, String tipo, String nome_data) {
        /*new Thread() {   @Override public void run() {*/ try {

            String s = System.getProperty("file.separator");
            File internalStorageDir = Activity.getFilesDir();

            String nome_criar_pasta_mensagem = null;

            //System.out.println( "ID = criar_pasta_deste_destinatario_no_remetente - " + id_criar_pasta_deste_destinatario_no_remetente );
            ////////dd.MM.yyyy HH:mm:ss/////////////EEEE, d MMMM yyyy HH:mm:ss
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

            DateFormat dfmt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            try {
                nome_criar_pasta_mensagem = dfmt.format(currentTimestamp);
            } catch (Exception e) {
            }
            ////////dd.MM.yyyy HH:mm:ss/////////////EEEE, d MMMM yyyy HH:mm:ss

            String data = nome_criar_pasta_mensagem.trim().toUpperCase().replace("/", "__");
            String minuto = data.trim().toUpperCase().replace(":", "__");
            String segundo = minuto.trim().toUpperCase().replace(".", "_");

            String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
            String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");
            String remetente_e_destinatario = email_REMETENTE + "-" + email_DESTINATARIO + "-";
            String destinatario_e_remetente = email_DESTINATARIO + "-" + email_REMETENTE + "-";

            Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s, "00_Externo");
            if (new File(internalStorageDir + s + "00_Externo").exists() == true) {

                System.out.println("00_Externo -- Existe --- " + internalStorageDir + s + "00_Externo");
            }
            Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo", "MENSAGENS_RECEBIDAS");
            if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS").exists() == true) {

                System.out.println("MENSAGENS_RECEBIDAS -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS");
            }
            Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS", email_REMETENTE);
            if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE).exists() == true) {

                System.out.println(email_REMETENTE + " -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE);
            }
            Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE, email_DESTINATARIO);
            if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO).exists() == true) {

                System.out.println(email_DESTINATARIO + " -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO);
            }
            Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, "NAO_ENVIADO");
            if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO").exists() == true) {

                System.out.println("NAO_ENVIADO -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO");
            }
            Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO, "ENVIADO");
            if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO").exists() == true) {

                System.out.println("ENVIADO -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO");
            }

            if (tipo.equals("ENVIAR")){
                Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO", segundo);
                /*if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO" + s + segundo).exists() == true) {

                    System.out.println("ENVIADO -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO" + s + segundo);
                }*/

                String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO" + s + segundo; //minuto
                String arquivoASerCriadoXX = destino + s + remetente_e_destinatario + ".html";
                File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(arquivoASerCriadoXX, html, "UTF-8");

                System.out.println("File criado: " + file_x.getPath());

                //criar_html_e_enviar( segundo, file_x, destino, remetente_e_destinatario );
                _envio_da_mensagem(html, destino, arquivoASerCriadoXX, segundo);
            }
            else if (tipo.equals("RECEBIDO")){

                String data2 = nome_data.trim().toUpperCase().replace("/", "__");
                String minuto2 = data2.trim().toUpperCase().replace(":", "__");
                String segundo2 = minuto2.trim().toUpperCase().replace(".", "_");

                Arquivo_Ou_Pasta.criarPasta(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO", segundo2);
                /*if (new File(internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO" + s + segundo).exists() == true) {

                    System.out.println("ENVIADO -- Existe --- " + internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "NAO_ENVIADO" + s + segundo);
                }*/

                String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO" + s + segundo2; //minuto
                String arquivoASerCriadoXX = destino + s + destinatario_e_remetente + ".html";
                File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(arquivoASerCriadoXX, html, "UTF-8");
            }

        } catch( Exception e ){ e.printStackTrace(); } //} }.start();
    }

    public void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.whatsapp001);
        mediaPlayer.start();
    }

    public void mensagem_direita(String msg, String titulo) {
        try {
            //<!-- Center aligned to bottom -->
            RelativeLayout mensagemLayout = new RelativeLayout(Activity);
            //mensagemLayout.setBackgroundColor(Color.CYAN);
            RelativeLayout.LayoutParams paramsCenter = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
            mensagemLayout.setLayoutParams(paramsCenter);
            mensagemLayout.setPadding(100,10,0,10);
            //mensagemLayout.setGravity(Gravity.CENTER);
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP,100);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //paramsCenter.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            /////////////define o layout como a view principal
            RelativeLayout mensagemLayout2 = new RelativeLayout(Activity);
            RelativeLayout.LayoutParams paramsCenter2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
            mensagemLayout2.setLayoutParams(paramsCenter2);
            mensagemLayout2.setGravity(Gravity.RIGHT);
            /////////////define o layout como a view principal
//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
            LinearLayout mensagemLayout23 = new LinearLayout(Activity);
            mensagemLayout23.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            mensagemLayout23.setOrientation(LinearLayout.VERTICAL);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

            //TextView QUEM
            TextView textView_quem =  new TextView(Activity);
            textView_quem.setPadding(5,5,5,5);
            textView_quem.setText( "Você " + titulo );
            //textView_quem.setTypeface(null, Typeface.BOLD);
            //textView_quem.setTypeface(null, Typeface.ITALIC);
            //textView_quem.setTypeface(null, Typeface.BOLD_ITALIC);
            textView_quem.setBackgroundColor(Color.parseColor("#ffffff"));
            mensagemLayout23.addView(textView_quem);

            //TextView mensagem
            TextView textView_mensagem =  new TextView(Activity);
            textView_mensagem.setPadding(5,5,5,5);
            textView_mensagem.setTextSize(18);
            textView_mensagem.setText( msg );
            textView_mensagem.setTypeface(textView_mensagem.getTypeface(), Typeface.BOLD);
            textView_mensagem.setBackgroundColor(Color.parseColor("#e3f1fc"));
            mensagemLayout23.addView(textView_mensagem);

            mensagemLayout2.addView(mensagemLayout23);

            mensagemLayout.addView(mensagemLayout2);

            relative_layout_center.addView( mensagemLayout );

            try{

                scrollView22.post(
                        new Runnable() { public void run() {

                            scrollView22.fullScroll(View.FOCUS_DOWN);
                        }
                        });
            } catch(Exception e){ }


        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void mensagem_esquerda(String msg, String titulo) {
        try {
            //<!-- Center aligned to bottom -->
            RelativeLayout mensagemLayout = new RelativeLayout(Activity);
            //mensagemLayout.setBackgroundColor(Color.CYAN);
            RelativeLayout.LayoutParams paramsCenter = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
            mensagemLayout.setLayoutParams(paramsCenter);
            mensagemLayout.setPadding(0,10,100,10);
            //mensagemLayout.setGravity(Gravity.CENTER);
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP,100);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //paramsCenter.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            /////////////define o layout como a view principal
            RelativeLayout mensagemLayout2 = new RelativeLayout(Activity);
            RelativeLayout.LayoutParams paramsCenter2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
            mensagemLayout2.setLayoutParams(paramsCenter2);
            mensagemLayout2.setGravity(Gravity.LEFT);
            /////////////define o layout como a view principal
//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
            LinearLayout mensagemLayout23 = new LinearLayout(Activity);
            mensagemLayout23.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            mensagemLayout23.setOrientation(LinearLayout.VERTICAL);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

            //TextView QUEM
            TextView textView_quem =  new TextView(Activity);
            textView_quem.setPadding(5,5,5,5);
            textView_quem.setText( nome.toUpperCase() + " "+titulo.trim() );
            textView_quem.setTypeface(textView_quem.getTypeface(), Typeface.BOLD);
            textView_quem.setBackgroundColor(Color.parseColor("#ffffff"));
            mensagemLayout23.addView(textView_quem);

            //TextView mensagem
            TextView textView_mensagem =  new TextView(Activity);
            textView_mensagem.setPadding(5,5,5,5);
            textView_mensagem.setTextSize(18);
            textView_mensagem.setText( msg );
            textView_mensagem.setTypeface(textView_mensagem.getTypeface(), Typeface.BOLD);
            textView_mensagem.setBackgroundColor(Color.parseColor("#FFFFE0"));
            mensagemLayout23.addView(textView_mensagem);

            mensagemLayout2.addView(mensagemLayout23);

            mensagemLayout.addView(mensagemLayout2);

            relative_layout_center.addView( mensagemLayout );

            try{

                scrollView22.post(
                        new Runnable() { public void run() {

                            scrollView22.fullScroll(View.FOCUS_DOWN);
                        }
                        });
            } catch(Exception e){ }

        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void mensagem_esquerda22222(String msg) {
        try {
            //<!-- Center aligned to bottom -->
            RelativeLayout mensagemLayout = new RelativeLayout(Activity);
            //mensagemLayout.setBackgroundColor(Color.CYAN);
            RelativeLayout.LayoutParams paramsCenter = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
            mensagemLayout.setLayoutParams(paramsCenter);
            mensagemLayout.setPadding(0,10,0,10);
            //mensagemLayout.setGravity(Gravity.CENTER);
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP,100);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //paramsCenter.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            /////////////define o layout como a view principal
            RelativeLayout mensagemLayout2 = new RelativeLayout(Activity);
            //mensagemLayout2.setBackgroundColor(Color.CYAN);
            RelativeLayout.LayoutParams paramsCenter2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
            mensagemLayout2.setLayoutParams(paramsCenter2);
/////////////define o layout como a view principal

            //EditText
            TextView usernameCenter =  new TextView(Activity);
            usernameCenter.setPadding(5,5,5,5);
            usernameCenter.setText( msg );
            usernameCenter.setTypeface(usernameCenter.getTypeface(), Typeface.BOLD);

            usernameCenter.setBackgroundColor(Color.parseColor("#FFFFE0"));
            mensagemLayout2.setPadding(10,0,100,0);
            mensagemLayout2.setGravity(Gravity.LEFT);

            mensagemLayout2.addView(usernameCenter);

            mensagemLayout.addView(mensagemLayout2);

            relative_layout_center.addView( mensagemLayout );

            //for( int i =0; i < 15; i++ ) {
            //linha_inserir_texto("Hello", true);
            //linha_inserir_texto("Hello", false);
            //}
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    int contador22 = 0;
    private void linha_inserir_texto(final String txt, final boolean esquerdaDireita) {
//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout3 = new LinearLayout(Activity);
        LinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout3.setOrientation(LinearLayout.HORIZONTAL);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 2 Nome e Email///////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout2 = new LinearLayout(Activity);
        LinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        LinearLayout2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout2.setGravity(Gravity.CENTER_VERTICAL);

        //LinearLayout2.setBackgroundColor(parseColor("#F2F2F2"));

        // Inserir Texto
        TextView textView = new TextView(Activity);
        if( esquerdaDireita == true ) {
            textView.setBackgroundColor(Color.parseColor("#e3f1fc"));

            LinearLayout2.setPadding(100,0,0,0);
        }
        else{
            textView.setBackgroundColor(Color.parseColor("#FFFFE0"));

            LinearLayout2.setPadding(0,0,100,0);
        }

        textView.setPadding(25,25,25,25);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        textView.setText( txt );
        LinearLayout2.addView(textView);
//PREENCHER Layout 2////////////////////////////////////////////////////////////////////////////////

//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////
        LinearLayout3.addView(LinearLayout2);
//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////


//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        relative_layout_center.addView( LinearLayout3 );
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        /*    }
        }.start();
        */
    }

    RelativeLayout LinearLayoutXML;
    private void preencher_layout() {
        try {

///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            LinearLayoutXML = (RelativeLayout) Activity.findViewById(R.id.header);
            //LinearLayoutXML = new RelativeLayout( Activity );
            //define a orientação do leiaute
            //LinearLayoutXML.setOrientation(LinearLayout.VERTICAL);
            //define os parâmetros do leiaute
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            LinearLayoutXML.setLayoutParams(params);

            //LinearLayoutXML.setBackgroundColor(Color.parseColor("#FFFFE0"));
///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////

/////////////define o layout como a view principal
            //Activity.setContentView(LinearLayoutXML);
/////////////define o layout como a view principal
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    RelativeLayout RelativeLayout_Topo;
    @SuppressLint("ResourceType")
    private void relativeLayout_Topo() {
        //Layout
        RelativeLayout atulsLayout = new RelativeLayout(Activity);
        atulsLayout.setBackgroundColor(Color.GREEN);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
        atulsLayout.setLayoutParams(params);

        //<!-- Header aligned to top -->
        RelativeLayout headerLayout = new RelativeLayout(Activity);
        headerLayout.setBackgroundColor(Color.BLUE);
        RelativeLayout.LayoutParams paramsHeader = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        headerLayout.setLayoutParams(paramsHeader);

        //EditText
        EditText usernameTop =  new EditText(Activity);
        usernameTop.setText("TESTE");
        usernameTop.setId(1);
        headerLayout.addView(usernameTop);
        //Add widget to layout(button is now a child of layout)
        atulsLayout.addView(headerLayout);
        //<!-- Header aligned to top -->

        //<!-- Footer aligned to bottom -->
        RelativeLayout footerLayout = new RelativeLayout(Activity);
        footerLayout.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams paramsFooter = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        footerLayout.setLayoutParams(paramsFooter);
        footerLayout.setGravity(Gravity.CENTER);

        //EditText
        EditText usernameFooter =  new EditText(Activity);
        usernameFooter.setText("TESTE");
        usernameFooter.setId(2);
        footerLayout.addView(usernameFooter);
        // position on right bottom
        //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_TOP,100);
        //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //Add widget to layout(button is now a child of layout)
        atulsLayout.addView(footerLayout);
        //<!-- Footer aligned to bottom -->

        //<!-- Content below header and above footer -->
        RelativeLayout centerLayout = new RelativeLayout(Activity);
        centerLayout.setBackgroundColor(Color.RED);
        RelativeLayout.LayoutParams paramsCenter = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
        //give rules to position widgets
        paramsCenter.addRule( RelativeLayout.ABOVE, usernameFooter.getId() );
        paramsCenter.addRule( RelativeLayout.BELOW, usernameTop.getId() );
        centerLayout.setLayoutParams(paramsCenter);
        centerLayout.setGravity(Gravity.CENTER);
        //Add widget to layout(button is now a child of layout)
        atulsLayout.addView(centerLayout);
        //<!-- Content below header and above footer -->

/*
        //Button
        Button redButton = new Button(Activity);
        redButton.setText("Log In");
        redButton.setBackgroundColor(Color.RED);

        //Username input
        EditText username =  new EditText(Activity);

        redButton.setId(1);
        username.setId(2);

        RelativeLayout.LayoutParams buttonDetails= new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams usernameDetails= new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        //give rules to position widgets
        usernameDetails.addRule(RelativeLayout.ABOVE,redButton.getId());
        usernameDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usernameDetails.setMargins(0,0,0,50);

        buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonDetails.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        //Resources r = Activity.getResources();
        //int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,r.getDisplayMetrics());
        //username.setWidth(px);

        //Add widget to layout(button is now a child of layout)
        atulsLayout.addView(redButton,buttonDetails);
        atulsLayout.addView(username,usernameDetails);
*/
        //Set these activities content/display to this view
        Activity.setContentView(atulsLayout);
    }

    RelativeLayout RelativeLayout_001;
    private void linha_001() {
        try {

///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            //LinearLayoutXML = (LinearLayout) Activity.findViewById(R.id.linearLayout_home);
            RelativeLayout_001 = new RelativeLayout( Activity );
            //define a orientação do leiaute
            //LinearLayoutXML.setOrientation(LinearLayout.VERTICAL);
            //define os parâmetros do leiaute
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 100 );
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            RelativeLayout_001.setLayoutParams(params);

            RelativeLayout_001.setBackgroundColor(Color.parseColor("#4F4F4F"));
///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////

/////////////define o layout como a view principal
            RelativeLayout_Topo.addView(RelativeLayout_001);
/////////////define o layout como a view principal
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    RelativeLayout RelativeLayout_Center;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void linha_002() {
        try {

///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            //LinearLayoutXML = (LinearLayout) Activity.findViewById(R.id.linearLayout_home);
            RelativeLayout_Center = new RelativeLayout( Activity );
            //define a orientação do leiaute
            //LinearLayoutXML.setOrientation(LinearLayout.VERTICAL);
            //define os parâmetros do leiaute
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT-200 );
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP,100);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,100);
            RelativeLayout_Center.setLayoutParams(params);

            //RelativeLayout_Center.setLeftTopRightBottom(0,100,0,100);

            RelativeLayout_Center.setBackgroundColor(Color.parseColor("#8B0000"));
///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////

/////////////define o layout como a view principal
            LinearLayoutXML.addView(RelativeLayout_Center);
/////////////define o layout como a view principal
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    RelativeLayout RelativeLayout_Footer;
    private void linha_003() {
        try {

///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            //LinearLayoutXML = (LinearLayout) Activity.findViewById(R.id.linearLayout_home);
            RelativeLayout_Footer = new RelativeLayout( Activity );
            //define a orientação do leiaute
            //LinearLayoutXML.setOrientation(LinearLayout.VERTICAL);
            //define os parâmetros do leiaute
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 100 );
            // position on right bottom
            //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            RelativeLayout_Footer.setLayoutParams(params);

            RelativeLayout_Footer.setBackgroundColor(Color.parseColor("#FFE0FF"));
///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////

/////////////define o layout como a view principal
            LinearLayoutXML.addView(RelativeLayout_Footer);
/////////////define o layout como a view principal
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    LinearLayout LinearLayoutXMLBody;
    private void linha_do_meio() {
//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout3 = new LinearLayout(Activity);
        LinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout3.setGravity(Gravity.RIGHT);

        // CRIA O BOTÃO PARA ADICIONAR NOVO CONTATO
        Button bt_novo_Contato = new Button(Activity);
        bt_novo_Contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir_Activity( OO1_Home.class, nome, email  );
                abrir_Activity(OO9_Abrir_Contatos.class, nome, de_email_remetente);
            }
        });
        bt_novo_Contato.setText("Voltar");
        LinearLayout3.addView(bt_novo_Contato);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 2 Nome e Email///////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout2 = new LinearLayout(Activity);
        LinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        LinearLayout2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout2.setGravity(Gravity.CENTER_VERTICAL);

        TextView textView = new TextView(Activity);
        textView.setText("    " + nome );
        textView.setTextColor(parseColor("#ffffff"));
        LinearLayout2.addView(textView);
//PREENCHER Layout 2////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout1 = new LinearLayout(Activity);
        LinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        LinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout1.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout1.setPadding(1, 2, 0, 0); //Padding( left, top, right, bottom);
        LinearLayout1.setBackgroundColor(parseColor("#008577"));
        //define a cor de fundo do layout
//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
        //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
        ImageView imagem = new ImageView(Activity);
        imagem.setImageResource(R.mipmap.ic_launcher_round);
        imagem.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imagem.setLayoutParams(new LinearLayout.LayoutParams(
                100, LinearLayout.LayoutParams.MATCH_PARENT
        ));

        LinearLayout1.addView(imagem);
        //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////
        LinearLayout1.addView(LinearLayout2);
        LinearLayout1.addView(LinearLayout3);
//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////

//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        LinearLayoutXML.addView(LinearLayout1, contador);
        contador++;
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
    }

    android.widget.ScrollView ScrollViewXML;

    int contador = 0;
    private void inicio() {
        try {

///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            //LinearLayoutXML = (LinearLayout) Activity.findViewById(R.id.linearLayout_home);
            LinearLayout LinearLayoutXML = new LinearLayout(Activity);
            //define a orientação do leiaute
            LinearLayoutXML.setOrientation(LinearLayout.VERTICAL);
            //define os parâmetros do leiaute
            LinearLayoutXML.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            ));
///// LinearLayoutXML //////////////////////////////////////////////////////////////////////////////

///// LinearLayoutXMLBody //////////////////////////////////////////////////////////////////////////
            //cria uma instância do Layout LinearLayout
            //LinearLayoutXMLBody = (LinearLayout) Activity.findViewById(R.id.linearLayout_home);
            LinearLayoutXMLBody = new LinearLayout(Activity);
            //define a orientação do leiaute
            LinearLayoutXMLBody.setOrientation(LinearLayout.VERTICAL);
            //define os parâmetros do leiaute
            LinearLayoutXMLBody.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            ));
///// LinearLayoutXMLBody //////////////////////////////////////////////////////////////////////////

/////////////define o layout como a view principal
            Activity.setContentView(LinearLayoutXML);
/////////////define o layout como a view principal

            //setar_Contatos_do_Properties();
            //linha_do_menu();

            ScrollViewXML = new ScrollView(Activity);

            LinearLayoutXML.addView(ScrollViewXML, contador);
            contador++;

            //define o layout como a view principal
            ScrollViewXML.addView(LinearLayoutXMLBody);

            //setar envio de mensagens;
            InserirDigitarMensagem();

            for( int i =0; i < 10; i++ ) {
                linha_inserir_texto("Hello", true);
                linha_inserir_texto("Hello", false);
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    // Inserir Digitar Mensagem
    private void InserirDigitarMensagem() {

//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout3 = new LinearLayout(Activity);
        LinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout3.setOrientation(LinearLayout.HORIZONTAL);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 2 Nome e Email///////////////////////////////////////////////////////////////////
        // Inserir Texto
        TextView textView = new TextView(Activity);
        textView.setText("abcd");
        textView.setPadding(25,25,25,25);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
//PREENCHER Layout 2////////////////////////////////////////////////////////////////////////////////

//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////
        LinearLayout3.addView(textView);
//ADICIONAR LAYOUT 2 E 3 AO LAYOUT 1////////////////////////////////////////////////////////////////////


//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        LinearLayoutXML.addView( LinearLayout3, contador );
        contador++;
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        /*    }
        }.start();
        */
    }

    private void abrir_Activity(Class Classe_a_Abrir, String nome_user_sis, String email_user_sis) {
        Intent intent = new Intent(Activity, Classe_a_Abrir);

        Bundle bundle = new Bundle();
        bundle.putString("nome", nome_user_sis);
        bundle.putString("email", email_user_sis);
        intent.putExtras(bundle);

        Activity.startActivity(intent);
        Activity.finish();
    }
}

// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class EnviarMensagem extends AsyncTask<String, String, String> {

    private OO8_Conversa OO8_Conversa23;
    String msg_a_enviar = "";

    String nome_destinatario = "";
    String de_email_remetente = "";
    String para_email_destinatario = "";
    String pasta = "";
    String file = "";
    String segundo = "";

    public EnviarMensagem(OO8_Conversa OO8_Conversa2, String msg_a_enviar2, String nome_destinatario2, String de_email_remetente2, String para_email_destinatario2, String pasta2, String file2, String segundo2) {
        this.OO8_Conversa23 = OO8_Conversa2;
        this.msg_a_enviar = msg_a_enviar2;
        this.nome_destinatario = nome_destinatario2;
        this.de_email_remetente = de_email_remetente2.toUpperCase();
        this.para_email_destinatario = para_email_destinatario2.toUpperCase();

        this.pasta = pasta2;
        this.file = file2;
        this.segundo = segundo2;
        /*
        System.out.println( "\n" +
                "\nMensagem - " + msg_a_enviar + "\n" +
                de_email_remetente + " - " + de_email_remetente + " - \n" +
                para_email_destinatario + " - " + para_email_destinatario
        );
        */
    }

    //É onde acontece o processamento
    //Este método é executado em uma thread a parte,
    //ou seja ele não pode atualizar a interface gráfica,
    //por isso ele chama o método onProgressUpdate,
    // o qual é executado pela
    //UI thread.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {

        String jsonDeResposta;

            HttpURLConnection connection = null;
            try {

                ConnectivityManager manager = (ConnectivityManager) OO8_Conversa23.Activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo network = manager.getActiveNetworkInfo();

                boolean isConnected = network != null && network.isConnected() && network.isAvailable();
                String ip2 = network.getTypeName();
                System.out.println( "\n" +
                        "\nip2 " + ip2
                );

                String id_0_formulario = "1FAIpQLSe26aTcu7OC_OnfIiBk9bkoSsI_cps6EDoI71kX64fZNrW6Hw";
                String id_1_formulario_email_de = "1897386233";
                String id_2_formulario_email_para = "1845166634";
                String id_3_sub_item_formulario_mensagem = "283884279";

                String email_remetente = de_email_remetente;
                String email_destinatario = para_email_destinatario;
                //String sub_item_formulario_mensagem = msg_a_enviar;
                String sub_item_formulario_mensagem = URLEncoder.encode(msg_a_enviar, "utf-8");

                String GET_URL = "https://docs.google.com/forms/d/e/" + id_0_formulario + "/formResponse?"
                        + "entry." + id_1_formulario_email_de + "=" + email_remetente
                        + "&entry." + id_2_formulario_email_para + "=" + email_destinatario
                        + "&entry." + id_3_sub_item_formulario_mensagem + "=" + sub_item_formulario_mensagem;

                URL url = new URL(GET_URL);
                URLConnection conn = url.openConnection();

                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                /*
                StringBuilder sb = new StringBuilder();

                String linha = br.readLine();

                while (linha != null) {

                    //System.out.println(linha);
                    sb.append(linha);
                    linha = br.readLine();

                }

                jsonDeResposta = sb.toString();
                */

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
            publishProgress(msg_a_enviar);

            return msg_a_enviar;
        //}while(true);
    }
    // É invocado para fazer uma atualização na
    // interface gráfica
    @Override
    protected void onProgressUpdate(String... values) {
        //OO8_Conversa23.mensagem_direita(values[0]);
        //OO8_Conversa23.playSound();
        try{

            String s = System.getProperty("file.separator");
            File internalStorageDir = this.OO8_Conversa23.Activity.getFilesDir();

            File[] listaDosArquivos2 = new File( this.pasta ).listFiles();
            if ( listaDosArquivos2 != null ) {

                for (int j = 0; j < listaDosArquivos2.length; j++) {

                    String nome_email_destinatario = this.para_email_destinatario.trim().toUpperCase().replace("@", "_");
                    String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
                    String arquivo_properties2 = this.de_email_remetente.trim().toUpperCase().replace("@", "_");
                    String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

                    Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO", segundo );
                    String destino = internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO" + s + segundo; //minuto
                    String arquivoASerCriadoXX = destino + s + email_REMETENTE    + "-" + email_DESTINATARIO + "-" + ".html";
                    File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F( arquivoASerCriadoXX, this.msg_a_enviar, "UTF-8" );
/*
                    Arquivo_Ou_Pasta.copiarArquivoNovoNome(listaDosArquivos2[j],
                            internalStorageDir + s + "00_Externo" + s + "MENSAGENS_RECEBIDAS" + s + email_REMETENTE + s + email_DESTINATARIO + s + "ENVIADO",
                            "eu" + ".html");
                    Arquivo_Ou_Pasta.deletar(listaDosArquivos2[j]);*/
                }
            }

            Arquivo_Ou_Pasta.deletar( new File( this.pasta ) );

            //OO11_Notificacoes OO11_Notificacoes = new OO11_Notificacoes("Nova Mensagem de: "+this.nome_destinatario, this.msg_a_enviar,this.OO8_Conversa23.Activity);
        }catch(Exception e ){}
    }
}

// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class LerMensagem extends AsyncTask<String, String, Void>{

    boolean executarUmaVez = false;

    int proxima_mensagem_a_ler = 1;

    private OO8_Conversa OO8_Conversa;
    private String ip;

    String nome_destinatario = "";
    String de_email_remetente = "";
    String para_email_destinatario = "";

    public LerMensagem(OO8_Conversa OO8_Conversa2, boolean executarUmaVez2, String ip2, String nome_destinatario2, String de_email_remetente2, String para_email_destinatario2) {
        this.OO8_Conversa = OO8_Conversa2;
        this.executarUmaVez = executarUmaVez2;
        this.ip = ip2;
        this.nome_destinatario = nome_destinatario2;
        this.de_email_remetente = de_email_remetente2.toUpperCase();
        this.para_email_destinatario = para_email_destinatario2.toUpperCase();
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
        //do {

            String jsonDeResposta = "";

            HttpURLConnection connection = null;
            try {

                /*
                ConnectivityManager manager = (ConnectivityManager) OO8_Conversa.Activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo network = manager.getActiveNetworkInfo();

                boolean isConnected = network != null && network.isConnected() && network.isAvailable();
                String ip2 = network.getTypeName();
                */

                String id_0_formulario = "1By3w9XL3XDO_Dzi5VomurGnqoMS-_zW552C4-B2SJKo";
                String id_1_Espaco = "A:F";
                String key = "AIzaSyBwiMCywJRFQHuuksWdhqwjOrR5mDaWJYs"; //AIzaSyBxe8Sl3WxpmhMenNBeMeBjKHlVjBahsr8
                String GET_URL = "https://sheets.googleapis.com/v4/spreadsheets/" + id_0_formulario + "/values/" + id_1_Espaco + "?key=" + key;

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

            } catch (Exception e) {
                e.printStackTrace();
                //JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
                //+ e.getMessage() + "\n", "Home" );
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

            try {
                if( this.executarUmaVez == false ) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        //}while(true);
    }
    // É invocado para fazer uma atualização na
    // interface gráfica
    @Override
    protected void onProgressUpdate(String... values) {
        //OO8_Conversa.mensagem_esquerda(values[0]);
        //System.out.println( values[0] );

        if( this.executarUmaVez == true ) {

            //pegar_proxima_mensagem_a_ler(values[0]);
            System.out.println("OO8_Conversa.mostrar_mensagens_iniciais(this.de_email_remetente, this.para_email_destinatario);");
            OO8_Conversa.mostrar_mensagens_iniciais(this.de_email_remetente, this.para_email_destinatario);
        }
        else {
            pegar_proxima_mensagem_a_ler(values[0]);
        }

        OO8_Conversa._ler_da_mensagens(false);
    }

    private void pegar_proxima_mensagem_a_ler(String values) { try {

        String s = System.getProperty("file.separator");
        File internalStorageDir = OO8_Conversa.Activity.getFilesDir();

        String arquivo_properties2 = de_email_remetente.trim().toUpperCase().replace("@", "_");
        String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

        String nome_email_destinatario = para_email_destinatario.trim().toUpperCase().replace("@", "_");
        String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");

        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "LEITURAS_M" );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "LEITURAS_M", email_REMETENTE );
        Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo" + s + "LEITURAS_M" + s + email_REMETENTE, email_DESTINATARIO );

        String properties001 = internalStorageDir + s + "00_Externo" + s + "LEITURAS_M" + s + email_REMETENTE + s + email_DESTINATARIO + ".properties";
        //System.out.println( properties001 );

        File criar = new File( properties001 );
        if ( !criar.exists() ) {

            try{
                Properties properties_zero = new Properties();
                properties_zero.put("ultimo", "1");
                FileOutputStream out = new FileOutputStream( properties001 );
                properties_zero.storeToXML(out, "updated", "UTF-8");
                out.flush();
                out.close();
            } catch( Exception ee ){ }

            proxima_mensagem_a_ler = 1;
            mostrar_mensagem_parte_1( values );
            //System.out.println( "if ( !criar.exists() ) {\n" + properties001 + "\n" );
        }
        else if ( criar.exists() ) {

            abrir_properties( values, properties001 );
            //System.out.println( "File criar.exists()\n" + properties001 + "\n" );
        }

    } catch( Exception e ){} }

    private void abrir_properties(String values, String properties001) {

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
                }
            }

            if( !numero_ultimo_lido.equals("") ) {

                try { atualizar_properties_ultima_linha_lida(false, de_email_remetente, para_email_destinatario); } catch( Exception e ){ }

                //System.out.println( "proxima_mensagem_a_ler - "+proxima_mensagem_a_ler );

                mostrar_mensagem_parte_1( values );
            }

        } catch( Exception e ){}
    }

    private void mostrar_mensagem_parte_1(String json) { try {

        String separacao_1[] = json.split("],");
        for( int i = 1; i < separacao_1.length; i++ ){

            mostrar_mensagem_parte_2( separacao_1[i], i );
            //System.out.println( separacao_1[i] );
        }

    } catch( Exception e ){} }

    private void mostrar_mensagem_parte_2( String json, int i_R ) { try {
        //System.out.println( json );

        String separacao_1[] = json.split(",");
        //System.out.println( separacao_1[0] );

        String data = separacao_1[0].replace("\"", "").replace("[", "");
        //System.out.println( data );
        String de_email = separacao_1[1].replace("\"", "").toUpperCase();
        //System.out.println( de_email );
        String para_email = separacao_1[2].replace("\"", "").toUpperCase();
        //System.out.println( para_email );
        //String mensagem = separacao_1[3].replaceAll("\"    ]  ]}", "\"");
        //String mensagem1 = separacao_1[3].replace("\"", "");
        String mensagem2 = separacao_1[3].replaceAll("]", "");
        String mensagem3 = mensagem2.replace("}", "");

        String str[] = null; try{ str = mensagem3.split("\""); } catch( Exception e ){}

        /*
        System.out.println( "\n" +
                "\nMensagem - " + mensagem3 + "\n" +
                de_email + " - " + de_email_remetente + " - \n" +
                para_email + " - " + para_email_destinatario+ "\n"+
                "\ni_R - " + i_R + "\n" +
                "\nproxima_mensagem_a_ler - " + proxima_mensagem_a_ler + "\n"+
                "\nstr - " + str + "\n"
        );
        */

        if( str != null ){

            String mensagem_a_mostrar = mensagem3.replaceAll("\"", "").trim();

            //System.out.println("if( str != null ){ " + str );

            if( de_email.trim().equalsIgnoreCase( de_email_remetente.trim() )  ){

                /*
                StringBuilder sb = new StringBuilder();
                String strX[] = str[1].split( "__" );
                for( int i = 0; i < strX.length; i++ ){
                    int x = Integer.parseInt( strX[i] );
                    if( x == 10 ){
                        sb.append("<p>");
                    }
                    else{
                        char c = (char) x;
                        sb.append(c);
                    }
                }
                */

                //System.out.println("IF mensagem3 " + mensagem_a_mostrar );

                if( i_R >= proxima_mensagem_a_ler ){

                    try { atualizar_properties_ultima_linha_lida(true, de_email_remetente, para_email_destinatario); } catch( Exception e ){ }
                    //OO8_Conversa.mensagem_direita( mensagem_a_mostrar, "" );
                }
                else{

                    if( this.executarUmaVez == true ) {

                        //OO8_Conversa.mensagem_direita( mensagem_a_mostrar, "");
                    }
                }
            }
            else if( de_email.trim().equalsIgnoreCase( para_email_destinatario.trim() )  ){

                /*
                StringBuilder sb = new StringBuilder();
                String strX[] = str[1].split( "__" );
                for( int i = 0; i < strX.length; i++ ){
                    int x = Integer.parseInt( strX[i] );
                    if( x == 10 ){
                        sb.append("<p>");
                    }
                    else{
                        char c = (char) x;
                        sb.append(c);
                    }
                }
                */

                //System.out.println("ELSE IF mensagem3 " + mensagem_a_mostrar );

                if( i_R >= proxima_mensagem_a_ler ){

                    try { atualizar_properties_ultima_linha_lida(true, de_email_remetente, para_email_destinatario); } catch( Exception e ){ }
                    OO8_Conversa.mensagem_esquerda( mensagem_a_mostrar, data );
                    OO8_Conversa.playSound();
                    OO8_Conversa.criar_arquivo( mensagem_a_mostrar, de_email_remetente, para_email_destinatario, "RECEBIDO", data);
                }
                else{

                    if( executarUmaVez == true ) {

                        //OO8_Conversa.mensagem_esquerda( mensagem_a_mostrar, "" );
                    }
                }
            }
        }
    } catch( Exception e ){
        //e.printStackTrace();
    } }

    String arquivoASerCriado = "";
    private void atualizar_properties_ultima_linha_lida(boolean atualizar,
        String email_remetente, String email_destinatario ){

        String s = System.getProperty("file.separator");
        File internalStorageDir = OO8_Conversa.Activity.getFilesDir();

        try {

            String arquivo_properties = email_destinatario.trim().toUpperCase().replace("@", "_");
            String email_DESTINATARIO = arquivo_properties.trim().toUpperCase().replace(".", "_");
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s, "00_Externo" );
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir + s + "00_Externo", "LEITURAS_M" );

            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");
            Arquivo_Ou_Pasta.criarPasta( internalStorageDir  + s +  "00_Externo" + s + "LEITURAS_M", email_REMETENTE );
            arquivoASerCriado = internalStorageDir  + s +  "00_Externo" + s + "LEITURAS_M" + s +  email_REMETENTE  + s +  email_DESTINATARIO;

            Properties properties = new Properties();
            FileInputStream in = new FileInputStream( arquivoASerCriado + ".properties" );
            properties.loadFromXML(in);
            in.close();

            Arquivo_Ou_Pasta.deletar( new File( arquivoASerCriado + ".properties" ) );

            Properties properties_zero = new Properties();
            String value = "";
            try{
                for(Enumeration elms = properties.propertyNames(); elms.hasMoreElements();){

                    if( atualizar == true ){
                        String prop = (String)elms.nextElement();
                        value = properties.getProperty(prop);
                        int v = Integer.parseInt( value.trim() );

                        int x = v+=1;
                        int anterior = x;

                        proxima_mensagem_a_ler = anterior;
                        properties_zero.put("ultimo", String.valueOf( anterior ) );
                        //System.out.println("true - atualizar_properties(boolean atualizar){ - prop: " + prop + " - value: " + anterior + " - proxima_mensagem_a_ler - " + proxima_mensagem_a_ler);
                    }
                    else{
                        String prop = (String)elms.nextElement();
                        value = properties.getProperty(prop);
                        int v = Integer.parseInt( value.trim() );

                        proxima_mensagem_a_ler = v;
                        properties_zero.put("ultimo", String.valueOf( v ) );
                        //System.out.println("false - atualizar_properties(boolean atualizar){ - prop: " + prop + " - value: " + v + " - proxima_mensagem_a_ler - " + proxima_mensagem_a_ler );
                    }
                }
            } catch( Exception e ){
                //System.out.println("for(Enumeration elms = properties.propertyNames(); elms.hasMoreElements();){");
                e.printStackTrace();
            }

            FileOutputStream out = new FileOutputStream( arquivoASerCriado + ".properties" );
            properties_zero.storeToXML(out, "updated", "UTF-8");
            out.flush();
            out.close();

        } catch( Exception e ){
            try{
                Properties properties_zero = new Properties();
                properties_zero.put("ultimo", "1");
                FileOutputStream out = new FileOutputStream( arquivoASerCriado + ".properties" );
                properties_zero.storeToXML(out, "updated", "UTF-8");
                out.flush();
                out.close();
            } catch( Exception ee ){ }
        } //} }.start();
    }
}