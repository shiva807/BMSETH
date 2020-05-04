package com.example.bmseth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class warden_details extends Fragment {

    private FirebaseAuth mauth;
    private FirebaseUser user;
    private DatabaseReference ref,ref2;
    TextView name,contact,email,dept,clickEmail,clickNumber;
    ImageView wardenpic;
    private String hname;
    private static String contact1,email1;
    private onFragmenttxtSelected lisner;
    private onFragmentemailSelected listener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_warden_details);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_warden_details, container, false );
        email=(TextView)view.findViewById(R.id.textView49);
        name=(TextView)view.findViewById(R.id.textView46);
        contact=(TextView)view.findViewById(R.id.textView53);
        dept=(TextView)view.findViewById(R.id.textView51);
        clickEmail=(TextView)view.findViewById(R.id.textView8);
        wardenpic=(ImageView)view.findViewById(R.id.imageView3);
        clickNumber=(TextView)view.findViewById((R.id.textView15));
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lisner.onContactSelected(contact1);
            }
        });
        clickEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEmailSelected(email.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof warden_details.onFragmenttxtSelected) {
            lisner = (warden_details.onFragmenttxtSelected) context;
        }
        if(context instanceof warden_details.onFragmentemailSelected){
            listener=(warden_details.onFragmentemailSelected)context;
        }

        else {
            throw new ClassCastException(context.toString() + "must implement listener");

        }
    }
    public interface onFragmenttxtSelected{
        public void onContactSelected(String contact1);

    }
    public interface onFragmentemailSelected{
        public void onEmailSelected(String email1);
    }

    @Override
    public void onStart() {
        super.onStart();
        ref=FirebaseDatabase.getInstance().getReference("StudentsRoomDetails").child(user.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    hname = dataSnapshot.child("hostelno").getValue(String.class);
                    //hostel.setText(hname);
                    ref2 = FirebaseDatabase.getInstance().getReference("Hostels").child(hname);
                    ref2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name1 = dataSnapshot.child("Warden name").getValue(String.class);
                                name.setText(name1);
                                contact1 = dataSnapshot.child("Warden Contact").getValue(String.class);
                                contact.setText(contact1);
                                email1=dataSnapshot.child("Warden email").getValue(String.class);
                                email.setText(email1);
                                clickEmail.setText("(Click here to file a Leave Application)");
                                clickNumber.setText("(Click on the Number to Call)");
                                String dept1=dataSnapshot.child("Warden Dept").getValue(String.class);
                                dept.setText(dept1);
                                String img=dataSnapshot.child("image").getValue(String.class);
                                Picasso.get().load(img).into(wardenpic);
                                //https://firebasestorage.googleapis.com/v0/b/bmshostels-d32ac.appspot.com/o/nbh1_nbh2_nbh5.JPG?alt=media&token=b52b9252-efc6-4e93-86f6-fb84e6635ead
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //Toast.makeText(getContext(), "ref2 ;" + ref2.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
