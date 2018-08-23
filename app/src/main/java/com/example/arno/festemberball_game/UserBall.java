package com.example.arno.festemberball_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import static com.example.arno.festemberball_game.GamePanel.ballSpeed;

public class UserBall extends GameObject {
    private GamePanel gamePanel;
    private double theta;
    private int width_;
    private int height_;




    public UserBall(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        theta = Math.toRadians(90);
        width_ = gamePanel.getWidth();
        height_ = gamePanel.getHeight();
        x = -1;
        y = -1;
        radius = 30;
    }

    public void update() {

        theta = Math.toDegrees(theta);
        theta = theta + ballSpeed;
        theta = Math.toRadians(theta);
        if(radius > 5) {
            radius = radius - radius / 1000;
        }

        Log.d("radius of the ball", String.valueOf(radius));
        ballSpeed = 0;



    }

    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        if (x < 0 && y < 0) {

            Log.d("initial theta", String.valueOf(theta));
            x = width_ / 2;
            y = width_ / 3 + height_ / 2;
            canvas.drawCircle(x, y, 5, paint);


        } else {

            x = (int) (width_ / 2 + width_ / 3 * (Math.cos(theta)));
            Log.d("x co", String.valueOf(x));
            y = (int) (height_ / 2 + width_ / 3 * (Math.sin(theta)));
            Log.d("y co", String.valueOf(y));

        }


        canvas.drawCircle(x, y, (float) radius, paint);
        Particle p;
        int recycleCount = 0;


        if (GamePanel.mRecycleList.size() > 1) {
            recycleCount = 2;
        } else {
            recycleCount = GamePanel.mRecycleList.size();
        }

        for (int i = 0; i < recycleCount; i++) {
            p = GamePanel.mRecycleList.remove(0);
            p.init(x , y);
            GamePanel.mParticleList.add(p);
        }

        for (int i = 0; i < 1 - recycleCount; i++){
            GamePanel.mParticleList.add(new Particle(x , y));
        }



    }





}
