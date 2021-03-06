package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class PolygonActivity extends FragmentActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private GoogleMap mMap;
    private Polygon polygon;
    private SeekBar fillHueSeekBar, fillAlphaSeekBar, strokeWidthSeekBar, strokeHueSeekBar, strokeAlphaSeekBar;
    private RadioButton rdBtnDefault, rdBtnDot, rdBtnDash, rdBtnMixed;

    static final LatLng Birjand = new LatLng(32.867128, 59.221670);
    static final LatLng Mashhad = new LatLng(36.289907, 59.618287);
    static final LatLng Isfahan = new LatLng(32.660477, 51.668476);
    static final LatLng Kerman = new LatLng(30.286078, 57.081697);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyMapUtils.checkPlayServices(this)) {
            setContentView(R.layout.activity_polygon);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Birjand, 5));

        drawPolygon();
        setListeners();
        setDefaultData();
    }

    private void findViews() {
        fillHueSeekBar = (SeekBar) findViewById(R.id.fillHueSeekBar);
        fillAlphaSeekBar = (SeekBar) findViewById(R.id.fillAlphaSeekBar);

        strokeWidthSeekBar = (SeekBar) findViewById(R.id.strokeWidthSeekBar);
        strokeHueSeekBar = (SeekBar) findViewById(R.id.strokeHueSeekBar);
        strokeAlphaSeekBar = (SeekBar) findViewById(R.id.strokeAlphaSeekBar);

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
    }

    private void drawPolygon() {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(Birjand);
        polygonOptions.add(Mashhad);
        polygonOptions.add(Isfahan);
        polygonOptions.add(Kerman);
        polygon = mMap.addPolygon(polygonOptions);
        polygon.setClickable(true);

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {
        fillHueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MyMapUtils.setPolygonFillColor(polygon, i);
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
                MyMapUtils.setPolygonFillAlpha(polygon, i);
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
                polygon.setStrokeWidth(progress);
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
                MyMapUtils.setPolygonStrokeColor(polygon, i);
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
                MyMapUtils.setPolygonStrokeAlpha(polygon, i);
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

    private void setDefaultData() {
        fillHueSeekBar.setProgress(200);
        strokeHueSeekBar.setProgress(63);
        fillAlphaSeekBar.setProgress(255);
        strokeAlphaSeekBar.setProgress(255);
        strokeWidthSeekBar.setProgress(15);

        rdBtnDefault.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (rdBtnDefault.isChecked())
            polygon.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DEFAULT));
        else if (rdBtnDot.isChecked())
            polygon.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DOT));
        else if (rdBtnDash.isChecked())
            polygon.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.DASH));
        else if (rdBtnMixed.isChecked())
            polygon.setStrokePattern(MyMapUtils.getPattern(MyMapUtils.patternType.MIXED));
    }
}
