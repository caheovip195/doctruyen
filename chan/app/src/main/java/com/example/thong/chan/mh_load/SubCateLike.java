package com.example.thong.chan.mh_load;

/**
 * Created by ThanhCong on 12/11/2017.
 */

public class SubCateLike extends SubCate {
    public SubCateLike(String sub_cat_id, String sub_cat_name, String image, String cat_id) {
       // super(sub_cat_id, sub_cat_name, image, cat_id);
        this.status = status;
        this.sub_cat_id=sub_cat_id;
        this.sub_cat_name=sub_cat_name;
        this.image=image;
        this.cat_id=cat_id;
    }
    public SubCateLike(String sub_cat_id,String sub_cat_name,String image){
        this.sub_cat_name=sub_cat_name;
        this.sub_cat_id=sub_cat_id;
        this.image=image;
    }
    public SubCateLike(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected String status;
}
