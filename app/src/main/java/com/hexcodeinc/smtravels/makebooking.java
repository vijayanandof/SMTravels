package com.hexcodeinc.smtravels;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hexcodeinc.smtravels.models.bookings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class makebooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
  private Button map,date,makebookin;
    private TextView from,to,distance,duration;
    //private DatePicker datePicker;
    private TimePicker timePicker1;
    private Calendar calendar;
    private EditText name,phonenum;
    private TextView sdate,edate,days,deptime;
    private int year, month,day;
    int i=0;
    private String format = "";
    String vehicle="",formattedDate;
    private DatabaseReference database;
    AutoCompleteTextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makebooking);
        map=(Button)findViewById(R.id.button2);
        from=(TextView)findViewById(R.id.textView8);
        to=(TextView)findViewById(R.id.textView9);
        distance=(TextView)findViewById(R.id.textView13);
        duration=(TextView)findViewById(R.id.textView14);
        days=(TextView)findViewById(R.id.textView17);
        deptime=(TextView)findViewById(R.id.textView18);
        sdate = (TextView) findViewById(R.id.textView15);
        edate = (TextView) findViewById(R.id.textView16);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //-----------USer name and email
        SharedPreferences login = getSharedPreferences("logindetails", MODE_PRIVATE);

        final String user = login.getString("username", null);
        final String email = login.getString("Mailid", null);

        database = FirebaseDatabase.getInstance().getReference();

        final String[] languages={"Later","A Driver","Q Driver","C driver","E Driver","S Driver"};
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        //------------------------------Edit texts
        name=(EditText)findViewById(R.id.editText);
        phonenum=(EditText)findViewById(R.id.editText3);
        //-------------Buttons
         makebookin=(Button)findViewById(R.id.button6);
         makebookin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(name.getText().toString().isEmpty()){
                     name.setError("Name Required!");
                 }
                 if(phonenum.getText().toString().isEmpty()){
                     phonenum.setError("Phone Number Wanted!");
                 }
                 if(phonenum.getText().toString().length()>10||phonenum.getText().toString().length()<10){
                     phonenum.setError("Phone Number Error!");
                 }
                 if(text.getText().toString().isEmpty()){
                     text.setError("Input Wanted!");
                 }
                 if(name.getText().toString().isEmpty()&&phonenum.getText().toString().isEmpty()&&text.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_SHORT).show();
                 }else {

                     bookings post = new bookings(email, name.getText().toString()
                             , phonenum.getText().toString(), from.getText().toString(),to.getText().toString(),
                             distance.getText().toString(),duration.getText().toString(),vehicle,sdate.getText().toString(),edate.getText().toString(),
                             days.getText().toString(),deptime.getText().toString(),text.getText().toString(),formattedDate);

                     Map<String, Object> postValues = post.toMap();
                     String key = database.child("bookings").push().getKey();
                     Map<String, Object> childUpdates = new HashMap<>();
                     childUpdates.put("/bookings/" +key, postValues);
                     //   childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

                     database.updateChildren(childUpdates);
                     Toast.makeText(getApplicationContext(),"Done!",Toast.LENGTH_LONG).show();
                 }


             }
         });


//-----------------------date
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day,5);
//----------------------------------------time
        timePicker1 = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formattedDate = df.format(calendar.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
        //---------------------
        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);

        text.setAdapter(adapter);
        text.setThreshold(1);

        //------------------
        date=(Button)findViewById(R.id.button4);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(makebooking.this,map_place.class);
                startActivity(a);
            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Car");
        categories.add("Bus");
        categories.add("Van");
        categories.add("Mini Bus");
        categories.add("Truck");
        categories.add("Luxury");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


/*
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String start = extras.getString("start");
        String destination = extras.getString("destnation");
        String duratio = extras.getString("duration");
        String distanc = extras.getString("distance");

        from.setText(start);
        to.setText(destination);
        distance.setText(distanc);
        duration.setText(duratio);
       */

        SharedPreferences prefs = getSharedPreferences("locdist", MODE_PRIVATE);

        String start = prefs.getString("start", null);
        String destination = prefs.getString("destination", null);
        String distanc=prefs.getString("distance",null);
        String duratio=prefs.getString("duration",null);
        if (start != null && destination!=null&&distance!=null&&duration!=null) {
            from.setText(start);
            to.setText(destination);
            distance.setText(distanc);
            duration.setText(duratio);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         vehicle = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
      //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    //-----------------------------
    @SuppressWarnings("deprecation")
    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        deptime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }


    //----------------------------
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Loading,..",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    if (i == 0) {
                        showDate(arg1, arg2 + 1, arg3,i);
                        i = 1;
                        date.setText("Enter Arrival Date?");
                    } else if (i == 1) {
                        showDate(arg1, arg2 + 1, arg3,1);
                        i=0;
                        date.setText("Change Date?");

                        //----------------
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String inputString1 = sdate.getText().toString();
                        String inputString2 = edate.getText().toString();

                        try {
                            Date date1 = myFormat.parse(inputString1);
                            Date date2 = myFormat.parse(inputString2);
                            long diff = date2.getTime() - date1.getTime();
                           // System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            long cal=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            days.setText("Days:"+cal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

    private void showDate(int year, int month, int day,int x) {
        if(x==0) {
            sdate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
        if(x==1){
            edate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
        if(x==5){
            sdate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }

}

