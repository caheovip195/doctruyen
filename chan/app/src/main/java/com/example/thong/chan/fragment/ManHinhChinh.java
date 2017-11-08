package com.example.thong.chan.fragment;

import android.app.Fragment;
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
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.man_hinh_chinh, container, false);
        return myView;

    }
    private void chuyenman() {
        ImageView imageView = getActivity().findViewById(R.id.imBaithuoc);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),com.example.thong.chan.fragment.DocTruyen.class);
                startActivity(intent);
            }
        });
    }

}