package com.example.bmseth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class BookRoomActivity extends AppCompatActivity {

    Spinner hostel_num, hostel_dur;
    ArrayList<String> hostelnames, hdurations;
    String duration,hostel;
    int dur_val=0;
    Button book;
    int c=0;

    RadioGroup food;
    RadioButton fc;   int radioId;
    String fc1="Veg";
    EditText ettotalAmount, etfees, gname,gcontact, emergcontact;
    String fee_pm;
    float tot_amt=0, fee_pm_val=0;
    String ta;
    int flag1=1,flag2=1;
    String assignedRoom;
     List<String>rooms=new ArrayList<>();

    EditText etdate;
    DatePickerDialog.OnDateSetListener setListener;


    //Firebase inits
   private FirebaseAuth mauth;
    private FirebaseUser user;
    private DatabaseReference mref,mref2;

    private static final Pattern PHONE_PATTERN= Pattern.compile("^" + "\\d{10}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);


        mauth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference("StudentsRoomDetails");

        book=(Button)findViewById(R.id.btn);
        gname=(EditText)findViewById(R.id.editText11);
        gcontact=(EditText)findViewById(R.id.editText12);
        emergcontact=(EditText)findViewById(R.id.editText13);
        hostel_num=(Spinner)findViewById(R.id.spin_hn);
        hostelnames=new ArrayList<>();
        hostelnames.add("--Hostel Names--");
        hostelnames.add("NGH2");
        hostelnames.add("NGH4");
        hostelnames.add("NGH5");
        hostelnames.add("NBH1");
        hostelnames.add("NBH2");
        hostelnames.add("NBH3");
        hostelnames.add("NBH4");
        hostelnames.add("NBH5");
        //hostelnames.add("IH");
        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, hostelnames);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hostel_num.setAdapter(adp);

        hostel_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                hostel=hostelnames.get(arg2);


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                flag1=0;

            }
        });

        hostel_dur=(Spinner)findViewById(R.id.spin_hd);
        hdurations=new ArrayList<>();
        hdurations.add("--Months--");
        hdurations.add("3");
        hdurations.add("6");
        hdurations.add("9");
        hdurations.add("12");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, hdurations);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hostel_dur.setAdapter(adp1);

        hostel_dur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                duration=hdurations.get(arg2);

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                    flag2=0;
            }
        });
        etfees=(EditText)findViewById(R.id.editText10);
        etfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hostel=="NBH1"||hostel=="NBH2"||hostel=="NBH3"||hostel=="NBH4"||hostel=="NBH5")
                    etfees.setText(Float.toString(13000));
                else
                    etfees.setText(Float.toString(12000));
            }
        });






        food=findViewById(R.id.foodchoice);



        ettotalAmount=(EditText)findViewById(R.id.editText14);

        /*fee_pm=etfees.getText().toString();

        try {
            fee_pm_val=Float.parseFloat(etfees.getText().toString());
            tot_amt=fee_pm_val;
        } catch(NumberFormatException nfe) {
            Log.i("Error","Could not parse " + nfe);
        }

        try {
            dur_val=Integer.parseInt(duration);
        } catch(NumberFormatException nfe) {
            Log.i("Error", "Could not parse " + nfe);
        }




            if (radioId == R.id.nonveg) {
                tot_amt =(Float) (fee_pm_val * dur_val + 2000);
            } else {
                tot_amt = (Float)(fee_pm_val * dur_val);
            }


        try{
            ta=String.valueOf(tot_amt);
        }catch(NumberFormatException nf){
            Log.i("Error:","Could not covert to String" + nf);
        }*/
        ettotalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fee_pm_val=Float.parseFloat(etfees.getText().toString());
                    //tot_amt=fee_pm_val;
                } catch(NumberFormatException nfe) {
                    Log.i("Error","Could not parse " + nfe);
                }
                //fee_pm_val=Float.parseFloat(etfees.getText().toString());
                try {
                    dur_val=Integer.parseInt(duration);
                } catch(NumberFormatException nfe) {
                    Log.i("Error", "Could not parse " + nfe);
                }
                //dur_val=Integer.parseInt(duration);

                if (radioId == R.id.nonveg) {
                    tot_amt =(Float) (fee_pm_val * dur_val + 2000);
                } else {
                    tot_amt = (Float)(fee_pm_val * dur_val);
                }


                ettotalAmount.setText("Rs."+tot_amt);
            }
        });



        etdate=findViewById(R.id.editText15);
        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(BookRoomActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String date=dayOfMonth+"/"+month+"/"+year;
                        etdate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        mref2=FirebaseDatabase.getInstance().getReference("Hostels").child("Rooms");
        //Log.d("ref2 ",mref2.toString());

        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rooms.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    String rm=dataSnapshot1.getValue(String.class);
                    rooms.add(rm);
                    c++;
                }
                //assignedRoom=rooms.get(0);
                //Log.d("Room booked ", assignedRoom);
                //assignedRoom=dataSnapshot.child("1").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        book.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validate()&&validatecontacts())
                {
                    final String gnm=gname.getText().toString().trim();
                    final String gphn=gcontact.getText().toString().trim();
                    final String econtact=emergcontact.getText().toString().trim();
                    final String total_fees=ettotalAmount.getText().toString().trim();
                    //final String duration=etdate.getText().toString().trim();
                    user=mauth.getCurrentUser();
                    assignedRoom=rooms.get(22-c);
                    mref2=FirebaseDatabase.getInstance().getReference("Hostels").child("Rooms").child(Integer.toString(22-c));
                    mref2.setValue(null);


                    StudentRoomDetails srd=new StudentRoomDetails(hostel,total_fees,fc1,duration+" Months",econtact,gnm,gphn,assignedRoom);
                    mref.child(user.getUid()).setValue(srd);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Toast.makeText(BookRoomActivity.this, "Room Booking successful ", Toast.LENGTH_SHORT).show();
                        }
                    },3000);
                    Toast.makeText(BookRoomActivity.this, "Room Booking successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BookRoomActivity.this,StuDashboardActivity.class));
                }
            }
        });

    }

    public void checkbutton(View v)
    {
        radioId=food.getCheckedRadioButtonId();

        fc=findViewById(radioId);
        fc1= fc.getText().toString();
        if(fc1!="Veg")
            fc1="Non Veg";
    }




    private Boolean validate()
    {
        boolean result=false;
        String etfees1=etfees.getText().toString();
        String gname1=gname.getText().toString();
        String gcontact1=gcontact.getText().toString();
        String econtact1=emergcontact.getText().toString();
        String etdate1=etdate.getText().toString();
        String ettotalAmount1=ettotalAmount.getText().toString();

        if(etfees1.isEmpty()||etdate1.isEmpty()||gname1.isEmpty()||gcontact1.isEmpty()||econtact1.isEmpty()||flag1==0||flag2==0||ettotalAmount1.isEmpty())
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        else
            result=true;
        return result;

    }
    private Boolean validatecontacts() {
        String gc = gcontact.getText().toString();
        String ec = emergcontact.getText().toString();
         if(!PHONE_PATTERN.matcher(gc).matches()&&!PHONE_PATTERN.matcher(ec).matches())
        {
            gcontact.setError("Enter a Valid Phone Number");
            emergcontact.setError("Enter a Valid Phone Number");
            Toast.makeText(BookRoomActivity.this, "Enter the form carefully with correct details", Toast.LENGTH_SHORT).show();
            return false;
        }
       else if (!PHONE_PATTERN.matcher(gc).matches()) {
            gcontact.setError("Enter a Valid Phone Number");
            Toast.makeText(BookRoomActivity.this, "Enter the form carefully with correct details", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PHONE_PATTERN.matcher(ec).matches()) {
            emergcontact.setError("Enter a Valid Phone Number");
            Toast.makeText(BookRoomActivity.this, "Enter the form carefully with correct details", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            gcontact.setError(null);
            emergcontact.setError(null);
            return true;
        }
    }



    @Override
    public void onBackPressed() {

        backButtonHandler();
        return;
    }
    public void backButtonHandler(){
        AlertDialog.Builder alrt=new AlertDialog.Builder(BookRoomActivity.this);
        alrt.setTitle("Warning");
        alrt.setMessage("Any changes made will not be saved. Are you sure you want to cancel the room booking and go back ?");
        alrt.setIcon(R.drawable.ic_dialog_icon);
        alrt.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(BookRoomActivity.this,StuDashboardActivity.class));
                finishAffinity();
            }
        });
        alrt.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alrt.show();
    }
}