package ir.ripz.monify.init.main;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import ir.ripz.monify.R;
import ir.ripz.monify.activity.MainActivity;
import ir.ripz.monify.instance.DataManager;
import ir.ripz.monify.instance.ProfileManager;
import ir.ripz.monify.model.DutyModel;
import ir.ripz.monify.model.DateModel;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.model.ProfileModel;
import ir.ripz.monify.util.SolarCalendar;
import ir.ripz.monify.util.T;
import ir.ripz.monify.view.CalendarAlert;
import ir.ripz.monify.view.chart.listener.PieChartOnValueSelectListener;
import ir.ripz.monify.view.chart.model.PieChartData;
import ir.ripz.monify.view.chart.model.SliceValue;
import ir.ripz.monify.view.chart.view.PieChartView;

public class RangeGraph implements PieChartOnValueSelectListener {
    private static final int LAYOUT_ID = +96666605;
    private MainActivity context;
    private float dimen[];
    private RelativeLayout view;

    private PieChartView pie;

    private TextView main_text;
    private TextView sub_text;

    private Handler handler;

    private static final int PIE_ID = +10600524;
    private float pie_size;
    private RelativeLayout pie_parent;
    private LinearLayout box;

    private AppCompatButton min;
    private AppCompatButton max;

    private AlertDialog alert_min;
    private AlertDialog alert_max;

    public RangeGraph(MainActivity context, float[] dimen) {
        this.context = context;
        this.dimen = dimen;
        this.handler = new Handler();
        this.pie_size = 34 * dimen[0] / 50;
        context.setDate(new SolarCalendar().toString());
    }

    public View getView() {
        view = new RelativeLayout(context) {
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                AlphaAnimation animation1 = new AlphaAnimation(0, 1);
                animation1.setDuration(500);
                animation1.setFillAfter(true);
                this.startAnimation(animation1);
            }
        };
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        view.addView(range());

