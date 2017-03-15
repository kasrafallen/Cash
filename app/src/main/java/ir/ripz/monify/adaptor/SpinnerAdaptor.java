package ir.ripz.monify.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.ripz.monify.util.T;

public class SpinnerAdaptor extends ArrayAdapter<String> {
    private ArrayList<String> objects;
    private Context context;
    private float[] dimen;

    public SpinnerAdaptor(Context context, int resource, ArrayList<String> objects, float[] dimen) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.dimen = dimen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;
        if (convertView == null) {
            convertView = createView(true);
            viewHolder = new Holder();
            viewHolder.tv = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        viewHolder.tv.setText(objects.get(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;
        if (convertView == null) {
            convertView = createView(false);
            viewHolder = new Holder();
            viewHolder.tv = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        viewHolder.tv.setText(objects.get(position));
        return convertView;
    }

    private View createView(boolean flag) {
        TextView tv = new TextView(context);
        AbsListView.LayoutParams p = new AbsListView.LayoutParams(-1, -2);
        tv.setLayoutParams(p);
        T.set(tv, context);
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine();
        tv.setTextSize(0, dimen[1] / 30);
        if (flag) {
            tv.setTextColor(Color.WHITE);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return tv;
    }

    static class Holder {
        private TextView tv;
    }
}
