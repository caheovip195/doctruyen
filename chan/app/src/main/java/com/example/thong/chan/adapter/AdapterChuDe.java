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
import com.example.thong.chan.fragment.TenTruyen;
import com.example.thong.chan.mh_load.SubCate;
import com.squareup.picasso.Picasso;

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
             holder.txtstt.setText((position+1)+"");
             holder.layout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                   //  Toast.makeText(activity, ""+position, Toast.LENGTH_LONG).show();
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
        Picasso.with(activity).load(ds.get(position).getImage()).error(R.drawable.sach).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class RecycleViewHoder extends RecyclerView.ViewHolder{
     TextView txt,txtstt;
     ImageView img;
     LinearLayout layout;
     public RecycleViewHoder(View itemView) {
         super(itemView);
         txt=itemView.findViewById(R.id.txt_them);
         img=itemView.findViewById(R.id.img_them);
         txtstt=itemView.findViewById(R.id.txtstt);
         layout=itemView.findViewById(R.id.list_them);
     }
 }
}
