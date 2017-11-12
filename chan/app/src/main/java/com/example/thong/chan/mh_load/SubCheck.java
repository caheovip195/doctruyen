package com.example.thong.chan.mh_load;

/**
 * Created by ThanhCong on 12/11/2017.
 */

public class SubCheck {
    private String sub_cat_id,cat_id;

    public SubCheck(String sub_cat_id, String cat_id) {
        this.sub_cat_id = sub_cat_id;
        this.cat_id = cat_id;
    }

    public SubCheck() {
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