        view.addView(layout());
        view.setId(LAYOUT_ID);
        this.alert_min = new CalendarAlert(context, dimen,CalendarAlert.FROM_RANGE).get(new CalendarAlert.Select() {
            @Override
            public void onSelect(DateModel date) {
                min.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                min.setTag(date);
                check();
            }
        });
        this.alert_max = new CalendarAlert(context, dimen,CalendarAlert.FROM_RANGE).get(new CalendarAlert.Select() {
            @Override
            public void onSelect(DateModel date) {
                max.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                max.setTag(date);
                check();
            }
        });
        return view;
    }

    private void check() {
        if (min.getTag() != null && max.getTag() != null) {
            pie.setPieChartData(setPies((DateModel) min.getTag(), (DateModel) max.getTag()));
        }
    }

    private View range() {
        LinearLayout layout = new LinearLayout(context);
        layout.setId(+698);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 10)));
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_TOP);

        layout.addView(space());
        layout.addView(max());
        layout.addView(space());
        layout.addView(middle());
        layout.addView(space());
        layout.addView(min());
        layout.addView(space());
        layout.addView(left());
        layout.addView(space());
        return layout;
    }

    private View space() {
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 1));
        return space;
    }

    private View max() {
        max = new AppCompatButton(context);
        max.setSupportBackgroundTintList(getState());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2, -2);
        p.gravity = Gravity.CENTER_VERTICAL;
        max.setLayoutParams(p);
        max.setText("انتخاب کنید");
        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_max.show();
            }
        });
        max.setTextSize(0, dimen[1] / 40);
        max.setTextColor(Color.WHITE);
        T.set(max, context);
        max.setSingleLine();
        return max;
    }

    private View middle() {
        TextView tv = new TextView(context);
        tv.setText(" تا ");
        tv.setId(+5414522);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2, -2);
        p.gravity = Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(p);
        T.set(tv, context);
        tv.setTextColor(Color.DKGRAY);
        tv.setSingleLine();
        tv.setTextSize(0, dimen[1] / 37);
        return tv;
    }

    private View min() {
        min = new AppCompatButton(context);
        min.setSupportBackgroundTintList(getState());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2, -2);
        p.gravity = Gravity.CENTER_VERTICAL;
        min.setLayoutParams(p);
        min.setText("انتخاب کنید");
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_min.show();
            }
        });
        min.setTextSize(0, dimen[1] / 40);
        min.setTextColor(Color.WHITE);
        T.set(min, context);
        min.setSingleLine();
        return min;
    }

    private View left() {
        TextView tv = new TextView(context);
        tv.setText("از تاریخ ");
        tv.setId(+7894500);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2, -2);
        p.gravity = Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(p);
        T.set(tv, context);
        tv.setTextColor(Color.DKGRAY);
        tv.setSingleLine();
        tv.setTextSize(0, dimen[1] / 37);
        return tv;
    }

    public void setData(DateModel mi, DateModel ma) {
        if (mi == null || ma == null) {
            if (min.getTag() != null && max.getTag() != null) {
                mi = (DateModel) min.getTag();
                ma = (DateModel) max.getTag();
            } else {
                return;
            }
        }
        if (main_text != null && sub_text != null && pie_parent != null) {
            main_text.setText(DutyModel.DATA_TITLE);
            long data = getRangeData(mi, ma);
            sub_text.setText(NumberFormat.getNumberInstance().format(data) + " تومان");
            sub_text.setTag(String.valueOf(data));
            pie_parent.setVisibility(View.VISIBLE);
        }
    }

    private long getRangeData(DateModel min, DateModel max) {
        return new DataManager(context).getRangeAll(min, max);
    }

    private ColorStateList getState() {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        context.getResources().getColor(R.color.theme_pressed),
                        context.getResources().getColor(R.color.theme_dark),
                }
        );
    }

    @Override
    public void onValueDeselected() {

    }

    private View layout() {
        pie_parent = new RelativeLayout(context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, -2);
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p.bottomMargin = (int) (dimen[1] / 12);
        pie_parent.setLayoutParams(p);
        pie_parent.setId(PIE_ID);
        pie_parent.setVisibility(View.GONE);

        pie = new PieChartView(context);
        pie.setLayoutParams(new RelativeLayout.LayoutParams((int) pie_size, (int) pie_size));
        ((RelativeLayout.LayoutParams) pie.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) pie.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_TOP);
        ((RelativeLayout.LayoutParams) pie.getLayoutParams()).setMargins((int) (pie_size / 25), 0, (int) (pie_size / 25), 0);
        pie.setChartRotationEnabled(true);
        pie.setOnValueTouchListener(this);
        pie.setChartRotation(180, true);
        pie.setId(+(PIE_ID + 50));

        pie_parent.addView(pie);
        pie_parent.addView(textBox());
        pie_parent.addView(chart());
        return pie_parent;
    }

    private View chart() {
        ScrollView view = new ScrollView(context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, (int) pie_size);
        p.addRule(RelativeLayout.RIGHT_OF, +(PIE_ID + 50));
        p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        view.setLayoutParams(p);
        view.setVerticalScrollBarEnabled(false);
        view.setHorizontalScrollBarEnabled(false);

        box = new LinearLayout(context);
        box.setOrientation(LinearLayout.VERTICAL);
        ScrollView.LayoutParams sp = new ScrollView.LayoutParams(-1, -2);
        sp.gravity = Gravity.CENTER;
        box.setLayoutParams(sp);
        setBoxItems();

        view.addView(box);
        return view;
    }

    private void setBoxItems() {
        if (box.getChildCount() > 0) {
            box.removeAllViews();
        }
        ProfileModel profile = new ProfileManager(context).getProfile();
        for (InterestModel model : profile.getInterest()) {
            box.addView(chartItem(model, profile.getInterest().indexOf(model)));
            box.addView(thinLine());
        }
    }

    private View thinLine() {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (dimen[0] / 550)));
        ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;
        view.setBackgroundColor(Color.LTGRAY);
        view.setAlpha(0.7f);
        view.setPadding((int) (dimen[0] / 50), 0, (int) (dimen[0] / 50), 0);
        return view;
    }

    private RelativeLayout chartItem(final InterestModel model, final int i) {
        float item_size = dimen[1] / 15;
        RelativeLayout item = new RelativeLayout(context);
        item.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) item_size));

        Button button = new Button(context);
        button.setClickable(false);
        button.setId(+8780200);
        button.setBackgroundColor(model.getColor());
        button.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[1] / 30), (int) (dimen[1] / 30)));
        ((RelativeLayout.LayoutParams) button.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ((RelativeLayout.LayoutParams) button.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) button.getLayoutParams()).setMargins((int) (dimen[0] / 60), 0, (int) (dimen[0] / 60), 0);

        TextView tv = new TextView(context);
        tv.setTextColor(Color.DKGRAY);
        tv.setTextSize(0, dimen[1] / 60);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.LEFT_OF, +8780200);
        T.set(tv, context);
        tv.setText(model.getName());

        item.addView(button);
        item.addView(tv);
        item.setBackgroundResource(R.drawable.selector_interest);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(i);
            }
        });
        return item;
    }

    private View textBox() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(((int) pie_size), -2));
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).setMargins((int) (pie_size / 30), 0, (int) (pie_size / 30), 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        main_text = new TextView(context);
        main_text.setSingleLine();
        T.set(main_text, context);
        main_text.setTextColor(Color.BLACK);
        main_text.setTextSize(0, dimen[1] / 33);
        main_text.setGravity(Gravity.CENTER_HORIZONTAL);
        main_text.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        ((LinearLayout.LayoutParams) main_text.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;

        sub_text = new TextView(context);
        sub_text.setSingleLine();
        T.set(sub_text, context);
        sub_text.setTextColor(Color.DKGRAY);
        sub_text.setTextSize(0, dimen[1] / 45);
        sub_text.setGravity(Gravity.CENTER_HORIZONTAL);
        sub_text.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        ((LinearLayout.LayoutParams) sub_text.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;

        layout.addView(main_text);
        layout.addView(sub_text);
        return layout;
    }

    private PieChartData setPies(DateModel min, DateModel max) {
        PieChartData data = new PieChartData();

        data.setHasLabels(true);
        data.setHasLabelsOutside(false);
        data.setCenterCircleColor(Color.TRANSPARENT);
        data.setHasCenterCircle(true);
        data.setHasLabelsOnlyForSelected(false);
        data.setSlicesSpacing(1);

        ArrayList<SliceValue> values = new ArrayList<>();
        ProfileModel profile = new ProfileManager(context).getProfile();
        for (int i = 0; i < profile.getInterest().size(); i++) {
            SliceValue value = new SliceValue();
            value.setColor(profile.getInterest().get(i).getColor());
            float progress = new DataManager(context).getRangeInterestPercent(profile.getInterest().get(i), min, max);
            value.setValue(progress);
            if (progress == DataManager.DEFAULT) {
                value.setLabel("-");
            } else {
                String label = Math.round(progress) + "%";
                value.setLabel(label);
            }
            values.add(value);
        }
        data.setValues(values);
        setData(min, max);
        return data;
    }

    boolean selected = false;

    @Override
    public void onValueSelected(int i, SliceValue sliceValue) {
        select(i);
    }

    private void select(int i) {
        if (min.getTag() != null && max.getTag() != null) {

            ProfileModel profile = new ProfileManager(context).getProfile();
            String text = NumberFormat.getNumberInstance().format(new DataManager(context)
                    .getRangeInterest(profile.getInterest().get(i), (DateModel) min.getTag(), (DateModel) max.getTag()));
            main_text.setText(profile.getInterest().get(i).getName());
            sub_text.setText(text + " تومان");

            if (selected) {
                handler.removeCallbacks(task);
            }

            selected = true;
            handler.postDelayed(task, 3500);
        }
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            selected = false;
            main_text.setText(DutyModel.DATA_TITLE);
            sub_text.setText(NumberFormat.getNumberInstance().format(new DataManager(context).getTodayAll()) + " تومان");
        }
    };
}
