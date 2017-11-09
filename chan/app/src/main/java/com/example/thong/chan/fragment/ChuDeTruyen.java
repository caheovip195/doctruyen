package com.example.thong.chan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thong.chan.R;
import com.example.thong.chan.api_data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by ThanhCong on 09/11/2017.
 */

public class ChuDeTruyen extends Fragment{
    SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fm_chudetruyen,null,false);
        Bundle bundle =getArguments();
        final String key =bundle.getString("cat_id");
        if(getCountItemDatabase(key)>0){
         SharedPreferences sharedPreferences =getActivity().getSharedPreferences("item_sum",Context.MODE_PRIVATE);
         int sum_SubCate=sharedPreferences.getInt(key,0);
         if(sum_SubCate==getCountItemDatabase(key)){
             //Load list len
             addViews(view);
             addEvents(view);
         }
         else {
             loaddata(key);
         }
          //  Toast.makeText(getActivity(), "sizedatabase:"+getCountItemDatabase(key)+" sizeload:"+sum_SubCate, Toast.LENGTH_LONG).show();

        }
        else {
           if(checkinternet()){
               loaddata(key);
           }
           else{
               AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
               builder.setTitle("Error!")
                       .setMessage("Not connected !")
                       .setCancelable(false)
                       .setInverseBackgroundForced(false)
                       .setNegativeButton("Reload", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               loaddata(key);
                           }
                       });
               builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                   @Override
                   public void onCancel(DialogInterface dialog) {
                       getActivity().onBackPressed();
                       getActivity().finish();
                   }
               });
               Dialog dialog =builder.create();
               dialog.show();
           }
        }
        return view;
    }

    private void addEvents(View view) {

    }

    private void addViews(View view) {

    }
    private boolean checkinternet(){
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo()!=null){
            return true;
        }
        return false;
    }

    private int getCountItemDatabase(String key){
        int t=0;
        database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select id from SubCate where cat_id ="+key,null);
        while (cursor.moveToNext()){
            t=t+1;
        }
        cursor.close();
        return t;
    }
    private void loaddata(final String id){
        final Dialog dialog =new Dialog(getActivity());
        dialog.setContentView(R.layout.loadprogressbar);
        dialog.show();
        JsonObjectRequest request =new JsonObjectRequest(api_data.SubCate + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                database =getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                database.delete("SubCate","cat_id="+id,null);
                try {
                    JSONArray arr= response.getJSONArray("results");
                    for (int i=0;i<arr.length();i++){
                        JSONObject obj =arr.getJSONObject(i);
                       ContentValues contentValues =new ContentValues();
                       contentValues.put("sub_cat_id",obj.getString("sub_cat_id"));
                       contentValues.put("sub_cat_name",obj.getString("sub_cat_name"));
                       contentValues.put("image",obj.getString("image"));
                       contentValues.put("cat_id",obj.getString("cat_id"));
                       Log.e("cat_id",obj.getString("sub_cat_id"));
                       database.insert("SubCate",null,contentValues);
                    }
                    dialog.cancel();
                    SharedPreferences sharedPreferences =getActivity().getSharedPreferences("item_sum",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt(id,arr.length());
                    editor.commit();
                    getFragmentManager().beginTransaction().detach(new ChuDeTruyen()).attach(new ChuDeTruyen()).commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("load",error.toString());
                AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
                builder.setTitle("Error!")
                        .setMessage("Not connected !")
                        .setCancelable(false)
                        .setInverseBackgroundForced(false)
                        .setNegativeButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getFragmentManager().beginTransaction().detach(new ChuDeTruyen()).attach(new ChuDeTruyen()).commit();
                            }
                        });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getActivity().onBackPressed();
                        getActivity().finish();
                    }
                });
                Dialog dialog =builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        RequestQueue requestQueue =Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}
