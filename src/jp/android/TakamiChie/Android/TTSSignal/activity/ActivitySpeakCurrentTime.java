package jp.android.TakamiChie.Android.TTSSignal.activity;

import jp.android.TakamiChie.Android.TTSSignal.R;
import jp.android.TakamiChie.Android.TTSSignal.util.TTS;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class ActivitySpeakCurrentTime extends Activity {

  private TTS tts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    tts = TTS.getInstance();
    tts.initialize(ActivitySpeakCurrentTime.this, new Runnable() {

      @Override
      public void run() {
        Log.i("TTSSignal", "Speach Current Time Only!");
        tts.speakCurrentTime();
        // 読み上げ処理が終わり次第終了
        Handler h = new Handler();
        h.post(new Runnable() {
          @Override
          public void run() {
            while(tts.isSpeak())
            {
              try {
                Thread.sleep(100);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              ActivitySpeakCurrentTime.this.finish();
            }
          }
        });
      }
    });
    this.setVisible(false);
  }

}
