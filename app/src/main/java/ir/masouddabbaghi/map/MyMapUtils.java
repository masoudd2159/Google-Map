package ir.masouddabbaghi.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MyMapUtils {
    private static Context context;

    private static final int PATTERN_DASH_LENGTH_PX = 50;
    private static final int PATTERN_GAP_LENGTH_PX = 20;

    private static final Dot DOT = new Dot();
    private static final Dash DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    private static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(DOT, GAP);
    private static final List<PatternItem> PATTERN_DASHED = Arrays.asList(DASH, GAP);
    private static final List<PatternItem> PATTERN_MIXED = Arrays.asList(DOT, GAP, DASH, GAP);

    public enum patternType {
        DEFAULT, DOT, DASH, MIXED
    }

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

    public static List<PatternItem> getPattern(patternType type) {
        switch (type) {
            case DOT:
                return PATTERN_DOTTED;
            case DASH:
                return PATTERN_DASHED;
            case MIXED:
                return PATTERN_MIXED;
            default:
                return null;
        }
    }

    public static void setPolylineColor(Polyline polyline, final int progress) {
        polyline.setColor(Color.HSVToColor(Color.alpha(polyline.getColor()), new float[]{progress, 1, 1}));
    }

    public static void setPolylineAlpha(Polyline polyline, int alphaProgress) {
        float[] prevHSV = new float[3];
        Color.colorToHSV(polyline.getColor(), prevHSV);
        polyline.setColor(Color.HSVToColor(alphaProgress, prevHSV));
    }

    public static void setPolygonFillColor(Polygon polygon, int progress) {
        polygon.setFillColor(Color.HSVToColor(Color.alpha(polygon.getFillColor()), new float[]{progress, 1, 1}));
    }

    public static void setPolygonFillAlpha(Polygon polygon, int progress) {
        int prevColor = polygon.getFillColor();
        polygon.setFillColor(Color.argb(progress, Color.red(prevColor), Color.green(prevColor), Color.blue(prevColor)));
    }

    public static void setPolygonStrokeColor(Polygon polygon, int progress) {
        polygon.setStrokeColor(Color.HSVToColor(Color.alpha(polygon.getStrokeColor()), new float[]{progress, 1, 1}));
    }

    public static void setPolygonStrokeAlpha(Polygon polygon, int progress) {
        int prevColorArgb = polygon.getStrokeColor();
        polygon.setStrokeColor(Color.argb(progress, Color.red(prevColorArgb), Color.green(prevColorArgb), Color.blue(prevColorArgb)));
    }


    public static void setCircleFillColor(Circle circle, int progress) {
        circle.setFillColor(Color.HSVToColor(Color.alpha(circle.getFillColor()), new float[]{progress, 1, 1}));
    }

    public static void setCircleFillAlpha(Circle circle, int progress) {
        int prevColor = circle.getFillColor();
        circle.setFillColor(Color.argb(progress, Color.red(prevColor), Color.green(prevColor), Color.blue(prevColor)));
    }

    public static void setCircleStrokeColor(Circle circle, int progress) {
        circle.setStrokeColor(Color.HSVToColor(Color.alpha(circle.getStrokeColor()), new float[]{progress, 1, 1}));
    }

    public static void setCircleStrokeAlpha(Circle circle, int progress) {
        int prevColorArgb = circle.getStrokeColor();
        circle.setStrokeColor(Color.argb(progress, Color.red(prevColorArgb), Color.green(prevColorArgb), Color.blue(prevColorArgb)));
    }
}
