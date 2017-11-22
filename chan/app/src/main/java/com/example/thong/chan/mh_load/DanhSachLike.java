package com.example.thong.chan.mh_load;

/**
 * Created by ThanhCong on 20/11/2017.
 */

public class DanhSachLike {
    private String title,image,content,author,sub_cat_id,cat_id,app_id;

    public DanhSachLike(String title, String image, String content, String author,String sub_cat_id,String cat_id,String app_id) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.author = author;
        this.app_id=app_id;
        this.cat_id=cat_id;
        this.sub_cat_id=sub_cat_id;
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

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
