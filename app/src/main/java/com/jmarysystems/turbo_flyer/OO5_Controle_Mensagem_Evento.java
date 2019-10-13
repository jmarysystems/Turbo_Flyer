package com.jmarysystems.turbo_flyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OO5_Controle_Mensagem_Evento extends AppCompatActivity {

    android.app.Activity Activity;
    LinearLayout LinearLayout1;
    TextView textView3_2;

    String nome_contato = "";
    String de_email_remetente = "";
    String para_email_destinatario = "";

    public OO5_Controle_Mensagem_Evento(android.app.Activity Activity2, LinearLayout LinearLayout12, TextView textView3_22, String nome_contato_r, String de_email_remetente2, String para_email_destinatario2) {
        Activity = Activity2;
        LinearLayout1 = LinearLayout12;
        textView3_2 = textView3_22;

        nome_contato = nome_contato_r;
        de_email_remetente = de_email_remetente2;
        para_email_destinatario = para_email_destinatario2;

        setar_evento();
        //setar_evento2();
    }

    private void setar_evento2() {
        LinearLayout1.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent motion) {
                TextView texto = new TextView( Activity.getApplicationContext() );
                String msg = "";

                if ( motion.getAction() == MotionEvent.ACTION_DOWN ) {
                    msg = "pressionou na tela. ";

                } else if ( motion.getAction() == MotionEvent.ACTION_MOVE ) {
                    msg = "moveu na tela. ";

                } else if ( motion.getAction() == MotionEvent.ACTION_UP ) {
                    msg = "soltou na tela. ";

                }

                msg += "x: " + motion.getRawX() + " y: " + motion.getRawY();

                texto.setText( msg );

                textView3_2.setText( nome_contato );

                return true;
            }
        } );
    }

    boolean umaVez = false;
    private void setar_evento() {

        LinearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky is clicked
                if( umaVez == false ) {
                    umaVez = true;

                    abrir_Activity(OO8_Conversa.class, nome_contato, de_email_remetente, para_email_destinatario);
                }
            }
        });
    }

    private void abrir_Activity(Class Classe_a_Abrir, String nome_user_sis, String email_user_sis, String email_destn_sis) {
        Intent intent = new Intent(Activity, Classe_a_Abrir);

        Bundle bundle = new Bundle();
        bundle.putString("nome", nome_user_sis);
        bundle.putString("email", email_user_sis);
        bundle.putString("emaildestinatario", email_destn_sis );
        intent.putExtras(bundle);

        Activity.startActivity(intent);
        Activity.finish();
    }
}
