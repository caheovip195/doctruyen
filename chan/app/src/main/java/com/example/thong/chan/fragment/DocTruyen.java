package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
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
import com.example.thong.chan.mh_load.ListCategory;

import java.util.ArrayList;
import java.util.List;

public class DocTruyen extends Fragment {

    SQLiteDatabase database;
    RecyclerView recyclerView;
    AdapterDocTruyen adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_truyen, container, false);
            addViews(view);
            addEvents(view);
        return view;
    }
    private void addEvents(View view) {
      adapter.setOnItemClickedListener(new AdapterDocTruyen.OnItemClickedListener() {
          @Override
          public void onItemClick(String dschude) {
             // Toast.makeText(getActivity(), ""+dschude, Toast.LENGTH_LONG).show();
          }
      });
    }
    private void addViews(View view) {
        recyclerView=view.findViewById(R.id.listdoctruyen);
        adapter=new AdapterDocTruyen(ListCategory.listcategory,getActivity());
        LinearLayoutManager manager =new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
