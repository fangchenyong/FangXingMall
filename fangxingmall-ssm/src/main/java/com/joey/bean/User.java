package com.joey.bean;

/**
 * 〈fxm-user〉
 *
 * @author Joey
 * @create 2019-05-15
 * @since 1.0.0
 */

public class User {

    private Integer Id;
    private String Name;
    private String NickName;
    private String PassWord;
    private String UserName;
    private String CellPhone;
    private String Email;
    private String Photo;
    private String Level;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", NickName='" + NickName + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", UserName='" + UserName + '\'' +
                ", CellPhone='" + CellPhone + '\'' +
                ", Email='" + Email + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Level='" + Level + '\'' +
                '}';
    }
}
