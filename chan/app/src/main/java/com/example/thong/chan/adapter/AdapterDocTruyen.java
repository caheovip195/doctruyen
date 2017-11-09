package com.example.thong.chan.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thong.chan.R;
import com.example.thong.chan.fragment.ChuDeTruyen;
import com.example.thong.chan.mh_load.Category;
import com.example.thong.chan.mh_load.SubCate;

import java.util.ArrayList;
import java.util.List;

public class AdapterDocTruyen extends RecyclerView.Adapter<AdapterDocTruyen.RecyclerViewHolder>{
    List<Category>dschude =new ArrayList<>();
    List<SubCate>dscate=new ArrayList<>();
    Activity context;
    int so=-1;
    public AdapterDocTruyen(List<Category> dschude, Activity context) {
        this.dschude = dschude;
        this.context = context;
    }
    public AdapterDocTruyen(int so,List<SubCate>dscate,Activity context){
        this.so=so;
        this.dscate=dscate;
        this.context=context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.adapterdoctruyen,null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        if(so>0){
            holder.txt.setText(dscate.get(position).getSub_cat_name());
        }
        else {
            holder.txt.setText(dschude.get(position).getCat_name());
        }

    holder.layout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle agruments =new Bundle();
            agruments.putString("cat_id",dschude.get(position).getCat_id());
            ChuDeTruyen chuDeTruyen =new ChuDeTruyen();
            chuDeTruyen.setArguments(agruments);
            FragmentManager manager =context.getFragmentManager();
            FragmentTransaction transaction =manager.beginTransaction();
            transaction.replace(R.id.content_frame,chuDeTruyen);
            transaction.addToBackStack("chudetruyen");
            transaction.commit();
        }
    });
    }

    @Override
    public int getItemCount() {
        return dschude.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        LinearLayout layout;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgdoctruyen);
            txt=itemView.findViewById(R.id.txtdoctruyen);
            layout=itemView.findViewById(R.id.listchude);
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(String dschude);
    }
    private OnItemClickedListener onItemClickedListener;
    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
