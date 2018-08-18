package com.example.arno.festemberball_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private BackGround bg;
    public final static int moveSpeed = -5;
    public int WIDTH;
    public int HEIGHT;
    //player
    float user_x;
    float user_y;
    float tempX;
    float tempY;

    public static int ballSpeed = 0;

    private UserBall userBall;


    public GamePanel(Context context) {
        super(context);
        //add the callback to SurfaceHolder to intercept events;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        //make game panel focusable so it can handle events;
        setFocusable(true);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        bg.draw(canvas);
        userBall.draw(canvas);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        user_x = event.getX();
        user_y = event.getY();
        Log.d("user-x", String.valueOf(user_x));
        Log.d("user-y", String.valueOf(user_y));
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                tempX = user_x;
                tempY = user_y;
                Log.d("initial coordinate", String.valueOf(tempX) + "\n" + String.valueOf(tempY));
                return true;

            case MotionEvent.ACTION_MOVE:
                if (tempX < user_x) {
                    tempX = user_x;
                    ballSpeed = -4;

                } else if (tempX > user_x) {
                    tempX = user_x;
                    ballSpeed = 4;
                }
                return true;

            case MotionEvent.ACTION_UP:
                    ballSpeed = 0;
                return true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new MainThread(getHolder(), this);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        WIDTH = bitmap.getWidth();
        HEIGHT = bitmap.getHeight();
        bg = new BackGround(bitmap, this);
        userBall = new UserBall(this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            Log.d("stopping the program", String.valueOf(retry));
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }


    public void update() {

        bg.update();
        userBall.update();
    }

}
