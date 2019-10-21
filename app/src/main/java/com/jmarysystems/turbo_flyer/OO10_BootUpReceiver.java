package com.jmarysystems.turbo_flyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OO10_BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, OO12_Ler_Mensagens_Auto.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(pushIntent);
        }
    }
}