package ir.ripz.monify.view.chart.animation;

import ir.ripz.monify.view.chart.animation.*;

public interface PieChartRotationAnimator {

    public static final int FAST_ANIMATION_DURATION = 200;

    public void startAnimation(float startAngle, float angleToRotate);

    public void cancelAnimation();

    public boolean isAnimationStarted();

    public void setChartAnimationListener(ir.ripz.monify.view.chart.animation.ChartAnimationListener animationListener);

}
