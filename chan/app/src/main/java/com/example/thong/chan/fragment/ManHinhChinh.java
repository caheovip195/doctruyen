package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thong.chan.R;
import com.example.thong.chan.mh_load.ManHinhLoad;

public class ManHinhChinh extends Fragment {
    ImageView img,img1,img2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.man_hinh_chinh, container, false);

        img2=(ImageView) view.findViewById(R.id.imThongtin);
        img2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager manager =getActivity().getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.content_frame,new ThongTinApp());
                transaction.addToBackStack("m2");
                transaction.commit();
            }
        });

        img1=(ImageView) view.findViewById(R.id.imDanhdau);
        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager manager =getFragmentManager();
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.addToBackStack("m2");
                transaction.replace(R.id.content_frame,new TruyenDaDanhDau());
                transaction.commit();
            }
        });

        img=(ImageView) view.findViewById(R.id.imBaithuoc);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager manager =getFragmentManager();
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.addToBackStack("m1");
                transaction.replace(R.id.content_frame,new DocTruyen());
                transaction.commit();
            }
        });
        return view;
    }
}