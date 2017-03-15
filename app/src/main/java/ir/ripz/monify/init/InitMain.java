package ir.ripz.monify.init;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.ripz.monify.R;
import ir.ripz.monify.activity.InterestFragment;
import ir.ripz.monify.activity.MainActivity;
import ir.ripz.monify.init.main.DailyGraph;
import ir.ripz.monify.init.main.DailyList;
import ir.ripz.monify.init.main.RangeGraph;
import ir.ripz.monify.init.main.RangeList;
import ir.ripz.monify.util.SolarCalendar;
import ir.ripz.monify.util.T;
import ir.ripz.monify.view.AboutUsAlert;
import ir.ripz.monify.view.ThemeButton;

public class InitMain {

    private static final int CALENDAR_ID = +98498428;
    private static final int TOOLBAR_ID = +98424284;
    private static final int DRAWER_ITEM_ID = +98455100;
    private static final int BASE_ID = +121212;
    private static final int LAYOUT_ID = +110110;

    private MainActivity context;
    private float dimen[];
    private FrameLayout display;

    public DailyGraph dailyGraph;
    public DailyList dailyList;
    public RangeGraph allGraph;
    public RangeList allList;

    private TextView tv;

    public InitMain(MainActivity context) {
        this.context = context;
        this.dailyGraph = new DailyGraph(context, context.dimen);
        this.dailyList = new DailyList(context, context.dimen);
        this.allGraph = new RangeGraph(context, context.dimen);
        this.allList = new RangeList(context, context.dimen);
    }

    public View create(float[] dimen) {
        this.dimen = dimen;

        FrameLayout all = new FrameLayout(context);
        all.setId(LAYOUT_ID);
        all.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        context.drawer = new DrawerLayout(context);
        context.drawer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        context.drawer.setScrimColor(context.getResources().getColor(R.color.theme_scrim));

        FrameLayout display = new FrameLayout(context);
        display.setLayoutParams(new DrawerLayout.LayoutParams(-1, -1));

        RelativeLayout layout = new RelativeLayout(context);
        layout.setBackgroundResource(R.color.theme_base);
        layout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        layout.addView(createBar());
        layout.addView(createCalendar());
        layout.addView(display());
        layout.addView(createShadow());

        display.addView(layout);

        context.drawer.addView(display);
        context.drawer.addView(drawerView());

        all.addView(context.drawer);
        return all;
    }

