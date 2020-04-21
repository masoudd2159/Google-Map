package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CameraPosition mashhad = new CameraPosition
            .Builder()
            .target(new LatLng(36.288014, 59.615502))
            .zoom(15.5f)
            .bearing(180)
            .tilt(50)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices()) {
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        //LatLng myHome = new LatLng(32.709638, 51.517019);       // Latitude + Longitude = LatLng
        //mMap.addMarker(new MarkerOptions().position(myHome).title("Marker in My Home"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myHome));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHome, 10));

        Address address = gotTOLocation("khomeini shahr");
        if (address != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 10));
        }
    }

    private Address gotTOLocation(String searchQuery) {
        Geocoder geocoder = new Geocoder(this);
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

    private boolean checkPlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(result))    // ایا خطای گوگل سرویس قابل اصلاح هست یا خیر
                googleApiAvailability.getErrorDialog(this, result, 9000).show();
            return false;
        }
    }

    private boolean checkMapIsReady() {
        if (mMap == null) {
            Toast.makeText(this, "Map Is Not Ready Yet!", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}
