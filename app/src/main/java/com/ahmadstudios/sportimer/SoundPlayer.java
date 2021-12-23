package com.ahmadstudios.sportimer;

import android.content.Context;
import android.media.MediaPlayer;

import com.ahmadstudios.sportimer.model.SportTimer;

import java.util.Observable;
import java.util.Observer;

/**
 * Данный класс воспроизводит звуки. Отдельный класс для этой цели был создан потому что модель
 * не должна использовать классы Android — Context и MediaPlayer. Данный класс прослушивает
 * внутренний класс SportTimer.Sound. Последний, в случае необходимости воспроизвести звук,
 * уведомляет о своём изменении, передавая в качестве параметра id звукового файла. В случае
 * необходимости прекратить воспроизведение звука, в качестве параметра передаётся значение -1
 */
public class SoundPlayer implements Observer {

    private final Context context;

    private final SportTimer.Sound sound;

    private MediaPlayer mediaPlayer;

    public SoundPlayer(Context context, SportTimer.Sound sound) {
        this.context = context.getApplicationContext();
        this.sound = sound;
        this.sound.addObserver(this);
    }

    @Override
    public void update(Observable o, Object rawID) {
        if ((int) rawID > 0) {
            mediaPlayer = MediaPlayer.create(context, (int)rawID);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            sound.deleteObserver(this);
        }
    }
}
