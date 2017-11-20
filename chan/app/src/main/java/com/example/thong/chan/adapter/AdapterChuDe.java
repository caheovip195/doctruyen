package com.example.thong.chan.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thong.chan.R;
import com.example.thong.chan.fragment.TenTruyen;
import com.example.thong.chan.mh_load.SubCateLike;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class AdapterChuDe extends RecyclerView.Adapter<AdapterChuDe.RecycleViewHoder> {
    Activity activity;
    List<SubCateLike>ds=new ArrayList<>();
    String catename;
    public AdapterChuDe(Activity activity, List<SubCateLike> ds,String catename) {
        this.activity = activity;
        this.ds = ds;
        this.catename=catename;
    }

    @Override
    public RecycleViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(activity);
        View view =inflater.inflate(R.layout.adapter_chude,null);
        return new RecycleViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final RecycleViewHoder holder, final int position) {
       // final SQLiteDatabase database =activity.openOrCreateDatabase("doctruyen.sqlite", Context.MODE_PRIVATE,null);
        holder.txt.setText(ds.get(position).getSub_cat_name());
             holder.txtmieuta.setText(catename);
             holder.txtstt.setText((position+1)+"");
             Picasso.with(activity).load(ds.get(position).getImage()).error(R.drawable.sach).into(holder.img);
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Bundle bundle =new Bundle();
                     bundle.putString("sub_cat_id",ds.get(position).getSub_cat_id());
                     TenTruyen fragment=new TenTruyen();
                     fragment.setArguments(bundle);
                     FragmentManager manager =activity.getFragmentManager();
                     FragmentTransaction transaction =manager.beginTransaction();
                     transaction.replace(R.id.content_frame,fragment);
                     transaction.addToBackStack("tentruyen");
                     transaction.commit();
                 }
             });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }


    public class RecycleViewHoder extends RecyclerView.ViewHolder{
     TextView txt,txtstt,txtmieuta;
     ImageView img;
     LinearLayout layout;
     public RecycleViewHoder(View itemView) {
         super(itemView);
         txt=itemView.findViewById(R.id.txt_them);
         img=itemView.findViewById(R.id.img_them);
         txtstt=itemView.findViewById(R.id.txtstt);
         layout=itemView.findViewById(R.id.list_them);
         txtmieuta=itemView.findViewById(R.id.txt_mieuta_chude);
       //  imglike=itemView.findViewById(R.id.like);
     }
 }
}
