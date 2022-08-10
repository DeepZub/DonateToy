package com.example.donatetoy.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Users {


    private String email;
    private String password;
    private String name;
    private String surname;
    private Matcher matcher;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
                    "@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);


    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("(?=.*[0-9a-zA-Z]).{6,}");

    public Users(String email) {
        this.email = email;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String email, String password, String name ,String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean emailGecerliMi(){

        matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();

    }
    public boolean sifreGecerliMi(){

        matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();

    }

}
