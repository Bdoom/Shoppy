package com.example.shoppy;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import android.Manifest;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.location.Location;
import android.location.Geocoder;
import android.location.Address;
import android.content.Context;
import java.io.IOException;
import android.support.design.widget.BottomNavigationView;
import java.util.Locale;
import android.view.View;
import android.view.MenuItem;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import androidx.navigation.fragment.NavHostFragment;

// Add multiple stores.
// Bed Stores:
// Matress Firm
// Matress warehouse
// Sears
// Macy's
// Sleep number?
// Electronics Stores:
// Best Buy
// Microcenter
// Clothing?:
// abercrombie & fitch
// hollister
// gap




public class MainActivity extends AppCompatActivity implements  mainFragment.OnFragmentInteractionListener, listFragment.OnFragmentInteractionListener, graphFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private Activity myActivity;

    public HashMap<String, Double> TargetRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WalmartRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WegmansRequestHashMap = new HashMap<>();

    private int NumberOfRequests = 3; // For now, just three, target, walmart, and wegmans.

    public void RequestFinished() {
        NumberOfRequests--;
        if (NumberOfRequests <= 0) {
            AllRequestsFinished();
        }
    }

    private void AllRequestsFinished() {
        // Sort the request data.
        //double targetMin = Collections.min(TargetRequestHashMap.values());
        //double walmartMin = Collections.min(WalmartRequestHashMap.values());
        //double wegmansMin = Collections.min(WegmansRequestHashMap.values());

        String cheapestTargetItemName = "";
        double cheapestTargetPrice = Double.MAX_VALUE;

        String cheapestWalmartItemName = "";
        double cheapestWalmartPrice = Double.MAX_VALUE;

        String cheapestWegmansItemName = "";
        double cheapestWegmansPrice = Double.MAX_VALUE;

        for (Map.Entry<String, Double> entry : TargetRequestHashMap.entrySet()) {
            if (entry.getValue() < cheapestTargetPrice)
            {
                cheapestTargetPrice = entry.getValue();
                cheapestTargetItemName = entry.getKey();
            }

        }

        for (Map.Entry<String, Double> entry : WalmartRequestHashMap.entrySet()) {
            if (entry.getValue() < cheapestWalmartPrice)
            {
                cheapestWalmartPrice = entry.getValue();
                cheapestWalmartItemName = entry.getKey();
            }
        }

        for (Map.Entry<String, Double> entry : WegmansRequestHashMap.entrySet()) {
            if (entry.getValue() < cheapestWegmansPrice)
            {
                cheapestWegmansPrice = entry.getValue();
                cheapestWegmansItemName = entry.getKey();
            }
        }

        System.out.println("Cheapest Target Item Name: " + cheapestTargetItemName + " Price: " + cheapestTargetPrice);
        System.out.println("Cheapest Walmart Item Name: " + cheapestWalmartItemName + " Price: " + cheapestWalmartPrice);
        System.out.println("Cheapest Wegmans Item Name: " + cheapestWegmansItemName + " Price: " + cheapestWegmansPrice);


    }

    private boolean checkPermissions() {

        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
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

        if (!checkPermissions()) {
            return null;
        }

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

        if (location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                return addresses.get(0).getPostalCode();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "Could not determine zip code.";
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    selectedFragment = mainFragment.newInstance();
                    break;

                case R.id.navigation_list:
                    selectedFragment = new listFragment();
                    break;

                case R.id.navigation_graphs:
                    selectedFragment = new graphFragment();
                    break;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();


            return true;
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

    private void ResetSearch()
    {
        TargetRequestHashMap.clear();
        WegmansRequestHashMap.clear();
        WalmartRequestHashMap.clear();
        NumberOfRequests = 3;
        System.out.println("Cleared HashMap, and reset number of requests.");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void MakeRequest()
    {
        EditText txtSearch = findViewById(R.id.txtSearch);
        checkPermissions();
        ResetSearch();

        TargetRequest targetRequest = new TargetRequest(myActivity);
        WegmansRequest wegRequest = new WegmansRequest(myActivity);
        WalmartRequest walmartRequest = new WalmartRequest(myActivity);

        String query = txtSearch.getText().toString();

        if (isEmulator()) {
            wegRequest.execute("20876", query);
            walmartRequest.execute("20876", query);
            targetRequest.execute("20876", "1", "5", query);

        } else {
            String zipCode = GetZipCode();
            wegRequest.execute(zipCode, query);
            walmartRequest.execute(zipCode, query);
            targetRequest.execute(zipCode, "1", "5", query);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mainFragment mainFragment = new mainFragment();
        transaction.replace(R.id.frame_layout, mainFragment);
        transaction.commit();

        myActivity = this;

    }

}
