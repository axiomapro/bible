package ru.niv.bible.basic.component;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;

import java.util.Locale;

public class Speech {

    private TextToSpeech textToSpeech;
    private boolean support;

    public void setPitch(float pitch) {
        textToSpeech.setPitch(pitch);
    }

    public void setSpeed(float speed) {
        textToSpeech.setSpeechRate(speed);
    }

    public void setLanguage(String language,String country) {
        textToSpeech.setLanguage(new Locale(language,country));
    }

    public void check(String language,String country,Context context) {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale = new Locale(language,country);
                int result = textToSpeech.setLanguage(locale);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.d(Static.log, "TTS: this language is not supported");
                } else support = true;
            } else Log.d(Static.log, "TTS: error");
        });
    }

    public void speak(String text,int mode) {
        textToSpeech.speak(text,mode,null,"audio");
    }

    public void stop() {
        textToSpeech.speak("",TextToSpeech.QUEUE_FLUSH,null,"audio");
        textToSpeech.stop();
    }

    public void destroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    public boolean isSupport() {
        return support;
    }

    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }

}

