package com.ahmadstudios.sportimer.model;

import com.ahmadstudios.sportimer.R;

import android.os.CountDownTimer;

import java.util.Observable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс — модель. Последовательно запускает таймеры обратного отсчёта для подхода и отдыха.
 * Процесс повторяется несколько раз, в зависимости от количества подходов. Во время работы
 * таймера уведомляются подписчики. В классе есть внутренний класс Sound, который уведомляет
 * подписчиков о необходимости воспроизвести звук или прекратить воспроизведение.
 */

@NoArgsConstructor
@Getter
@Setter
public class SportTimer extends Observable {

    private final Sound sound = new Sound();

    private long milliseconds;

    private long millisUntilFinished;

    private int minutesUntilFinished;

    private int secondsUntilFinished;

    private int currentSet;

    private int sets;

    private int workMinutes;

    private int workSeconds;

    private int restMinutes;

    private int restSeconds;

    private CountDownTimer countDownTimer;

    private boolean isWork;

    public static class Sound extends Observable {
        public void play(int rawId) {
            setChanged();
            notifyObservers(rawId);
            clearChanged();
        }

        public void stop() {
            setChanged();
            notifyObservers(-1);
            clearChanged();
        }
    }

    public void start(int sets, int workMinutes, int workSeconds, int restMinutes, int restSeconds) {
        this.sets = sets;
        this.workMinutes = workMinutes;
        this.workSeconds = workSeconds;
        this.restMinutes = restMinutes;
        this.restSeconds = restSeconds;
        currentSet = 1;
        startWorkTimer();
    }

    public void stop() {
        countDownTimer.cancel();
        sound.stop();
    }

    private void startWorkTimer() {
        if (currentSet <= sets) {
            milliseconds = workMinutes * 60000L + workSeconds * 1000L;
            isWork = true;
            sound.play(R.raw.gong);

            countDownTimer = new CountDownTimer(milliseconds, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tick(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    finish();
                    startRestTimer();
                }
            }.start();
        }
    }

    private void startRestTimer() {
        milliseconds = restMinutes * 60000L + restSeconds * 1000L;
        isWork = false;

        countDownTimer = new CountDownTimer(milliseconds, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                tick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                currentSet++;
                finish();
                startWorkTimer();
            }
        }.start();
    }

    private void tick(long millisUntilFinished) {
        minutesUntilFinished = (int) millisUntilFinished / 60000;
        secondsUntilFinished = (int) (millisUntilFinished / 1000) % 60;
        this.millisUntilFinished = millisUntilFinished;

        if (millisUntilFinished < 10200 && millisUntilFinished > 10180) {
            sound.play(R.raw.tick);
        }

        setChanged();
        notifyObservers(isWork);
        clearChanged();
    }

    private void finish() {
        millisUntilFinished = 0;
        setChanged();
        notifyObservers(isWork);
        clearChanged();
    }
}
