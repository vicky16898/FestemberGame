package com.example.arno.festemberball_game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private int FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamepanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamepanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamepanel = gamepanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        //loop mechanism
        //loop should run 30 times per second;
        //if time taken to run the loop is t sec,t equals 1000/FPS;
        //actual loop run time is less,to achieve the target time,the thread should wait.
        //wait time equals actual time taken subtracted from target time ;

        while (running) {

            startTime = System.nanoTime();
            canvas = null;

            //locking the canvas;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamepanel.update();
                    this.gamepanel.draw(canvas);

                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;//time taken to run the loop once;
            waitTime = targetTime - timeMillis;
            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;//represents the no of times the loop has executed;
            if (frameCount == 30) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.v("AVERAGE FPS", String.valueOf(averageFPS));
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
