package com.lujianzhi.photoalbum.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lawson on 2016/4/17.
 */
public class Comment implements Parcelable {

    /**
     * 评论ID
     */
    private int id;
    /**
     * 评论内容
     */
    private String content; // 评论内容
    /**
     * 评论相册的ID
     */
    private int photoId;// 评论相册的Id
    /**
     * 评论人ID
     */
    private int userId;// 评论人Id
    /**
     * 评论人
     */
    private String name; // 评论人
    /**
     * 时间
     */
    private String date;

    public Comment() {
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        content = in.readString();
        photoId = in.readInt();
        userId = in.readInt();
        name = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeInt(photoId);
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
