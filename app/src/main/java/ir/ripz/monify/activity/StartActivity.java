package ir.ripz.monify.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import ir.ripz.monify.R;
import ir.ripz.monify.instance.ProfileManager;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.model.ProfileModel;
import ir.ripz.monify.util.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StartActivity extends AppIntro2 {
    public ProfileModel model = new ProfileModel();
    public boolean isOpen = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void init(Bundle savedInstanceState) {
        int color = getResources().getColor(R.color.theme_lite);
        addSlide(AppIntroFragment.newInstance("عنوان", "توضیحات", R.mipmap.face, color));
        addSlide(AppIntroFragment.newInstance("عنوان", "توضیحات", R.mipmap.face, color));
        addSlide(InterestFragment.newInstance());
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        showSelector();
    }

    private void showSelector() {
        if (model.getInterest() == null || model.getInterest().size() == 0) {
            Toast.makeText(this, "not enough", Toast.LENGTH_LONG).show();
        } else if (isOpen) {
            Toast.makeText(this, "close current task", Toast.LENGTH_LONG).show();
        } else {
            new Util(this).setStarted();
            InterestModel interestModel = new InterestModel(getResources()
                    .getColor(R.color.theme_extra_color), "هزینه های جانبی", InterestModel.DEFAULT_ID);
            interestModel.setIs_default(true);
            model.getInterest().add(interestModel);
            new ProfileManager(this).setProfile(model);
            startActivity(new Intent(this, LaunchActivity.class));
            finish();
        }
    }

    @Override
    public void onSlideChanged() {

    }
}
