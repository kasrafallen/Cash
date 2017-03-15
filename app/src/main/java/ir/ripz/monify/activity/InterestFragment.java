package ir.ripz.monify.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ir.ripz.monify.R;
import ir.ripz.monify.instance.ProfileManager;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.model.ProfileModel;
import ir.ripz.monify.util.T;
import ir.ripz.monify.util.Util;
import ir.ripz.monify.view.ChooseAlert;

public class InterestFragment extends Fragment {
    private Activity context;
    private ProfileModel profile;
    private float[] dimen;
    private float function_box;
    private float title_size;
    private RelativeLayout view;
    private LinearLayout item_box;
    private RelativeLayout fab_view;

    private static final int TITLE_ID = +548248;
    private static final int ITEMS_ID = +9426468;
    private static final int FUNCTION_ID = +244684;
    private static final int LINE_ID = +2488999;
    private static final int COLOR_PICKER = +9802117;
    private static final int TEXT_ID = +9825842;
    private static final int FAB_ID = +98274200;
    private static final int FAB_ADD = +63952871;
    private static final int FAB_CHOOSE = +82827292;

    public static InterestFragment newInstance() {
        InterestFragment sampleSlide = new InterestFragment();

        Bundle args = new Bundle();
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    public InterestFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity();
        this.dimen = new Util(context).getDimen();
        this.profile = new ProfileManager(context).getProfile();
        if (profile == null) {
            profile = new ProfileModel();
            profile.setInterest(new ArrayList<InterestModel>());
        }
        return createView();
    }

    public View createView() {
        this.title_size = dimen[1] / 8;
        this.function_box = dimen[1] / 3 + dimen[1] / 12;

        if (view != null) {
            return view;
        }
        view = new RelativeLayout(context);
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        view.setBackgroundResource(R.color.theme_dark);
        view.addView(title(true));
        view.addView(shadow());
        view.addView(items());
        view.addView(fab());
        view.setClickable(true);
        return view;
    }

