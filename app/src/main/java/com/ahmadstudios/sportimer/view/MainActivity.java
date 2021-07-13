package com.ahmadstudios.sportimer.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.ahmadstudios.sportimer.R;

/**
 * Класс получает данные от пользователя — количество минут и секунд для таймеров подхода и
 * отдыха, а также количество подходов. Затем создаётся новая активность, в которой будет
 * отображаться сам таймер, в эту активность передаются полученные от пользователя данные.
 */
public class MainActivity extends AppCompatActivity {
    private NumberPicker workMinNumPicker;
    private NumberPicker workSecNumPicker;
    private NumberPicker restMinNumPicker;
    private NumberPicker restSecNumPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        EditText setsEditText = findViewById(R.id.setsEditText);
        workMinNumPicker = findViewById(R.id.workMinNumPicker);
        workSecNumPicker = findViewById(R.id.workSecNumPicker);
        restMinNumPicker = findViewById(R.id.restMinNumPicker);
        restSecNumPicker = findViewById(R.id.restSecNumPicker);
        setRangesPickers();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                if (!setsEditText.getText().toString().equals(""))
                    intent.putExtra("sets", Integer.parseInt(setsEditText.getText().toString()));
                intent.putExtra("workMinutes", workMinNumPicker.getValue());
                intent.putExtra("workSeconds", workSecNumPicker.getValue());
                intent.putExtra("restMinutes", restMinNumPicker.getValue());
                intent.putExtra("restSeconds", restSecNumPicker.getValue());
                startActivity(intent);
            }
        });
    }

    private void setRangesPickers() {
        workMinNumPicker.setMinValue(0);
        workMinNumPicker.setMaxValue(59);
        workSecNumPicker.setMinValue(0);
        workSecNumPicker.setMaxValue(59);
        restMinNumPicker.setMinValue(0);
        restMinNumPicker.setMaxValue(59);
        restSecNumPicker.setMinValue(0);
        restSecNumPicker.setMaxValue(59);
    }
}