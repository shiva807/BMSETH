package com.example.bmseth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentRoomDetails  extends Fragment {
    private onFragmentbtnSelected listener;
    private FirebaseUser user;
    private DatabaseReference myRef,ref2;
    private FirebaseAuth mauth;
    private TextView hostel,fees,duration,econtact,food,gname,gcontact,room;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_details, container, false );
        Button bookroom=(Button)view.findViewById(R.id.button3);
        hostel=(TextView)view.findViewById(R.id.textView28);
        room=(TextView )view.findViewById(R.id.textView29);
        fees=(TextView)view.findViewById(R.id.textView30);
        food=(TextView)view.findViewById(R.id.textView31);
        duration=(TextView)view.findViewById(R.id.textView32);
        econtact=(TextView)view.findViewById(R.id.textView33);
         gname=(TextView)view.findViewById(R.id.textView34);
        gcontact=(TextView)view.findViewById(R.id.textView35);
        bookroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentbtnSelected){
            listener = (onFragmentbtnSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");

        }
    }

    public interface onFragmentbtnSelected
    {
        public void onButtonSelected();
    }


    @Override
    public void onStart() {
        super.onStart();
        myRef= FirebaseDatabase.getInstance().getReference("StudentsRoomDetails").child(user.getUid());
        //if(myRef!=null) {

            myRef.addValueEventListener(new ValueEventListener() {
                List<String>rooms=new ArrayList<>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        Button bookroom=getView().findViewById(R.id.button3);
                        bookroom.setVisibility(View.GONE);
                        String hn=dataSnapshot.child("hostelno").getValue(String.class);
                        hostel.setText(hn);
                        String fee=dataSnapshot.child("fees").getValue(String.class);
                        fees.setText(fee);
                        String fs=dataSnapshot.child("foodStatus").getValue(String.class);
                        food.setText(fs);
                        String time=dataSnapshot.child("duration").getValue(String.class);
                        duration.setText(time);
                        String emercontact=dataSnapshot.child("emergencyContact").getValue(String.class);
                        econtact.setText(emercontact);
                        String gn = dataSnapshot.child("guardianName").getValue(String.class);
                        gname.setText(gn);
                        String gc = dataSnapshot.child("guardianContact").getValue(String.class);
                        gcontact.setText(gc);
                        String  rm=dataSnapshot.child("room").getValue(String.class);
                        if(rm!=null)
                        room.setText(hn+" "+rm);
                        else
                            room.setText("Not Assigned Yet");
                        //ref2=FirebaseDatabase.getInstance().getReference(hn).child(hn+"Rooms");

                        //Toast.makeText(getContext(),"ref2 :"+ref2.toString(),Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}
