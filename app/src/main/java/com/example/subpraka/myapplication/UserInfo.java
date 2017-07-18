package com.example.subpraka.myapplication;

/**
 * Created by subpraka on 7/12/2017.
 */

public class UserInfo {


    int _id;
    String _name;
    String _email;
    String _password;

    public UserInfo() {

    }

    public UserInfo(int id, String name, String email, String _password) {
        this._id = id;
        this._name=name;
        this._email=email;
        this._password=_password;

    }

    public UserInfo(String name, String email, String _password){
        this._name=name;
        this._email=email;
        this._password=_password;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_email() {
        return _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

}
