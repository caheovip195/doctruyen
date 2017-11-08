package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thong.chan.R;

public class ThongTinApp extends Fragment implements View.OnClickListener {

    Button btn;
    private View mview1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview1 = inflater.inflate(R.layout.thong_tin_app, container, false);
        getUiInitiazalization();
        return mview1;

    }

    public void getUiInitiazalization() {
        btn = (Button) mview1.findViewById(R.id.btnsendemail);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsendemail:

                Log.i("Send email", "");
                String[] TO = {"tnthong.oplai@gmail.com"};
                String[] CC = {"ntcong95.oplai@gmail.com"};
                Intent emailIntent = new Intent(Intent.EXTRA_EMAIL);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    onStop();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Cảm ơn bạn đã phản hồi vào email của chúng tôi", Toast.LENGTH_SHORT).show();
                }
        }
    }
}