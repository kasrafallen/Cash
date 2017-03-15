package ir.ripz.monify.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import ir.ripz.monify.init.InitMain;
import ir.ripz.monify.util.SolarCalendar;
import ir.ripz.monify.util.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    public float[] dimen;
    public DrawerLayout drawer;
    public Toolbar toolbar;
    public InitMain initMain;

    public int counter = -1;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dimen = new Util(this).getDimen();
        this.initMain = new InitMain(this);
        setContentView(initMain.create(dimen));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setDate(new SolarCalendar().toString());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        } else if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
            return;
        } else if (counter != 0) {
            initMain.setDisplay(0);
            return;
        }
        super.onBackPressed();
    }

    public void setDate(String date) {
        if (initMain != null)
            initMain.setText(date);
    }

    public void sync() {
        switch (counter) {
            case -1:
                initMain.dailyGraph.setData();
                break;
            case 0:
                initMain.dailyGraph.setData();
                break;
            case 1:
                initMain.dailyList.setData();
                break;
            case 2:
                initMain.allGraph.setData(null, null);
                break;
            case 3:
                initMain.allList.setData(null, null);
                break;
        }
    }
}
