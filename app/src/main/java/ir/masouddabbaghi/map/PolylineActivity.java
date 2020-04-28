package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class PolylineActivity extends FragmentActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private GoogleMap mMap;
    private Polyline polyline;
    private SeekBar seekBarHUE;
    private SeekBar seekBarAlpha;
    private SeekBar seekBarWidth;
    private RadioButton radioButtonDefault;
    private RadioButton radioButtonDot;
    private RadioButton radioButtonDash;
    private RadioButton radioButtonMixed;
    private static final LatLng latLngMashhad = new LatLng(36.287945, 59.615542);
    private static final LatLng latLngIsfahan = new LatLng(32.657332, 51.677509);
    private static final LatLng latLngTabriz = new LatLng(38.081570, 46.292161);
    private static final LatLng latLngTehran = new LatLng(35.699789, 51.338059);
    private static final LatLng latLngKish = new LatLng(26.532746, 53.986784);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyMapUtils.checkPlayServices(this)) {
            setContentView(R.layout.activity_polyline);
            init();
            setSeekBarMax();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    private void init() {
        seekBarHUE = findViewById(R.id.seekBarHUE);
        seekBarAlpha = findViewById(R.id.seekBarAlpha);
        seekBarWidth = findViewById(R.id.seekBarWidth);
        radioButtonDefault = findViewById(R.id.radioButtonDefault);
        radioButtonDot = findViewById(R.id.radioButtonDot);
        radioButtonDash = findViewById(R.id.radioButtonDash);
        radioButtonMixed = findViewById(R.id.radioButtonMixed);
    }

    private void setSeekBarMax() {
        seekBarHUE.setMax(360);
        seekBarAlpha.setMax(255);
        seekBarWidth.setMax(100);
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
        mMap.addMarker(new MarkerOptions().position(myHome).title("Marker in My Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHome, 5));

        drawLine();
        setListeners();
        DefaultData();
    }

    private void drawLine() {
        final PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(latLngKish);
        polylineOptions.add(latLngIsfahan);
        polylineOptions.add(latLngMashhad);
        polylineOptions.add(latLngTehran);
        polylineOptions.add(latLngTabriz);

        polylineOptions.clickable(true);

        polyline = mMap.addPolyline(polylineOptions);

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(PolylineActivity.this, "City : Kish,Isfahan,Mashhad,Tehran,Tabriz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {
        seekBarHUE.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyMapUtils.setPolylineColor(polyline, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyMapUtils.setPolylineAlpha(polyline, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                polyline.setWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioButtonDefault.setOnCheckedChangeListener(this);
        radioButtonDot.setOnCheckedChangeListener(this);
        radioButtonDash.setOnCheckedChangeListener(this);
        radioButtonMixed.setOnCheckedChangeListener(this);
    }

    private void DefaultData() {
        radioButtonDot.setChecked(true);
        seekBarHUE.setProgress(36);
        seekBarAlpha.setProgress(255);
        seekBarWidth.setProgress(16);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (radioButtonDefault.isChecked())
            polyline.setPattern(MyMapUtils.getPattern(MyMapUtils.patternType.DEFAULT));
        else if (radioButtonDot.isChecked())
            polyline.setPattern(MyMapUtils.getPattern(MyMapUtils.patternType.DOT));
        else if (radioButtonDash.isChecked())
            polyline.setPattern(MyMapUtils.getPattern(MyMapUtils.patternType.DASH));
        else if (radioButtonMixed.isChecked())
            polyline.setPattern(MyMapUtils.getPattern(MyMapUtils.patternType.MIXED));
    }
}
