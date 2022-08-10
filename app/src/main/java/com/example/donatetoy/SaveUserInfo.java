package com.example.donatetoy;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUserInfo {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String email;
    private String password;


    public SaveUserInfo(Context context){
        sp = context.getSharedPreferences("LoginInfo",Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void save(String email, String password) {
        editor.putString("email",email);
        editor.putString("password",password);
        editor.commit();
    }

    public void remove(){
        editor.remove("email");
        editor.remove("password");
        editor.commit();
    }

    public void get(){
        this.email = sp.getString("email","");
        this.password = sp.getString("password","");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
