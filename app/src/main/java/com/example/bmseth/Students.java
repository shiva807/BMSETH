package com.example.bmseth;


public class Students {
    public String name,usn,email,phone,address,gender,branch;
    //public Integer room;
    Students()
    {
        //Default ctor
    }
    Students(String name,String usn,String phone,String address,String gender,String email,String branch)
    {
        this.name=name;
        this.usn=usn;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.gender=gender;
        this.branch=branch;
        //this.room=room;
    }
}
