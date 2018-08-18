package com.example.arno.festemberball_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

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
        radius = 35;
    }

    public void update() {

                theta = Math.toDegrees(theta);
                theta = theta + ballSpeed;
                theta = Math.toRadians(theta);
                ballSpeed = 0;




    }

    public void draw(Canvas canvas) {

        if (x < 0 && y < 0) {
            Log.d("initial theta", String.valueOf(theta));
            x = width_ / 2;
            y = width_ / 3 + height_ / 2;
        } else {
            x = (int) (width_ / 2 + width_ / 3 * (Math.cos(theta)));
            Log.d("x co", String.valueOf(x));
            y = (int) (height_ / 2 + width_ / 3 * (Math.sin(theta)));
            Log.d("y co", String.valueOf(y));
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radius, paint);

    }
}