    private View shadow() {
        View view = new View(context);
        view.setId(LINE_ID);
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (dimen[1] / 250)));
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.BELOW, TITLE_ID);
        view.setBackgroundResource(R.drawable.gradient);
        return view;
    }

    private View fab() {
        final FloatingActionButton button = new FloatingActionButton(context);
        button.setId(FAB_ID);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-2, -2);
        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        if (Build.VERSION.SDK_INT >= 21) {
            p.setMargins((int) (dimen[0] / 30), 0, 0, (int) (dimen[0] / 30));
        }
        button.setLayoutParams(p);
        button.setTag("");
        button.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.theme_fab)));
        button.setImageResource(R.drawable.ic_content_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getTag() != null && view.findViewById(FUNCTION_ID) == null) {
                    button.setTag(null);
                    view.addView(createFABs());
                    button.setClickable(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.setClickable(true);
                        }
                    }, 400);
                }
            }
        });
        return button;
    }

    public void removeFABs(final FloatingActionButton fab) {
        fab.setTag("");
        fab.setClickable(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(400);
        fab_view.startAnimation(alphaAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.removeView(fab_view);
                fab.setClickable(true);
            }
        }, 400);
    }

    private View createFABs() {
        fab_view = new RelativeLayout(context) {
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(400);
                this.startAnimation(alphaAnimation);
            }
        };
        fab_view.setBackgroundResource(R.color.theme_fade);
        fab_view.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        fab_view.addView(getFab(FAB_ADD));
        fab_view.addView(getFab(FAB_CHOOSE));
        fab_view.setClickable(true);
        fab_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFABs((FloatingActionButton) context.findViewById(FAB_ID));
            }
        });
        return fab_view;
    }

    private View getFab(int id) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        TextView tv = new TextView(context);
        tv.setTextColor(Color.WHITE);
        tv.setSingleLine();
        tv.setTextSize(0, dimen[1] / 40);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).addRule(RelativeLayout.RIGHT_OF, id);
        ((RelativeLayout.LayoutParams) tv.getLayoutParams()).leftMargin = (int) (dimen[0] / 20);
        T.set(tv, context);

        FloatingActionButton b = (FloatingActionButton) fab();
        b.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        ((RelativeLayout.LayoutParams) b.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) b.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        b.setId(id);
        b.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));

        float margin = dimen[1] / 40;
        switch (id) {
            case FAB_ADD:
                b.setImageResource(R.mipmap.apartment);
                tv.setText("وارد کردن هزینه");
                if (Build.VERSION.SDK_INT >= 21) {
                    ((RelativeLayout.LayoutParams) layout.getLayoutParams()).setMargins((int) (margin), 0, 0, (int) (margin * 6));
                } else {
                    ((RelativeLayout.LayoutParams) layout.getLayoutParams()).setMargins(0, 0, 0, (int) (margin * 6));
                }
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFABs((FloatingActionButton) view.findViewById(FAB_ID));
                        showFunction(profile.getInterest().size(), false);
                    }
                });
                break;
            case FAB_CHOOSE:
                b.setImageResource(R.mipmap.beach);
                if (Build.VERSION.SDK_INT >= 21) {
                    ((RelativeLayout.LayoutParams) layout.getLayoutParams()).setMargins((int) (margin), 0, 0, (int) (margin * 12));
                } else {
                    ((RelativeLayout.LayoutParams) layout.getLayoutParams()).setMargins(0, 0, 0, (int) (margin * 12));
                }
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFABs((FloatingActionButton) view.findViewById(FAB_ID));
                        openChooser();
                    }
                });
                tv.setText("انتخاب از هزینه های پیش فرض");
                break;
        }

        layout.addView(b);
        layout.addView(tv);
        return layout;
    }

    private void openChooser() {
        new ChooseAlert(context, dimen, new ChooseAlert.ChooseBack() {
            @Override
            public void onDone(ArrayList<InterestModel> models) {
                profile.setInterest(models);
                setItems();
            }
        }, profile.getInterest()).show();
    }

    private View items() {
        ScrollView scrollView = new ScrollView(context);
        scrollView.setId(ITEMS_ID);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(-1, -2);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        p.addRule(RelativeLayout.BELOW, LINE_ID);
        p.bottomMargin = (int) (dimen[1] / 6);
        scrollView.setLayoutParams(p);

        item_box = new LinearLayout(context);
        item_box.setOrientation(LinearLayout.VERTICAL);
        item_box.setLayoutParams(new ScrollView.LayoutParams(-1, -2));
        ((ScrollView.LayoutParams) item_box.getLayoutParams()).gravity = Gravity.CENTER_HORIZONTAL;
        setItems();
        scrollView.addView(item_box);
        return scrollView;
    }

    private View blank() {
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (dimen[1] / 7)));
        tv.setText("هزینه ویژه ای وارد نشده است. برای شروع + کنید.");
        tv.setTextColor(Color.WHITE);
        T.set(tv, context);
        return tv;
    }

    private View item(final int i) {
        final RelativeLayout layout = new RelativeLayout(context);
        layout.setTag(profile.getInterest().get(i));
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        layout.setBackgroundResource(R.drawable.selector_interest);

        Button header = new Button(context);
        header.setClickable(false);
        header.setId(+8888777);
        header.setText((i + 1) + "");
        header.setTextColor(Color.WHITE);
        header.setGravity(Gravity.CENTER);
        header.setTextSize(0, dimen[1] / 50);
        header.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[1] / 14), (int) (dimen[1] / 14)));
        ((RelativeLayout.LayoutParams) header.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ((RelativeLayout.LayoutParams) header.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) header.getLayoutParams()).setMargins((int) (dimen[0] / 22), (int) (dimen[1] / 37)
                , (int) (dimen[0] / 22), (int) (dimen[1] / 37));
        header.setBackgroundResource(R.drawable.circle_drawable);
        ((GradientDrawable) header.getBackground()).setColor(profile.getInterest().get(i).getColor());

        int size = (int) (dimen[1] / 14 + dimen[0] / 11);
        View view = new View(context);
        view.setAlpha(0.6f);
        view.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[0] - size - size / 2), (int) (dimen[1] / 550)));
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).leftMargin = size / 2;
        view.setBackgroundColor(Color.LTGRAY);

        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        textView.setSingleLine();
        textView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.LEFT_OF, +8888777);
        ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.RIGHT_OF, +88528225);
        ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        textView.setGravity(Gravity.RIGHT);
        textView.setText(profile.getInterest().get(i).getName());
        T.set(textView, context);
        textView.setTextSize(0, dimen[1] / 35);

        AppCompatImageButton delete = new AppCompatImageButton(context);
        delete.setId(+88528225);
        delete.setScaleType(ImageView.ScaleType.CENTER_CROP);
        delete.setImageResource(R.drawable.ic_navigation_close);
        delete.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[1] / 12), (int) (dimen[1] / 12)));
        ((RelativeLayout.LayoutParams) delete.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        ((RelativeLayout.LayoutParams) delete.getLayoutParams()).addRule(RelativeLayout.CENTER_VERTICAL);
        ((RelativeLayout.LayoutParams) delete.getLayoutParams()).setMargins((int) (dimen[0] / 20), 0, (int) (dimen[0] / 20), 0);
        delete.setSupportBackgroundTintList(new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        context.getResources().getColor(R.color.theme),
                        context.getResources().getColor(R.color.transparent),
                }
        ));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(i, layout);
            }
        });

        layout.addView(header);
        layout.addView(view);
        layout.addView(textView);
        layout.addView(delete);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFunction(i, true);
            }
        });
        return layout;
    }

    private void deleteDialog(final int i, final RelativeLayout layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("حذف هزینه ثابت").setCancelable(true).setMessage("با انتخاب بلی این مورد از لیست حذف میشود, ادامه میدهید؟")
                .setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        profile.getInterest().remove(layout.getTag());
                        item_box.removeView(layout);
                        if (isAdded() && context instanceof MainActivity) {
                            new ProfileManager(context).setProfile(profile);
                        }
                    }
                }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void showFunction(int i, boolean flag) {
        if (view.findViewById(FUNCTION_ID) != null) {
            if (flag) {
                removeFunction();
            } else {
                return;
            }
        }
        final RelativeLayout layout = new RelativeLayout(context) {
            @Override
            protected void onAttachedToWindow() {
                super.onAttachedToWindow();
                AlphaAnimation animation = new AlphaAnimation(0, 1);
                animation.setDuration(300);
                this.startAnimation(animation);
            }
        };
        layout.setId(FUNCTION_ID);
        layout.setLayoutParams(new RelativeLayout.LayoutParams((int) (dimen[0] - dimen[0] / 6), (int) function_box));
        ((RelativeLayout.LayoutParams) layout.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(functionColorPicker(i, layout));
        layout.addView(functionText(i, layout, flag));
        layout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        view.addView(layout);
        if (isAdded() && context instanceof StartActivity) {
            ((StartActivity) context).isOpen = true;
        }
    }

    private View functionText(int i, final RelativeLayout box, final boolean flag) {
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));

        if (view == null) {
            createView();
        }

        final TextInputLayout input = (TextInputLayout) tv(i);
        final EditText title = (EditText) input.findViewById(TEXT_ID);

        LinearLayout f_b = new LinearLayout(context);
        f_b.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        f_b.setOrientation(LinearLayout.HORIZONTAL);

        AppCompatButton accept = new AppCompatButton(context);
        accept.setSingleLine();
        accept.setTextColor(Color.WHITE);
        T.set(accept, context);
        accept.setText("تایید");
        accept.setTextSize(0, dimen[1] / 45);
        accept.setSupportBackgroundTintList(getState());
        accept.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 3), (int) (dimen[1] / 10)));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = title.getText().toString();
                boolean exist = false;
                if (text.length() > 0) {
                    for (InterestModel model : profile.getInterest()) {
                        if (model.getName().equalsIgnoreCase(text)) {
                            exist = true;
                            break;
                        }
                    }
                    if (!flag && exist) {
                        input.setError("موردی با این نام یافت شد!");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                input.setError("");
                            }
                        }, 2000);
                    } else {
                        box.removeView(layout);
                        box.setTag(text);
                        box.findViewById(COLOR_PICKER).setVisibility(View.VISIBLE);
                    }
                } else {
                    input.setError("چیزی وارد نشده است!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            input.setError("");
                        }
                    }, 2000);
                }
            }
        });
        AppCompatButton discard = new AppCompatButton(context);
        discard.setSingleLine();
        discard.setTextColor(Color.WHITE);
        T.set(discard, context);
        discard.setText("بازگشت");
        discard.setTextSize(0, dimen[1] / 45);
        discard.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 3), (int) (dimen[1] / 10)));
        discard.setSupportBackgroundTintList(getState());
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.findViewById(FUNCTION_ID) != null) {
                    removeFunction();
                    if (isAdded() && context instanceof StartActivity) {
                        ((StartActivity) context).isOpen = false;
                    }
                }
            }
        });

        f_b.addView(spaceShip());
        f_b.addView(discard);
        f_b.addView(spaceShip());
        f_b.addView(accept);
        f_b.addView(spaceShip());

        layout.addView(input);
        layout.addView(space());
        layout.addView(f_b);
        layout.addView(space());
        return layout;
    }

    private void removeFunction() {
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (view.findViewById(FUNCTION_ID) != null) {
            view.findViewById(FUNCTION_ID).startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.removeView(view.findViewById(FUNCTION_ID));
                }
            }, 500);
        }
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

    private View space() {
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 1));
        return space;
    }

    private View functionColorPicker(int i, RelativeLayout box) {
        LinearLayout layout = new LinearLayout(context);
        layout.setId(COLOR_PICKER);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) function_box));
        ((LinearLayout.LayoutParams) layout.getLayoutParams()).topMargin = (int) (dimen[1] / 100);
        ((LinearLayout.LayoutParams) layout.getLayoutParams()).bottomMargin = (int) (dimen[1] / 100);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout row1 = new LinearLayout(context);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        row1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        row1.addView(spaceShip());
        row1.addView(ball(Color.parseColor("#E91E63"), i, box));
        row1.addView(spaceShip());
        row1.addView(ball(Color.parseColor("#F44336"), i, box));
        row1.addView(spaceShip());
        row1.addView(ball(Color.parseColor("#9C27B0"), i, box));
        row1.addView(spaceShip());

        LinearLayout row2 = new LinearLayout(context);
        row2.setOrientation(LinearLayout.HORIZONTAL);
        row2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        row2.addView(spaceShip());
        row2.addView(ball(Color.parseColor("#2196F3"), i, box));
        row2.addView(spaceShip());
        row2.addView(ball(Color.parseColor("#3F51B5"), i, box));
        row2.addView(spaceShip());
        row2.addView(ball(Color.parseColor("#009688"), i, box));
        row2.addView(spaceShip());

        LinearLayout row3 = new LinearLayout(context);
        row3.setOrientation(LinearLayout.HORIZONTAL);
        row3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        row3.addView(spaceShip());
        row3.addView(ball(Color.parseColor("#4CAF50"), i, box));
        row3.addView(spaceShip());
        row3.addView(ball(Color.parseColor("#64FFDA"), i, box));
        row3.addView(spaceShip());
        row3.addView(ball(Color.parseColor("#FFEB3B"), i, box));
        row3.addView(spaceShip());

        layout.addView(space());
        layout.addView(row1);
        layout.addView(space());
        layout.addView(row2);
        layout.addView(space());
        layout.addView(row3);
        layout.addView(space());
        layout.setVisibility(View.GONE);
        return layout;
    }

    private View spaceShip() {
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 1f));
        return space;
    }

    private View ball(final int color, final int i, final RelativeLayout box) {
        AppCompatButton button = new AppCompatButton(context);
        button.setLayoutParams(new LinearLayout.LayoutParams((int) (function_box / 4), (int) (function_box / 4)));
        button.setBackgroundResource(R.drawable.color_picker);
        ((GradientDrawable) button.getBackground()).setColor(color);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) box.getTag();
                if (profile.getInterest().size() == i) {
                    profile.getInterest().add(new InterestModel());
                }
                InterestModel model = profile.getInterest().get(i);
                if (model.getId() == 0) {
                    model = new InterestModel(color, text, generateId());
                } else {
                    model = new InterestModel(color, text, model.getId());
                }
                profile.getInterest().set(i, model);
                setItems();
                removeFunction();
                if (isAdded() && context instanceof StartActivity) {
                    ((StartActivity) context).isOpen = false;
                }
            }
        });
        return button;
    }

    private int generateId() {
        int id = 0;
        do {
            id = new Random().nextInt();
            if (id >= 0 && id < 50) {
                id = id + 1000;
            }
        } while (!checkId(id));
        return id;
    }

    private boolean checkId(int id) {
        for (InterestModel interestModel : profile.getInterest()) {
            if (interestModel.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private void setItems() {
        item_box.removeAllViews();
        if (profile.getInterest().size() > 0) {
            item_box.addView(title(false));
            for (int i = 0; i < profile.getInterest().size(); i++) {
                if (!profile.getInterest().get(i).is_default()) {
                    item_box.addView(item(i));
                }
            }
        } else {
            item_box.addView(blank());
        }
        if (isAdded() && context instanceof StartActivity) {
            ((StartActivity) context).model = profile;
        }
        if (isAdded() && context instanceof MainActivity) {
            new ProfileManager(context).setProfile(profile);
        }
    }


    private View tv(int i) {
        TextInputLayout layout = new TextInputLayout(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        p.setMargins(0, (int) dimen[1] / 40, 0, (int) dimen[1] / 40);
        layout.setLayoutParams(p);
        layout.setPadding((int) (dimen[0] / 15), (int) dimen[1] / 40, (int) (dimen[0] / 15), (int) dimen[1] / 40);

        AppCompatEditText text = new AppCompatEditText(context);
        text.setId(TEXT_ID);
        text.setLayoutParams(new TextInputLayout.LayoutParams(-1, -2));
        text.setSingleLine();
        text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(35)});
        text.setTextSize(0, dimen[1] / 35);
        text.setLayoutParams(p);
        text.setHint("عنوان آیتم");
        T.set(text, context);
        if (profile.getInterest().size() > i) {
            text.setText(profile.getInterest().get(i).getName() + "");
        }

        layout.addView(text);
        layout.setErrorEnabled(true);
        return layout;
    }

    private View title(boolean flag) {
        if (flag) {
            TextView textView = new TextView(context);
            textView.setBackgroundResource(R.color.theme_lite);
            textView.setId(TITLE_ID);
            textView.setText("هزینه هایی  که فکر میکنید نیاز به مدیریت بیشتری دارند را وارد کنید");
            textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            textView.setTextSize(0, dimen[1] / 40);
            textView.setTextColor(Color.WHITE);
            textView.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) title_size));
            ((RelativeLayout.LayoutParams) textView.getLayoutParams()).setMargins(0, 0, 0, 0);
            ((RelativeLayout.LayoutParams) textView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_TOP);
            textView.setPadding((int) (dimen[0] / 20), 0, (int) (dimen[0] / 20), 0);
            T.set(textView, context);
            return textView;
        } else {
            TextView textView = new TextView(context);
            textView.setText("هزینه های وارد شده:");
            textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            textView.setTextSize(0, dimen[1] / 50);
            textView.setTextColor(Color.LTGRAY);
            textView.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (title_size / 2)));
            textView.setPadding((int) (dimen[0] / 20), (int) (dimen[1] / 100), (int) (dimen[0] / 20), (int) (dimen[1] / 100));
            T.set(textView, context);
            textView.setSingleLine();
            return textView;
        }
    }

    @Override
    public void onDestroy() {
        if (context != null && context instanceof MainActivity) {
            ((MainActivity) context).sync();
        }
        super.onDestroy();
    }
}
