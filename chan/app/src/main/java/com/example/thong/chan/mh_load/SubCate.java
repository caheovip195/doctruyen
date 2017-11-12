package com.example.thong.chan.mh_load;

public class SubCate {
    private String sub_cat_id,sub_cat_name,image,cat_id;

    public SubCate(String sub_cat_id, String sub_cat_name, String image, String cat_id) {
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
        this.image = image;
        this.cat_id = cat_id;
    }

    public SubCate() {
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
