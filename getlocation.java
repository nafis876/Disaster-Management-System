package com.example.dms;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class getlocation extends AppCompatActivity {
    private static final int REQUEST_LOCATION=1;

    Button getlocationbtn,sendbtn;
    EditText showlocationtxt;
    LocationManager locationManager;
    String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_text);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        showlocationtxt = findViewById(R.id.showlocation);
        getlocationbtn=findViewById(R.id.getloc);

        sendbtn = findViewById(R.id.send);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getlocation.this,EmergencyText.class));
            }
        });


        getlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);

                // CHECK GPS IS ENABLE OR NOT
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    OnGPS();
                }
                else {
                    getLocation();
                }
            }
        });
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getlocation.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getlocation.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
        {
            Location locationgps=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationpassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (locationgps!=null){
                double lati=locationgps.getLatitude();
                double longi= locationgps.getLongitude();

                latitude=String.valueOf(lati);
                longitude=String.valueOf(longi);

                showlocationtxt.setText("Latitude:"+latitude+"\n"+"Longitude:"+longitude);

            }
            else if (locationNetwork!=null){
                double lati=locationNetwork.getLatitude();
                double longi= locationNetwork.getLongitude();

                latitude=String.valueOf(lati);
                longitude=String.valueOf(longi);

                showlocationtxt.setText("Latitude:"+latitude+"\n"+"Longitude:"+longitude);


            }
            else if (locationpassive!=null){
                double lati=locationpassive.getLatitude();
                double longi= locationpassive.getLongitude();

                latitude=String.valueOf(lati);
                longitude=String.valueOf(longi);

                showlocationtxt.setText("Latitude:"+latitude+"\n"+"Longitude:"+longitude);
            }
            else {
                Toast.makeText(this,"Can not find your Location",Toast.LENGTH_SHORT).show();
            }
        }
    }

}