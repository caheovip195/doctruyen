package com.example.thong.chan.mh_load;

/**
 * Created by ThanhCong on 20/11/2017.
 */

public class DanhSachLike {
    private String title,image,content,author;

    public DanhSachLike(String title, String image, String content, String author) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.author = author;
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
