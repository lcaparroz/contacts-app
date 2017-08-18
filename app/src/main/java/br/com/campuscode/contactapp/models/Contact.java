package br.com.campuscode.contactapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by campuscode02 on 8/15/17.
 */

public class Contact implements Serializable{
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
