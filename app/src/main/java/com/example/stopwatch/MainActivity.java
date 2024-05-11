package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton, pauseButton, resetButton;

    private boolean isTimerRunning = false;
    private long startTime = 0L;
    private Handler handler = new Handler();
    private long elapsedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(timerRunnable, 0);
            isTimerRunning = true;
        }
    }

    private void pauseTimer() {
        if (isTimerRunning) {
            handler.removeCallbacks(timerRunnable);
            isTimerRunning = false;
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    private void resetTimer() {
        if (!isTimerRunning) {
            elapsedTime = 0L;
            updateTimerText(0);
        }
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
            updateTimerText(elapsedTime);
            handler.postDelayed(this, 100); // update every 100 milliseconds
        }
    };

    private void updateTimerText(long timeInMillis) {
        int minutes = (int) (timeInMillis / 60000);
        int seconds = (int) (timeInMillis % 60000) / 1000;
        int milliseconds = (int) (timeInMillis % 1000) / 10;

        String timeString = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
        timerTextView.setText(timeString);
    }
}
