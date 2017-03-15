package ir.ripz.monify.instance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import ir.ripz.monify.model.ProfileModel;

public class ProfileManager {
    private SharedPreferences pref;
    private final static String PREF_PROFILE = "PREF_PROFILE";

    public ProfileManager(Context context) {
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setProfile(ProfileModel model) {
        pref.edit().putString(PREF_PROFILE, new Gson().toJson(model)).apply();
    }

    public ProfileModel getProfile() {
        return new Gson().fromJson(pref.getString(PREF_PROFILE, null), ProfileModel.class);
    }
}
