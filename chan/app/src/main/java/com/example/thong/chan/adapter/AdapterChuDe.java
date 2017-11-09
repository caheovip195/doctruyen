package com.example.thong.chan.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thong.chan.R;
import com.example.thong.chan.fragment.ChuDeTruyen;
import com.example.thong.chan.mh_load.SubCate;

import java.util.ArrayList;
import java.util.List;

public class AdapterChuDe extends RecyclerView.Adapter<AdapterChuDe.RecycleViewHoder> {
    Activity activity;
    List<SubCate>ds=new ArrayList<>();

    public AdapterChuDe(Activity activity, List<SubCate> ds) {
        this.activity = activity;
        this.ds = ds;
    }

    @Override
    public RecycleViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(activity);
        View view =inflater.inflate(R.layout.adapter_chude,null);
        return new RecycleViewHoder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewHoder holder, final int position) {
             holder.txt.setText(ds.get(position).getSub_cat_name());
             holder.layout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(activity, ""+position, Toast.LENGTH_LONG).show();
                 }
             });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class RecycleViewHoder extends RecyclerView.ViewHolder{
     TextView txt;
     ImageView img;
     LinearLayout layout;
     public RecycleViewHoder(View itemView) {
         super(itemView);
         txt=itemView.findViewById(R.id.txt_them);
         img=itemView.findViewById(R.id.img_them);
         layout=itemView.findViewById(R.id.list_them);
     }
 }
}
