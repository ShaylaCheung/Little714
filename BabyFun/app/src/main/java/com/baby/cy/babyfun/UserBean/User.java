package com.baby.cy.babyfun.UserBean;

/**
 * Created by chenyu on 15/10/25.
 */
public class User {
    private String user_name;
    private String user_password;
    private String user_phone;
    private String user_pic_path;

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_pic_path() {
        return user_pic_path;
    }

    public void setUser_pic_path(String user_pic_path) {
        this.user_pic_path = user_pic_path;
    }
}
