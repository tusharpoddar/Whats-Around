package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Context context = getApplicationContext();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.lookingfor);
        mediaPlayer.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSpeechInput();
            }
        }, 2000);





    }

    public boolean onTouchEvent(MotionEvent e) {
        Intent intent = new Intent(Second.this, DetectorActivity.class);
        startActivity(intent);
        return false;
    }

    public void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }
    String res = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("Result", result.toString());
//                    String fileName = "data.txt";
//
//                    boolean first = false;
//                    boolean third = false;
//
//                    // reading the file
//                    File file = new File(R.);
//
//                    Log.d("resssss", file.getAbsolutePath());
//                    BufferedReader br = null;
//
//                    try {
//                        br = new BufferedReader(new FileReader(file));
//                        Log.d("res-BR", String.valueOf(br));
//
//                    } catch (FileNotFoundException e) {
//                        Log.d("res-BR", " errorrrr");
//                        e.printStackTrace();
//                    }


                    String st = "";
                    String answer = "";
//                    while (true) {
//                        try {
//                            if (br == null || !((st = br.readLine()) != null)) break;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        // checking of the word is there or not
//                        for (String word : result) {
//                            if (st.charAt(0) == word.charAt(0) && !third) {
//                                first = true;
//                                if (st.charAt(1) == word.charAt(1) && st.charAt(2) == word.charAt(2)) {
//                                    third = true;
//                                    answer = word;
//                                }
//                            }
//                        }
//                    }
                    res = result.get(0);
                }

                // shared preferences kya chutiyap hai bc
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("word", res); // Storing string

                // this statement adds the data intot the store.
                editor.apply();

        }
    }


}
