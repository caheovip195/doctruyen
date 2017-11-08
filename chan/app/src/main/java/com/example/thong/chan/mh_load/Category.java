package com.example.thong.chan.mh_load;

/**
 * Created by ThanhCong on 08/11/2017.
 */

public class Category {
    private String cat_id;
    private String cat_name;
    private String url_image;

    public Category(String cat_id, String cat_name, String url_image) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.url_image = url_image;
    }

    public Category() {
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
