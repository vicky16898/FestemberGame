package com.example.arno.festemberball_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class MainThread extends Thread {
    private int FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamepanel;
    private boolean running;
    public static Canvas canvas;

    private ArrayList<Particle> mParticleList = new ArrayList<Particle>();
    private ArrayList<Particle> mRecycleList = new ArrayList<Particle>();
    private Bitmap mImage[] = new Bitmap[3];
    private Paint mPaint;
    private int mCanvasWidth;
    private int mCanvasHeight;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamepanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamepanel = gamepanel;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mImage[0] = ((BitmapDrawable) gamepanel.getResources().getDrawable(R.drawable.yellow_spark)).getBitmap();
        mImage[1] = ((BitmapDrawable) gamepanel.getResources().getDrawable(R.drawable.blue_spark)).getBitmap();
        mImage[2] = ((BitmapDrawable) gamepanel.getResources().getDrawable(R.drawable.red_spark)).getBitmap();

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

                    this.gamepanel.draw(canvas);
                    this.gamepanel.update();
                    doDraw(canvas);


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

    private void doDraw(Canvas c) {

        synchronized (mParticleList) {
            for (int i = 0; i < mParticleList.size(); i++) {
                Particle p = mParticleList.get(i);
                p.move();
                //c.drawBitmap(mImage[p.color], p.x - 10, p.y - 10, null);
                c.drawCircle(p.x, p.y, 5, mPaint);
                if (p.x < 0 || p.x > mCanvasWidth || p.y < 0 || p.y > mCanvasHeight) {
                    mRecycleList.add(mParticleList.remove(i));
                    i--;
                }
            }
        }
    }


    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList getParticleList() {
        return mParticleList;
    }

    public ArrayList getRecycleList() {
        return mRecycleList;
    }

    public void setSurfaceSize(int width, int height) {
        mCanvasWidth = width;
        mCanvasHeight = height;
    }

}
