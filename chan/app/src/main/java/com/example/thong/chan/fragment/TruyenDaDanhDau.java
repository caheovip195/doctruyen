package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thong.chan.R;
import com.example.thong.chan.adapter.AdapterLike;
import com.example.thong.chan.mh_load.SubCateLike;

import java.util.ArrayList;

public class TruyenDaDanhDau extends Fragment {
    ArrayList<SubCateLike>ds=new ArrayList<>();
    RecyclerView recyclerView;
    AdapterLike adapterLike;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.truyen_da_danh_dau, container, false);

        return view;
    }
    private void loaddata(){
        SQLiteDatabase database =getActivity().openOrCreateDatabase("doctruyen.sql", Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select sub_cat_id,sub_cat_name,image from LikeSubCate",null);
        while (cursor.moveToNext()){
             ds.add(new SubCateLike(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        cursor.close();
    }
}