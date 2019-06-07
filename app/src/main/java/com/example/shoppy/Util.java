package com.example.shoppy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;

public class Util {

    /**
     * Utility: Return the storage directory.  Create it if necessary.
     */
    public static File dataDir()
    {
        File sdcard = Environment.getExternalStorageDirectory();
        if( sdcard == null || !sdcard.isDirectory() ) {
            // TODO: warning popup
            return null;
        }
        File datadir = new File(sdcard, "Shoppy");
        if( !confirmDir(datadir) ) {
            // TODO: warning popup
            return null;
        }
        return datadir;
    }


    /**
     * Create dir if necessary, return true on success
     */
    public static final boolean confirmDir(File dir) {
        if( dir.isDirectory() ) return true;
        if( dir.exists() ) return false;
        return dir.mkdirs();
    }

    public static String GetZipCode(Activity activity) {

        if (!checkPermissions(activity)) {
            return null;
        }

        Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
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

    public static boolean checkPermissions(Activity activity) {

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
            result = activity.checkSelfPermission(p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            activity.requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

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


}
