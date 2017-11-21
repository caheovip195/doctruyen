package com.example.thong.chan.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thong.chan.R;
import com.example.thong.chan.adapter.AdapterLike;
import com.example.thong.chan.mh_load.DanhSachLike;
import com.example.thong.chan.mh_load.SubCateLike;

import java.util.ArrayList;

public class TruyenDaDanhDau extends Fragment {
    ArrayList<DanhSachLike>ds=new ArrayList<>();
    RecyclerView recyclerView;
    AdapterLike adapterLike;
    ImageView imgSearch;
    EditText edtSearch;
    TextView txtClose;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.truyen_da_danh_dau, container, false);
        recyclerView=view.findViewById(R.id.list_like);
        edtSearch =view.findViewById(R.id.edtSeach3);
        imgSearch=view.findViewById(R.id.im_timkiem);
        adapterLike=new AdapterLike(getActivity(),ds);
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
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterLike);
        addEvents(view);
        return view;
    }

    private void addEvents(final View view) {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtSearch.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Nhập vào từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                    loaddata();
                }
                else {
                    SQLiteDatabase database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                    Cursor cursor =database.rawQuery("select title_app,content_app,author_app,image from ThichSubCate where title_app like ?"
                            ,new String[]{"%"+edtSearch.getText().toString()+"%"});
                    ds.clear();
                    while (cursor.moveToNext()){
                        ds.add(new DanhSachLike(cursor.getString(0),
                                cursor.getString(3)
                                ,cursor.getString(1),
                                cursor.getColumnName(2)));
                    }
                    cursor.close();
                    if(ds.size()<=0){
                        Toast.makeText(getActivity(), "Không tìm thấy truyện !", Toast.LENGTH_SHORT).show();
                        loaddata();
                    }
                    else {
                        //
                        adapterLike.notifyDataSetChanged();
                    }
                }
            }
        });
       /* edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                 Log.e("beforeChange",s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("onChangeText",s.toString());
                ds.clear();
                SQLiteDatabase database=getActivity().openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
                Cursor cursor =database.rawQuery("select title_app,content_app,author_app,image from ThichSubCate where title_app like ?"
                        ,new String[]{"%"+s.toString()+"%"});
                while (cursor.moveToNext()){
                    ds.add(new DanhSachLike(cursor.getString(0),
                            cursor.getString(3)
                            ,cursor.getString(1),
                            cursor.getColumnName(2)));
                }
                cursor.close();
                if(ds.size()<=0){
                    TextView button =new TextView(getActivity());
                    button.setText("Không tìm thấy!");
                    button.setTextSize(17);
                    button.setTextColor(Color.BLACK);
                    button.setGravity(Gravity.CENTER);
                    LinearLayout layout =view.findViewById(R.id.layout_like);
                    layout.setGravity(Gravity.CENTER);
                    layout.addView(button);
                    layout.removeAllViews();
                }
                else {
                    adapterLike.notifyDataSetChanged();
                }*/

    }

    private void loaddata(){
        ds.clear();
        SQLiteDatabase database =getActivity().openOrCreateDatabase("doctruyen.sqlite", Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select title_app,content_app,author_app,image from ThichSubCate",null);
        while (cursor.moveToNext()){
            // ds.add(new SubCateLike(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            ds.add(new DanhSachLike(cursor.getString(0),
                    cursor.getString(3)
                    ,cursor.getString(1),
                    cursor.getColumnName(2)));
        }
        cursor.close();
        adapterLike.notifyDataSetChanged();
        Log.e("sum_like",ds.size()+"");
    }
}