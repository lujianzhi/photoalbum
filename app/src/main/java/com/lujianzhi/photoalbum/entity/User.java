package com.lujianzhi.photoalbum.entity;

import cn.bmob.v3.BmobUser;

/**
 * Bmob提供了一个专门的用户类——BmobUser来自动处理用户账户管理所需的功能。
 * 有了这个类，你就可以在你的应用程序中添加用户账户功能。
 * BmobUser是BmobObject的一个子类，它继承了BmobObject所有的方法，具有BmobObject相同的功能。
 * 不同的是，BmobUser增加了一些特定的关于用户账户管理相关的功能。
 * <p/>
 * BmobUser除了从BmobObject继承的属性外，还有几个特定的属性：
 * username: 用户的用户名（必需）。
 * password: 用户的密码（必需）。
 * email: 用户的电子邮件地址（可选）。
 * emailVerified:邮箱认证状态（可选）。
 * mobilePhoneNumber：手机号码（可选）。
 * mobilePhoneNumberVerified：手机号码的认证状态（可选）。
 * <p/>
 * Created by lujianzhi on 2016/1/28.
 */
public class User extends BmobUser {

    /**
     * 用户头像
     */
    private String userPortrait;

    /**
     * 用户类型
     * 0 --> 匿名用户
     * 1 --> 注册用户
     */
    private Integer userType;

    /**
     * 用户性别
     * 0 --> 男
     * 1 --> 女
     */
    private Integer sex;

    /**
     * 昵称
     */
    private String nickName;

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
