package com.example.thong.chan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thong.chan.R;
import com.example.thong.chan.adapter.AdapterTenTruyen;
import com.example.thong.chan.api_data;
import com.example.thong.chan.mh_load.App;
import com.example.thong.chan.mh_load.SubCate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ThanhCong on 09/11/2017.
 */

public class TenTruyen extends Fragment {

    SQLiteDatabase database;
    ArrayList<App>ds=new ArrayList<>();
     RecyclerView recyclerView;
     AdapterTenTruyen adapterTenTruyen;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_doctruyen,null,false);
        Bundle bundle =getArguments();
        final String key =bundle.getString("sub_cat_id");
        recyclerView=view.findViewById(R.id.listtentruyen);
        adapterTenTruyen=new AdapterTenTruyen(getActivity(),ds);
        getdata(key);
        LinearLayoutManager manager =new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterTenTruyen);
        if(getCountItemDatabase(key)>0){
            SharedPreferences sharedPreferences =getActivity().getSharedPreferences("item_sum",Context.MODE_PRIVATE);
            int sum_SubCate=sharedPreferences.getInt(key,0);
            if(sum_SubCate==getCountItemDatabase(key)){
                //Load list len
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
        Cursor cursor =database.rawQuery("select id from App where sub_cat_id ="+key,null);
        while (cursor.moveToNext()){
            t=t+1;
        }
        cursor.close();
        return t;
    }

    private void getdata(String key){
        database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select _id,title,content,thumbnail,sub_cat_id from App where sub_cat_id ="+key,null);
        while (cursor.moveToNext()){
            ds.add(new App(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),null));
        }
        cursor.close();
       // .notifyDataSetChanged();
        adapterTenTruyen.notifyDataSetChanged();
    }

    private void loaddata(final String id){
        final Dialog dialog =new Dialog(getActivity());
        dialog.setContentView(R.layout.loadprogressbar);
        dialog.show();
        JsonObjectRequest request =new JsonObjectRequest(api_data.App + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                database =getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                database.delete("App","sub_cat_id="+id,null);
                try {
                    JSONArray arr= response.getJSONArray("results");
                    for (int i=0;i<arr.length();i++){
                        JSONObject obj =arr.getJSONObject(i);
                        ContentValues contentValues =new ContentValues();
                        contentValues.put("_id",obj.getString("id"));
                        contentValues.put("title",obj.getString("title"));
                        contentValues.put("content",obj.getString("content"));
                        contentValues.put("thumbnail",obj.getString("thumbnail"));
                        contentValues.put("sub_cat_id",obj.getString("sub_cat_id"));
                        Log.e("cat_id",obj.getString("id"));
                        database.insert("App",null,contentValues);
                    }
                    dialog.cancel();
                    SharedPreferences sharedPreferences =getActivity().getSharedPreferences("item_sum",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt(id,arr.length());
                    editor.commit();
                    getdata(id);
                    //

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}
