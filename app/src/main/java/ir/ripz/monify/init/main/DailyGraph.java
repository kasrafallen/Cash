package ir.ripz.monify.init.main;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ir.ripz.monify.R;
import ir.ripz.monify.activity.MainActivity;
import ir.ripz.monify.instance.DataManager;
import ir.ripz.monify.instance.ProfileManager;
import ir.ripz.monify.model.DateModel;
import ir.ripz.monify.model.DutyModel;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.model.ProfileModel;
import ir.ripz.monify.util.SolarCalendar;
import ir.ripz.monify.util.T;
import ir.ripz.monify.view.chart.listener.PieChartOnValueSelectListener;
import ir.ripz.monify.view.chart.model.PieChartData;
import ir.ripz.monify.view.chart.model.SliceValue;
import ir.ripz.monify.view.chart.view.PieChartView;

public class DailyGraph implements PieChartOnValueSelectListener, TextWatcher {
    private MainActivity context;
    private float dimen[];
    private RelativeLayout view;

    private float width;
    private PieChartView pie;

    private TextView main_text;
    private TextView sub_text;
    private TextInputLayout input;
    private AppCompatEditText what_text;
    private AppCompatEditText input_text;
    private LinearLayout box;

    private LinearLayout layout;

    private static final int PIE_ID = +98000284;
    private float pie_size;

    private Handler handler;

    public DailyGraph(MainActivity context, float[] dimen) {
        this.context = context;
        this.dimen = dimen;
        this.width = dimen[0] - dimen[0] / 5;
        this.pie_size = 34 * dimen[0] / 50;
        this.handler = new Handler();
        context.setDate(new SolarCalendar().toString());
    }

    private void saveValue() {
        InterestModel model = getInterestFromWhat(what_text.getText().toString());
        long cash = getValueFromText(input_text.getText().toString());

        DateModel calendar = new SolarCalendar().get();
        DutyModel data = new DutyModel();
        data.setDateModel(calendar);
        data.setTitle(what_text.getText().toString());
        data.setDuty(cash);
        data.setInterest(model);
        new DataManager(context).addItem(data);

        setData();

        ScaleAnimation animation = new ScaleAnimation(1, 0.9f, 1, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(300);
        layout.startAnimation(animation);

        Toast.makeText(context, "هزینه ثبت شد.", Toast.LENGTH_SHORT).show();

        what_text.setText("");
        input_text.setText("");
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
        view.addView(layout());
        view.addView(form());
        setData();
        return view;
    }

    public void setData() {
        if (main_text != null && sub_text != null && pie != null) {
            main_text.setText(DutyModel.DATA_TITLE);
            sub_text.setText(NumberFormat.getNumberInstance().format(new DataManager(context).getTodayAll()) + " تومان");
            pie.setPieChartData(setPies());
            setBoxItems();
        }
    }

    private View form() {
        layout = new LinearLayout(context);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.BELOW, PIE_ID);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(space());
        layout.addView(what());
        layout.addView(space());
        layout.addView(input());
        layout.addView(space());
        layout.addView(accept());
        layout.addView(space());
        return layout;
    }

