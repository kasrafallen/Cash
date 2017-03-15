package ir.ripz.monify.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import ir.ripz.monify.R;

public class ThemeButton extends AppCompatImageButton {
    public ThemeButton(Context context) {
        super(context);
        Resources res = context.getResources();
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        res.getColor(R.color.theme_pressed),
                        res.getColor(R.color.transparent),
                }
        );
        this.setSupportBackgroundTintList(myColorStateList);
        this.setScaleType(ScaleType.CENTER_INSIDE);
    }

    public ThemeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThemeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
