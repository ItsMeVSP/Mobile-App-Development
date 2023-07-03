package com.mad.exno9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;

import android.location.Location;


import androidx.core.app.ActivityCompat;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button btn_locate;
    TextView tv_latitude, tv_longhitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_locate = (Button)findViewById(R.id.btn_locate);
        tv_latitude = (TextView)findViewById(R.id.tv_latitude);
        tv_longhitud = (TextView)findViewById(R.id.tv_longhitud);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        btn_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSLocator gpsLocator = new GPSLocator(getApplicationContext());
                Location location = gpsLocator.GetLocation();
                if(location != null){
                    double latitude = location.getLatitude();
                    double longhitud = location.getLongitude();
                    tv_latitude.setText(String.valueOf(latitude));
                    tv_longhitud.setText(String.valueOf(longhitud));
                }
            }
        });
    }


}