    private View createShadow() {
        View view = new View(context);
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 250)));
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.BELOW, TOOLBAR_ID);
        view.setBackgroundResource(R.drawable.gradient);
        return view;
    }

    private View display() {
        display = new FrameLayout(context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, -1);
        p.addRule(RelativeLayout.BELOW, CALENDAR_ID);
        display.setLayoutParams(p);
        setDisplay(0);
        return display;
    }

    private View createCalendar() {
        tv = new TextView(context);
        tv.setBackgroundResource(R.color.theme_lite);
        tv.setId(CALENDAR_ID);
        tv.setSingleLine();
        T.set(tv, context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, -2);
        p.bottomMargin = (int) (dimen[1] / 50);
        p.addRule(RelativeLayout.BELOW, TOOLBAR_ID);
        tv.setLayoutParams(p);
        tv.setTextColor(Color.WHITE);
        tv.setPadding(0, (int) (dimen[1] / 60), 0, (int) (dimen[1] / 60));
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    private Toolbar createBar() {
        context.toolbar = new Toolbar(context);
        context.toolbar.setId(TOOLBAR_ID);
        context.toolbar.setBackgroundResource(R.color.theme);
        context.toolbar.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        context.toolbar.setContentInsetsRelative(0, 0);
        context.toolbar.setContentInsetsAbsolute(0, 0);

        RelativeLayout layout = new RelativeLayout(context);
        Toolbar.LayoutParams p = new Toolbar.LayoutParams(-1, (int) (dimen[1] / 10));
        p.gravity = Gravity.CENTER;
        layout.setLayoutParams(p);

        ThemeButton button = new ThemeButton(context);
        button.setImageResource(R.drawable.ic_navigation_menu);
        RelativeLayout.LayoutParams bp = new RelativeLayout.LayoutParams((int) (dimen[1] / 12), (int) (dimen[1] / 12));
        bp.addRule(RelativeLayout.CENTER_VERTICAL);
        bp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        button.setLayoutParams(bp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context.drawer.isDrawerOpen(Gravity.RIGHT)) {
                    context.drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    context.drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        TextView tv = new TextView(context);
        RelativeLayout.LayoutParams tp = new RelativeLayout.LayoutParams(-2, -2);
        tp.setMargins((int) (dimen[0] / 30), 0, 0, 0);
        tp.addRule(RelativeLayout.CENTER_VERTICAL);
        tp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(1, 15);
        tv.setText("name");
        tv.setLayoutParams(tp);
        T.set(tv, context);

        layout.addView(button);
        layout.addView(tv);
        context.toolbar.addView(layout);
        return context.toolbar;
    }

    private View drawerView() {
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundResource(R.color.theme_base);
        DrawerLayout.LayoutParams p = new DrawerLayout.LayoutParams((int) (33 * dimen[0] / 50), -1);
        p.gravity = Gravity.RIGHT;
        layout.setLayoutParams(p);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(image());
        layout.addView(item(0));
        layout.addView(item(1));
        layout.addView(item(2));
        layout.addView(item(3));
        layout.addView(item(4));
        layout.addView(item(5));
        return layout;
    }

    private View item(final int i) {
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setId(+(BASE_ID + i));
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (dimen[1] / 8)));
        if (i == 0) {
            layout.setBackgroundResource(R.color.drawer_selected);
        } else {
            layout.setBackgroundResource(R.drawable.selector_interest);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setDisplay(i)) {
                    if (context.drawer.isDrawerOpen(Gravity.RIGHT)) {
                        context.drawer.closeDrawer(Gravity.RIGHT);
                    }
                }
            }
        });
        layout.addView(tv(i));
        layout.addView(line());
        return layout;
    }

    private View image() {
        Button view = new Button(context);
        view.setClickable(false);
        view.setFocusable(false);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (dimen[1] / 10)));
        view.setBackgroundResource(R.color.theme_dark);
        view.setText("پنل کاربر");
        view.setTextColor(Color.WHITE);
        view.setTextSize(0, dimen[1] / 40);
        return view;
    }

    public boolean setDisplay(int i) {
        if (context.counter == i) {
            return false;
        } else if (i == 5) {
            showAboutUs();
            return true;
        } else if (i == 4) {
            openInterest();
            return true;
        }
        context.counter = i;
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setFillAfter(true);
        if (display.getChildAt(0) != null)
            display.getChildAt(0).startAnimation(animation);
        selectDrawer(i);
        switch (i) {
            case 0:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        display.removeView(display.getChildAt(0));
                        display.addView(dailyGraph.getView());
                    }
                }, 500);
                break;
            case 1:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        display.removeView(display.getChildAt(0));
                        display.addView(dailyList.getView());
                    }
                }, 500);
                break;
            case 2:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        display.removeView(display.getChildAt(0));
                        display.addView(allGraph.getView());
                    }
                }, 500);
                break;
            case 3:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        display.removeView(display.getChildAt(0));
                        display.addView(allList.getView());
                    }
                }, 500);
                break;
        }
        context.setDate(new SolarCalendar().toString());
        return true;
    }

    private void openInterest() {
        Fragment fragment = InterestFragment.newInstance();
        context.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                .add(LAYOUT_ID, fragment, "TAG_APP_INTEREST")
                .addToBackStack("TAG_APP_INTEREST").commit();
    }

    private void showAboutUs() {
        new AboutUsAlert(context, dimen).show();
    }

    private void selectDrawer(int id) {
        for (int i = 0; i < 6; i++) {
            View view = context.findViewById(+(BASE_ID + i));
            if (view != null) {
                if (i == id) {
                    view.setBackgroundResource(R.color.drawer_selected);
                } else {
                    view.setBackgroundResource(R.drawable.selector_interest);
                }
            }
        }
    }

    private View tv(int i) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1));
        layout.setId(DRAWER_ITEM_ID);

        View view = new View(context);
        view.setBackgroundResource(R.drawable.drawer_shape);
        view.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[0] / 45), (int) (dimen[1] / 11)));
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        AppCompatTextView text = new AppCompatTextView(context);
        T.set(text, context);
        text.setSingleLine();
        text.setTextSize(0, dimen[1] / 45);
        text.setTextColor(Color.DKGRAY);
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-2, -2);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p.rightMargin = (int) (dimen[0] / 10);
        text.setLayoutParams(p);
        switch (i) {
            case 0:
                text.setText("صفحه اصلی");
                ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(R.color.theme_dark));
                break;
            case 1:
                text.setText("لیست هزینه های روزانه");
                ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(R.color.theme_lite));
                break;
            case 2:
                text.setText("لیست هزینه های ثابت");
                ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(R.color.theme));
                break;
            case 3:
                text.setText("گزارش همه هزینه ها");
                ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(R.color.theme_dark));
                break;
            case 4:
                text.setText("روتین ها");
                ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(R.color.theme_lite));
                break;
            case 5:
                text.setText("توضیحات");
                ((GradientDrawable) view.getBackground()).setColor(context.getResources().getColor(R.color.theme));
                break;
        }

        layout.addView(text);
        layout.addView(view);
        return layout;
    }

    private View line() {
        View view = new View(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams((int) (3 * dimen[0] / 6), (int) (dimen[1] / 570));
        p.gravity = Gravity.CENTER_HORIZONTAL;
        view.setLayoutParams(p);
        view.setBackgroundColor(Color.LTGRAY);
        return view;
    }

    public void setText(String date) {
        if (tv != null)
            tv.setText(date);
    }
}
