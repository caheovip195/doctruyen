package com.example.thong.chan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
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
import com.example.thong.chan.Connectivity;
import com.example.thong.chan.R;
import com.example.thong.chan.adapter.AdapterChuDe;
import com.example.thong.chan.api_data;
import com.example.thong.chan.mh_load.SubCate;
import com.example.thong.chan.mh_load.SubCateLike;
import com.example.thong.chan.mh_load.SubCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChuDeTruyen extends Fragment{
    RequestQueue requestQueue;
    JsonObjectRequest request;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    AdapterChuDe adapterDocTruyen;
    ArrayList<SubCateLike>ds=new ArrayList<>();
   // ArrayList<SubCheck>dsCheck=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fm_chudetruyen,null,false);
        Bundle bundle =getArguments();
        SQLiteDatabase database =getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select sub_cat_id,sub_cat_name,cat_id from ThichSubCate",null);
        while (cursor.moveToNext()){
            Log.e("cat_id & sub_cat_id",cursor.getString(2)+" && "+cursor.getString(0)+" "+cursor.getString(1));
        }
        cursor.close();
        final String key =bundle.getString("cat_id");
        String catname=bundle.getString("cat_name");
        recyclerView=view.findViewById(R.id.listthemtruyen);
        adapterDocTruyen=new AdapterChuDe(getActivity(),ds,catname);
        LinearLayoutManager manager =new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterDocTruyen);
        if(checkinternet()){
            if(Connectivity.isConnectedFast(getActivity())){
                loaddata(key);
            }
            else{
                getdata(key);
            }
        }
        else{
            getdata(key);
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

    private void getdata(String key){
        database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select sub_cat_id,sub_cat_name,image,cat_id from SubCate where cat_id ="+key,null);
        ds.clear();
        while (cursor.moveToNext()){
            Log.e("cat_id",cursor.getString(3));
            Log.e("sub_cat_id",cursor.getString(0));
                ds.add(new SubCateLike(cursor.getString(0)
                        ,cursor.getString(1)
                        ,cursor.getString(2)
                        ,cursor.getString(3),0+""));
        }
        cursor.close();
        adapterDocTruyen.notifyDataSetChanged();
    }
    private void loaddata(final String id){
        final Dialog dialog =new Dialog(getActivity());
        dialog.setContentView(R.layout.loadprogressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
         request =new JsonObjectRequest(api_data.SubCate + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                database =getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                database.delete("SubCate","cat_id="+id,null);
                if(ds.size()>0){
                    ds.clear();
                }
                try {
                    JSONArray arr= response.getJSONArray("results");
                    SQLiteDatabase database =getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                    Cursor cursor =database.rawQuery("select sub_cat_id ,cat_id from ThichSubCate",null);
                    for (int i=0;i<arr.length();i++) {
                        boolean flag = false;
                        JSONObject obj = arr.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("sub_cat_id", obj.getString("sub_cat_id"));
                        contentValues.put("sub_cat_name", obj.getString("sub_cat_name"));
                        contentValues.put("image", obj.getString("image"));
                        contentValues.put("cat_id", obj.getString("cat_id"));
                        database.insert("SubCate", null, contentValues);
                        ds.add(new SubCateLike(obj.getString("sub_cat_id"),
                                obj.getString("sub_cat_name"),
                                obj.getString("image"),
                                obj.getString("cat_id"),
                                0 + ""));
                    }
                    dialog.cancel();
                    adapterDocTruyen.notifyDataSetChanged();
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
                Dialog dialog1=builder.create();
                dialog1.show();
            }
        });
        requestQueue =Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(requestQueue==null){

        }
        else {
            requestQueue.stop();
        }
    }
}
