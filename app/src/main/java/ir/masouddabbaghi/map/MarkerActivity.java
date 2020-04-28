package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private SeekBar seekBarRotation;
    private TextView textViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyMapUtils.checkPlayServices(this)) {
            setContentView(R.layout.activity_marker);
            seekBarRotation = findViewById(R.id.seekBarRotation);
            textViewInfo = findViewById(R.id.textViewInfo);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
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

        LatLng myHome = new LatLng(32.709638, 51.517019);       // Latitude + Longitude = LatLng
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHome, 12));

        seekBarRotation.setMax(360);
        seekBarRotation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (MyMapUtils.checkMapIsReady(mMap)) {
                    if (marker != null)
                        marker.setRotation(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder geocoder = new Geocoder(MarkerActivity.this);
                List<Address> addressList = new ArrayList<>();
                try {
                    addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList.size() > 0) {
                        if (marker != null)
                            marker.remove();
                        addMarker(addressList.get(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.info_window, null);

                TextView txtLocality = view.findViewById(R.id.textViewLocality);
                TextView txtLat = view.findViewById(R.id.textViewLat);
                TextView txtLng = view.findViewById(R.id.textViewLog);
                TextView txtSnippet = view.findViewById(R.id.textViewSnippet);

                txtLocality.setText(marker.getTitle());
                txtLat.setText("latitude : " + marker.getPosition().latitude);
                txtLng.setText("longitude : " + marker.getPosition().longitude);
                txtSnippet.setText(marker.getSnippet());
                return view;
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String msg = marker.getTitle() + " (" +
                        marker.getPosition().latitude + " , " +
                        marker.getPosition().longitude + ")";
                Toast.makeText(MarkerActivity.this, msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onMarkerDrag(Marker marker) {
                textViewInfo.setText("Latitude : " + marker.getPosition().latitude + "\nLongitude : " + marker.getPosition().longitude);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder geocoder = new Geocoder(MarkerActivity.this);
                List<Address> addressList = new ArrayList<>();
                try {
                    addressList = geocoder.getFromLocation(
                            marker.getPosition().latitude,
                            marker.getPosition().longitude,
                            1);
                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);
                        marker.setTitle(address.getLocality());
                        marker.setSnippet(address.getCountryName());
                        marker.showInfoWindow();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MarkerActivity.this, "on click", Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Toast.makeText(MarkerActivity.this, "on long click", Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                Toast.makeText(MarkerActivity.this, "Info Window Close", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarker(Address address) {
        MarkerOptions markerOptions = new MarkerOptions()
                .title(address.getLocality())
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                .snippet(address.getCountryName())
                .draggable(true)
                .position(new LatLng(address.getLatitude(), address.getLongitude()))
                .rotation(360);
        marker = mMap.addMarker(markerOptions);
        MyMapUtils.animateMarker(marker, 750, new BounceInterpolator());
    }
}
