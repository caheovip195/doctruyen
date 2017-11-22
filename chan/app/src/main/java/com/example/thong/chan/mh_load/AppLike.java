package com.example.thong.chan.mh_load;

/**
 * Created by ThanhCong on 20/11/2017.
 */

public class AppLike {

    private String cat_id,sub_cat_id,title_app,image,app_id,author_app,content_app;

    public AppLike(String cat_id, String sub_cat_id, String title_app, String image, String app_id, String author_app, String content_app) {
        this.cat_id = cat_id;
        this.sub_cat_id = sub_cat_id;
        this.title_app = title_app;
        this.image = image;
        this.app_id = app_id;
        this.author_app = author_app;
        this.content_app = content_app;
    }
    public AppLike(){

    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getTitle_app() {
        return title_app;
    }

    public void setTitle_app(String title_app) {
        this.title_app = title_app;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getAuthor_app() {
        return author_app;
    }

    public void setAuthor_app(String author_app) {
        this.author_app = author_app;
    }

    public String getContent_app() {
        return content_app;
    }

    public void setContent_app(String content_app) {
        this.content_app = content_app;
    }
}
