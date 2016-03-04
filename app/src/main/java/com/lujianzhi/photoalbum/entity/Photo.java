package com.lujianzhi.photoalbum.entity;

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
 * Created by lujianzhi on 2016/1/22.
 */
public class Photo extends BmobObject {

    /**
     * 照片id
     */
    private Integer id;

    /**
     * 照片名
     */
    private String name;

    /**
     * 照片类型
     */
    private String type;

    /**
     * 照片url
     */
    private String photoUrl;

    /**
     * 照片评论
     */
    private String comment;

    /**
     * 资源id
     */
    private Integer resId;

    public Photo() {

    }

    public Photo(Integer id, String name, String type, String photoUrl, Integer resId, String comment) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photoUrl = photoUrl;
        this.resId = resId;
        this.comment = comment;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }
}
