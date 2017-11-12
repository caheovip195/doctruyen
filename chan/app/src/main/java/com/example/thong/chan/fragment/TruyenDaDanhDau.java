package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        recyclerView=view.findViewById(R.id.list_like);
        loaddata();
        if(ds.size()<=0){
            TextView button =new TextView(getActivity());
            button.setText("Danh sách trống !");
            button.setTextSize(17);
            button.setTextColor(Color.BLACK);
            button.setGravity(Gravity.CENTER);
            LinearLayout layout =view.findViewById(R.id.layout_like);
            layout.setGravity(Gravity.CENTER);
            layout.addView(button);
        }
        else {
            adapterLike=new AdapterLike(getActivity(),ds);
            recyclerView.setAdapter(adapterLike);
        }

        return view;
    }
    private void loaddata(){
        SQLiteDatabase database =getActivity().openOrCreateDatabase("doctruyen.sqlite", Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select sub_cat_id,sub_cat_name,image from ThichSubCate",null);
        while (cursor.moveToNext()){
             ds.add(new SubCateLike(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        cursor.close();
    }
}