package ir.ripz.monify.view.chart.listener;


import ir.ripz.monify.view.chart.model.Viewport;

public interface ViewportChangeListener {

    /**
     * Called when current viewport of chart changed. You should not modify that viewport.
     */
    public void onViewportChanged(Viewport viewport);

}
