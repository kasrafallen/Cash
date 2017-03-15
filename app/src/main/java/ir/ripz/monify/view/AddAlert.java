package ir.ripz.monify.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;

import ir.ripz.monify.R;

public class AddAlert implements View.OnClickListener {
    private Context context;
    private float[] dimen;
    private AppCompatTextView text;
    private final static int row_1 = +145421;
    private final static int row_2 = +145422;
    private final static int row_3 = +145423;
    private final static int row_4 = +145424;
    private final static int row_5 = +145425;
    private final static int row_6 = +145426;
    private final static int row_7 = +145427;
    private final static int row_8 = +145428;
    private final static int row_9 = +145429;
    private final static int row_0 = +145420;

    private final static int row_plus = +2482248;
    private final static int row_divide = +828282;
    private final static int row_minus = +2727822;
    private final static int row_time = +7227872;
    private final static int row_dot = +9825641;
    private final static int row_equal = +654248;

    private double q_number;
    private double add_number = -1;
    private boolean math_function;
    private int current_function;
    private View current_function_view;

    public void show(Context context, float[] dimen) {
        this.context = context;
        this.dimen = dimen;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setView(getView());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_style;
        dialog.show();
    }

    private View getView() {
        LinearLayout dialog = new LinearLayout(context);
        dialog.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        dialog.setOrientation(LinearLayout.VERTICAL);

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) dimen[1]));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(space());
        layout.addView(input());
        layout.addView(space());
        layout.addView(row(row_1, row_2, row_3, row_plus));
        layout.addView(row(row_4, row_5, row_6, row_minus));
        layout.addView(row(row_7, row_8, row_9, row_time));
        layout.addView(row(row_dot, row_0, row_equal, row_divide));
        layout.addView(space());
        layout.addView(register());

        dialog.addView(layout);
        return dialog;
    }

    private View register() {
        return new Button(context);
    }

    private View row(int id1, int id2, int id3, int function) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-2, -2);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        layout.setLayoutParams(p);
        if (id1 != 0) {
            layout.addView(bundle(id1));
        } else {
            layout.addView(itemSpace());
        }
        if (id2 != 0) {
            layout.addView(bundle(id2));
        } else {
            layout.addView(itemSpace());
        }
        if (id3 != 0) {
            layout.addView(bundle(id3));
        } else {
            layout.addView(itemSpace());
        }
        layout.addView(bundle(function));
        return layout;
    }

    private View itemSpace() {
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 5), (int) (dimen[0] / 5)));
        return space;
    }

    private View space() {
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1));
        return space;
    }

    private View bundle(int id) {
        AppCompatButton button = new AppCompatButton(context);
        button.setLayoutParams(new LinearLayout.LayoutParams((int) (dimen[0] / 5), (int) (dimen[0] / 5)));
        button.setId(id);
        button.setTextColor(Color.BLACK);
        button.setTextSize(0, dimen[1] / 25);
        switch (id) {
            case row_0:
                button.setText(0 + "");
                break;
            case row_1:
                button.setText(1 + "");
                break;
            case row_2:
                button.setText(2 + "");
                break;
            case row_3:
                button.setText(3 + "");
                break;
            case row_4:
                button.setText(4 + "");
                break;
            case row_5:
                button.setText(5 + "");
                break;
            case row_6:
                button.setText(6 + "");
                break;
            case row_7:
                button.setText(7 + "");
                break;
            case row_8:
                button.setText(8 + "");
                break;
            case row_9:
                button.setText(9 + "");
                break;
            case row_plus:
                button.setText("+");
                break;
            case row_minus:
                button.setText("-");
                break;
            case row_divide:
                button.setText("÷");
                break;
            case row_time:
                button.setText("×");
                break;
            case row_dot:
                button.setText(".");
                break;
            case row_equal:
                button.setText("=");
                break;
        }
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        context.getResources().getColor(R.color.theme_pressed),
                        context.getResources().getColor(R.color.theme_base),
                }
        );
        button.setSupportBackgroundTintList(myColorStateList);
        button.setOnClickListener(this);
        return button;
    }

    private View input() {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams((int) dimen[0], -2));

        text = new AppCompatTextView(context);
        text.setLayoutParams(new RelativeLayout.LayoutParams((int) ((int) dimen[0] - dimen[0] / 10), -2));
        ((RelativeLayout.LayoutParams) text.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
        text.setTextSize(0, dimen[1] / 25);
        text.setTextColor(Color.BLACK);
        text.setSingleLine(true);
        text.setPadding((int) (dimen[0] / 20), (int) (dimen[0] / 40), (int) (dimen[0] / 20), (int) (dimen[0] / 40));
        text.setText("");
        text.setBackgroundResource(R.drawable.input_number);

        layout.addView(text);
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case row_0:
                append(0 + "", false);
                break;
            case row_1:
                append(1 + "", false);
                break;
            case row_2:
                append(2 + "", false);
                break;
            case row_3:
                append(3 + "", false);
                break;
            case row_4:
                append(4 + "", false);
                break;
            case row_5:
                append(5 + "", false);
                break;
            case row_6:
                append(6 + "", false);
                break;
            case row_7:
                append(7 + "", false);
                break;
            case row_8:
                append(8 + "", false);
                break;
            case row_9:
                append(9 + "", false);
                break;
            case row_dot:
                append(-1 + "", false);
                break;
            case row_plus:
                if (current_function_view == null || current_function_view != v) {
                    setCurrent(row_plus, v);
                } else {
                    removeCurrent(v);
                }
                break;
            case row_minus:
                if (current_function_view == null || current_function_view != v) {
                    setCurrent(row_minus, v);
                } else {
                    removeCurrent(v);
                }
                break;
            case row_divide:
                if (current_function_view == null || current_function_view != v) {
                    setCurrent(row_divide, v);
                } else {
                    removeCurrent(v);
                }
                break;
            case row_time:
                if (current_function_view == null || current_function_view != v) {
                    setCurrent(row_time, v);
                } else {
                    removeCurrent(v);
                }
                break;
            case row_equal:
                if (!math_function) {
                    double aDouble;
                    switch (current_function) {
                        case row_time:
                            if (add_number == -1) {
                                add_number = Double.valueOf(text.getText().toString());
                            }
                            append(String.valueOf((q_number * add_number)), true);
                            aDouble = Double.valueOf(text.getText().toString());
                            q_number = aDouble;
                            break;
                        case row_divide:
                            if (add_number == -1) {
                                add_number = Double.valueOf(text.getText().toString());
                            }
                            append(String.valueOf((q_number / add_number)), true);
                            aDouble = Double.valueOf(text.getText().toString());
                            q_number = aDouble;
                            break;
                        case row_minus:
                            if (add_number == -1) {
                                add_number = Double.valueOf(text.getText().toString());
                            }
                            append(String.valueOf((q_number - add_number)), true);
                            aDouble = Double.valueOf(text.getText().toString());
                            q_number = aDouble;
                            break;
                        case row_plus:
                            if (add_number == -1) {
                                add_number = Double.valueOf(text.getText().toString());
                            }
                            append(String.valueOf((q_number + add_number)), true);
                            aDouble = Double.valueOf(text.getText().toString());
                            q_number = aDouble;
                            break;
                    }
                    break;
                }
        }
    }

    private void removeCurrent(View v) {
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{
                        context.getResources().getColor(R.color.theme_pressed),
                        context.getResources().getColor(R.color.theme_base),
                }
        );
        ((AppCompatButton) v).setSupportBackgroundTintList(myColorStateList);
        current_function = 0;
        current_function_view = null;
        math_function = false;
        add_number = -1;
    }

    private void clear() {
        current_function = 0;
        current_function_view = null;
        math_function = false;
        add_number = -1;
        q_number = 0;
        text.setText("");
    }

    private void setCurrent(int id, View v) {
        if (text.getText().length() != 0) {
            if (current_function_view != null) {
                removeCurrent(current_function_view);
            }
            ColorStateList myColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_pressed},
                            new int[]{}
                    },
                    new int[]{
                            context.getResources().getColor(R.color.theme_pressed),
                            context.getResources().getColor(R.color.theme_lite),
                    }
            );
            ((AppCompatButton) v).setSupportBackgroundTintList(myColorStateList);
            current_function = id;
            current_function_view = v;
            if (text.getText().toString().length() > 0) {
                q_number = Double.valueOf(text.getText().toString());
            }
            math_function = true;
        }
    }

    private void append(String data, boolean flag) {
        if (data.endsWith(".0")) {
            data = data.substring(0, data.length() - 2);
        }
        if (math_function) {
            math_function = false;
            text.setText("");
        }
        if (flag) {
            text.setText("");
            text.append(data);
        } else {
            if (data.equals("-1")) {
                if (text.getText().toString().contains(".")) {
                    return;
                } else if (text.getText().length() == 0) {
                    text.append("0.");
                } else {
                    text.append(".");
                }
            } else {
                if (text.getText().toString().equals("0")) {
                    text.setText("");
                    text.append(data);
                } else {
                    text.append(data);
                }
            }
        }
    }
}
