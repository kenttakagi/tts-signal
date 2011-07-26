package jp.android.TakamiChie.Android.TTSSignal.activity;

import jp.android.TakamiChie.Android.TTSSignal.R;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ActivitySettings extends PreferenceActivity implements OnSharedPreferenceChangeListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO 自動生成されたメソッド・スタブ
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.pref_ttssignal);

    PackageManager pm = getPackageManager();
    switch(pm.getComponentEnabledSetting(new ComponentName(this, ActivitySpeakCurrentTime.class)))
    {
      case PackageManager.COMPONENT_ENABLED_STATE_DEFAULT:
      case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("enabledLongSearch", true);
        break;
      case PackageManager.COMPONENT_ENABLED_STATE_DISABLED:
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("enabledLongSearch", false);
        break;
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreference, String key) {
    if(key.equals("enabledLongSearch"))
    {
      PackageManager pm = getPackageManager();
      ComponentName cn = new ComponentName(ActivitySettings.this, ActivitySpeakCurrentTime.class);
      if(sharedPreference.getBoolean(key, false)){
        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
      }else{
        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
      }
    }
  }

}
