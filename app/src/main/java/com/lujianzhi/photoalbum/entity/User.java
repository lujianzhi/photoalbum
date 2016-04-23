package com.lujianzhi.photoalbum.entity;

public class User {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型
     * 0 --> 匿名用户
     * 1 --> 注册用户
     */
    private int userType;

    /**
     * 用户性别
     * 0 --> 男
     * 1 --> 女
     */
    private int sex;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
