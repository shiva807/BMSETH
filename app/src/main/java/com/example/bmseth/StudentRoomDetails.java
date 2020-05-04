package com.example.bmseth;

public class StudentRoomDetails {
    public String hostelno,room,fees,foodStatus,duration,emergencyContact,guardianName,guardianContact;
    StudentRoomDetails()
    {
        //default ctor;
    }
    StudentRoomDetails(String h,String fees,String FS,String d,String ec,String gn,String gc,String Room)
    {
        this.hostelno=h;

        this.fees=fees;
        this.foodStatus=FS;
        this.duration=d;
        this.emergencyContact=ec;
        this.guardianName=gn;
        this.guardianContact=gc;
        this.room=Room;
    }
}
