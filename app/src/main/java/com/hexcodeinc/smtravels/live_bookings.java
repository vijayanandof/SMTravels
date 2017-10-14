package com.hexcodeinc.smtravels;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hexcodeinc.smtravels.models.bookings;

public class live_bookings extends AppCompatActivity {
    private RecyclerView rview;
    FirebaseDatabase database;
    DatabaseReference myref;
    private FirebaseRecyclerAdapter<bookings, PostViewHolder> mPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_bookings);
        rview = (RecyclerView) findViewById(R.id.recycler);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences login = getSharedPreferences("logindetails", MODE_PRIVATE);

        final String user = login.getString("username", null);
          Toast.makeText(getApplicationContext(),"Here",Toast.LENGTH_LONG).show();
        database = FirebaseDatabase.getInstance();
       myref = database.getReference();
       myref = FirebaseDatabase.getInstance().getReference("bookings");
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializescreen();

    }

    private void initializescreen() {

        mPostAdapter = new FirebaseRecyclerAdapter<bookings, PostViewHolder>(
                bookings.class,
                R.layout.item_post,
                PostViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, final bookings model, int position) {


                viewHolder.setemail(model.getEmail());
                viewHolder.setName(model.getNamee());
                viewHolder.setdays(model.getDays());
                viewHolder.setdist(model.getDist());
                viewHolder.setfr(model.getFr());
                viewHolder.setto(model.getToo());
                viewHolder.setduration(model.getDuration());
                viewHolder.setvehicle(model.getVehicle());
                viewHolder.setdriver(model.getDriver());

                viewHolder.setdettime(model.getDeptime());
                viewHolder.setsdate(model.getSdate());
                viewHolder.setedate(model.getEdate());
                viewHolder.settimestamp(model.getTimestamp());
                viewHolder.setphonenum(model.getPhonenum());
            }
        };
     rview.setAdapter(mPostAdapter);
    }

    public static class  PostViewHolder extends RecyclerView.ViewHolder {

        public TextView nameee,email,phonenumm,fro,too,dist,duration,vehicle,sdate,edate,days,deptime,driver,timestamp;

        public PostViewHolder(View itemView) {
            super(itemView);

            nameee = (TextView) itemView.findViewById(R.id.namee);
            email = (TextView) itemView.findViewById(R.id.emaill);
            phonenumm = (TextView) itemView.findViewById(R.id.phonenumm);
            fro = (TextView) itemView.findViewById(R.id.fro);
            too = (TextView) itemView.findViewById(R.id.too);
            dist = (TextView) itemView.findViewById(R.id.dist);
            duration = (TextView) itemView.findViewById(R.id.duration);
            vehicle = (TextView) itemView.findViewById(R.id.vehiclee);
            sdate = (TextView) itemView.findViewById(R.id.sdate);
            edate = (TextView) itemView.findViewById(R.id.edate);
            days = (TextView) itemView.findViewById(R.id.days);
            deptime = (TextView) itemView.findViewById(R.id.deptime);
            driver = (TextView) itemView.findViewById(R.id.driver);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        }

        public void setName(String n) {
            nameee.setText(n);
        }
        public void setemail(String n) {
            email.setText(n);
        }
        public void setphonenum(String n) {
            phonenumm.setText(n);
        }
        public void setfr(String n) {
            fro.setText(n);
        }
        public void setto(String n) {
            too.setText(n);
        }
        public void setdist(String n) {
            dist.setText(n);
        }
        public void setduration(String n) {
            duration.setText(n);
        }
        public void setsdate(String n) {
            sdate.setText(n);
        }
        public void setedate(String n) {
            edate.setText(n);
        }
        public void setdays(String n) {
            days.setText(n);
        }
        public void setdettime(String n) {
            deptime.setText(n);
        }
        public void setdriver(String n) {
            driver.setText(n);
        }
        public void settimestamp(String n) {
            timestamp.setText(n);
        }
        public void setvehicle(String n) {
            vehicle.setText(n);
        }



    }

}