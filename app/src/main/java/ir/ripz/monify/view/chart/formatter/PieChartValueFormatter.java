package ir.ripz.monify.view.chart.formatter;

import ir.ripz.monify.view.chart.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
