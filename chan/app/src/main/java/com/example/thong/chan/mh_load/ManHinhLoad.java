package com.example.thong.chan.mh_load;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thong.chan.Connectivity;
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
import java.util.ArrayList;

public class ManHinhLoad extends AppCompatActivity {
    SQLiteDatabase database;
    private static final String DATABASE_PATH="/databases/";
    String DATABASE_NAME="doctruyen.sqlite";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_load);
        copyDataBaseFromAsset();
        if(checkInternet()){
            if(Connectivity.isConnectedFast(ManHinhLoad.this)){
                loadData(dialogInformation());
            }
            else {
                getItemDatabase();
            }
        }
        else {
            getItemDatabase();
        }
    }
    private Dialog dialogInformation(){
        Dialog dialog =new Dialog(this);
        dialog.setContentView(R.layout.loadprogressbar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
     private void getItemDatabase(){
         ListCategory.listcategory.clear();
         database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
         Cursor cursor =database.rawQuery("select cat_id,cat_name from Category",null);
         while (cursor.moveToNext()){
             ListCategory.listcategory.add(new Category(cursor.getString(0),cursor.getString(1),null));
         }
         cursor.close();
         if(ListCategory.listcategory.size()<=0){
             dialognotConnected().show();
         }
         else{
             startActivity(new Intent(this,MainActivity.class));
         }
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
    private Dialog dialognotConnected(){
        Dialog dialog=new Dialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Not connected internet !");
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    private void loadData(final Dialog dialog) {
        JsonObjectRequest objCategory=new JsonObjectRequest(Request.Method.GET,api_data.Category,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("responese",response.toString());
                try {
                    if(ListCategory.listcategory!=null){
                        ListCategory.listcategory.clear();
                    }
                    ArrayList<Category>list=new ArrayList<>();
                    JSONArray arr=response.getJSONArray("results");
                    database =openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
                    database.delete("Category",null,null);
                    Log.e("le",arr.length()+"");
                    for(int i=0;i<arr.length();i++){
                        Log.e("i",i+"");
                        JSONObject resultcategory =arr.getJSONObject(i);
                        ContentValues contentValues =new ContentValues();
                        contentValues.put("cat_id",resultcategory.getString("cat_id"));
                        contentValues.put("cat_name",resultcategory.getString("cat_name"));
                        database.insert("Category",null,contentValues);
                        Log.e("ten",resultcategory.getString("cat_id"));
                        list.add(new Category(resultcategory.getString("cat_id"),resultcategory.getString("cat_name"),null));
                    }
                    ListCategory.listcategory=list;
                    dialog.cancel();
                    Intent intent =new Intent(ManHinhLoad.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Log.e("error",error.toString());
              dialog.cancel();
              AlertDialog.Builder builder=new AlertDialog.Builder(ManHinhLoad.this);
              builder.setTitle("Lỗi").setMessage("Mạng của bạn đang có vấn đề!Nếu bạn chắc chắn rằng đã load dữ liệu lần đầu thì hãy tắt mạng để sử dụng offline")
                      .setNegativeButton("Reload", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              loadData(dialogInformation());
                          }
                      }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                  @Override
                  public void onCancel(DialogInterface dialog) {
                      finish();
                  }
              });
              Dialog d2 = builder.create();
              d2.setCanceledOnTouchOutside(false);
              d2.setCancelable(false);
              d2.show();
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
