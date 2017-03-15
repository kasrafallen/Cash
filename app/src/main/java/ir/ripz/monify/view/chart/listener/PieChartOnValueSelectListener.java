package ir.ripz.monify.view.chart.listener;

import ir.ripz.monify.view.chart.model.SliceValue;

public interface PieChartOnValueSelectListener extends ir.ripz.monify.view.chart.listener.OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
