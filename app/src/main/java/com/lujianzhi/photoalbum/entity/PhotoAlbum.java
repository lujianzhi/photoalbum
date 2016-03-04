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
 * Created by lujianzhi on 2016/1/21.
 */
public class PhotoAlbum extends BmobObject {

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
     * 是否公开  true/false
     */
    private Boolean isPublic;

    /**
     * 相册类型
     * 0 --> 公开
     * 1 --> 隐私
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

    public PhotoAlbum(Integer id, String name, Boolean isPublic, Integer type, String coverUrl, Integer count, String comment) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.type = type;
        this.coverUrl = coverUrl;
        this.count = count;
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

    public Boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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
                ", isPublic=" + isPublic +
                ", type='" + type + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", count=" + count +
                '}';
    }
}
