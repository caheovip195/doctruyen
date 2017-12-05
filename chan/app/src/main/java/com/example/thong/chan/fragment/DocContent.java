package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thong.chan.R;

public class DocContent extends Fragment {
    ImageView imglike;
    TextView hientieudemandoc;
    WebView webview;
    SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.doccontent,container,false);
        webview=view.findViewById(R.id.doccontent);
        imglike=view.findViewById(R.id.like_bentrong);
        hientieudemandoc=view.findViewById(R.id.hientieudemandoc);
        Bundle bundle =getArguments();
        final String content=bundle.getString("content");
        final String tentacgia=bundle.getString("tacgia");
        final int cat_id=Integer.parseInt(bundle.getString("cat_id"));
        final int sub_cat_id=Integer.parseInt(bundle.getString("sub_cat_id"));
        final int app_id=Integer.parseInt(bundle.getString("app_id"));
        final String image =bundle.getString("image");
        Log.e("tentacgia",tentacgia);
        final String title =bundle.getString("title");
        hientieudemandoc.setText(title);
        boolean flag =false;
        database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select cat_id,sub_cat_id,app_id from ThichSubCate",null);
        while (cursor.moveToNext()){
            if(cat_id==Integer.parseInt(cursor.getString(0))
                    && sub_cat_id==Integer.parseInt(cursor.getString(1))
                    && app_id == Integer.parseInt(cursor.getString(2))){
                flag=true;
                break;
            }
        }
        cursor.close();
        if(flag==true){
              imglike.setImageResource(R.drawable.like11);
        }
        else {
            imglike.setImageResource(R.drawable.like2);
        }
        final boolean finalFlag = flag;
        imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalFlag ==true){
                    database.delete("ThichSubCate","sub_cat_id=? and cat_id=? and app_id=?"
                            ,new String[]{sub_cat_id+"",cat_id+"",app_id+""
                            });
                    imglike.setImageResource(R.drawable.like2);
                }
                else {
                    ContentValues contentValues =new ContentValues();
                    contentValues.put("sub_cat_id",sub_cat_id+"");
                    contentValues.put("cat_id",cat_id+"");
                    contentValues.put("image",image);
                    contentValues.put("app_id",app_id+"");
                    contentValues.put("title_app",title);
                    contentValues.put("content_app",content);
                    contentValues.put("author_app",tentacgia);
                    database.insert("ThichSubCate",null,contentValues);
                    imglike.setImageResource(R.drawable.like11);
                }
            }
        });
        //webview.setBackgroundColor(Color.BLUE);
        Log.e("content",content);
        //txttacgia.setText("Tác giả : "+tentacgia);
        webview.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = webview.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        //String scandinavianCharacters = "øæå";

// Won't render correctly
       // webView.loadData(scandinavianCharacters, "text/html", "UTF-8");

// Will render correctly
       // webview.loadDataWithBaseURL(content, scandinavianCharacters, "text/html", "UTF-8", null);
        webview.loadData(content,"text/html; charset=utf-8", "utf-8");
        webview.setWebViewClient(new WebViewClient());
        Log.e("content",content);
        return view;
    }

}
