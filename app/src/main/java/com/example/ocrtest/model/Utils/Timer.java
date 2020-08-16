package com.example.ocrtest.model.Utils;

public class Timer {
    private long deltaTime;
    private long startTime;
    private long endTime;
    private float millis = -1;

    private boolean running = false;

    public void start() {
        running = true;
        startTime = System.nanoTime();
    }


    public void end() {
        if (!running)
            return;
        endTime = System.nanoTime();
        deltaTime = endTime - startTime;
        millis = (float) deltaTime / (1000 * 1000);

        running = false;
    }

    private long getDeltaTime() {
        return deltaTime;
    }

    public float getMillis() {
        return this.millis;
    }


}
