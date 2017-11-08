package com.example.thong.chan.mh_load;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thong.chan.api_data;
import com.example.thong.chan.fragment.MainActivity;
import com.example.thong.chan.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ManHinhLoad extends AppCompatActivity {
    SQLiteDatabase database;
    private static final String DATABASE_PATH="/databases/";
    String DATABASE_NAME="doctruyen.sqlite";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_load);
        copyDataBaseFromAsset();
        if(getCountItemDatabase()>0){
            SharedPreferences sharedPreferences =getSharedPreferences("item_sum",MODE_PRIVATE);
            int sum_item =sharedPreferences.getInt("category",0);
            if(sum_item==getCountItemDatabase()){
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                final Dialog dialog =new Dialog(this);
                dialog.setContentView(R.layout.loadprogressbar);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
                loadData(dialog);
            }
        }
        else{
           if(checkInternet()){
                Dialog dialog =new Dialog(this);
               dialog.setContentView(R.layout.loadprogressbar);
               dialog.setCanceledOnTouchOutside(false);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
               dialog.setCancelable(false);
               dialog.show();
               loadData(dialog);
           }
           else {
               Dialog dialog=new Dialog(this);
               dialog.setCancelable(false);
               dialog.setTitle("Not connected internet !");
               dialog.setCanceledOnTouchOutside(false);
               dialog.show();
           }
        }

    }

     private int getCountItemDatabase(){
         int t=0;
         database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
         Cursor cursor =database.rawQuery("select id from Category",null);
         while (cursor.moveToNext()){
             t=t+1;
         }
         cursor.close();
        return t;
     }
    private boolean checkInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()!=null){
                return true;
        }
        else{
            return false;
        }
    }
    private void loadData(final Dialog dialog) {
        JsonObjectRequest objCategory=new JsonObjectRequest(api_data.Category, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr=response.getJSONArray("results");
                    database =openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
                    database.delete("Category",null,null);
                    for(int i=0;i<arr.length();i++){
                        JSONObject resultcategory =arr.getJSONObject(i);
                        ContentValues contentValues =new ContentValues();
                        contentValues.put("cat_id",resultcategory.getString("cat_id"));
                        contentValues.put("cat_name",resultcategory.getString("cat_name"));
                        contentValues.put("url_image",resultcategory.getString("url_image"));
                        database.insert("Category",null,contentValues);
                        Log.e("ten",resultcategory.getString("cat_id"));
                    }
                    SharedPreferences sharedPreferences =getSharedPreferences("item_sum",MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("category",arr.length());
                    editor.commit();
                    dialog.cancel();
                    Intent intent =getIntent();
                    finish();
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Log.e("error",error.toString());
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(objCategory);
    }

    private String getCreatPathInSystem(){
        //trả về đường dẫn vào thư mục databases trong hệ thống điện thoại
        return getApplicationInfo().dataDir + DATABASE_PATH+DATABASE_NAME;
    }
    private void copyDataBaseFromAsset() {//Copy database
        File file =  getDatabasePath(DATABASE_NAME);
        if(!file.exists()) {
            try{
                coppyingDataBase();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void coppyingDataBase() {//Copy database từ asset vào hệ thống điện thoại
        try{ //Copy Database from Asset on System
            InputStream inp = getAssets().open(DATABASE_NAME);
            String outFile =getCreatPathInSystem();
            File f =new File(getApplicationInfo().dataDir+DATABASE_PATH);
            if(!f.exists())
            {
                f.mkdir();
            }
            OutputStream output =new FileOutputStream(outFile);
            byte[] writebuffe =new byte[1024];
            int lengt;
            while((lengt=inp.read(writebuffe))>0)
            {
                output.write(writebuffe,0,lengt);
            }
            output.flush();
            output.close();
            inp.close();
            Log.e("succes","copy database succes");
        }
        catch(IOException io){
            io.printStackTrace();
        }
    }
}