    private View what() {
        what_text = new AppCompatEditText(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams((int) width, -2);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        what_text.setLayoutParams(p);
        what_text.setSingleLine();
        what_text.setEllipsize(TextUtils.TruncateAt.END);
        what_text.setHint("نوع هزینه");
        what_text.setCompoundDrawablePadding((int) (dimen[0] / 50));
        what_text.setTextSize(0, dimen[1] / 40);
        what_text.setGravity(Gravity.RIGHT);
        what_text.setInputType(InputType.TYPE_CLASS_TEXT);
        T.set(what_text, context);
        what_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    what_text.setTag(s.toString());
                } else {
                    what_text.setTag(null);
                }
            }
        });
        return what_text;
    }

    private View space() {
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1));
        return space;
    }

    private View accept() {
        final AppCompatButton accept = new AppCompatButton(context);
        accept.setSingleLine();
        accept.setTextColor(Color.WHITE);
        T.set(accept, context);
        accept.setText("ثبت");
        accept.setTextSize(0, dimen[1] / 45);
        accept.setSupportBackgroundTintList(getState(true));
        accept.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 2), (int) (dimen[1] / 10)));
        ((LinearLayout.LayoutParams) accept.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (what_text.getTag() == null) {
                    what_text.setError("نوع هزینه را انتخاب کنید.");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            what_text.setError(null);
                        }
                    }, 2000);
                } else {
                    if (input_text.getText() != null && input_text.getText().length() > 0 && !input_text.getText().toString()
                            .equals(NumberFormat.getNumberInstance().format(0))) {
                        saveValue();
                    } else {
                        input.setError("هزینه ای وارد نشده است.");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                input.setError("");
                            }
                        }, 2000);
                    }
                }
            }
        });
        return accept;
    }

    private InterestModel getInterestFromWhat(String text) {
        ProfileModel profile = new ProfileManager(context).getProfile();
        for (int i = 0; i < profile.getInterest().size(); i++) {
            if (profile.getInterest().get(i).getName().equalsIgnoreCase(text)) {
                return profile.getInterest().get(i);
            }
        }
        for (int i = 0; i < profile.getInterest().size(); i++) {
            if (profile.getInterest().get(i).getId() == InterestModel.DEFAULT_ID) {
                return profile.getInterest().get(i);
            }
        }
        return null;
    }

    private ColorStateList getState(boolean flag) {
        if (flag) {
            return new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_pressed},
                            new int[]{}
                    },
                    new int[]{
                            context.getResources().getColor(R.color.theme_pressed),
                            context.getResources().getColor(R.color.theme_lite),
                    }
            );
        } else {
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
    }

    private View input() {
        RelativeLayout layout = new RelativeLayout(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        layout.setLayoutParams(p);

        input = new TextInputLayout(context);
        RelativeLayout.LayoutParams inputParams = new RelativeLayout.LayoutParams(-2, -2);
        inputParams.addRule(RelativeLayout.CENTER_VERTICAL);
        inputParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        inputParams.addRule(RelativeLayout.RIGHT_OF, +696969);
        inputParams.rightMargin = (int) (dimen[0] / 10);
        input.setLayoutParams(inputParams);

        input_text = new AppCompatEditText(context);
        input_text.setLayoutParams(new TextInputLayout.LayoutParams(-1, -2));
        input_text.setSingleLine();
        input_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        input_text.setTextSize(0, dimen[1] / 37);
        input_text.setHint("هزینه را وارد کنید.");
        input_text.setGravity(Gravity.LEFT);
        input_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        input_text.addTextChangedListener(this);
        T.set(input_text, context);

        AppCompatTextView tv = new AppCompatTextView(context);
        tv.setId(+696969);
        RelativeLayout.LayoutParams tvp = new RelativeLayout.LayoutParams(-2, -2);
        tvp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tvp.addRule(RelativeLayout.CENTER_VERTICAL);
        tvp.leftMargin = (int) (dimen[0] / 10);
        tv.setSingleLine();
        tv.setTextColor(Color.BLACK);
        T.set(tv, context);
        tv.setTextSize(0, dimen[1] / 35);
        tv.setText("تومان");
        tv.setGravity(Gravity.LEFT);
        tv.setPadding(0, 0, (int) (dimen[0] / 10), 0);
        tv.setLayoutParams(tvp);

        Button button = new Button(context);
        button.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[1] / 20), (int) (dimen[1] / 20)));
        ((RelativeLayout.LayoutParams) button.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ((RelativeLayout.LayoutParams) button.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) button.getLayoutParams()).rightMargin = (int) (dimen[0] / 30);
        button.setBackgroundResource(R.drawable.ic_content_backspace);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_text.setText("");
            }
        });

        input.addView(input_text);
        input.setErrorEnabled(true);

        layout.addView(input);
        layout.addView(tv);
        layout.addView(button);
        return layout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override

    public void afterTextChanged(Editable text) {
        try {
            input_text.removeTextChangedListener(this);
            String value = text.toString();
            if (value.length() > 0) {
                input_text.setText(NumberFormat.getNumberInstance().format(getValueFromText(value)) + "");
                input_text.setSelection(input_text.getText().toString().length());
            }
            input_text.addTextChangedListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            input_text.addTextChangedListener(this);
        }
    }

    private long getValueFromText(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return Long.parseLong(builder.toString());
    }

    private View layout() {
        RelativeLayout pie_parent = new RelativeLayout(context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, -2);
        pie_parent.setLayoutParams(p);
        pie_parent.setId(PIE_ID);

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

    private PieChartData setPies() {
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
            float progress = new DataManager(context).getTodayInterestPercent(profile.getInterest().get(i));
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
        return data;
    }

    boolean selected = false;

    @Override
    public void onValueSelected(int i, SliceValue sliceValue) {
        select(i);
    }

    private void select(int i) {
        ProfileModel profile = new ProfileManager(context).getProfile();
        String text = NumberFormat.getNumberInstance().format(new DataManager(context).getTodayInterest(profile.getInterest().get(i)));

        main_text.setText(profile.getInterest().get(i).getName());
        sub_text.setText(text + " تومان");
        if (!profile.getInterest().get(i).is_default()) {
            what_text.setText(profile.getInterest().get(i).getName());
        } else {
            what_text.setText("");
        }

        if (selected) {
            handler.removeCallbacks(task);
        }

        selected = true;
        handler.postDelayed(task, 3500);
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            selected = false;
            main_text.setText(DutyModel.DATA_TITLE);
            sub_text.setText(NumberFormat.getNumberInstance().format(new DataManager(context).getTodayAll()) + " تومان");
        }
    };

    @Override
    public void onValueDeselected() {

    }
}
