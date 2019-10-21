package com.jmarysystems.turbo_flyer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class OO11_Notificacoes extends AppCompatActivity {

    String titulo = "Título da Notificação";
    String bigText = "Texto da notificação Simples";
    int id = 1;

    Context Activity;

    public OO11_Notificacoes(String titulo2, String texto2, Context Activity2) {

        titulo = titulo2;
        bigText = texto2;
        Activity = Activity2;

        id = (int) (System.currentTimeMillis() / 1000);

        Intent intent = new Intent(Activity, Activity.getClass());

        // PendingIntent para executar a Activity se o usuário selecionar a notificação
        PendingIntent pendingIntent = PendingIntent.getActivity(
                Activity,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Activity)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(titulo)
                .setContentText(bigText)
                //.setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));

        NotificationManager notifyManager = (NotificationManager) Activity.getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(id, builder.build());
    }
}