package ir.ripz.monify.init.main;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

import ir.ripz.monify.R;
import ir.ripz.monify.activity.MainActivity;
import ir.ripz.monify.adaptor.ListAdaptor;
import ir.ripz.monify.instance.DataManager;
import ir.ripz.monify.instance.ProfileManager;
import ir.ripz.monify.model.DateModel;
import ir.ripz.monify.model.DutyModel;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.model.ProfileModel;
import ir.ripz.monify.util.SolarCalendar;
import ir.ripz.monify.util.T;
import ir.ripz.monify.view.CalendarAlert;

public class RangeList implements AdapterView.OnItemLongClickListener {
    private static final int LIST_ID = +98210546;
    private static final int TOOLBAR_ID = +7854630;
    private static final int FACTOR_ID = +1442808;

    private MainActivity context;
    private RelativeLayout view;
    private float dimen[];
    private float width;
    private ListAdaptor adaptor;
    private ArrayList<DutyModel> list;
    private ListView listView;

    private TextInputLayout input;
    private AppCompatEditText input_text;

    private TextInputLayout input1;
    private AppCompatEditText input_text1;
    private AlertDialog dialog;
    private TextView factor;

    private AppCompatButton min;
    private AppCompatButton max;

    private AlertDialog alert_min;
    private AlertDialog alert_max;

