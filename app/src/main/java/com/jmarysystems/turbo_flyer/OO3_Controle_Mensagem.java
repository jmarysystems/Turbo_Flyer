package com.jmarysystems.turbo_flyer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class OO3_Controle_Mensagem extends AppCompatActivity {

    String nome = "";
    String de_email_remetente = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo3__controle__mensagem);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nome = bundle.getString("nome");
        de_email_remetente = bundle.getString("email");

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.hide();

        /*
        Toolbar toolbar; = new Toolbar(this);
        //Toolbar toolbar; = (Toolbar) findViewById(R.id.toolbar);
        //setting the title
        toolbar.setTitle("Contatos");
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        */

        inserir_menu();

        try {
            OO4_Controle_Mensagem_Contatos OO4_Controle_Mensagem_Contatos = new OO4_Controle_Mensagem_Contatos(nome,de_email_remetente,this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    private void inserir_menu() {

    }

    /*
    toolbar.setLogo(resize(logo, (int) Float.parseFloat(mContext.getResources().getDimension(R.dimen._120sdp) + ""), (int) Float.parseFloat(mContext.getResources().getDimension(R.dimen._35sdp) + "")));

    public Drawable resize(Drawable image, int width, int height)
    {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    */

    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */

    /*
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (menu != null) {
            ((MainActivity)context).setSelectedPackageId(list.get(getLayoutPosition()).getNumber());
            Package pack = list.get(getLayoutPosition());
            menu.setHeaderTitle(pack.getName());
            // Set different title according to the data({@link Package#readable})
            if (pack.isReadable()) {
                menu.add(Menu.NONE, R.id.action_set_readable, 0, R.string.set_read);
            } else {
                menu.add(Menu.NONE, R.id.action_set_readable, 0, R.string.set_unread);
            }
            menu.add(Menu.NONE, R.id.action_copy_code, 0, R.string.copy_code);
            menu.add(Menu.NONE, R.id.action_share, 0, R.string.share);
        }
    }
    */

}
