package com.company;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimerClass {
    JLabel []timerCounter;

    int secondsPassed=0;

    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            Methods.setText7Bit(timerCounter,secondsPassed);
        }
    };

    public TimerClass(JLabel []timerCounter) {
        this.timerCounter=timerCounter;
    }

    public void start(){
        timer.scheduleAtFixedRate(timerTask,1000,1000);
    }

    public void stop(){
        timer.cancel();
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }
}