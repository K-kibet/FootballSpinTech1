package com.extreme.spinsport;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

public class SplashActivity extends AppCompatActivity {
    private AppCompatImageView img;
    private String TAG;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String ONESIGNAL_APP_ID = "96d39dd8-7ff5-4844-98d8-e00df002c887";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if(!isConnected(SplashActivity.this)) {
            Intent ii = new Intent(SplashActivity.this,NoInternet.class);
            startActivity(ii);
        }
        else {


// Enable verbose OneSignal logging to debug issues if needed.
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

            // OneSignal Initialization
            OneSignal.initWithContext(this);
            OneSignal.setAppId(ONESIGNAL_APP_ID);

            pref = getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
            editor = pref.edit();


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("adsone");
            DatabaseReference appStatus = database.getReference("statustwo");
            DatabaseReference redirect = database.getReference("redirect");

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    pref.edit().remove("adsoption").commit();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    //Toast.makeText(SplashActivity.this, dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                    setAdsOption(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            appStatus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    pref.edit().remove("appstatus").commit();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                   Toast.makeText(SplashActivity.this, dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                    setAppStatus(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            redirect.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    pref.edit().remove("redirect").commit();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    //Toast.makeText(SplashActivity.this, dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                    setRedirect(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            img = findViewById(R.id.vipImage);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
            img.startAnimation(animation);
            final Intent intent = new Intent(this, MainActivity.class);
            //final Intent intent2 = new Intent(this, Redirect.class);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            @SuppressLint("MissingPermission") android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            @SuppressLint("MissingPermission") android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public void setAdsOption(String appid) {
        editor.putString("adsoption", appid);
        editor.apply();

    }

    public void setAppStatus(String appStatus) {
        editor.putString("appstatus", appStatus);
        editor.apply();

    }

    public void setRedirect(String redirect) {
        editor.putString("redirect", redirect);
        editor.apply();

    }
}
