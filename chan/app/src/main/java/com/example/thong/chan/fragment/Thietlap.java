package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.Spinner;

import com.example.thong.chan.R;

import java.util.ArrayList;

public class Thietlap extends Fragment {
    Spinner spinner, spinner2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thiet_lap, container, false);
        spinner = view.findViewById(R.id.spinner);
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Bình Thường");
        arrayList.add("Ban Đêm");
        arrayList.add("Ban ngày");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //click cho cac item trong spiner.
                SharedPreferences pre = getActivity().getSharedPreferences("regime",Context.MODE_PRIVATE);
                SharedPreferences.Editor sEditor=pre.edit();
                sEditor.putInt("regime", Integer.parseInt(arrayList.get(position)));
                sEditor.commit();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner2 = view.findViewById(R.id.spinner2);
        final ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("10");
        arrayList2.add("15");
        arrayList2.add("20");
        arrayList2.add("25");
        arrayList2.add("30");
        ArrayAdapter arrayAdapterFont = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapterFont.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(arrayAdapterFont);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //click cho cac item trong spiner.

                SharedPreferences pre = getActivity().getSharedPreferences("setting",Context.MODE_PRIVATE);
                SharedPreferences.Editor sEditor=pre.edit();
                sEditor.putInt("fontsize", Integer.parseInt(arrayList2.get(position)));
                sEditor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void onClick(View v) {
        Fragment newFragment = new ManHinhChinh();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, newFragment);
        transaction.addToBackStack(null);
    }
}
   // setTheme(R.style.Theme_Day);
