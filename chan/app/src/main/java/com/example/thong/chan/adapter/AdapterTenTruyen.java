package com.example.thong.chan.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.thong.chan.fragment.DocContent;
import com.example.thong.chan.mh_load.App;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhCong on 09/11/2017.
 */

public class AdapterTenTruyen  extends RecyclerView.Adapter<AdapterTenTruyen.RecycleViewHolder>{
    Activity activity;
    List<App>ds =new ArrayList<>();

    public AdapterTenTruyen(Activity activity, List<App> ds) {
        this.activity = activity;
        this.ds = ds;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(activity);
        View view =inflater.inflate(R.layout.tentruyen_adapter,null);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, final int position) {
        //holder.txtten.setText(ds.get(position).getTitle());
        holder.txtstt.setText((position+1)+"");
        String sub =ds.get(position).getContent().substring(0,30);
        holder.txtten.setText(ds.get(position).getTitle());
       // holder.txtmieuta.setText(sub);
        Log.e("position",(position+1)+"");
        Picasso.with(activity).load(ds.get(position).getThumbnail()).error(R.drawable.sach).into(holder.img);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString("content",ds.get(position).getContent());
                DocContent content =new DocContent();
                content.setArguments(bundle);
                FragmentManager manager =activity.getFragmentManager();
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.replace(R.id.content_frame,content);
                transaction.addToBackStack("doccontent");
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
           LinearLayout layout;
           ImageView img;
           TextView txtten,txtstt,txtmieuta;
        public RecycleViewHolder(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.listten);
            img=itemView.findViewById(R.id.imgten);
            txtten=itemView.findViewById(R.id.txtten);
            txtstt=itemView.findViewById(R.id.txtstt);
            //txtmieuta=itemView.findViewById(R.id.txtstt);
        }
    }
}
