package ir.ripz.monify.view.chart.model;

import android.graphics.Typeface;

import ir.ripz.monify.view.chart.model.*;

/**
 * Base interface for all chart data models.
 */
public interface ChartData {

    /**
     * Updates data by scale during animation.
     *
     * @param scale value from 0 to 1.0
     */
    public void update(float scale);

    /**
     * Inform data that animation finished(data should be update with scale 1.0f).
     */
    public void finish();

    /**
     * @see #setAxisXBottom(ir.ripz.monify.view.chart.model.Axis)
     */
    public ir.ripz.monify.view.chart.model.Axis getAxisXBottom();

    /**
     * Set horizontal axis at the bottom of the chart. Pass null to remove that axis.
     *
     * @param axisX
     */
    public void setAxisXBottom(ir.ripz.monify.view.chart.model.Axis axisX);

    /**
     * @see #setAxisYLeft(ir.ripz.monify.view.chart.model.Axis)
     */
    public ir.ripz.monify.view.chart.model.Axis getAxisYLeft();

    /**
     * Set vertical axis on the left of the chart. Pass null to remove that axis.
     *
     * @param axisY
     */
    public void setAxisYLeft(ir.ripz.monify.view.chart.model.Axis axisY);

    /**
     * @see #setAxisXTop(ir.ripz.monify.view.chart.model.Axis)
     */
    public ir.ripz.monify.view.chart.model.Axis getAxisXTop();

    /**
     * Set horizontal axis at the top of the chart. Pass null to remove that axis.
     *
     * @param axisX
     */
    public void setAxisXTop(ir.ripz.monify.view.chart.model.Axis axisX);

    /**
     * @see #setAxisYRight(ir.ripz.monify.view.chart.model.Axis)
     */
    public ir.ripz.monify.view.chart.model.Axis getAxisYRight();

    /**
     * Set vertical axis on the right of the chart. Pass null to remove that axis.
     *
     * @param axisY
     */
    public void setAxisYRight(ir.ripz.monify.view.chart.model.Axis axisY);

    /**
     * Returns color used to draw value label text.
     */
    public int getValueLabelTextColor();

    /**
     * Set value label text color, by default Color.WHITE.
     */
    public void setValueLabelsTextColor(int labelsTextColor);

    /**
     * Returns text size for value label in SP units.
     */
    public int getValueLabelTextSize();

    /**
     * Set text size for value label in SP units.
     */
    public void setValueLabelTextSize(int labelsTextSize);

    /**
     * Returns Typeface for value labels.
     *
     * @return Typeface or null if Typeface is not set.
     */
    public Typeface getValueLabelTypeface();

    /**
     * Set Typeface for all values labels.
     *
     * @param typeface
     */
    public void setValueLabelTypeface(Typeface typeface);

    /**
     * @see #setValueLabelBackgroundEnabled(boolean)
     */
    public boolean isValueLabelBackgroundEnabled();

    /**
     * Set whether labels should have rectangle background. Default is true.
     */
    public void setValueLabelBackgroundEnabled(boolean isValueLabelBackgroundEnabled);

    /**
     * @see #setValueLabelBackgroundAuto(boolean)
     */
    public boolean isValueLabelBackgroundAuto();

    /**
     * Set false if you want to set custom color for all value labels. Default is true.
     */
    public void setValueLabelBackgroundAuto(boolean isValueLabelBackgrountAuto);

    /**
     * @see #setValueLabelBackgroundColor(int)
     */
    public int getValueLabelBackgroundColor();

    /**
     * Set value labels background. This value is used only if isValueLabelBackgroundAuto returns false. Default is
     * green.
     */
    public void setValueLabelBackgroundColor(int valueLabelBackgroundColor);
}
