package com.ahmadstudios.sportimer.controller;

public interface IController {
    void start(int sets, int workMinutes, int workSeconds, int restMinutes, int restSeconds);
    void stop();
}
