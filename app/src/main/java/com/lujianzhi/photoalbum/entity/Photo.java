package com.lujianzhi.photoalbum.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Photo implements Parcelable {

    /**
     * 照片id
     */
    private int id;

    /**
     * 照片名
     */
    private String name;

    /**
     * 照片url
     */
    private String photoUrl;

    /**
     * 照片评论
     */
    private List<Comment> comment;

    /**
     * 属于哪个相册
     */
    private int belongId;

    public Photo(){

    }

    public Photo(int id, String name, String photoUrl, List<Comment> comment, int belongId) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.comment = comment;
        this.belongId = belongId;
    }


    protected Photo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        photoUrl = in.readString();
        comment = in.createTypedArrayList(Comment.CREATOR);
        belongId = in.readInt();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(photoUrl);
        dest.writeTypedList(comment);
        dest.writeInt(belongId);
    }
}
