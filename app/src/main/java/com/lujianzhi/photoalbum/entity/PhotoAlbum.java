package com.lujianzhi.photoalbum.entity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Bmob存储的数据是建立在BmobObject基础上的，所以任何要保存的数据对象必须继承自BmobObject类。
 * BmobObject类本身包含objectId、createdAt、updatedAt、ACL四个默认的属性，
 * objectId是数据的唯一标示，相当于数据库中表的主键，createdAt是数据的创建时间，updatedAt是数据的最后修改时间，ACL是数据的操作权限。
 * <p/>
 * 尽可能使用Integer、Boolean，而不是int、boolean，也就是选择包装类，而不是使用基本数据类型
 * <p/>
 * JavaBean不需要对objectId、createdAt、updatedAt、ACL四个属性进行定义。
 * <p/>
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbum extends BmobObject implements Parcelable{

    public static final String TABLENAME = "album";

    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册名
     */
    private String name;

    /**
     * 相册类型
     * 0 --> 隐私
     * 1 --> 公开
     */
    private Integer type;

    /**
     * 相册封面url
     */
    private String coverUrl;

    /**
     * 相片数量
     */
    private Integer count;

    /**
     * 相册评论
     */
    private String comment;

    public PhotoAlbum() {
        this.setTableName(PhotoAlbum.TABLENAME);
    }

    public PhotoAlbum(Integer id, String name, Integer type, String coverUrl, Integer count, String comment) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.coverUrl = coverUrl;
        this.count = count;
        this.comment = comment;
    }

    protected PhotoAlbum(Parcel in) {
        name = in.readString();
        coverUrl = in.readString();
        comment = in.readString();
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "PhotoAlbum{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(coverUrl);
        dest.writeString(comment);
    }
}
