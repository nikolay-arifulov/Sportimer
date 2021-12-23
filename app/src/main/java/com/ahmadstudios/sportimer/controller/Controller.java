package com.ahmadstudios.sportimer.controller;

public interface Controller {

    void start(int sets, int workMinutes, int workSeconds, int restMinutes, int restSeconds);

    void stop();
}