    public RangeList(MainActivity context, float[] dimen) {
        this.context = context;
        this.dimen = dimen;
        this.width = dimen[0] - dimen[0] / 20 - dimen[0] / 20;
        this.list = new ArrayList<>();
        this.adaptor = new ListAdaptor(context, 0, list, dimen, new DailyList.OptionMenu() {
            @Override
            public void onLong(int position) {
                RangeList.this.onLong(position);
            }
        });
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
        view.addView(header());
        view.addView(shadow());
        view.addView(list());
        view.addView(factor());
        this.alert_min = new CalendarAlert(context, dimen, CalendarAlert.FROM_RANGE).get(new CalendarAlert.Select() {
            @Override
            public void onSelect(DateModel date) {
                min.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                min.setTag(date);
                check();
            }
        });
        this.alert_max = new CalendarAlert(context, dimen, CalendarAlert.FROM_RANGE).get(new CalendarAlert.Select() {
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
            setData((DateModel) min.getTag(), (DateModel) max.getTag());
        }
    }

    private View range() {
        LinearLayout layout = new LinearLayout(context);
        layout.setId(+985);
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

    private View shadow() {
        View view = new View(context);
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 250)));
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.BELOW, TOOLBAR_ID);
        view.setBackgroundResource(R.drawable.gradient);
        return view;
    }

    private View header() {
        Toolbar toolbar = new Toolbar(context);
        toolbar.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 15)));
        ((RelativeLayout.LayoutParams) toolbar.getLayoutParams()).addRule(RelativeLayout.BELOW, +985);
        toolbar.setId(TOOLBAR_ID);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setContentInsetsRelative(0, 0);

        TextView tv = new TextView(context);
        tv.setLayoutParams(new Toolbar.LayoutParams(-2, -2));
        tv.setText("نوع هزینه");
        T.set(tv, context);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(0, dimen[1] / 45);
        tv.setGravity(Gravity.RIGHT);
        ((Toolbar.LayoutParams) tv.getLayoutParams()).gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        ((Toolbar.LayoutParams) tv.getLayoutParams()).rightMargin = (int) (dimen[0] / 20);
        tv.setSingleLine();

        TextView tv1 = new TextView(context);
        tv1.setLayoutParams(new Toolbar.LayoutParams(-2, -2));
        tv1.setText("مبلغ");
        T.set(tv1, context);
        tv1.setTextColor(Color.BLACK);
        tv1.setTextSize(0, dimen[1] / 45);
        tv1.setGravity(Gravity.RIGHT);
        ((Toolbar.LayoutParams) tv1.getLayoutParams()).gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
        ((Toolbar.LayoutParams) tv1.getLayoutParams()).leftMargin = (int) (dimen[0] / 5);
        tv1.setSingleLine();

        toolbar.addView(tv);
        toolbar.addView(tv1);
        return toolbar;
    }

    private View factor() {
        RelativeLayout layout = new RelativeLayout(context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 9));
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.setLayoutParams(p);
        layout.setId(FACTOR_ID);

        TextView tv = new TextView(context);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        tv.setText("جمع کل مبلغ در بازه");
        T.set(tv, context);
        tv.setTextColor(Color.DKGRAY);
        tv.setAlpha(0.8f);
        tv.setTextSize(0, dimen[1] / 40);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).rightMargin = (int) (dimen[0] / 20);
        tv.setSingleLine();

        factor = new TextView(context);
        factor.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        T.set(factor, context);
        factor.setTextColor(Color.DKGRAY);
        factor.setAlpha(0.8f);
        factor.setTextSize(0, dimen[1] / 35);
        ((RelativeLayout.LayoutParams) factor.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) factor.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) factor.getLayoutParams()).leftMargin = (int) (dimen[0] / 20);
        factor.setSingleLine();

        layout.addView(line());
        layout.addView(tv);
        layout.addView(factor);
        return layout;
    }

    private View line() {
        View view = new View(context);
        view.setBackgroundColor(Color.DKGRAY);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int) width, (int) (dimen[1] / 550));
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        view.setLayoutParams(p);
        return view;
    }

    private View list() {
        listView = new ListView(context);
        listView.setId(LIST_ID);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, -1);
        p.addRule(RelativeLayout.ABOVE, FACTOR_ID);
        p.addRule(RelativeLayout.BELOW, TOOLBAR_ID);
        listView.setLayoutParams(p);
        listView.setAdapter(adaptor);
        listView.setDivider(null);
        listView.setOnItemLongClickListener(this);
        listView.setVisibility(View.GONE);
        return listView;
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
        if (factor != null && list != null && adaptor != null && listView != null) {
            factor.setText(NumberFormat.getNumberInstance().format(new DataManager(context).getRangeAll(mi, ma)) + "");
            factor.append(" تومان");
            list.clear();
            list.addAll(new DataManager(context).getRangeItems(mi, ma));
            adaptor.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        onLong(position);
        return false;
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

    private View getEditView(DutyModel position) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));

        layout.addView(tvTitle(position));
        layout.addView(tvPrice(position));
        layout.addView(button(position));
        return layout;
    }

    private View button(final DutyModel model) {
        final AppCompatButton accept = new AppCompatButton(context);
        accept.setSingleLine();
        accept.setTextColor(Color.WHITE);
        T.set(accept, context);
        accept.setText("ثبت ویرایش");
        accept.setTextSize(0, dimen[1] / 45);
        accept.setSupportBackgroundTintList(getState());
        accept.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 3), (int) (dimen[1] / 10)));
        ((LinearLayout.LayoutParams) accept.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_text.getText().length() > 0 && !input_text.getText().toString().equals(NumberFormat.getNumberInstance().format(0))) {
                    if (input_text1.getText().length() > 0) {
                        model.setInterest(getInterestFromText(input_text1.getText().toString()));
                        model.setTitle(input_text1.getText().toString());
                        model.setDuty(getValueFromText(input_text.getText().toString()));

                        new DataManager(context).edit(model);
                        if (min.getTag() != null && max.getTag() != null) {
                            setData((DateModel) min.getTag(), (DateModel) max.getTag());
                        }
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        Toast.makeText(context, "تغییرات ذخیره شد.", Toast.LENGTH_SHORT).show();
                    } else {
                        input1.setError("هزینه ای وارد نشده است.");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                input1.setError("");
                            }
                        }, 2000);
                    }
                } else {
                    input.setError("نوع هزینه را انتخاب کنید.");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            input.setError("");
                        }
                    }, 2000);
                }
            }
        });
        return accept;
    }

    private View tvPrice(DutyModel model) {
        input = new TextInputLayout(context);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams((int) (dimen[0] / 2), -2);
        inputParams.gravity = Gravity.CENTER_HORIZONTAL;
        input.setLayoutParams(inputParams);
        input.setPadding(0, (int) (dimen[1] / 20), 0, (int) (dimen[1] / 20));

        input_text = new AppCompatEditText(context);
        input_text.setLayoutParams(new TextInputLayout.LayoutParams(-1, -2));
        input_text.setSingleLine();
        input_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        input_text.setTextSize(0, dimen[1] / 37);
        input_text.setHint("هزینه را وارد کنید. (به تومان)");
        input_text.setText(NumberFormat.getNumberInstance().format(model.getDuty()) + "");
        input_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        T.set(input_text, context);
        input_text.addTextChangedListener(new TextWatcher() {
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
        });

        input.addView(input_text);
        return input;
    }

    private View tvTitle(DutyModel position) {
        input1 = new TextInputLayout(context);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams((int) (dimen[0] / 2), -2);
        inputParams.gravity = Gravity.CENTER_HORIZONTAL;
        input1.setLayoutParams(inputParams);
        input1.setPadding(0, (int) (dimen[1] / 20), 0, (int) (dimen[1] / 20));

        input_text1 = new AppCompatEditText(context);
        input_text1.setLayoutParams(new TextInputLayout.LayoutParams(-1, -2));
        input_text1.setSingleLine();
        input_text1.setEllipsize(TextUtils.TruncateAt.END);
        input_text1.setTextSize(0, dimen[1] / 37);
        input_text1.setHint("Set the name");
        input_text1.setText(position.getTitle());
        T.set(input_text1, context);

        input1.addView(input_text1);
        return input1;
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

    private void onLong(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setItems(new CharSequence[]{"ویرایش", "حذف"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        showEdit(position);
                        break;
                    case 1:
                        showDelete(position);
                        break;
                }
            }
        }).show();
    }

    private void showDelete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setTitle("حذف").setMessage("آیا مایلید این مورد را حذف کنید؟")
                .setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DataManager(context).delete(list.get(position));
                        if (min.getTag() != null && max.getTag() != null) {
                            setData((DateModel) min.getTag(), (DateModel) max.getTag());
                        }
                    }
                }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showEdit(final int position) {
        DutyModel model = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setTitle("ویرایش").setView(getEditView(model));
        dialog = builder.create();
        dialog.show();
    }


    private InterestModel getInterestFromText(String text) {
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
}
