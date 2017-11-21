package com.example.thong.chan.mh_load;

public class App {
    public String getAuthor_app() {
        return author_app;
    }

    public void setAuthor_app(String author_app) {
        this.author_app = author_app;
    }

    private String id,title,content,thumbnail,sub_cat_id,cat_id,author_app;
    public App(String id, String title, String content, String thumbnail, String sub_cat_id, String cat_id,String author_app) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.sub_cat_id = sub_cat_id;
        this.cat_id = cat_id;
        this.author_app=author_app;
    }

    public App() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
