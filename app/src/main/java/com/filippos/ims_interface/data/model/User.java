package com.filippos.ims_interface.data.model;

public class User {

    public int id;
    public String first_name;
    public String last_name;
    public String facility_name;
    public String role_description;
    public int access_level;

    public User(int id, String first_name, String last_name, String facility_name, String role_description, int access_level) {

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.facility_name = facility_name;
        this.role_description = role_description;
        this.access_level = access_level;
    }
}
