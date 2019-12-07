package com.example.boussole;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //déclaration bouton
    Button boutonP30 = null;
    Button boutonM30 = null;
    //fenetre de texte lattitude longitude
    TextView lattitude = null;
    TextView longitude = null;

    ImageView Boussole = null;

  //  SensorManager sensorManager = null;

    float rotVal = 0;

    private LocationManager objgps = null;
    private LocationListener objlistener = null;

    private SensorManager mSensorManager = null;
    private Sensor mAccelerometer = null;
    private Sensor mMagneto = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "First onCreate() calls", Toast.LENGTH_SHORT).show();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneto =  mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        objgps = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objlistener = new Myobjlistener();



        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        objgps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, objlistener);
        //**variable qui pointe sur  mes champs d'affichage*************
        longitude   = (TextView) findViewById(R.id.Longitude);
        lattitude = (TextView) findViewById(R.id.Lattitude);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Démarrage Boussole", Toast.LENGTH_LONG).show();
        Log.e("onStart", "Start app");
        boutonM30 = findViewById(R.id.buttonM30);
        boutonP30 = findViewById(R.id.buttonP30);
        boutonP30.setText("+30°");
        boutonM30.setText("-30°");

        // Faire la liste des capteurs de l'appareil
        //  listSensor();


        Boussole = findViewById(R.id.Compass);

        //locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boutonP30.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rotVal = Boussole.getRotation();
                Boussole.setRotation(rotVal + 30);


            }
        });

        boutonM30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotVal = Boussole.getRotation();
                Boussole.setRotation(rotVal - 30);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener, mAccelerometer);
    }
    private class Myobjlistener implements LocationListener
    {



        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }


        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }


        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // TODO Auto-generated method stub
        }


        public void onLocationChanged(Location location) {

            //affichage des valeurs dans la les zone de saisie
            lattitude.setText(" "+location.getLatitude());
            longitude.setText(" "+location.getLongitude());
        }

    }

    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            float x, y, z;
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                Log.d("Accel", "Rotation sur l'axe x: " + sensorEvent.values[0]);
                Log.d("Accel", "Rotation sur l'axe y : " + sensorEvent.values[1]);
                Log.d("Accel", "Rotation sur l'axe z : " + sensorEvent.values[2]);
            }
        }
    };

}
