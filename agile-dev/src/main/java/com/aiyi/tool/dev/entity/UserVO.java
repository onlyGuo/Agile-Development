package com.aiyi.tool.dev.entity;

import com.aiyi.core.annotation.po.FieldName;
import com.aiyi.core.annotation.po.ID;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.beans.Po;

import java.util.Date;

/**
 * @Auther: 郭胜凯
 * @Date: 2019-04-16 10:10
 * @Email 719348277@qq.com
 * @Description: 用户实体
 */
@TableName(name = "DEV_USER")
public class UserVO extends Po {

    /**
     * 用户ID
     */
    @ID
    @FieldName(name = "ID")
    private String id;

    /**
     * 用户名称
     */
    @FieldName(name = "NAME")
    private String name;

    /**
     * 用户账号
     */
    @FieldName(name = "ACCOUNT")
    private String account;

    /**
     * 用户邮箱
     */
    @FieldName(name = "EMAIL")
    private String email;

    /**
     * 用户密码
     */
    @FieldName(name = "PASSWORD")
    private String password;

    /**
     * 用户创建时间
     */
    @FieldName(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 用户最后登录时间
     */
    @FieldName(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}