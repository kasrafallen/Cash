package ir.ripz.monify.view;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import ir.ripz.monify.R;
import ir.ripz.monify.adaptor.SpinnerAdaptor;
import ir.ripz.monify.instance.DataManager;
import ir.ripz.monify.model.DateModel;
import ir.ripz.monify.util.SolarCalendar;
import ir.ripz.monify.util.T;

public class CalendarAlert {
    public final static boolean FROM_DAILY = true;
    public final static boolean FROM_RANGE = false;
    private boolean from;

    public interface Select {
        void onSelect(DateModel date);
    }

    private static final int CALENDAR_ID = +585;
    private static final int BOX_ID = +595;
    private final float size;
    private float width;
    private float row;
    private float circle_size;
    private int month;
    private int counter;
    private Button last_pressed;
    private float[] dimen;
    private AlertDialog dialog;
    private LinearLayout layout;
    private final Activity context;
    private AppCompatButton accept;
    private Select select;

    private final static String[] month_array = {"فروردین", "اردیبهشت", "خرداد", "تیر",
            "مرداد", "شهریور", "مهر",
            "آبان", "آذر", "دی",
            "بهمن", "اسفند"};

    public CalendarAlert(Activity context, float[] dimen, boolean from) {
        this.context = context;
        this.size = 2 * dimen[1] / 3;
        this.dimen = dimen;
        this.width = dimen[0];
        this.row = width / 7;
        this.circle_size = 9 * row / 10;
        this.month = getCurrentMonth();
        this.from = from;
    }

    public AlertDialog get(Select select) {
        this.select = select;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setView(getView());
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        return dialog;
    }

