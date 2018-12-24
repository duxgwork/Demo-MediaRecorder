package con.me.kevindue.autolayout.utils;

import android.util.Log;

public class L {
    public static boolean debug = false;
    private static final String TAG = "AUTO_LAYOUT";

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }


}
