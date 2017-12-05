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
import android.text.Html;
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
import com.example.thong.chan.mh_load.SubCheck;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterTenTruyen  extends RecyclerView.Adapter<AdapterTenTruyen.RecycleViewHolder>{
    Activity activity;
    List<App>ds =new ArrayList<>();
    SQLiteDatabase database;
    ArrayList<SubCheck>listcheck=new ArrayList<>();
    public AdapterTenTruyen(Activity activity, List<App> ds) {
        this.activity = activity;
        this.ds = ds;
        database=activity.openOrCreateDatabase("doctruyen.sqlite",Context.MODE_PRIVATE,null);
        Cursor cursor =database.rawQuery("select sub_cat_id,cat_id,app_id from ThichSubCate",null);
        while (cursor.moveToNext()){
            listcheck.add(new SubCheck(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        cursor.close();
    }
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(activity);
        View view =inflater.inflate(R.layout.tentruyen_adapter,null);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
        //holder.txtten.setText(ds.get(position).getTitle());
        boolean flag2=false;
        for(SubCheck ck : listcheck){
            if(Integer.parseInt(ck.getCat_id())==Integer.parseInt(ds.get(position).getCat_id())
                    && Integer.parseInt(ck.getSub_cat_id())== Integer.parseInt(ds.get(position).getSub_cat_id())
                    && Integer.parseInt(ck.getApp_id())==Integer.parseInt(ds.get(position).getId())){
                flag2=true;
                break;
            }
        }
        if(flag2==true){
            holder.nutlike.setImageResource(R.drawable.like11);
        }
        else {
            holder.nutlike.setImageResource(R.drawable.like2);
        }
        holder.txtstt.setText((position+1)+"");
        String sub =ds.get(position).getContent().substring(0,30);
        holder.txtten.setText(ds.get(position).getTitle());
        holder.txttacgia.setText("Tác giả: "+ds.get(position).getAuthor_app());
       // holder.txtmieuta.setText(sub);
        holder.nutlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database=activity.openOrCreateDatabase("doctruyen.sqlite", Context.MODE_PRIVATE,null);
                boolean flag=false;
                Cursor cursor =database.rawQuery("select cat_id,sub_cat_id,app_id from ThichSubCate",null);
                while (cursor.moveToNext()){
                    if(Integer.parseInt(cursor.getString(0))==Integer.parseInt(ds.get(position).getCat_id())
                     && Integer.parseInt(cursor.getString(1))==Integer.parseInt(ds.get(position).getSub_cat_id())
                     && Integer.parseInt(cursor.getString(2))==Integer.parseInt(ds.get(position).getId())){
                        flag=true;
                        break;
                    }
                }
                cursor.close();
                if(flag==true){
                    //Delete item
                    database.delete("ThichSubCate","sub_cat_id=? and cat_id=? and app_id=?"
                            ,new String[]{ds.get(position).getSub_cat_id()
                                    ,ds.get(position).getCat_id()
                                    ,ds.get(position).getId()
                    });
                    holder.nutlike.setImageResource(R.drawable.like2);
                }
                else{
                    ContentValues contentValues =new ContentValues();
                    contentValues.put("sub_cat_id",ds.get(position).getSub_cat_id());
                    contentValues.put("cat_id",ds.get(position).getCat_id());
                    contentValues.put("image",ds.get(position).getThumbnail());
                    contentValues.put("app_id",ds.get(position).getId());
                    contentValues.put("title_app",ds.get(position).getTitle());
                    contentValues.put("content_app",ds.get(position).getContent());
                    contentValues.put("author_app",ds.get(position).getAuthor_app());
                    database.insert("ThichSubCate",null,contentValues);
                    holder.nutlike.setImageResource(R.drawable.like11);
                }
            }
        });
        Log.e("position",(position+1)+"");
        Picasso.with(activity).load(ds.get(position).getThumbnail()).error(R.drawable.sach).into(holder.img);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString("content",ds.get(position).getContent());
                bundle.putString("tacgia",ds.get(position).getAuthor_app());
                bundle.putString("cat_id",ds.get(position).getCat_id());
                bundle.putString("sub_cat_id",ds.get(position).getSub_cat_id());
                bundle.putString("app_id",ds.get(position).getId());
                bundle.putString("image",ds.get(position).getThumbnail());
                bundle.putString("title",ds.get(position).getTitle());
                DocContent content =new DocContent();
                content.setArguments(bundle);
                FragmentManager manager =activity.getFragmentManager();
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.replace(R.id.content_frame,content);
                transaction.addToBackStack("doccontent");
                transaction.commit();
            }
        });
        String desc=ds.get(position).getContent().split("<div")[0];
        holder.doantext.setText(Html.fromHtml(desc).toString().trim().substring(1)+"...");
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder{
           LinearLayout layout;
           ImageView img,nutlike;
           TextView txtten,txtstt,txtmieuta,txttacgia,doantext;
        public RecycleViewHolder(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.listten);
            img=itemView.findViewById(R.id.imgten);
            txtten=itemView.findViewById(R.id.txtten);
            txtstt=itemView.findViewById(R.id.txtstt);
            nutlike=itemView.findViewById(R.id.nutlike);
            txttacgia=itemView.findViewById(R.id.tentacgia);
            doantext=itemView.findViewById(R.id.doantext);
            //txtmieuta=itemView.findViewById(R.id.txtstt);

        }
    }
}
