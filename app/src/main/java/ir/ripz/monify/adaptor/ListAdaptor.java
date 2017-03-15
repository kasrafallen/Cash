package ir.ripz.monify.adaptor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import ir.ripz.monify.R;
import ir.ripz.monify.init.main.DailyList;
import ir.ripz.monify.model.DutyModel;
import ir.ripz.monify.util.T;

public class ListAdaptor extends ArrayAdapter<DutyModel> {
    private ArrayList<DutyModel> objects;
    private Context context;
    private float[] dimen;
    private DailyList.OptionMenu optionMenu;

    public ListAdaptor(Context context, int resource, ArrayList<DutyModel> objects, float[] dimen, DailyList.OptionMenu optionMenu) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.dimen = dimen;
        this.optionMenu = optionMenu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DutyModel model = objects.get(position);
        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new AbsListView.LayoutParams(-1, (int) (dimen[1] / 10)));
        layout.addView(line());
        layout.addView(title(model));
        layout.addView(price(model));
        layout.addView(button(position));
        return layout;
    }

    private View button(final int id) {
        AppCompatImageButton button = new AppCompatImageButton(context);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int) (dimen[1] / 12), (int) (dimen[1] / 12));
        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        button.setLayoutParams(p);
        button.setSupportBackgroundTintList(getState());
        button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        button.setImageResource(R.drawable.ic_navigation_more_vert);
        button.setFocusable(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionMenu.onLong(id);
            }
        });
        return button;
    }

    private ColorStateList getState() {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        Color.LTGRAY,
                        context.getResources().getColor(R.color.transparent),
                }
        );
    }

    private View price(DutyModel model) {
        TextView tv1 = new TextView(context);
        tv1.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        tv1.setText(getValue(model.getDuty()) + "");
        tv1.append(" تومان ");
        T.set(tv1, context);
        tv1.setTextColor(Color.DKGRAY);
        tv1.setAlpha(0.8f);
        tv1.setTextSize(0, dimen[1] / 45);
        ((RelativeLayout.LayoutParams) tv1.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) tv1.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) tv1.getLayoutParams()).leftMargin = (int) (dimen[0] / 5);
        tv1.setSingleLine();
        return tv1;
    }

    private String getValue(long duty) {
        return NumberFormat.getNumberInstance().format(duty);
    }

    private View title(DutyModel model) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        tv.setText(model.getTitle());
        T.set(tv, context);
        tv.setTextColor(Color.DKGRAY);
        tv.setAlpha(0.8f);
        tv.setTextSize(0, dimen[1] / 50);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).rightMargin = (int) (dimen[0] / 20);
        tv.setSingleLine();
        return tv;
    }

    private View line() {
        View view = new View(context);
        view.setBackgroundColor(Color.DKGRAY);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int) (dimen[0] - dimen[0] / 20 - dimen[0] / 20), (int) (dimen[1] / 550));
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(p);
        return view;
    }
}
