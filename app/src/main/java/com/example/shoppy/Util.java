package com.example.shoppy;

import java.io.File;
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

}
