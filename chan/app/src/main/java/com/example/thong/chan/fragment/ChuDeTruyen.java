package com.example.thong.chan.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
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

public class ChuDeTruyen extends Fragment {
    SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fm_chudetruyen,null,false);
        if(getCountItemDatabase()>0){

        }
        else {

        }
        return view;
    }
    private int getCountItemDatabase(){
        int t=0;
        database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select id from Category",null);
        while (cursor.moveToNext()){
            t=t+1;
        }
        cursor.close();
        return t;
    }
    private void loaddata(String id, final Dialog dialog){
        JsonObjectRequest request =new JsonObjectRequest(api_data.SubCate + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                database =getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                database.delete("SubCate",null,null);
                try {
                    JSONArray arr= response.getJSONArray("results");
                    for (int i=0;i<arr.length();i++){
                        JSONObject obj =arr.getJSONObject(i);
                       ContentValues contentValues =new ContentValues();
                       contentValues.put("sub_cat_id",obj.getString("sub_cat_id"));
                       contentValues.put("sub_cat_name",obj.getString("sub_cat_name"));
                       contentValues.put("image",obj.getString("image"));
                       contentValues.put("cat_id",obj.getString("cat_id"));
                       database.insert("SubCate",null,contentValues);
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
