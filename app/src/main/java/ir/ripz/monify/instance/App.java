package ir.ripz.monify.instance;

import android.app.Application;

import ir.ripz.monify.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/font.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
