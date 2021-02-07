package com.qiufeng.blog.entity;

import javax.persistence.*;

@Entity
@Table(name = "sys_user")
public class SysUser {

    /** 用户ID */
    @Id
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户邮箱 */
    private String email;

    /** 用户手机号 */
    private Long phone;

    /** 用户性别：0表示男，1表示女，2表示未知 */
    private char sex;

    /** 用户头像地址 */
    private String avatar;

    /** 用户密码 */
    private String password;

    /** 账户状态：0表示正常，1表示禁用 */
    private char status;

    /** 账户删除标识：0表示未删除，1表示已删除 */
    private char delFlag;

    /** 用户登录Ip地址 */
    private String loginIp;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && userId == 1L;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(char delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", delFlag=" + delFlag +
                ", loginIp='" + loginIp + '\'' +
                '}';
    }
}
