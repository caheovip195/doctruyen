package com.example.thong.chan.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thong.chan.R;
import com.example.thong.chan.mh_load.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhCong on 08/11/2017.
 */

public class AdapterDocTruyen extends RecyclerView.Adapter<AdapterDocTruyen.RecyclerViewHolder> {
    List<Category>dschude =new ArrayList<>();
    Activity context;
    public AdapterDocTruyen(List<Category> dschude, Activity context) {
        this.dschude = dschude;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.adapterdoctruyen,null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
    holder.txt.setText(dschude.get(position).getCat_name());
    }

    @Override
    public int getItemCount() {
        return dschude.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgdoctruyen);
            txt=itemView.findViewById(R.id.txtdoctruyen);
        }
    }
}
