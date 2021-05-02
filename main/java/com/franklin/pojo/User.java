package com.franklin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String uuid;
    private String name;
    private String password;
    private int sex;//0 for Women, 1 for Man
    private int age;
    private String email;
    private String department;
    private String level;//admin,organization,staff,student,guest
    private String salt;
    private String ciphertext;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public int getSex () {
        return sex;
    }

    public void setSex (int sex) {
        this.sex = sex;
    }

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getDepartment () {
        return department;
    }

    public void setDepartment (String department) {
        this.department = department;
    }

    public String getLevel () {
        return level;
    }

    public void setLevel (String level) {
        this.level = level;
    }

    public String getSalt () {
        return salt;
    }

    public void setSalt (String salt) { this.salt = salt; }

    public String getCiphertext () {
        return ciphertext;
    }

    public void setCiphertext (String ciphertext) { this.ciphertext = ciphertext; }

}
