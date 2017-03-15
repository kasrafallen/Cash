package ir.ripz.monify.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.ripz.monify.R;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.util.T;

public class ChooseAlert {
    public interface ChooseBack {
        void onDone(ArrayList<InterestModel> models);
    }

    private Context context;
    private float[] dimen;
    private ArrayList<ChooseModel> all;
    private ArrayList<InterestModel> users;
    private LinearLayout box;
    private AlertDialog dialog;
    private ChooseBack call;

    public ChooseAlert(Activity context, float[] dimen, ChooseBack call, ArrayList<InterestModel> interest) {
        this.context = context;
        this.dimen = dimen;
        this.all = getList();
        this.call = call;
        this.users = interest;
    }

    private ArrayList<ChooseModel> getList() {
        ArrayList<ChooseModel> list = new ArrayList<>();
        list.add(new ChooseModel(R.mipmap.taxi, new InterestModel(Color.parseColor("#facf0e"), "تاکسی", 1)));
        list.add(new ChooseModel(R.mipmap.food, new InterestModel(Color.parseColor("#F940BE"), "فست فود", 2)));
        list.add(new ChooseModel(R.mipmap.www, new InterestModel(Color.parseColor("#1763dc"), "اینترنت", 3)));
        list.add(new ChooseModel(R.mipmap.iphone, new InterestModel(Color.parseColor("#23e246"), "شارژ موبایل", 4)));
        list.add(new ChooseModel(R.mipmap.gas_station, new InterestModel(Color.parseColor("#fac02d"), "بنزین", 5)));
        list.add(new ChooseModel(R.mipmap.rent, new InterestModel(Color.parseColor("#38bf9d"), "اجاره مسکن", 6)));
        return list;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setView(getView());
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_style;
        dialog.show();
    }

    private View getView() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LinearLayout box = new LinearLayout(context);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setLayoutParams(new LinearLayout.LayoutParams((int) dimen[0], (int) dimen[1]));
        box.addView(title());
        box.addView(description());
        box.addView(thinLine());
        box.addView(generateView());
        box.addView(thinLine());
        box.addView(accept());

        layout.addView(box);
        return layout;
    }

    private View accept() {
        AppCompatButton accept = new AppCompatButton(context);
        accept.setSingleLine();
        accept.setTextColor(Color.WHITE);
        T.set(accept, context);
        accept.setText("تایید");
        accept.setTextSize(0, dimen[1] / 45);
        accept.setSupportBackgroundTintList(getState());
        accept.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 2), (int) (dimen[1] / 10)));
        ((LinearLayout.LayoutParams) accept.getLayoutParams()).gravity = Gravity.CENTER;
        ((LinearLayout.LayoutParams) accept.getLayoutParams()).setMargins(0, (int) (dimen[1] / 40), 0, (int) (dimen[1] / 40));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSend();
            }
        });
        return accept;
    }

    private void doSend() {
        for (int i = 0; i < box.getChildCount(); i++) {
            View view = box.getChildAt(i);
            InterestModel model = (InterestModel) view.getTag();
            if (view.getTag() != null) {
                boolean flag = false;
                for (InterestModel h : users) {
                    if (h.getId() == (i + 1) || h.getName().equalsIgnoreCase(model.getName())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    users.add(model);
                }
            } else {
                ArrayList<InterestModel> copy = new ArrayList<>(users);
                for (InterestModel h : users) {
                    if (h.getId() == (i + 1)) {
                        copy.remove(h);
                        break;
                    }
                }
                users = new ArrayList<>(copy);
            }
        }
        call.onDone(users);
        dialog.dismiss();
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

    private View generateView() {
        ScrollView scroll = new ScrollView(context);
        scroll.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1));
        scroll.addView(createItems());
        return scroll;
    }

    private View createItems() {
        box = new LinearLayout(context);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setLayoutParams(new ScrollView.LayoutParams(-1, -2));
        for (int i = 0; i < all.size(); i++) {
            box.addView(item(i));
        }
        return box;
    }

    private View item(int i) {
        final ChooseModel model = all.get(i);

        final RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        layout.setBackgroundResource(R.drawable.selector_interest);

        Button header = new Button(context);
        header.setClickable(false);
        header.setId(+8888777);
        header.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[1] / 16), (int) (dimen[1] / 16)));
        ((RelativeLayout.LayoutParams) header.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ((RelativeLayout.LayoutParams) header.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) header.getLayoutParams()).setMargins((int) (dimen[0] / 20), (int) (dimen[1] / 30)
                , (int) (dimen[0] / 20), (int) (dimen[1] / 30));
        header.setBackgroundResource(model.getLogo());

        View view = new View(context);
        view.setAlpha(0.6f);
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 550)));
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setBackgroundColor(Color.LTGRAY);

        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setSingleLine();
        textView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.LEFT_OF, +8888777);
        ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.RIGHT_OF, +8880777);
        ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        textView.setGravity(Gravity.RIGHT);
        textView.setText(model.getModel().getName());
        T.set(textView, context);
        textView.setTextSize(0, dimen[1] / 45);

        final AppCompatCheckBox box = new AppCompatCheckBox(context);
        box.setId(+8880777);
        box.setClickable(false);
        box.setChecked(false);
        box.setFocusable(false);
        box.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        ((RelativeLayout.LayoutParams) box.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) box.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) box.getLayoutParams()).setMargins((int) (dimen[0] / 20), 0, (int) (dimen[0] / 20), 0);

        for (InterestModel m : users) {
            if (m.getId() == model.getModel().getId()) {
                box.setChecked(true);
                layout.setTag(model.getModel());
                break;
            }
        }

        layout.addView(header);
        layout.addView(view);
        layout.addView(box);
        layout.addView(textView);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.getTag() == null) {
                    box.setChecked(true);
                    layout.setTag(model.getModel());
                } else {
                    box.setChecked(false);
                    layout.setTag(null);
                }
            }
        });
        return layout;
    }

    private View description() {
        TextView tv1 = new TextView(context);
        T.set(tv1, context);
        tv1.setAlpha(0.3f);
        tv1.setTextColor(context.getResources().getColor(R.color.theme));
        tv1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        tv1.setTextSize(0, dimen[1] / 60);
        tv1.setPadding(0, 0, (int) (dimen[0] / 20), (int) (dimen[0] / 15));
        tv1.setGravity(Gravity.RIGHT);
        tv1.setText("شما میتوانید یک یا چند مورد را انتخاب کنید.");
        return tv1;
    }

    private View title() {
        TextView tv = new TextView(context);
        T.set(tv, context);
        tv.setTextColor(context.getResources().getColor(R.color.theme));
        tv.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        tv.setTextSize(0, dimen[1] / 28);
        tv.setPadding(0, (int) (dimen[0] / 15), (int) (dimen[0] / 20), 0);
        tv.setGravity(Gravity.RIGHT);
        tv.setText("هزینه های ثابت پیش فرض");
        return tv;
    }

    private View thinLine() {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (dimen[1] / 550)));
        view.setBackgroundColor(Color.GRAY);
        view.setAlpha(0.1f);
        return view;
    }

    private class ChooseModel {
        private InterestModel model;
        private int logo;

        public ChooseModel(int logo, InterestModel model) {
            this.logo = logo;
            this.model = model;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public InterestModel getModel() {
            return model;
        }

        public void setModel(InterestModel model) {
            this.model = model;
        }
    }
}
