package com.hexcodeinc.smtravels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hexcodeinc.smtravels.logger.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class map_place extends AppCompatActivity  implements PlaceSelectionListener,DistanceFinder.DF  {
    private static final String TAG = "map_place";
    private TextView mPlaceDetailsText;

    private DatabaseReference mDatabase;

    private TextView mPlaceAttribution;
    private int i = 0;
    private TextView mPlace2, mplaceatrri, distance,distance2;
    double lat1, long1, lat2, long2;
    private Button dist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_place);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        // Retrieve the TextViews that will display details about the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.textView8);
        mPlaceAttribution = (TextView) findViewById(R.id.textView9);
        mPlace2 = (TextView) findViewById(R.id.textView10);
        mplaceatrri = (TextView) findViewById(R.id.textView11);
        distance = (TextView) findViewById(R.id.textView12);
        distance2=(TextView)findViewById(R.id.textView2);
        dist=(Button)findViewById(R.id.button3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlaceDetailsText.getText().toString().isEmpty()||mPlace2.getText().toString().isEmpty()||distance.getText().toString().isEmpty()||distance2.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),"Fill in Data!",Toast.LENGTH_SHORT).show();
                }
                Snackbar.make(view, "<===ReDirecting To form!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               /* Intent intent = new Intent(map_place.this, makebooking.class);
                Bundle extras = new Bundle();
                extras.putString("start",mPlaceDetailsText.getText().toString());
                extras.putString("destination",mPlace2.getText().toString());
                extras.putString("duration",distance.getText().toString());
                extras.putString("distance",distance2.getText().toString());
                intent.putExtras(extras);
                startActivity(intent);
*/
                //------------------------------------
                SharedPreferences preferences = getSharedPreferences("locdist", Context.MODE_PRIVATE);
                SharedPreferences.Editor locdist = preferences.edit();
                locdist.putString("start",mPlaceDetailsText.getText().toString());
                locdist.putString("destination",mPlace2.getText().toString());
                locdist.putString("duration",distance.getText().toString());
                locdist.putString("distance",distance2.getText().toString());
                locdist.apply();

                Intent a=new Intent(map_place.this,makebooking.class);
                startActivity(a);

            }
        });
        dist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String origin = mPlaceDetailsText.getText().toString();
                String destination = mPlace2.getText().toString();
                Toast.makeText(getBaseContext(),origin+" "+destination,Toast.LENGTH_SHORT).show();

            //    if(origin.isEmpty()&&destination.isEmpty()){
            ///        Toast.makeText(getBaseContext(),"Typing Data!!",Toast.LENGTH_SHORT).show();
             //   }
                try {
                    origin = Encoder(origin);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    destination = Encoder(destination);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String searchLanguage="en-EN";
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + destination + "&mode=driving&language=" + searchLanguage + "&avoid=tolls&key=" +"AIzaSyD2uCA8yNUjd9hoIuG1E0I3VUqfcGsinSo";
                new DistanceFinder(map_place.this).execute(url);
            }
        });

    }
    public void setDouble(String result) {
        String res[] = result.split(",");
        Double minutes = (Double.parseDouble(res[0]) / 60);
        int distanc = (Integer.parseInt(res[1]) / 1000);
        distance.setText((int) (minutes / 60) + " hours:" + (int) (minutes % 60) + " min");
        distance2.setText(distanc + " km");
    }
    String Encoder (String place) throws UnsupportedEncodingException{
        return URLEncoder.encode(place, "utf-8");
    }
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());
        if (i == 0) {
            // Format the returned place's details and display them in the TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(),
                    place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));
            mPlaceDetailsText.setText(place.getName());
            i = 1;
            lat1 = place.getLatLng().latitude;
            long1 = place.getLatLng().longitude;
            CharSequence attributions = place.getAttributions();
            if (!TextUtils.isEmpty(attributions)) {
                mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
            } else {
                mPlaceAttribution.setText(place.getAttributions());
            }
        } else if (i == 1) {
            mPlace2.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(),
                    place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));
            mPlace2.setText(place.getName());
            i = 0;
            lat2 = place.getLatLng().latitude;
            long2 = place.getLatLng().longitude;
            //--------------------

        //    distance.setText(mPlaceDetailsText.getText().toString() + "  " + mPlace2.getText().toString());
            Toast.makeText(getBaseContext(),"Here",Toast.LENGTH_SHORT).show();
            //----------------

           // distance.setText(response);

            CharSequence attributions = place.getAttributions();
            if (!TextUtils.isEmpty(attributions)) {
                mplaceatrri.setText(Html.fromHtml(attributions.toString()));
            } else {
                mplaceatrri.setText(place.getAttributions());
            }
        }
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
    }


}






