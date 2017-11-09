package com.example.thong.chan.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thong.chan.R;
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
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.txtten.setText(ds.get(position).getTitle());
        String sub =ds.get(position).getContent().substring(0,30);
        holder.txtmieuta.setText(sub);
        Picasso.with(activity).load(ds.get(position).getThumbnail()).error(R.drawable.anhdoctruyen).into(holder.img);
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
            txtstt=itemView.findViewById(R.id.txtsttten);
            txtmieuta=itemView.findViewById(R.id.txtsttten);
        }
    }
}
