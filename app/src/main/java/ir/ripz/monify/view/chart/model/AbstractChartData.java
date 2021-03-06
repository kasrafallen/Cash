package ir.ripz.monify.view.chart.model;

import android.graphics.Color;
import android.graphics.Typeface;

import ir.ripz.monify.view.chart.model.*;
import ir.ripz.monify.view.chart.model.Axis;
import ir.ripz.monify.view.chart.util.ChartUtils;

/**
 * Base class for most chart data models.
 */
public abstract class AbstractChartData implements ir.ripz.monify.view.chart.model.ChartData {
    public static final int DEFAULT_TEXT_SIZE_SP = 12;
    protected ir.ripz.monify.view.chart.model.Axis axisXBottom;
    protected ir.ripz.monify.view.chart.model.Axis axisYLeft;
    protected ir.ripz.monify.view.chart.model.Axis axisXTop;
    protected ir.ripz.monify.view.chart.model.Axis axisYRight;
    protected int valueLabelTextColor = Color.WHITE;
    protected int valueLabelTextSize = DEFAULT_TEXT_SIZE_SP;
    protected Typeface valueLabelTypeface;

    /**
     * If true each value label will have background rectangle
     */
    protected boolean isValueLabelBackgroundEnabled = true;

    /**
     * If true and {@link #isValueLabelBackgroundEnabled} is true each label will have background rectangle and that
     * rectangle will be filled with color specified for given value.
     */
    protected boolean isValueLabelBackgrountAuto = true;

    /**
     * If {@link #isValueLabelBackgroundEnabled} is true and {@link #isValueLabelBackgrountAuto} is false each label
     * will have background rectangle and that rectangle will be filled with this color. Helpful if you want all labels
     * to have the same background color.
     */
    protected int valueLabelBackgroundColor = ChartUtils.darkenColor(ChartUtils.DEFAULT_DARKEN_COLOR);

    public AbstractChartData() {

    }

    /**
     * Copy constructor for deep copy.
     *
     * @param data
     */
    public AbstractChartData(AbstractChartData data) {
        if (null != data.axisXBottom) {
            this.axisXBottom = new ir.ripz.monify.view.chart.model.Axis(data.axisXBottom);
        }
        if (null != data.axisXTop) {
            this.axisXTop = new ir.ripz.monify.view.chart.model.Axis(data.axisXTop);
        }
        if (null != data.axisYLeft) {
            this.axisYLeft = new ir.ripz.monify.view.chart.model.Axis(data.axisYLeft);
        }
        if (null != data.axisYRight) {
            this.axisYRight = new ir.ripz.monify.view.chart.model.Axis(data.axisYRight);
        }
        this.valueLabelTextColor = data.valueLabelTextColor;
        this.valueLabelTextSize = data.valueLabelTextSize;
        this.valueLabelTypeface = data.valueLabelTypeface;
    }

    @Override
    public ir.ripz.monify.view.chart.model.Axis getAxisXBottom() {
        return axisXBottom;
    }

    @Override
    public void setAxisXBottom(ir.ripz.monify.view.chart.model.Axis axisX) {
        this.axisXBottom = axisX;
    }

    @Override
    public ir.ripz.monify.view.chart.model.Axis getAxisYLeft() {
        return axisYLeft;
    }

    @Override
    public void setAxisYLeft(ir.ripz.monify.view.chart.model.Axis axisY) {
        this.axisYLeft = axisY;
    }

    @Override
    public ir.ripz.monify.view.chart.model.Axis getAxisXTop() {
        return axisXTop;
    }

    @Override
    public void setAxisXTop(ir.ripz.monify.view.chart.model.Axis axisX) {
        this.axisXTop = axisX;
    }

    @Override
    public ir.ripz.monify.view.chart.model.Axis getAxisYRight() {
        return axisYRight;
    }

    @Override
    public void setAxisYRight(Axis axisY) {
        this.axisYRight = axisY;
    }

    @Override
    public int getValueLabelTextColor() {
        return valueLabelTextColor;
    }

    @Override
    public void setValueLabelsTextColor(int valueLabelTextColor) {
        this.valueLabelTextColor = valueLabelTextColor;
    }

    @Override
    public int getValueLabelTextSize() {
        return valueLabelTextSize;
    }

    @Override
    public void setValueLabelTextSize(int valueLabelTextSize) {
        this.valueLabelTextSize = valueLabelTextSize;
    }

    @Override
    public Typeface getValueLabelTypeface() {
        return valueLabelTypeface;
    }

    @Override
    public void setValueLabelTypeface(Typeface typeface) {
        this.valueLabelTypeface = typeface;
    }

    @Override
    public boolean isValueLabelBackgroundEnabled() {
        return isValueLabelBackgroundEnabled;
    }

    @Override
    public void setValueLabelBackgroundEnabled(boolean isValueLabelBackgroundEnabled) {
        this.isValueLabelBackgroundEnabled = isValueLabelBackgroundEnabled;
    }

    @Override
    public boolean isValueLabelBackgroundAuto() {
        return isValueLabelBackgrountAuto;
    }

    @Override
    public void setValueLabelBackgroundAuto(boolean isValueLabelBackgrountAuto) {
        this.isValueLabelBackgrountAuto = isValueLabelBackgrountAuto;
    }

    @Override
    public int getValueLabelBackgroundColor() {
        return valueLabelBackgroundColor;
    }

    @Override
    public void setValueLabelBackgroundColor(int valueLabelBackgroundColor) {
        this.valueLabelBackgroundColor = valueLabelBackgroundColor;
    }

}