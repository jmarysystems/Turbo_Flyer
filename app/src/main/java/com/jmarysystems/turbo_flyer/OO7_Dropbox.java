package com.jmarysystems.turbo_flyer;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class OO7_Dropbox extends AppCompatActivity {

    final static private String APP_KEY = "mlvc88wuzck1hlp";
    final static private String APP_SECRET = "sbypxalkamc5hmy";

    final static private String ACCOUNT_PREFS_NAME = "jmarysystems@gmail.com";
    final static private String ACCESS_TOKEN = "V1jMYaBuyaAAAAAAAAABusFr1HxVDFhkx2FbrQXUvD0y4x7UNQRmgx6siymUryEX";

    private List<Entry> list;

    private DropboxAPI<AndroidAuthSession> dropboxApi;

    private ImageView ivImg;

    private Button btUploadFile;

    android.app.Activity Activity;

    String nome_contato = "";
    String email_contato = "";

    public OO7_Dropbox(android.app.Activity Activity2, String nome_contato_r, String email_contato_r){

        Activity = Activity2;
        nome_contato = nome_contato_r;
        email_contato = email_contato_r;

        new Thread(){
            public void run(){

            try{

                list = new ArrayList<Entry>();

                AndroidAuthSession session = buildSession();
                dropboxApi = new DropboxAPI<AndroidAuthSession>(session);

                ivImg = new ImageView(Activity);
                btUploadFile = new Button(Activity.getBaseContext());

                uploadFile();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            }
        }.start();
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oo7__dropbox);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nome = bundle.getString("nome");
        email = bundle.getString("email");

        list = new ArrayList<Entry>();

        AndroidAuthSession session = buildSession();
        dropboxApi = new DropboxAPI<AndroidAuthSession>(session);

        ivImg = new ImageView(this);
        btUploadFile = new Button(this.getBaseContext());

        uploadFile();
    }
    */

    @Override
    public void onResume(){
        super.onResume();
        AndroidAuthSession session = dropboxApi.getSession();

        if(session.authenticationSuccessful()){
            session.finishAuthentication();

            saveLogged(session);
            //enableViews(true);
        }
    }

    public AndroidAuthSession buildSession(){
        AppKeyPair akp = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(akp);
        loadSession(session);

        return(session);
    }

    public void loadSession(AndroidAuthSession session){
        try{
        SharedPreferences sp = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String token = sp.getString(ACCESS_TOKEN, null);

        if(token == null || token.length() == 0){
            return;
        }
        else{
            session.setOAuth2AccessToken(token);
        }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("***** - loadSession(AndroidAuthSession session){ - " +
                    "******************************\n"
                    +e.getMessage());
        }
    }

    public void saveLogged(AndroidAuthSession session){
        String token = session.getOAuth2AccessToken();
        if(token != null){
            SharedPreferences sp = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(ACCESS_TOKEN, token);
            edit.commit();
        }
    }

    public void getDocs(Entry entry) throws DropboxException{
        list.add(entry);
        List<Entry> eList = entry.contents;
        if(eList != null){
            for(Entry e : eList){
                //list.add(e);
                e = dropboxApi.metadata(e.path, 0, null, true, null);
                getDocs(e);
            }
        }
    }


    // VIEWs METHODS
    public void login(View view){
        dropboxApi.getSession().startOAuth2Authentication(OO7_Dropbox.this);
    }


    public void logout(View view){
        dropboxApi.getSession().unlink();

        SharedPreferences sp = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();

        //enableViews(false);
    }

    public void loadList(View view){
        new Thread(){
            public void run(){

                try{
                    Entry e = dropboxApi.metadata("/Android", 0, null, true, null);
                    getDocs(e);
                }
                catch(DropboxException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable(){
                    public void run(){
                        //listView.setAdapter(new AdapterDropbox(MainActivity.this, list));
                    }
                });
            }
        }.start();
    }

    public void downloadFile(View view){
        //btDownloadFile.setText("Carregando...");
        //btDownloadFile.setEnabled(false);

        new Thread(){
            public void run(){
                File file = null;
                try {
                    DropboxProgressListener dpl = new DropboxProgressListener(btUploadFile);

                    file = new File(Environment.getExternalStorageDirectory(), "Pictures/image_aux.jpg");
                    FileOutputStream os = new FileOutputStream(file);
                    DropboxFileInfo info = dropboxApi.getFile("/ExemploDB/image_01.jpg", null, os, dpl);
                    Log.i("Script", "Revision HASH: "+info.getMetadata().rev);
                }
                catch (DropboxException e) {
                    e.printStackTrace();
                }
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }

                final String path = file == null ? null : file.getAbsolutePath();
                runOnUiThread(new Runnable(){
                    public void run(){
                        //btDownloadFile.setText("Download File");
                        //btDownloadFile.setEnabled(true);
                        ivImg.setImageBitmap(BitmapFactory.decodeFile(path));
                    }
                });
            }
        }.start();
    }


    //View view
    public void uploadFile(){
        //btUploadFile.setText("Enviando...");
        //btUploadFile.setEnabled(false);

        new Thread(){
            public void run(){
                try {
                    try {
                        dropboxApi.metadata("/ExemploDB", 0, null, false, null);
                    }
                    catch(DropboxServerException e) {
                        dropboxApi.createFolder("/ExemploDB");
                    }

                    DropboxProgressListener dpl = new DropboxProgressListener(btUploadFile);

                    String s = System.getProperty("file.separator");
                    File internalStorageDir = getFilesDir();

                    String endereco = internalStorageDir + s + "00_Externo"+ s + "USUARIO_CADASTRADO" + s + "USER" + ".properties";
                    File file = new File( endereco );
                    if ( !file.exists() ) {

                        //File file = new File(Environment.getExternalStorageDirectory(), "Pictures/image_01.jpg");
                        FileInputStream is = new FileInputStream(file);
                        Entry reponse = dropboxApi.putFile("/ExemploDB/image_01.jpg", is, file.length(), null, dpl);
                        Log.i("Script", "Revision HASH (uploadFile): "+reponse.rev);
                    }

                }
                catch (DropboxException e) {
                    e.printStackTrace();
                }
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable(){
                    public void run(){
                        //btUploadFile.setText("Upload File");
                        //btUploadFile.setEnabled(true);
                    }
                });
            }
        }.start();
    }


    public void updateFile(View view){
        //btUpdateFile.setText("Atualizando...");
        //btUpdateFile.setEnabled(false);

        new Thread(){
            public void run(){
                try {
                    DropboxProgressListener dpl = new DropboxProgressListener(btUploadFile);

                    File file = new File(Environment.getExternalStorageDirectory(), "Pictures/image_02.jpg");
                    FileInputStream is = new FileInputStream(file);
                    Entry reponse = dropboxApi.putFileOverwrite("/ExemploDB/image_01.jpg", is, file.length(), dpl);
                    Log.i("Script", "Revision HASH (updateFile): "+reponse.rev);
                }
                catch (DropboxException e) {
                    e.printStackTrace();
                }
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable(){
                    public void run(){
                        //btUpdateFile.setText("Update File");
                        //btUpdateFile.setEnabled(true);
                    }
                });
            }
        }.start();
    }


    public void deleteFile(View view){
        new Thread(){
            public void run(){
                try {
                    dropboxApi.delete("/ExemploDB/image_01.jpg");
                } catch (DropboxException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void deleteFolder(View view){
        new Thread(){
            public void run(){
                try {
                    dropboxApi.delete("/ExemploDB");
                } catch (DropboxException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

class DropboxProgressListener extends ProgressListener{
    private Button bt;

    public DropboxProgressListener(Button bt){
        super();
        this.bt = bt;
    }

    @Override
    public void onProgress(long now, long total) {
        final long val = (long)(((double)now / (double)total) * 100);
        /*
        runOnUiThread(new Runnable(){
            public void run(){
                bt.setText(bt.getText()+" ("+val+"%)");
            }
        });
        */
    }

    @Override
    public long progressInterval(){
        return(100);
    }
}
