package ir.masouddabbaghi.map;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CameraActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ToggleButton toggleButtonAnimate;

    private EditText editTextInputSearchCity;

    private static final CameraPosition tehranPosition = new CameraPosition
            .Builder()
            .target(new LatLng(35.699743, 51.338020))
            .zoom(10.5f)
            .bearing(180)
            .tilt(50)
            .build();
    private static final CameraPosition isfahanPosition = new CameraPosition
            .Builder()
            .target(new LatLng(32.657364, 51.677519))
            .zoom(10.5f)
            .bearing(180)
            .tilt(50)
            .build();

    private static final int DURATION = 2000;
    private static final int SCROLL_BY_PX = 100;
    public static final String LOG_TAG = "CameraActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyMapUtils.checkPlayServices(this)) {
            setContentView(R.layout.activity_camera);
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
        toggleButtonAnimate = findViewById(R.id.toggleButtonAnimate);
        editTextInputSearchCity = findViewById(R.id.editTextInputSearchCity);
    }

    private void setListeners() {
        findViewById(R.id.imageButtonStopAnimation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.stopAnimation();
                Log.i(LOG_TAG, "Stop Animation");
            }
        });
        findViewById(R.id.imageButtonScrollUpArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.scrollBy(0, -SCROLL_BY_PX), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Scroll Up");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.imageButtonScrollLeftArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.scrollBy(-SCROLL_BY_PX, 0), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Scroll Left");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.imageButtonScrollRightArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX, 0), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Scroll Right");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.imageButtonScrollDownArrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.scrollBy(0, SCROLL_BY_PX), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Scroll Down");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.imageButtonZoomIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.zoomIn(), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Zoom In");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.imageButtonZoomOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.zoomOut(), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Zoom Out");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.imageButtonTiltMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap)) {
                    float newTilt = mMap.getCameraPosition().tilt + 10;
                    if (newTilt > 30)
                        newTilt = 30;
                    changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder(mMap.getCameraPosition()).tilt(newTilt).build()), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Tilt More");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        });
        findViewById(R.id.imageButtonTiltLess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap)) {
                    float newTilt = mMap.getCameraPosition().tilt - 10;
                    if (newTilt < 0)
                        newTilt = 0;
                    changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder(mMap.getCameraPosition()).tilt(newTilt).build()), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Tilt Less");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        });
        findViewById(R.id.imageButtonSearchCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap)) {
                    if (editTextInputSearchCity.getText().length() > 0) {
                        final Address address = MyMapUtils.gotTOLocation(editTextInputSearchCity.getText().toString().trim());
                        if (address != null) {
                            changeCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(),
                                    address.getLongitude()), 10), new GoogleMap.CancelableCallback() {
                                @Override
                                public void onFinish() {
                                    Log.i(LOG_TAG, "Search City : " + address.getCountryName() + " - " + address.getLocality());
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    } else
                        Toast.makeText(CameraActivity.this, "Please Einter your City", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.buttonCityTehran).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.newCameraPosition(tehranPosition), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Reached To Tehran");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });
        findViewById(R.id.buttonCityIsfahan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMapUtils.checkMapIsReady(mMap))
                    changeCamera(CameraUpdateFactory.newCameraPosition(isfahanPosition), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.i(LOG_TAG, "Reached To Isfahan");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Log.i(LOG_TAG, "Camera Is Finished");
            }
        });

        mMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                Log.i(LOG_TAG, "Camera Is Cancel");
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                Log.i(LOG_TAG, "Move");
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                Log.i(LOG_TAG, "Move Start");
            }
        });
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

    private void changeCamera(CameraUpdate cameraUpdate, GoogleMap.CancelableCallback callback) {
        if (toggleButtonAnimate.isChecked())
            mMap.animateCamera(cameraUpdate, DURATION, callback);
        else
            mMap.moveCamera(cameraUpdate);
    }
}
