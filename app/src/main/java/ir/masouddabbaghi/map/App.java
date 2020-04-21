package ir.masouddabbaghi.map;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new MyMapUtils(getApplicationContext());
    }
}
