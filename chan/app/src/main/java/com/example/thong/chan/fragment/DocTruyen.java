package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.thong.chan.R;
import com.example.thong.chan.adapter.AdapterDocTruyen;
import com.example.thong.chan.mh_load.Category;

import java.util.ArrayList;

public class DocTruyen extends Fragment{
    SQLiteDatabase database;
    RecyclerView recyclerView;
    AdapterDocTruyen adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_truyen, container, false);
        if(loadata().isEmpty()){
            //Danh sach trong
        }
        else {
            recyclerView=view.findViewById(R.id.listdoctruyen);
            adapter=new AdapterDocTruyen(loadata(),getActivity());
            LinearLayoutManager manager =new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
    private ArrayList<Category>loadata(){
        ArrayList<Category>ds=new ArrayList<>();
        database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select cat_id,cat_name,url_image from Category",null);
        while (cursor.moveToNext()){
            ds.add(new Category(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        cursor.close();
       return ds;
    }
}
