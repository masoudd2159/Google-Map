package ir.masouddabbaghi.map;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;

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
}
