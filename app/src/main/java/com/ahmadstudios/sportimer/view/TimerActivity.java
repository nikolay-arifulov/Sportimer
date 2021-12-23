package com.ahmadstudios.sportimer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmadstudios.sportimer.controller.ControllerImpl;
import com.ahmadstudios.sportimer.R;
import com.ahmadstudios.sportimer.controller.Controller;
import com.ahmadstudios.sportimer.model.SportTimer;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * Данная активность создаёт объект Controller, передаёт в него команду start вместе с данными от
 * пользователя. Кроме того, активность подписывается на обновление модели (SportTimer). Во время
 * обновления модели происходит получение данных из неё, перерисовка ProgressBar и отображение
 * оставшегося времени в соответствующем TextView.
 */
public class TimerActivity extends AppCompatActivity implements Observer, BaseView {

    private ProgressBar timerProgressBar;

    private TextView timerTextView;

    private TextView workRestTextView;

    private SportTimer sportTimer;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        int sets = getIntent().getIntExtra("sets", 1);
        int workMinutes = getIntent().getIntExtra("workMinutes", 0);
        int workSeconds = getIntent().getIntExtra("workSeconds", 0);
        int restMinutes = getIntent().getIntExtra("restMinutes", 0);
        int restSeconds = getIntent().getIntExtra("restSeconds", 0);

        timerTextView = findViewById(R.id.timerTextView);
        workRestTextView = findViewById(R.id.workRestTextView);
        timerProgressBar = findViewById(R.id.timerProgressBar);
        timerProgressBar.setRotation(-90f);

        controller = new ControllerImpl(this);
        if (sportTimer != null) {
            sportTimer.addObserver(this);
        }
        controller.start(sets, workMinutes, workSeconds, restMinutes, restSeconds);
    }

    @Override
    public void setSportTimer(SportTimer sportTimer) {
        this.sportTimer = sportTimer;
    }

    @Override
    public void update(Observable o, Object isWork) {
        int minutes = sportTimer.getMinutesUntilFinished();
        int seconds = sportTimer.getSecondsUntilFinished();
        int currentSet = sportTimer.getCurrentSet();

        timerProgressBar.setMax((int) sportTimer.getMilliseconds());
        timerProgressBar.setProgress((int) sportTimer.getMillisUntilFinished());
        timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));

        if ((boolean) isWork){
            workRestTextView.setText(String.format(Locale.getDefault(), "%s %d", getResources().getString(R.string.work), currentSet));
        } else {
            workRestTextView.setText(R.string.rest);
        }

        if (currentSet > sportTimer.getSets()) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sportTimer.deleteObserver(this);
        controller.stop();
    }
}
