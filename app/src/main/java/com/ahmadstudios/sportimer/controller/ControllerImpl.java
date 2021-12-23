package com.ahmadstudios.sportimer.controller;

import android.content.Context;

import com.ahmadstudios.sportimer.SoundPlayer;
import com.ahmadstudios.sportimer.model.SportTimer;
import com.ahmadstudios.sportimer.view.BaseView;

/**
 * Класс, который создаёт объекты SportTimer и SoundPlayer.
 * Он передаёт соответствующие команды от пользователя вместе с данными в модель.
 */
public class ControllerImpl implements Controller {

    private final SportTimer sportTimer;

    public ControllerImpl(BaseView view) {
        sportTimer = new SportTimer();
        view.setSportTimer(sportTimer);
        new SoundPlayer((Context) view, sportTimer.getSound());
    }

    @Override
    public void start(int sets, int workMinutes, int workSeconds, int restMinutes, int restSeconds) {
        sportTimer.start(sets, workMinutes, workSeconds, restMinutes, restSeconds);
    }

    @Override
    public void stop() {
        sportTimer.stop();
    }
}
