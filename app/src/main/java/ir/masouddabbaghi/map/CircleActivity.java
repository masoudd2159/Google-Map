package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CircleActivity extends FragmentActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private GoogleMap mMap;
    private Circle circle;
    private static final LatLng Birjand = new LatLng(32.873832, 59.224773);
    private SeekBar fillHueSeekBar, fillAlphaSeekBar, strokeWidthSeekBar, strokeHueSeekBar, strokeAlphaSeekBar, radiusSeekBar;
    private RadioButton rdBtnDefault, rdBtnDot, rdBtnDash, rdBtnMixed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyMapUtils.checkPlayServices(this)) {
            setContentView(R.layout.activity_circle);
            findViews();
            setSeekBarMax();
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
        mMap.addMarker(new MarkerOptions().position(Birjand).title("Marker in Birjand"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Birjand, 6.5f));
        drawCircle();
        setListeners();
        setDefaultData();
    }

    private void findViews() {
        fillHueSeekBar = (SeekBar) findViewById(R.id.fillHueSeekBar);
        fillAlphaSeekBar = (SeekBar) findViewById(R.id.fillAlphaSeekBar);
        strokeWidthSeekBar = (SeekBar) findViewById(R.id.strokeWidthSeekBar);
        strokeHueSeekBar = (SeekBar) findViewById(R.id.strokeHueSeekBar);
        strokeAlphaSeekBar = (SeekBar) findViewById(R.id.strokeAlphaSeekBar);
        radiusSeekBar = (SeekBar) findViewById(R.id.radiusSeekBar);

        rdBtnDefault = (RadioButton) findViewById(R.id.rdBtnDefault);
        rdBtnDot = (RadioButton) findViewById(R.id.rdBtnDot);
        rdBtnDash = (RadioButton) findViewById(R.id.rdBtnDash);
        rdBtnMixed = (RadioButton) findViewById(R.id.rdBtnMixed);
    }

    private void setSeekBarMax() {
        fillHueSeekBar.setMax(360);
        strokeHueSeekBar.setMax(360);
        fillAlphaSeekBar.setMax(255);
        strokeAlphaSeekBar.setMax(255);
        strokeWidthSeekBar.setMax(100);
        radiusSeekBar.setMax(200000);
    }

    private void setListeners() {

        fillHueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MyMapUtils.setCircleFillColor(circle, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fillAlphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MyMapUtils.setCircleFillAlpha(circle, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        strokeWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                circle.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        strokeHueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MyMapUtils.setCircleStrokeColor(circle, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        strokeAlphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MyMapUtils.setCircleStrokeAlpha(circle, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                circle.setRadius(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rdBtnDefault.setOnCheckedChangeListener(this);
        rdBtnDot.setOnCheckedChangeListener(this);
        rdBtnDash.setOnCheckedChangeListener(this);
        rdBtnMixed.setOnCheckedChangeListener(this);
    }

    private void drawCircle() {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(Birjand);
        circle = mMap.addCircle(circleOptions);

        circle.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DOT));
        circle.setClickable(true);

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefaultData() {
        fillHueSeekBar.setProgress(200);
        strokeHueSeekBar.setProgress(63);
        fillAlphaSeekBar.setProgress(255);
        strokeAlphaSeekBar.setProgress(255);
        strokeWidthSeekBar.setProgress(15);
        radiusSeekBar.setProgress(200000);

        rdBtnDefault.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (rdBtnDefault.isChecked())
            circle.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DEFAULT));
        else if (rdBtnDot.isChecked())
            circle.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DOT));
        else if (rdBtnDash.isChecked())
            circle.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DASH));
        else if (rdBtnMixed.isChecked())
            circle.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.MIXED));
    }
}
