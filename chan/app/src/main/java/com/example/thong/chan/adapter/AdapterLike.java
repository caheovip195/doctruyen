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

import com.example.thong.chan.R;
import com.example.thong.chan.fragment.TenTruyen;
import com.example.thong.chan.mh_load.SubCateLike;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLike extends RecyclerView.Adapter<AdapterLike.RecycleViewHolder>{
    Activity activity;
    ArrayList<SubCateLike>ds=new ArrayList<>();

    public AdapterLike(Activity activity, ArrayList<SubCateLike> ds) {
        this.activity = activity;
        this.ds = ds;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(activity);
        View view =inflater.inflate(R.layout.adapter_like,null);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, final int position) {
           holder.txtdoctruyenlike.setText(ds.get(position).getSub_cat_name());
           holder.txtsttdetai_like.setText(position+"");
           Picasso.with(activity).load(ds.get(position).getImage()).error(R.drawable.sach).into(holder.img);
           holder.layout.setOnClickListener(new View.OnClickListener() {
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

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtdoctruyenlike,txtsttdetai_like;
        LinearLayout layout;
        public RecycleViewHolder(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.listchude_like);
            img=itemView.findViewById(R.id.imgdoctruyen_like);
            txtdoctruyenlike=itemView.findViewById(R.id.txtdoctruyen_like);
            txtsttdetai_like=itemView.findViewById(R.id.txtsttdetai_like);
        }
    }
}
