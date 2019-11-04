package com.jmarysystems.turbo_flyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jm.Arquivo_Ou_Pasta;

import static android.graphics.Color.parseColor;

public class OO8_Conversa extends AppCompatActivity {

    String s = null;
    //String internalStorageDir = System.getProperty("user.dir");
    String internalStorageDir = null;

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

        internalStorageDir = Activity.getFilesDir().getPath();
        s = System.getProperty("file.separator");

        scrollView22 = (ScrollView) findViewById(R.id.scrollcentro);

        preencher_layout_topo();
        linha_do_menu_add();
        linha_do_footer_add();
        //mensagem_direita("mensagem_direita");
        //mensagem_esquerda("mensagem_esquerda");


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Turbo_Flyer_Auto_Leitura_Offline
                try{
                    String nome_email_destinatario = para_email_destinatario.trim().toUpperCase().replace("@", "_");
                    String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
                    String arquivo_properties2 = de_email_remetente.trim().toUpperCase().replace("@", "_");
                    String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

                    String endereco_txt_mensagens = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO;

                    Arquivo_Ou_Pasta.criarPasta(endereco_txt_mensagens, "MENSAGENS_TXT");
                    Arquivo_Ou_Pasta.deletar_Todos_Arquivos_da_Pasta( new File(endereco_txt_mensagens+ s+ "MENSAGENS_TXT") );

                    File file_x = Arquivo_Ou_Pasta.criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F(
                            endereco_txt_mensagens + s + "MENSAGENS_TXT" + s + "txt" + ".txt", "MENSAGENS_TXT\n", "UTF-8");

                } catch (Exception e) {}

                _ler_da_mensagens(0,true);
            }
        }, 1);
    }

    boolean estado_da_tela = true;

    public void _ler_da_mensagens(int contador, boolean executarUmaVez){
        try{

            if( estado_da_tela == true ) {

                String ip = "http://192.168.0.100:8000/";

                LerMensagem LerMensagem = new LerMensagem(
                        this,
                        Activity,
                        de_email_remetente,
                        para_email_destinatario,
                        contador, executarUmaVez);
                LerMensagem.execute(ip);
            }

        } catch( Exception e ){
            e.printStackTrace();
            //JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
            //+ e.getMessage() + "\n", "Home" );
        }
    }

    public void _envio_da_mensagem(String msg_a_enviar){
        try{

            ////////dd.MM.yyyy HH:mm:ss/////////////EEEE, d MMMM yyyy HH:mm:ss
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

            String dataCadastro = "";
            //DateFormat dfmt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try{ dataCadastro = dfmt.format(currentTimestamp);  }catch(Exception e){}
            ////////dd.MM.yyyy HH:mm:ss/////////////EEEE, d MMMM yyyy HH:mm:ss

            Turbo_Flyer_Auto_Enviar_Offline Turbo_Flyer_Auto_Enviar_Offline = new Turbo_Flyer_Auto_Enviar_Offline(
                    Activity,
                    msg_a_enviar,
                    dataCadastro,
                    de_email_remetente,
                    para_email_destinatario
            );

        } catch( Exception e ){ e.printStackTrace(); }
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
        Button bt_voltar_Contato = new Button(Activity);
        bt_voltar_Contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir_Activity( OO1_Home.class, nome, email  );
                estado_da_tela = false;
                abrir_Activity(OO9_Abrir_Contatos.class, nome, de_email_remetente);
            }
        });
        bt_voltar_Contato.setText("Voltar");
        LinearLayout3.addView(bt_voltar_Contato);
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

                        _envio_da_mensagem(msg_a_enviar);

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

// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class LerMensagem extends AsyncTask<String, String, Void> {

        String s = null;
        String internalStorageDir = null;
        Context HomeAndroid;
        OO8_Conversa OO8_Conversa2;

        String email_remetenteX;
        String email_destinatarioX;
        int contadorX = 0;
        boolean primeira_vez = false;

        public LerMensagem(
                OO8_Conversa OO8_Conversa3,
                Context HomeAndroidX,

                String email_remetente,
                String email_destinatario,
                int contadorR,
                boolean primeira_vez2) {

            HomeAndroid = HomeAndroidX;
            s = System.getProperty("file.separator");
            internalStorageDir = HomeAndroidX.getFilesDir().getPath();
            OO8_Conversa2 = OO8_Conversa3;

            email_remetenteX = email_remetente;
            email_destinatarioX = email_destinatario;
            contadorX = contadorR;
            primeira_vez = primeira_vez2;
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

            //System.out.println( "\n\n Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
            //System.out.println( "internalStorageDir - " + internalStorageDir + " - contadorX - " + contadorX + " - primeira_vez - " + primeira_vez);
            //System.out.println( "Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");

            try {
                if( this.primeira_vez == false ) {
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//////// pegar_Endereco_Da_pasta_Da_Mensagem ////////////////////////////////////////////////////////////////////////////////////////////

            pegar_Endereco_Da_pasta_Da_Mensagem(
                    email_remetenteX,
                    email_destinatarioX,
                    contadorX
            );

//////// pegar_Endereco_Da_pasta_Da_Mensagem ////////////////////////////////////////////////////////////////////////////////////////////

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
        protected void onProgressUpdate(String... values) {

            try{
                /*
                pegar_Endereco_Da_pasta_Da_Mensagem(
                        email_remetenteX,
                        email_destinatarioX,
                        contadorX
                );
                */
            } catch( Exception e ){}

            try{

                OO8_Conversa2._ler_da_mensagens( contadorX, false );
            } catch( Exception e ){}
        }

    private void pegar_Endereco_Da_pasta_Da_Mensagem(
            String email_remetente,
            String email_destinatario,
            int contador) {

        contadorX = contador;

        //System.out.println( "\n\n pegar_Endereco_Da_pasta_Da_Mensagem - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
        //System.out.println( "email_remetente - " + email_remetente + " - email_destinatario - " + email_destinatario + " - contador - " + contador);
        //System.out.println( "pegar_Endereco_Da_pasta_Da_Mensagem - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");

        String email_DESTINATARIO = "";
        String email_REMETENTE = "";

        List<String[]> lista_de_mensagens_nao_lidas = new ArrayList<String[]>();
        try {
            String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
            email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

            String destino = internalStorageDir +s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+ s + email_REMETENTE + s + email_DESTINATARIO;
            //System.out.println( "\n\n pegar_Endereco_Da_pasta_Da_Mensagem - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
            //System.out.println( "email_remetente - " + email_remetente );
            //System.out.println( "email_destinatario - " + email_destinatario );
            //System.out.println( "contador - " + contador );
            //System.out.println( "internalStorageDir - " + internalStorageDir );
            //System.out.println( "destino - " + destino );
            //System.out.println( "pegar_Endereco_Da_pasta_Da_Mensagem - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");

            File diretorio = new File( destino );
            File[] pasta_do_remetente_destinatario = diretorio.listFiles();
            if ( pasta_do_remetente_destinatario != null ) {
                    /*
                    System.out.println( "\n\n pegar_Endereco_Da_pasta_Da_Mensagem - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                    System.out.println( "email_remetente - " + email_remetente );
                    System.out.println( "email_destinatario - " + email_destinatario );
                    System.out.println( "contador - " + contador );
                    System.out.println( "internalStorageDir - " + internalStorageDir );
                    System.out.println( "destino - " + destino );
                    System.out.println( "if ( lista_data_pasta_da_msg_recebida != null ) {" );
                    System.out.println( "quantidade de arquivos - " + lista_data_pasta_da_msg_recebida.length );
                    System.out.println( "pegar_Endereco_Da_pasta_Da_Mensagem - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");
                    */
                //for (int j=0; j < lista_data_pasta_da_msg_recebida.length; j++) {
                int count_recebido = contador;
                for (int j=count_recebido; j < pasta_do_remetente_destinatario.length; j++) {

                    if( primeira_vez == true ){
                        System.out.println( "\nFor ( J >= contador ) { - " + "J - " + j + " - contador - " + contador );

                        System.out.println( "\nprimeira_vez == true");

                        for (int r=0; r < pasta_do_remetente_destinatario.length; r++) {
                            //
                            contador += 1;
                            contadorX = contador;

                            String nome_Da_pasta_Da_Mensagem_Data = pasta_do_remetente_destinatario[r].getName().trim().toUpperCase();
                            String endereco_Da_pasta_Da_Mensagem_Data = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO + s + pasta_do_remetente_destinatario[r].getName().trim();
                            //System.out.println( "\nnome_Da_pasta_Da_Mensagem_Data - " + nome_Da_pasta_Da_Mensagem_Data);
                            //System.out.println( "\nendereco_Da_pasta_Da_Mensagem_Data - " + endereco_Da_pasta_Da_Mensagem_Data);
                            File data_diretorio2 = new File( endereco_Da_pasta_Da_Mensagem_Data );
                            File[] data_lista_pasta_Da_Mensagem = data_diretorio2.listFiles();
                            for (int i=0; i < data_lista_pasta_Da_Mensagem.length; i++) {

                                String subnome_Da_pasta_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].getName().trim().toUpperCase();
                                String endereco_recebido_e_nao_lido = endereco_Da_pasta_Da_Mensagem_Data + s + subnome_Da_pasta_Da_Mensagem;
                                if ( !subnome_Da_pasta_Da_Mensagem.equals("ENVIADO") ) {
                                    //System.out.println( "\nsubnome_Da_pasta_Da_Mensagem - " + subnome_Da_pasta_Da_Mensagem);
                                    //System.out.println( "\nendereco_recebido_e_nao_lido - " + endereco_recebido_e_nao_lido);
                                    File[] data_lista_arquivos_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].listFiles();
                                    for (int x = 0; x < data_lista_arquivos_Da_Mensagem.length; x++) {

                                        String nome_do_arquivo = data_lista_arquivos_Da_Mensagem[i].getName().trim().toUpperCase();
                                        String endereco_do_arquivo = data_lista_arquivos_Da_Mensagem[i].getPath().trim().toUpperCase();
                                        //System.out.println( "\nnome_do_arquivo - " + nome_do_arquivo);
                                        //System.out.println( "\nendereco_do_arquivo - " + endereco_do_arquivo);

                                        String str_para_extencao = new File( endereco_recebido_e_nao_lido ).getPath() + s + new File( data_lista_arquivos_Da_Mensagem[x].getName() ).getPath();
                                        String extencao_do_arquivo = str_para_extencao.substring( str_para_extencao.lastIndexOf('.') + 1 );
                                        //System.out.println( "\nstr_para_extencao - " + str_para_extencao);
                                        //System.out.println( "\nextencao_do_arquivo - " + extencao_do_arquivo);

                                        if( extencao_do_arquivo.equalsIgnoreCase( "html" ) ){

                                            //System.out.println( "\nstr_para_extencao - " + str_para_extencao);
                                            //System.out.println( "\nextencao_do_arquivo - " + extencao_do_arquivo);
                                            filtrarTipoArquivos__iniciais_2(
                                                    OO8_Conversa2,
                                                    HomeAndroid,
                                                    data_lista_arquivos_Da_Mensagem[x],
                                                    nome_Da_pasta_Da_Mensagem_Data,
                                                    email_remetente,
                                                    email_destinatario );

                                            try {

                                                boolean nl = true;
                                                String endereco_txt_mensagens = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO + s + "MENSAGENS_TXT" + s + "txt" + ".txt";

                                                BufferedWriter out = new BufferedWriter(new FileWriter(endereco_txt_mensagens ,true));
                                                out.write(nome_Da_pasta_Da_Mensagem_Data);
                                                if(nl) { out.newLine(); }

                                                out.close();
                                            } catch (Exception e) {}
                                        }
                                    }
                                }
                            }
                        }


                            /*
                            File data_diretorio2 = new File( endereco_Da_pasta_Da_Mensagem_Data );
                            File[] data_lista_pasta_Da_Mensagem = data_diretorio2.listFiles();
                            for (int i=0; i < data_lista_pasta_Da_Mensagem.length; i++) {
                                String subnome_Da_pasta_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].getName().trim().toUpperCase();
                                String endereco_recebido_e_nao_lido = endereco_Da_pasta_Da_Mensagem_Data + s + subnome_Da_pasta_Da_Mensagem;
                                if ( !subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase("ENVIADO") ) {
                                    File[] data_lista_arquivos_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].listFiles();
                                    for (int x = 0; x < data_lista_arquivos_Da_Mensagem.length; x++) {
                                        String str_para_extencao = new File( endereco_recebido_e_nao_lido ).getPath() + s + new File( data_lista_arquivos_Da_Mensagem[x].getName() ).getPath();
                                        String extencao_do_arquivo = str_para_extencao.substring( str_para_extencao.lastIndexOf('.') + 1 );
                                        if( extencao_do_arquivo.equalsIgnoreCase( "html" ) ){
                                            filtrarTipoArquivos__iniciais_2( Email_Mensagens_Por_Contato, data_lista_arquivos_Da_Mensagem[x], lista_de_mensagens_nao_lidas.get(j)[1], email_remetente, email_destinatario );
                                        }
                                    }
                                }
                            }
                            */
                        break;
                    }
                    else {

                        System.out.println( "\nFor ( J >= contador ) { - " + "J - " + j + " - contador - " + contador );

                        System.out.println( "\nprimeira_vez == false");

                        boolean umaVez = false;

                        for (int r=0; r < pasta_do_remetente_destinatario.length; r++) {

                            String nome_Da_pasta_Da_Mensagem_Data = pasta_do_remetente_destinatario[r].getName().trim().toUpperCase();
                            if( !nome_Da_pasta_Da_Mensagem_Data.equals("MENSAGENS_TXT") ){
                                if( verificar_se_arquivo_tem_string( nome_Da_pasta_Da_Mensagem_Data, email_remetente, email_destinatario) == false ){
                                    //
                                    if( umaVez == false ){
                                        umaVez = true;
                                        contador += 1;
                                        contadorX = contador;
                                    }
                                    String endereco_Da_pasta_Da_Mensagem_Data = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO + s + pasta_do_remetente_destinatario[r].getName().trim();
                                    //System.out.println( "\nnome_Da_pasta_Da_Mensagem_Data - " + nome_Da_pasta_Da_Mensagem_Data);
                                    //System.out.println( "\nendereco_Da_pasta_Da_Mensagem_Data - " + endereco_Da_pasta_Da_Mensagem_Data);
                                    File data_diretorio2 = new File( endereco_Da_pasta_Da_Mensagem_Data );
                                    File[] data_lista_pasta_Da_Mensagem = data_diretorio2.listFiles();
                                    for (int i=0; i < data_lista_pasta_Da_Mensagem.length; i++) {

                                        String subnome_Da_pasta_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].getName().trim().toUpperCase();
                                        String endereco_recebido_e_nao_lido = endereco_Da_pasta_Da_Mensagem_Data + s + subnome_Da_pasta_Da_Mensagem;
                                        if ( !subnome_Da_pasta_Da_Mensagem.equals("ENVIADO") ) {
                                            if ( !subnome_Da_pasta_Da_Mensagem.equals("ENVIADO_ONLINE") ) {
                                                //System.out.println( "\nsubnome_Da_pasta_Da_Mensagem - " + subnome_Da_pasta_Da_Mensagem);
                                                //System.out.println( "\nendereco_recebido_e_nao_lido - " + endereco_recebido_e_nao_lido);
                                                File[] data_lista_arquivos_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].listFiles();
                                                for (int x = 0; x < data_lista_arquivos_Da_Mensagem.length; x++) {

                                                    String nome_do_arquivo = data_lista_arquivos_Da_Mensagem[i].getName().trim().toUpperCase();
                                                    String endereco_do_arquivo = data_lista_arquivos_Da_Mensagem[i].getPath().trim().toUpperCase();
                                                    //System.out.println( "\nnome_do_arquivo - " + nome_do_arquivo);
                                                    //System.out.println( "\nendereco_do_arquivo - " + endereco_do_arquivo);

                                                    String str_para_extencao = new File( endereco_recebido_e_nao_lido ).getPath() + s + new File( data_lista_arquivos_Da_Mensagem[x].getName() ).getPath();
                                                    String extencao_do_arquivo = str_para_extencao.substring( str_para_extencao.lastIndexOf('.') + 1 );
                                                    //System.out.println( "\nstr_para_extencao - " + str_para_extencao);
                                                    //System.out.println( "\nextencao_do_arquivo - " + extencao_do_arquivo);

                                                    if( extencao_do_arquivo.equalsIgnoreCase( "html" ) ){

                                                        //System.out.println( "\nstr_para_extencao - " + str_para_extencao);
                                                        //System.out.println( "\nextencao_do_arquivo - " + extencao_do_arquivo);
                                                        filtrarTipoArquivos__iniciais_2(
                                                                OO8_Conversa2,
                                                                HomeAndroid,
                                                                data_lista_arquivos_Da_Mensagem[x],
                                                                nome_Da_pasta_Da_Mensagem_Data,
                                                                email_remetente,
                                                                email_destinatario );

                                                        try {

                                                            boolean nl = true;
                                                            String endereco_txt_mensagens = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO + s + "MENSAGENS_TXT" + s + "txt" + ".txt";

                                                            BufferedWriter out = new BufferedWriter(new FileWriter(endereco_txt_mensagens ,true));
                                                            out.write(nome_Da_pasta_Da_Mensagem_Data);
                                                            if(nl) { out.newLine(); }

                                                            out.close();
                                                        } catch (Exception e) {}
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        break;
                    }
/*
                        if ( lista_data_pasta_da_msg_recebida[j].isDirectory()  ) {

                            // PARA NÃO REPETIR A INCLUSÃO DAS MESMAS MENSAGENS AO ABRIR
                            if ( j >= contador ) {
                                contador += 1;

                                System.out.println( "\nif ( J >= contador ) { - " + "J - " + j + " - contador - " + contador );

                                String nome_Da_pasta_Da_Mensagem_Data = lista_data_pasta_da_msg_recebida[j].getName().trim().toUpperCase();
                                //System.out.println( "pasta_Data_Mensagem - " + nome_Da_pasta_Da_Mensagem_Data );
                                /*
                                System.out.println( "\n\n pegar_Endereco_Da_pasta_Da_Mensagem - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                                System.out.println( "email_remetente - " + email_remetente );
                                System.out.println( "email_destinatario - " + email_destinatario );
                                System.out.println( "contador - " + contador );
                                System.out.println( "internalStorageDir - " + internalStorageDir );
                                System.out.println( "destino - " + destino );
                                System.out.println( "if ( lista_data_pasta_da_msg_recebida != null ) {" );
                                System.out.println( "quantidade de arquivos - " + lista_data_pasta_da_msg_recebida.length );
                                System.out.println( "Arquivo - " + j + " - " + lista_data_pasta_da_msg_recebida[j].getPath() );
                                System.out.println( "pegar_Endereco_Da_pasta_Da_Mensagem - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");
                                */
/*                                String endereco_Da_pasta_Da_Mensagem_Data = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO + s + lista_data_pasta_da_msg_recebida[j].getName().trim();

                                File data_diretorio2 = new File( endereco_Da_pasta_Da_Mensagem_Data );
                                File[] data_lista_pasta_Da_Mensagem = data_diretorio2.listFiles();
                                if ( data_lista_pasta_Da_Mensagem != null ) {

                                    for (int i=0; i < data_lista_pasta_Da_Mensagem.length; i++) {
                                        /*
                                        System.out.println( "\n\n pegar_Endereco_Da_pasta_Da_Mensagem - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                                        System.out.println( "email_remetente - " + email_remetente );
                                        System.out.println( "email_destinatario - " + email_destinatario );
                                        System.out.println( "contador - " + contador );
                                        System.out.println( "internalStorageDir - " + internalStorageDir );
                                        System.out.println( "destino - " + destino );
                                        System.out.println( "if ( lista_data_pasta_da_msg_recebida != null ) {" );
                                        System.out.println( "quantidade de arquivos - " + lista_data_pasta_da_msg_recebida.length );
                                        System.out.println( "Pasta - " + j + " - " + lista_data_pasta_da_msg_recebida[j].getPath() );
                                        System.out.println( "Arquivo - " + i + " - " + data_lista_pasta_Da_Mensagem[i].getPath() );
                                        System.out.println( "pegar_Endereco_Da_pasta_Da_Mensagem - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");
                                        */
/*                                        String subnome_Da_pasta_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].getName().trim().toUpperCase();
                                        String endereco_recebido_e_nao_lido = endereco_Da_pasta_Da_Mensagem_Data + s + subnome_Da_pasta_Da_Mensagem;

                                        boolean continuar = false;

                                        if ( subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase("RECEBIDO_E_NAO_LIDO") ) {
                                            System.out.println( "\nif ( subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase(\"RECEBIDO_E_NAO_LIDO\") ) {" + contador  );

                                            continuar = true;
                                        } else if ( subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase("ENVIADO_ONLINE") ) {
                                            System.out.println( "\n} else if ( subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase(\"ENVIADO_ONLINE\") ) { - " + contador  );

                                            if( primeira_vez == true ){

                                                continuar = true;
                                            }
                                        } else if ( subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase("ENVIADO") ) {

                                            System.out.println( "\nif ( subnome_Da_pasta_Da_Mensagem.equalsIgnoreCase(\"ENVIADO\") ) { - " + contador  );
                                            System.out.println( "continuar = false;" + contador  );
                                        }

                                        if ( continuar == true ) {
                                            String[] strArray = new String[3];
                                            strArray[0] = endereco_recebido_e_nao_lido;
                                            strArray[1] = nome_Da_pasta_Da_Mensagem_Data;
                                            strArray[2] = endereco_Da_pasta_Da_Mensagem_Data;

                                            lista_de_mensagens_nao_lidas.add( strArray );

/////////////////////////////////////////////arquivos /////////////////////////////////////////////
                                            File[] data_lista_arquivos_Da_Mensagem = data_lista_pasta_Da_Mensagem[i].listFiles();
                                            for (int x = 0; x < data_lista_arquivos_Da_Mensagem.length; x++) {


                                             System.out.println( "\n\n pegar_Endereco_Da_pasta_Da_Mensagem - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                                             System.out.println( "email_remetente - " + email_remetente );
                                             System.out.println( "email_destinatario - " + email_destinatario );
                                             System.out.println( "contador - " + contador );
                                             System.out.println( "internalStorageDir - " + internalStorageDir );
                                             System.out.println( "destino - " + destino );
                                             System.out.println( "if ( lista_data_pasta_da_msg_recebida != null ) {" );
                                             System.out.println( "quantidade de arquivos - " + lista_data_pasta_da_msg_recebida.length );
                                             System.out.println( "Pasta - " + j + " - " + lista_data_pasta_da_msg_recebida[j].getPath() );
                                             System.out.println( "Subpasta - " + i + " - " + data_lista_pasta_Da_Mensagem[i].getPath() );
                                             System.out.println( "Arquivo - " + x + " - " + data_lista_arquivos_Da_Mensagem[x].getPath() );
                                             System.out.println( "pegar_Endereco_Da_pasta_Da_Mensagem - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");

                                                try{
                                                    String str_para_extencao = new File( endereco_recebido_e_nao_lido ).getPath() + s + new File( data_lista_arquivos_Da_Mensagem[x].getName() ).getPath();
                                                    String extencao_do_arquivo = str_para_extencao.substring( str_para_extencao.lastIndexOf('.') + 1 );
                                                    if( extencao_do_arquivo.equalsIgnoreCase( "html" ) ){
                                                        /*
                                                        System.out.println( "HTML - extencao_do_arquivo - " + extencao_do_arquivo );
                                                        System.out.println( "Arquivo - " + x + " - " + data_lista_arquivos_Da_Mensagem[x].getPath() );
                                                        System.out.println( "HTML - extencao_do_arquivo - " + extencao_do_arquivo );
                                                        */
 /*                                                   }
                                                } catch( Exception e ){}
                                            }

                                        }
                                    }
                                }
                                //}
                            }
                            else{

                                //System.out.println( "\nelse ( J >= contador ) { - " + "J - " + j + " - contador - " + contador );
                            }
                        }
*/
                }
            }
        } catch( Exception e ){}

        //try{

        //    mostrar_arquivo_html__iniciais_1( Email_Mensagens_Por_Contato, lista_de_mensagens_nao_lidas, email_REMETENTE, email_DESTINATARIO, contador );
        //} catch( Exception e ){}

        //Reiniciar
        try {

            //System.out.println( "Fim - Turbo_Flyer_Auto_Leitura_Offline***************************************************\n\n");

            //Thread.sleep( 2000 );
            //Turbo_Flyer_Auto_Leitura_Offline Turbo_Flyer_Auto_Leitura_Offline = new Turbo_Flyer_Auto_Leitura_Offline( Email_Mensagens_Por_Contato, email_remetente, email_destinatario, contador++, false );

        } catch( Exception e ){}
    }

        private void mostrar_arquivo_html__iniciais_1(
                OO8_Conversa OO8_Conversa2,
                Context HomeAndroid,
                List<String[]> lista_de_mensagens_nao_lidas,
                String email_remetente,
                String email_destinatario,
                int contador){

            try{

                for (int j = 0; j < lista_de_mensagens_nao_lidas.size(); j++) {

                    String endereco_recebido_e_nao_lido = lista_de_mensagens_nao_lidas.get(j)[0];
                    String nome_Da_pasta_Da_Mensagem_Data = lista_de_mensagens_nao_lidas.get(j)[1];
                    String endereco_Da_pasta_Da_Mensagem_Data = lista_de_mensagens_nao_lidas.get(j)[2];

                    File[] listaDosArquivos = new File( endereco_recebido_e_nao_lido ).listFiles();

                    try{
                        for (int i=0; i < listaDosArquivos.length; i++) {

                            String str_para_extencao = new File( endereco_recebido_e_nao_lido ).getPath() + s + new File( listaDosArquivos[i].getName() ).getPath();
                            String extencao_do_arquivo = str_para_extencao.substring( str_para_extencao.lastIndexOf('.') + 1 );
                            if( extencao_do_arquivo.equalsIgnoreCase( "html" ) ){

                                filtrarTipoArquivos__iniciais_2( OO8_Conversa2, HomeAndroid, listaDosArquivos[i], lista_de_mensagens_nao_lidas.get(j)[1], email_remetente, email_destinatario );
                            }
                        }

                    } catch( Exception e ){}
                }

            } catch( Exception e ){}

/////////////////////////////////////////////////////////////////////////////////////////////////
            contadorX = contador;
            /*
            try{

                OO8_Conversa2._ler_da_mensagens( contador, false );
            } catch( Exception e ){}
            */
/////////////////////////////////////////////////////////////////////////////////////////////////
        }

        private void filtrarTipoArquivos__iniciais_2(
                OO8_Conversa OO8_Conversa2,
                Context HomeAndroid,
                File file_recebido,
                String nome_Da_pasta_Da_Mensagem,
                String email_remetente,
                String email_destinatario) {

            try {

///////////////////////////////////////////////////////////////////////////////
                //Lendo arquivo
                if ( file_recebido.isFile() == true ) {

                    // abertura do arquivo
                    BufferedReader bR = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file_recebido), "UTF-8"));
                    StringBuilder sb = new StringBuilder();

                    String linha;
                    while ((linha = bR.readLine()) != null) {

                        //System.out.println( linha );
                        sb.append(linha);
                        //String decodedUrl = java.net.URLDecoder.decode(linha, "UTF-8");
                        //sb.append(decodedUrl);
                    }
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
                    // codePointAt para String
                    StringBuilder sb_final = new StringBuilder();
                    String parte_01[] = sb.toString().trim().split("_");
                    for (int i = 0; i < parte_01.length; i++) {

                        int codePointAt = Integer.parseInt( parte_01[i] );
                        // converting to char[] pair
                        char[] charPair = Character.toChars(codePointAt);
                        // and to String, containing the character we want
                        String symbol = new String(charPair);

                        sb_final.append(symbol);

                        //Transformar unicode para utf
                        if (Character.charCount(codePointAt) == 1) {

                            System.out.println( "\n\n filtrarTipoArquivos__iniciais_2 - Início - Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                            System.out.println( " String Completa - " + sb.toString() );
                            System.out.println( " String parte_01[i] - " + parte_01[i] );
                            System.out.println( " codePointAt - " + codePointAt );
                            System.out.println( " char[] charPair - " + charPair );
                            System.out.println( " Symbol - " + symbol );
                            System.out.println( " filtrarTipoArquivos__iniciais_2 - Fim - Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");
                        } else {
                            //return new String(Character.toChars(codePoint));
                        }
                    }
////////////////////////////////////////////////////////////////////////////////

                    //String mensagemSX = sb.toString().trim();
                    String lineSeparator = System.getProperty("line.separator");
                    String mensagemSX = sb_final.toString().trim().replaceAll("__@jm@quebra_linha@jm@__", lineSeparator );

                    String datas[] = nome_Da_pasta_Da_Mensagem.trim().split("_");
                    String nome_data_hora_mensagem = datas[2] + "/" + datas[1] + "/" + datas[0] + " " + datas[3] + ":" + datas[4] + ":" + datas[5];

                    String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
                    String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
                    String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
                    String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

                    String remetente_e_destinatario = email_REMETENTE + "-" + email_DESTINATARIO + "-";
                    String destinatario_e_remetente = email_DESTINATARIO + "-" + email_REMETENTE + "-";

                    /*
                    System.out.println( "\n\n filtrarTipoArquivos__iniciais_2 - Início Turbo_Flyer_Auto_Leitura_Offline ***************************************************");
                    System.out.println( "mensagemSX - " + mensagemSX );
                    System.out.println( " filtrarTipoArquivos__iniciais_2 - Fim Turbo_Flyer_Auto_Leitura_Offline ***************************************************\n\n");
                    */

                    if (file_recebido.getName().equalsIgnoreCase(remetente_e_destinatario + ".HTML")) {

                        //OO8_Conversa2.mensagem_direita( mensagemSX, nome_data_hora_mensagem);
                        Mensagem_esquerda_direita Mensagem_esquerda_direita = new Mensagem_esquerda_direita(
                                mensagemSX, nome_data_hora_mensagem, OO8_Conversa2, "direita"
                        );
                        Mensagem_esquerda_direita.execute("");
                    } else if (file_recebido.getName().equalsIgnoreCase(destinatario_e_remetente + ".HTML")) {


                        //OO8_Conversa2.mensagem_esquerda( mensagemSX, nome_data_hora_mensagem);
                        Mensagem_esquerda_direita Mensagem_esquerda_direita = new Mensagem_esquerda_direita(
                                mensagemSX, nome_data_hora_mensagem, OO8_Conversa2, "esquerda"
                        );
                        Mensagem_esquerda_direita.execute("");
                    }
                }

            } catch (Exception e) {
                System.out.println("Zero - Diretórios - Home - filtrarTipoArquivos( File diretório, File arquivo )");
            }
        }

    private boolean verificar_se_arquivo_tem_string(
            String nome_Da_pasta_Da_Mensagem,
            String email_remetente,
            String email_destinatario){

        boolean retorno = false;

        try{

            String nome_email_destinatario = email_destinatario.trim().toUpperCase().replace("@", "_");
            String email_DESTINATARIO = nome_email_destinatario.trim().toUpperCase().replace(".", "_");
            String arquivo_properties2 = email_remetente.trim().toUpperCase().replace("@", "_");
            String email_REMETENTE = arquivo_properties2.trim().toUpperCase().replace(".", "_");

            String endereco_txt_mensagens = internalStorageDir + s+"00_Externo"+s+"MENSAGENS_RECEBIDAS"+s + email_REMETENTE + s + email_DESTINATARIO + s + "MENSAGENS_TXT";

///////////////////////////////////////////////////////////////////////////////
            //Lendo arquivo
            if ( new File( endereco_txt_mensagens + s + "txt" + ".txt" ).exists() == true ) {

                // abertura do arquivo
                BufferedReader bR = new BufferedReader(new InputStreamReader(
                        new FileInputStream(new File( endereco_txt_mensagens + s + "txt" + ".txt" )), "UTF-8"));

                String linha;
                while ((linha = bR.readLine()) != null) {

                    if( linha.trim().equalsIgnoreCase(nome_Da_pasta_Da_Mensagem) ){

                        retorno = true;
                        System.out.println( "\nVerificando arquivo: " + nome_Da_pasta_Da_Mensagem);
                        break;
                    }
                }
            }
            else{

                retorno = false;
            }

        } catch( Exception e ){}

        return retorno;
    }

    }
}


// AsyncTask<Parâmetros,Progresso,Resultado>
// AsyncTask<String, Void, String>
class Mensagem_esquerda_direita extends AsyncTask<String, String, Void> {

    String mensagemSX;
    String nome_data_hora_mensagem;
    OO8_Conversa OO8_Conversa2;
    String posicao;

    public Mensagem_esquerda_direita( String mensagemSX2, String nome_data_hora_mensagem2, OO8_Conversa OO8_Conversa3, String posicao2 ) {

        mensagemSX = mensagemSX2;
        nome_data_hora_mensagem = nome_data_hora_mensagem2;
        OO8_Conversa2 = OO8_Conversa3;
        posicao = posicao2;
    }

    @Override
    protected Void doInBackground(String... params) {
        String jsonDeResposta = null;

        try{

            //Thread.sleep( 30000 );

        } catch( Exception e ){}

        publishProgress(jsonDeResposta);

        return null;
    }

    @Override
    protected void onProgressUpdate(String... jsonDeResposta2) {
        //System.out.println( jsonDeResposta[0] );

        try {

            if( posicao.equals("direita") ){

                OO8_Conversa2.mensagem_direita( mensagemSX, nome_data_hora_mensagem);
            }
            else{

                OO8_Conversa2.mensagem_esquerda( mensagemSX, nome_data_hora_mensagem);
            }

        } catch (Exception e) {}
    }
}