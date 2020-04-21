package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UiSettingsActivity extends FragmentActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private GoogleMap mMap;

    private CheckBox checkBox_ZoomControls;
    private CheckBox checkBox_ZoomGestures;
    private CheckBox checkBox_ScrollGestures;
    private CheckBox checkBox_TiltGestures;
    private CheckBox checkBox_RotateGestures;

    private RadioButton radioButton_None;
    private RadioButton radioButton_Normal;
    private RadioButton radioButton_Satellite;
    private RadioButton radioButton_Terrain;
    private RadioButton radioButton_Hybrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyMapUtils.checkPlayServices(this)) {
            setContentView(R.layout.activity_ui_settings);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                init();
                mapFragment.getMapAsync(this);
            }
        }
    }

    private void init() {
        checkBox_ZoomControls = findViewById(R.id.checkbox_ZoomControls);
        checkBox_ZoomGestures = findViewById(R.id.checkbox_ZoomGestures);
        checkBox_ScrollGestures = findViewById(R.id.checkbox_ScrollGestures);
        checkBox_TiltGestures = findViewById(R.id.checkbox_TiltGestures);
        checkBox_RotateGestures = findViewById(R.id.checkbox_RotateGestures);

        radioButton_None = findViewById(R.id.radioButton_NONE);
        radioButton_Normal = findViewById(R.id.radioButton_NORMAL);
        radioButton_Satellite = findViewById(R.id.radioButton_SATELLITE);
        radioButton_Terrain = findViewById(R.id.radioButton_TERRAIN);
        radioButton_Hybrid = findViewById(R.id.radioButton_HYBRID);
    }

    private void setListeners() {
        checkBox_ZoomControls.setOnCheckedChangeListener(this);
        checkBox_ZoomGestures.setOnCheckedChangeListener(this);
        checkBox_ScrollGestures.setOnCheckedChangeListener(this);
        checkBox_TiltGestures.setOnCheckedChangeListener(this);
        checkBox_RotateGestures.setOnCheckedChangeListener(this);

        radioButton_None.setOnCheckedChangeListener(this);
        radioButton_Normal.setOnCheckedChangeListener(this);
        radioButton_Satellite.setOnCheckedChangeListener(this);
        radioButton_Terrain.setOnCheckedChangeListener(this);
        radioButton_Hybrid.setOnCheckedChangeListener(this);
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        setListeners();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mMap.getUiSettings().setZoomControlsEnabled(checkBox_ZoomControls.isChecked());
        mMap.getUiSettings().setZoomGesturesEnabled(checkBox_ZoomGestures.isChecked());
        mMap.getUiSettings().setScrollGesturesEnabled(checkBox_ScrollGestures.isChecked());
        mMap.getUiSettings().setTiltGesturesEnabled(checkBox_TiltGestures.isChecked());
        mMap.getUiSettings().setRotateGesturesEnabled(checkBox_RotateGestures.isChecked());

        if (radioButton_None.isChecked())
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        else if (radioButton_Normal.isChecked())
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        else if (radioButton_Satellite.isChecked())
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else if (radioButton_Terrain.isChecked())
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        else if (radioButton_Hybrid.isChecked())
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
