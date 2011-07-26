package jp.android.TakamiChie.Android.TTSSignal.util;

import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class TTS {

  private static final String TAG = "TTSSignal";

  private static TTS instance = new TTS();

  private boolean initialized = false;

  public TextToSpeech mTTS;

  public static TTS getInstance() {
    return instance;
  }

  /**
   * TexttoSpeechエンジンを初期化する
   *
   * @param context
   *          処理を実施するコンテキストオブジェクト
   */
  public void initialize(final Context context, final Runnable finished) {
    // TODO TTS未インストール時の処理
    mTTS = new TextToSpeech(context, new OnInitListener() {

      @Override
      public void onInit(int status) {
        try {
          if (status == TextToSpeech.SUCCESS) {
            // TODO インストール済み言語の選択
            int res = mTTS.setLanguage(Locale.US);
            if (res == TextToSpeech.LANG_MISSING_DATA
                || res == TextToSpeech.LANG_NOT_SUPPORTED) {
              throw new Exception("Language is not avaiable");
            } else {
              Log.d(TAG, "TTS Engine On");
              initialized = true;
              if (finished != null)
              {
                Log.d(TAG, "Finished Call");
                finished.run();
              }
            }
          } else {
            throw new Exception("Could not initialize TextToSpeech.");
          }
        } catch (Exception e) {
          Log.e(TAG, e.getMessage());
          e.printStackTrace();
        }
      }
    });
  }

  public boolean isInitialized() {
    return initialized;
  }

  /**
   * 現在の時間および分のおおよその値を読み上げる
   */
  public void speakAbout30Time() {
    Calendar calendar = Calendar.getInstance();
    int h = calendar.get(Calendar.HOUR);
    int m = calendar.get(Calendar.MINUTE);
    if (mTTS.isSpeaking()) {
      mTTS.stop();
    }
    if (m > 29) {
      m = 30;
    } else {
      m = 0;
    }
    String speak = String.format("%d:%02d o'clock now", h, m);
    Log.i(TAG, speak);
    mTTS.speak(speak, TextToSpeech.QUEUE_FLUSH, null);
  }

  /**
   * 現在の時間および分を読み上げる
   */
  public void speakCurrentTime() {
    Calendar calendar = Calendar.getInstance();
    int h = calendar.get(Calendar.HOUR);
    int m = calendar.get(Calendar.MINUTE);
    if (mTTS.isSpeaking()) {
      mTTS.stop();
    }

    String speak = String.format("%d:%02d o'clock now", h, m);
    Log.i(TAG, speak);
    mTTS.speak(speak, TextToSpeech.QUEUE_FLUSH, null);

  }

  public boolean isSpeak() {
    return mTTS.isSpeaking();
  }
}
