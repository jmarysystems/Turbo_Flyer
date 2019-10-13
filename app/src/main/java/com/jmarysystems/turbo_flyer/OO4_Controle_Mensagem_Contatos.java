package com.jmarysystems.turbo_flyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.Properties;

import static android.graphics.Color.parseColor;

public class OO4_Controle_Mensagem_Contatos extends AppCompatActivity {

    // 1 - wrap_content - informa ao componente/view para ocupar o espaço que ele vai necessitar (altura e/ou largura)
         // para exibir suas informações no layout.
    // 2 - match_parent - informa ao componente/view para ocupar o mesmo espaço da sua view pai, ou seja, ele vai preencher
        //todo_o conteúdo de seu layout pai.
    // 3 - fill_parent em versões anteriores á versão 2.3. Esse atributo atua da mesma forma que match_parent.

    String nome = "";
    String de_email_remetente = "";
    static android.app.Activity Activity;
    static java.io.File internalStorageDir = null;

    OO4_Controle_Mensagem_Contatos Controle_Mensagem_Contatos22;

    int contador = 0;

    LinearLayout LinearLayoutXML;
    android.widget.ScrollView ScrollViewXML;

    public OO4_Controle_Mensagem_Contatos(String nome2, String de_email_remetente2, Activity Activity2) throws IOException, GeneralSecurityException {
        nome = nome2.toUpperCase();
        de_email_remetente = de_email_remetente2.toLowerCase();

        Activity = Activity2;
        internalStorageDir = Activity2.getFilesDir();

        Controle_Mensagem_Contatos22 = this;

        //cria uma instância do Layout LinearLayout
        //LinearLayoutXML = (LinearLayout) Activity.findViewById(R.id.linearLayout_home);
        LinearLayoutXML = new LinearLayout(Activity);
        //define a orientação do leiaute
        LinearLayoutXML.setOrientation(LinearLayout.VERTICAL);
        //define os parâmetros do leiaute
        LinearLayoutXML.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));

        //define o layout como a view principal
        //Activity.setContentView(LinearLayoutXML);

        ScrollViewXML = new ScrollView(Activity);
        //define o layout como a view principal
        ScrollViewXML.addView(LinearLayoutXML);

        //define o layout como a view principal
        Activity.setContentView(ScrollViewXML);

        new Thread() {
            @Override
            public void run() {

            }
        }.start();

        linha_novo_contato();
        //pergar_lista_de_contatos_do_properties();
        //pergar_lista_de_contatos_do_properties();
        setar_Contatos_do_Properties();
    }

    private void setar_Contatos_do_Properties() {
        /*new Thread() {   @Override public void run() {*/
        try {
            String esp = System.getProperty("file.separator");
            java.io.File userdir = Activity.getFilesDir();

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

                            pergar_lista_de_contatos_do_properties(nome, email);

                        } catch (Exception e) {
                        }

                    }

                }
            }

        } catch (Exception e) {
        } //} }.start();
    }

    private void linha_novo_contato() {
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
                abrir_Activity(OO6_Cadastrar_Contato_Novo.class, nome, de_email_remetente);
            }
        });
        bt_novo_Contato.setText("Novo");
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
        textView.setText("    Meus Contatos");
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
        LinearLayoutXML.addView(LinearLayout1, contador);
        contador++;
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
    }

    private void pergar_lista_de_contatos_do_properties(final String nome_contato_r, final String para_email_destinatario) {
//PREENCHER Layout 3 - MENSAGENS////////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout3 = new LinearLayout(Activity);
        LinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout3.setGravity(Gravity.RIGHT);

        TextView textView3_1 = new TextView(Activity);
        textView3_1.setText("MSG");
        textView3_1.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout3.addView(textView3_1);

        final TextView textView3_2 = new TextView(Activity);
        textView3_2.setText("");
        textView3_2.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout3.addView(textView3_2);
//PREENCHER Layout 3////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 2 Nome e Email///////////////////////////////////////////////////////////////////
        LinearLayout LinearLayout2 = new LinearLayout(Activity);
        LinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        LinearLayout2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout2.setGravity(Gravity.CENTER_VERTICAL);

        TextView textView = new TextView(Activity);
        textView.setText("  " + nome_contato_r);
        LinearLayout2.addView(textView);

        TextView textView2 = new TextView(Activity);
        textView2.setText("  " + para_email_destinatario);
        LinearLayout2.addView(textView2);
//PREENCHER Layout 2////////////////////////////////////////////////////////////////////////////////

//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////
        final LinearLayout LinearLayout1 = new LinearLayout(Activity);
        LinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 100
        ));
        LinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout1.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout1.setPadding(1, 2, 0, 0); //Padding( left, top, right, bottom);
        if (contador % 2 == 0) {
            LinearLayout1.setBackgroundColor(parseColor("#F2F2F2"));
        } else {
            LinearLayout1.setBackgroundColor(parseColor("#FFFFFF"));
        }
        //define a cor de fundo do layout
//PREENCHER Layout 1////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
        //ADICIONAR IMAGEM AO LAYOUT 1//////////////////////////////////////////////////////////////
        ImageView imagem = new ImageView(Activity);
        imagem.setImageResource(R.drawable.ic_launcher_background);
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

//ADICIONAR EVENTO AO LAYOUT 1////////////////////////////////////////////////////////////////////
        /*
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable(){
                    public void run(){
                        abrir_Activity(OO8_Conversa.class, nome_contato_r, email_contato_r);
                    }
                });
            }
        });
        */
//ADICIONAR EVENTO AO LAYOUT 1////////////////////////////////////////////////////////////////////


//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
        LinearLayoutXML.addView(LinearLayout1, contador);
        contador++;

        OO5_Controle_Mensagem_Evento OO5_Controle_Mensagem_Evento = new OO5_Controle_Mensagem_Evento(Activity, LinearLayout1, textView3_2, nome_contato_r, de_email_remetente, para_email_destinatario);
//FINALIZAR/////////////////////////////////////////////////////////////////////////////////////////
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
