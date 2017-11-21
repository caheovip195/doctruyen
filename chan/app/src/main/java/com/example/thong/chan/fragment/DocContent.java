package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.thong.chan.R;

public class DocContent extends Fragment {

    WebView webview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.doccontent,container,false);
        webview=view.findViewById(R.id.doccontent);
        Bundle bundle =getArguments();
        String content=bundle.getString("content");
        //webview.setBackgroundColor(Color.BLUE);
        Log.e("content",content);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadData(content,"text/html","utf-8");
        webview.setWebViewClient(new WebViewClient());
        Log.e("content",content);
        return view;
    }
}
