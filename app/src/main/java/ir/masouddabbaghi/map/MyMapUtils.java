package ir.masouddabbaghi.map;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MyMapUtils {
    private static Context context;

    MyMapUtils(Context context) {
        MyMapUtils.context = context;
    }

    static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (result == ConnectionResult.SUCCESS)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(result))    // ایا خطای گوگل سرویس قابل اصلاح هست یا خیر
                googleApiAvailability.getErrorDialog(activity, result, 9000).show();
            return false;
        }
    }

    static boolean checkMapIsReady(GoogleMap mMap) {
        if (mMap == null) {
            Toast.makeText(context, "Map Is Not Ready Yet!", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    static Address gotTOLocation(String searchQuery) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(searchQuery, 1);
            if (addressList.size() > 0)
                return addressList.get(0);
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

     static void animateMarker(final Marker marker, final long duration, final Interpolator interpolator) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float x = 1 - interpolator.getInterpolation((float) elapsed / duration);
                Log.i("myMapLog", "X = " + x + "");
                float t = Math.max(x, 0);
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}
