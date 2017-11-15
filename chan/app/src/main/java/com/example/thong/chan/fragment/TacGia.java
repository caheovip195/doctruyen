package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thong.chan.R;

public class TacGia extends Fragment {

    Button btn;
    private View mview1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mview1 = inflater.inflate(R.layout.tac_gia, container, false);
        return mview1;
    }

}
