package ir.ripz.monify.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class T {
    public static void set(TextView tv, Context context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf"));
    }
}
