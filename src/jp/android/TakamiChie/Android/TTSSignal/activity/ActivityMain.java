package jp.android.TakamiChie.Android.TTSSignal.activity;

import jp.android.TakamiChie.Android.TTSSignal.R;
import jp.android.TakamiChie.Android.TTSSignal.util.TTS;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final TTS tts = TTS.getInstance();

    final Button buttonTestExecute = (Button) findViewById(R.id.buttonTestExecute);
    buttonTestExecute.setEnabled(false);

    buttonTestExecute.setOnClickListener(new ButtonTestExecute_OnClick());
    tts.initialize(this, new Runnable() {

      @Override
      public void run() {
        if (tts.isInitialized()){
          buttonTestExecute.setEnabled(true);
        }
      }
    });
    ((Button) findViewById(R.id.buttonSettings))
        .setOnClickListener(new ButtonSettings_OnClick());
  }

  private final class ButtonSettings_OnClick implements OnClickListener {
    @Override
    public void onClick(View v) {
      startActivity(new Intent(ActivityMain.this, ActivitySettings.class));
    }
  }

  private final class ButtonTestExecute_OnClick implements OnClickListener {
    @Override
    public void onClick(View v) {
      TTS.getInstance().speakCurrentTime();

    }
  }

}
