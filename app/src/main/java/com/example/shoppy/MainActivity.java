package com.example.shoppy;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Geocoder;
import android.location.Address;
import android.location.LocationManager;
import android.location.Location;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;

import android.os.Build;
import android.widget.Button;

import java.io.IOException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Activity myActivity;

    private boolean checkPermissions() {

        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE,
                Manifest.permission.RECORD_AUDIO};

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = checkSelfPermission(p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    public String GetZipCode() {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0).getPostalCode();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "Could not determine zip code.";
    }

    private void MakeRequest(String query) {

        checkPermissions();

        TargetRequestStoreIDTask req = new TargetRequestStoreIDTask(myActivity);
        req.execute(GetZipCode(), "1", "5", query);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnSearch) {
                    EditText txtSearch = findViewById(R.id.txtSearch);
                    MakeRequest(txtSearch.getText().toString());
                    WegmansRequest wegRequest = new WegmansRequest();
                    WalmartRequest walmartRequest = new WalmartRequest();

                    if (isEmulator())
                    {
                        wegRequest.execute("20876", "Pizza");
                        walmartRequest.execute("20876", "Pizza");
                    }
                    else
                    {
                        String zipCode = GetZipCode();
                        wegRequest.execute(zipCode, "Pizza");
                        walmartRequest.execute(zipCode, "Pizza");
                    }

                }
            }
        });

        myActivity = this;

    }

}
