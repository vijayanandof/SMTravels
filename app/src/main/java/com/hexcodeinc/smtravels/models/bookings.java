package com.hexcodeinc.smtravels.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class bookings {



    public String email;
    public String namee;
    public String phonenum;
    public String fr;
    public String too;
    public String dist,duration,vehicle,sdate,edate,days,deptime,driver,timestamp;


    public bookings() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public bookings(String email, String name, String phonenum, String fr, String too, String dist, String duration, String vehicle,
                    String sdate, String edate, String days, String deptime, String driver,String timestamp) {
        this.email = email;
        this.namee = name;
        this.phonenum = phonenum;
        this.fr = fr;
        this.too = too;
        this.dist=dist;
        this.duration=duration;
        this.vehicle=vehicle;
        this.sdate=sdate;
        this.edate=edate;
        this.deptime=deptime;
        this.days=days;
        this.driver=driver;
        this.timestamp=timestamp;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", namee);
        result.put("phonenumber", phonenum);
        result.put("from", fr);

        result.put("too", too);
        result.put("distance", dist);
        result.put("duration", duration);
        result.put("vehicle", vehicle);
        result.put("sdate", sdate);
        result.put("edate", edate);
        result.put("deptime", deptime);
        result.put("days", days);
        result.put("driver", driver);
        result.put("timestamp", timestamp);
        return result;
    }
    // [END post_to_map]
    public String getEmail() {
        return email;
    }

    public String getNamee() {
        return namee;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getFr() {
        return fr;
    }

    public String getToo() {
        return too;
    }

    public String getDist() {
        return dist;
    }

    public String getDuration() {
        return duration;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getSdate() {
        return sdate;
    }

    public String getEdate() {
        return edate;
    }

    public String getDays() {
        return days;
    }

    public String getDeptime() {
        return deptime;
    }

    public String getDriver() {
        return driver;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
// [END post_class]