    private View getView() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.color.theme_dark);

        LinearLayout box = new LinearLayout(this.context);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setLayoutParams(new LinearLayout.LayoutParams((int) width, -2));

        box.addView(spinner());
        box.addView(calendar());
        box.addView(accept());

        layout.addView(box);
        return layout;
    }

    private View accept() {
        accept = new AppCompatButton(context);
        accept.setId(+985);
        accept.setSingleLine();
        accept.setTextColor(Color.WHITE);
        T.set(accept, context);
        accept.setText("Accept");
        accept.setTextSize(0, dimen[1] / 45);
        accept.setSupportBackgroundTintList(getState());
        accept.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 2), (int) (dimen[1] / 10)));
        ((LinearLayout.LayoutParams) accept.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept.getTag() != null) {
                    select.onSelect((DateModel) accept.getTag());
                }
                dialog.dismiss();
            }
        });
        return accept;
    }

    private ColorStateList getState() {
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
    }

    private View calendar() {
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setId(BOX_ID);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (width)));
        layout.addView(header());
        layout.addView(getColumn());
        return layout;
    }

    private int getCurrentMonth() {
        month = new SolarCalendar().get().getMonth();
        return month;
    }

    private View header() {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundResource(R.color.theme_fade);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) row));
        ((LinearLayout.LayoutParams) layout.getLayoutParams()).bottomMargin = (int) (dimen[1] / 50);
        layout.addView(createTitle("ج"));
        layout.addView(createTitle("پ"));
        layout.addView(createTitle("چ"));
        layout.addView(createTitle("س"));
        layout.addView(createTitle("د"));
        layout.addView(createTitle("ی"));
        layout.addView(createTitle("ش"));
        return layout;
    }

    private View createTitle(String s) {
        Button button = new Button(context);
        button.setLayoutParams(new LinearLayout.LayoutParams((int) (width / 7), (int) row));
        button.setText(s);
        button.setBackgroundResource(R.color.transparent);
        button.setSingleLine();
        button.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        button.setTextSize(0, size / 22);
        T.set(button, context);
        button.setTextColor(Color.WHITE);
        return button;
    }

    private View spinner() {
        AppCompatSpinner spinner = new AppCompatSpinner(context);
        spinner.setGravity(Gravity.RIGHT);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        ((LinearLayout.LayoutParams) spinner.getLayoutParams()).gravity = Gravity.RIGHT;
        ((LinearLayout.LayoutParams) spinner.getLayoutParams()).topMargin = (int) (dimen[1] / 30);
        ((LinearLayout.LayoutParams) spinner.getLayoutParams()).bottomMargin = (int) (dimen[1] / 30);
        ((LinearLayout.LayoutParams) spinner.getLayoutParams()).rightMargin = (int) (dimen[1] / 30);
        spinner.setAdapter(new SpinnerAdaptor(context, 0, new ArrayList<String>(Arrays.asList(month_array)), dimen));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position + 1;
                if (layout != null) {
                    layout.removeView(layout.findViewById(CALENDAR_ID));
                    layout.addView(getColumn());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(month - 1);
        return spinner;
    }

    private View getColumn() {
        counter = 0;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setId(CALENDAR_ID);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (6 * row)));
        layout.addView(base(+1));
        layout.addView(base(+2));
        layout.addView(base(+3));
        layout.addView(base(+4));
        layout.addView(base(+5));
        layout.addView(base(+6));
        return layout;
    }

    private View base(int i) {
        LinearLayout base = new LinearLayout(context);
        base.setOrientation(LinearLayout.HORIZONTAL);
        base.setGravity(Gravity.CENTER_VERTICAL);
        base.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) row));
        base.setId(i);
        fillBase(base, getCount(month, i), i);
        return base;
    }

    private int getCount(int month, int i) {
        int row = 0;
        switch (month) {
            case 1:
                row = get1(i);
                break;
            case 2:
                row = get2(i);
                break;
            case 3:
                row = get3(i);
                break;
            case 4:
                row = get4(i);
                break;
            case 5:
                row = get5(i);
                break;
            case 6:
                row = get6(i);
                break;
            case 7:
                row = get7(i);
                break;
            case 8:
                row = get8(i);
                break;
            case 9:
                row = get9(i);
                break;
            case 10:
                row = get10(i);
                break;
            case 11:
                row = get11(i);
                break;
            case 12:
                row = get12(i);
                break;
        }
        return row;
    }

    private void fillBase(LinearLayout base, int count, int row) {
        counter = count + counter;
        if (row == +1) {
            for (int i = 0; i < count; i++) {
                base.addView(ball(false, counter - i));
            }
            for (int i = 0; i < 7 - count; i++) {
                base.addView(ball(true, 0));
            }
        } else {
            for (int i = 0; i < 7 - count; i++) {
                base.addView(ball(true, 0));
            }
            for (int i = 0; i < count; i++) {
                base.addView(ball(false, counter - i));
            }
        }
    }

    private View ball(boolean isNull, final int i) {
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-2, (int) row, 1f));
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        final Button b = new Button(context);
        b.setSingleLine(true);
        b.setTextColor(Color.WHITE);
        b.setText(i + "");
        b.setTextSize(0, circle_size / 4 + circle_size / 10);
        T.set(b, context);
        b.setLayoutParams(new LinearLayout.LayoutParams((int) circle_size, (int) circle_size));

        boolean flag = check(i);
        if (flag) {
            b.setBackgroundResource(R.drawable.calendar_true);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (last_pressed == null) {
                        last_pressed = b;
                    } else {
                        last_pressed.setBackgroundResource(R.drawable.calendar_true);
                        last_pressed.setTextColor(context.getResources().getColor(R.color.white));
                        last_pressed = b;
                    }
                    b.setBackgroundResource(R.drawable.calendar_pressed);
                    b.setTextColor(context.getResources().getColor(R.color.black));
                    accept.setTag(new DateModel(i, month, "", getString(month), new SolarCalendar().get().getYear()));
                }
            });
        } else {
            b.setBackgroundResource(R.color.transparent);
        }

        layout.addView(b);

        if (isNull) {
            layout.setVisibility(View.INVISIBLE);
        }
        if (accept != null && accept.getTag() != null) {
            DateModel date = (DateModel) accept.getTag();
            if (date.getDay() == i && month == date.getMonth()) {
                if (last_pressed == null) {
                    last_pressed = b;
                } else {
                    last_pressed.setBackgroundResource(R.drawable.calendar_true);
                    last_pressed.setTextColor(context.getResources().getColor(R.color.white));
                    last_pressed = b;
                }
                b.setBackgroundResource(R.drawable.calendar_pressed);
                b.setTextColor(context.getResources().getColor(R.color.black));
                accept.setTag(new DateModel(i, month, "", getString(month), new SolarCalendar().get().getYear()));
            }
        }
        return layout;
    }

    private String getString(int month) {
        String strMonth = null;
        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }
        return strMonth;
    }

    private boolean check(int i) {
        ArrayList<DateModel> data;
        if (from) {
            data = new ArrayList<>();
            DateModel model = new SolarCalendar().get();
            if (month < model.getMonth() || (month == model.getMonth() && i <= model.getDay())) {
                data.add(new DateModel(i, month, "", getString(month), model.getYear()));
            }
        } else {
            data = new DataManager(context).getRange();
            data.add(new SolarCalendar().get());
        }
        for (DateModel date : data) {
            if (month == date.getMonth() && date.getDay() == i) {
                return true;
            }
        }
        return false;
    }

    private int get8(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 7;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 2;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get9(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 5;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 4;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get10(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 3;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 6;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get11(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 1;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 7;
                break;
            case +6:
                row = 1;
                break;
        }
        return row;
    }

    private int get12(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 6;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 2;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get6(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 5;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 5;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get7(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 2;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 7;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get5(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 1;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 7;
                break;
            case +6:
                row = 2;
                break;
        }
        return row;
    }

    private int get4(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 4;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 6;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get3(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 7;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 3;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get2(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 3;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 7;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }

    private int get1(int i) {
        int row = 0;
        switch (i) {
            case +1:
                row = 6;
                break;
            case +2:
                row = 7;
                break;
            case +3:
                row = 7;
                break;
            case +4:
                row = 7;
                break;
            case +5:
                row = 4;
                break;
            case +6:
                row = 0;
                break;
        }
        return row;
    }
}
