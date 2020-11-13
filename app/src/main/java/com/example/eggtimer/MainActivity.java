package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;
    TextView countdownTextVIew;
    boolean countdownActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    public void resetTimer() {

        countdownActive = false;
        seekbar.setEnabled(true);
        goButton.setText("GO!");
        countdownTextVIew.setText("0:30");
        seekbar.setProgress(30);
        countDownTimer.cancel();
        mediaPlayer.stop();

    }

    public void buttonClicked(View view) {

        if(countdownActive) {

            resetTimer();

        }
        else {

            countdownActive = true;
            seekbar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(seekbar.getProgress() * 1000 + 100, 1000) {

                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                }

                public void onFinish() {

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timersound);
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            resetTimer();
                        }
                    });
                }

            }.start();
        }
    }

    public void updateTimer(int progress){

        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);

        String secondsString = Integer.toString(seconds);

        if(secondsString.length() == 1)
        {
            secondsString = "0" + secondsString;
        }

        countdownTextVIew.setText(Integer.toString(minutes) + ":" + secondsString);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = findViewById(R.id.timerSeekBar);
        countdownTextVIew = findViewById(R.id.countdownTextView);
        goButton = findViewById(R.id.goButton);

        seekbar.setMax(600);
        seekbar.setProgress(30);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}