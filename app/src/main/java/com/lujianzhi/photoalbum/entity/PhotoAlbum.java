package com.lujianzhi.photoalbum.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoAlbum implements Parcelable {

    /**
     * 相册id
     */
    private int id;

    /**
     * 相册名
     */
    private String name;

    /**
     * 相册类型
     * 0 --> 隐私
     * 1 --> 公开
     */
    private int type;

    /**
     * 相册封面url
     */
    private String coverUrl;

    public PhotoAlbum() {
    }

    protected PhotoAlbum(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readInt();
        coverUrl = in.readString();
    }

    public static final Creator<PhotoAlbum> CREATOR = new Creator<PhotoAlbum>() {
        @Override
        public PhotoAlbum createFromParcel(Parcel in) {
            return new PhotoAlbum(in);
        }

        @Override
        public PhotoAlbum[] newArray(int size) {
            return new PhotoAlbum[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public String toString() {
        return "PhotoAlbum{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(type);
        dest.writeString(coverUrl);
    }
}
